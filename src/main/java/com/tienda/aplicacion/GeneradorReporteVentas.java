package com.tienda.aplicacion;

import com.tienda.dominio.OrdenCompra;
import com.tienda.dominio.RepositorioOrdenCompra;
import com.tienda.compartido.ExcepcionNegocio;
import com.tienda.compartido.MensajesSistema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Servicio de aplicación para la generación de reportes de ventas
 */
public class GeneradorReporteVentas {
    
    private final RepositorioOrdenCompra repositorioOrden;
    
    public GeneradorReporteVentas(RepositorioOrdenCompra repositorioOrden) {
        this.repositorioOrden = repositorioOrden;
    }
    
    /**
     * Genera un reporte de ventas para un período determinado
     * @param fechaInicio fecha de inicio del período
     * @param fechaFin fecha de fin del período
     * @return reporte de ventas
     */
    public ReporteVentas generarReporteVentas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            throw new ExcepcionNegocio("Las fechas de inicio y fin son requeridas");
        }
        
        if (fechaInicio.isAfter(fechaFin)) {
            throw new ExcepcionNegocio("La fecha de inicio no puede ser posterior a la fecha de fin");
        }
        
        List<OrdenCompra> ordenes = repositorioOrden.obtenerPorRangoFechas(fechaInicio, fechaFin);
        
        // Filtrar solo órdenes completadas
        List<OrdenCompra> ordenesCompletadas = ordenes.stream()
                .filter(OrdenCompra::estaCompletada)
                .collect(Collectors.toList());
        
        return new ReporteVentas(fechaInicio, fechaFin, ordenesCompletadas);
    }
    
    /**
     * Genera un reporte de ventas del día actual
     * @return reporte de ventas del día
     */
    public ReporteVentas generarReporteVentasHoy() {
        LocalDateTime inicioDia = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime finDia = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        
        return generarReporteVentas(inicioDia, finDia);
    }
    
    /**
     * Genera un reporte de ventas del mes actual
     * @return reporte de ventas del mes
     */
    public ReporteVentas generarReporteVentasMes() {
        LocalDateTime inicioMes = LocalDateTime.now()
                .withDayOfMonth(1)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        
        LocalDateTime finMes = LocalDateTime.now()
                .withDayOfMonth(LocalDateTime.now().toLocalDate().lengthOfMonth())
                .withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999999999);
        
        return generarReporteVentas(inicioMes, finMes);
    }
    
    /**
     * Clase interna que representa un reporte de ventas
     */
    public static class ReporteVentas {
        private final LocalDateTime fechaInicio;
        private final LocalDateTime fechaFin;
        private final List<OrdenCompra> ordenes;
        private final BigDecimal totalVentas;
        private final int totalOrdenes;
        private final Map<String, Integer> productosVendidos;
        private final Map<String, BigDecimal> volumenPorProducto;
        
        public ReporteVentas(LocalDateTime fechaInicio, LocalDateTime fechaFin, List<OrdenCompra> ordenes) {
            this.fechaInicio = fechaInicio;
            this.fechaFin = fechaFin;
            this.ordenes = ordenes;
            this.totalVentas = calcularTotalVentas();
            this.totalOrdenes = ordenes.size();
            this.productosVendidos = calcularProductosVendidos();
            this.volumenPorProducto = calcularVolumenPorProducto();
        }
        
        private BigDecimal calcularTotalVentas() {
            return ordenes.stream()
                    .map(OrdenCompra::getTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        
        private Map<String, Integer> calcularProductosVendidos() {
            return ordenes.stream()
                    .flatMap(orden -> orden.getItems().stream())
                    .collect(Collectors.groupingBy(
                            item -> item.getProducto().getNombre(),
                            Collectors.summingInt(item -> item.getCantidad())
                    ));
        }
        
        private Map<String, BigDecimal> calcularVolumenPorProducto() {
            return ordenes.stream()
                    .flatMap(orden -> orden.getItems().stream())
                    .collect(Collectors.groupingBy(
                            item -> item.getProducto().getNombre(),
                            Collectors.reducing(BigDecimal.ZERO, 
                                    item -> item.getPrecioUnitario().multiply(BigDecimal.valueOf(item.getCantidad())),
                                    BigDecimal::add)
                    ));
        }
        
        // Getters
        public LocalDateTime getFechaInicio() {
            return fechaInicio;
        }
        
        public LocalDateTime getFechaFin() {
            return fechaFin;
        }
        
        public List<OrdenCompra> getOrdenes() {
            return ordenes;
        }
        
        public BigDecimal getTotalVentas() {
            return totalVentas;
        }
        
        public int getTotalOrdenes() {
            return totalOrdenes;
        }
        
        public Map<String, Integer> getProductosVendidos() {
            return productosVendidos;
        }
        
        public Map<String, BigDecimal> getVolumenPorProducto() {
            return volumenPorProducto;
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("=== REPORTE DE VENTAS ===\n");
            sb.append("Período: ").append(fechaInicio).append(" - ").append(fechaFin).append("\n");
            sb.append("Total de órdenes: ").append(totalOrdenes).append("\n");
            sb.append("Total de ventas: $").append(totalVentas).append("\n\n");
            
            sb.append("Productos vendidos:\n");
            productosVendidos.forEach((producto, cantidad) -> 
                sb.append("- ").append(producto).append(": ").append(cantidad).append(" unidades\n"));
            
            sb.append("\nVolumen por producto:\n");
            volumenPorProducto.forEach((producto, volumen) -> 
                sb.append("- ").append(producto).append(": $").append(volumen).append("\n"));
            
            return sb.toString();
        }
    }
} 