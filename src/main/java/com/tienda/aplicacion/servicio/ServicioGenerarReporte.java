package com.tienda.aplicacion.servicio;

import com.tienda.dominio.modelo.OrdenCompra;
import com.tienda.dominio.modelo.Producto;
import com.tienda.dominio.repositorio.RepositorioOrdenCompra;
import com.tienda.dominio.repositorio.RepositorioProducto;
import com.tienda.compartido.excepcion.ExcepcionNegocio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Encargado de la generación de reportes de ventas
 */
public class ServicioGenerarReporte {
    
    private final RepositorioOrdenCompra repositorioOrdenCompra;
    private final RepositorioProducto repositorioProducto;
    
    public ServicioGenerarReporte(RepositorioOrdenCompra repositorioOrdenCompra, 
                                 RepositorioProducto repositorioProducto) {
        this.repositorioOrdenCompra = repositorioOrdenCompra;
        this.repositorioProducto = repositorioProducto;
    }
    
    /**
     * Genera un reporte de ventas por período
     * @param fechaInicio fecha de inicio del período
     * @param fechaFin fecha de fin del período
     * @return mapa con estadísticas del reporte
     */
    public Map<String, Object> generarReportePorPeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio.isAfter(fechaFin)) {
            throw new ExcepcionNegocio("La fecha de inicio no puede ser posterior a la fecha de fin");
        }
        
        List<OrdenCompra> ordenes = repositorioOrdenCompra.listar()
                .stream()
                .filter(orden -> {
                    LocalDate fechaOrden = orden.getFecha().toLocalDate();
                    return !fechaOrden.isBefore(fechaInicio) && !fechaOrden.isAfter(fechaFin);
                })
                .collect(Collectors.toList());
        
        Map<String, Object> reporte = new HashMap<>();
        reporte.put("fechaInicio", fechaInicio);
        reporte.put("fechaFin", fechaFin);
        reporte.put("totalOrdenes", ordenes.size());
        reporte.put("totalVentas", calcularTotalVentas(ordenes));
        reporte.put("ordenes", ordenes);
        
        return reporte;
    }
    
    /**
     * Obtiene los productos más vendidos
     * @return lista de productos ordenados por cantidad vendida
     */
    public List<Map<String, Object>> obtenerProductosMasVendidos() {
        List<OrdenCompra> ordenes = repositorioOrdenCompra.listar();
        Map<String, Integer> ventasPorProducto = new HashMap<>();
        
        // Contar ventas por producto
        for (OrdenCompra orden : ordenes) {
            for (var item : orden.getItems()) {
                String productoId = item.getProducto().getId().toString();
                ventasPorProducto.merge(productoId, item.getCantidad(), Integer::sum);
            }
        }
        
        // Crear lista de productos más vendidos
        return ventasPorProducto.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(entry -> {
                    Map<String, Object> productoVenta = new HashMap<>();
                    productoVenta.put("productoId", entry.getKey());
                    productoVenta.put("cantidadVendida", entry.getValue());
                    
                    // Obtener información del producto
                    repositorioProducto.obtenerPorId(entry.getKey())
                            .ifPresent(producto -> {
                                productoVenta.put("nombreProducto", producto.getNombre());
                                productoVenta.put("precioUnitario", producto.getPrecio());
                                productoVenta.put("totalVentas", producto.getPrecio() * entry.getValue());
                            });
                    
                    return productoVenta;
                })
                .collect(Collectors.toList());
    }
    
    /**
     * Calcula el total de ventas de una lista de órdenes
     * @param ordenes lista de órdenes
     * @return total de ventas
     */
    private double calcularTotalVentas(List<OrdenCompra> ordenes) {
        return ordenes.stream()
                .mapToDouble(OrdenCompra::calcularTotal)
                .sum();
    }
    
    /**
     * Genera un reporte de inventario
     * @return mapa con información del inventario
     */
    public Map<String, Object> generarReporteInventario() {
        List<Producto> productos = repositorioProducto.listar();
        
        Map<String, Object> reporte = new HashMap<>();
        reporte.put("totalProductos", productos.size());
        reporte.put("productosActivos", productos.stream().filter(Producto::isActivo).count());
        reporte.put("productosSinStock", productos.stream().filter(p -> p.getStockDisponible() == 0).count());
        reporte.put("valorTotalInventario", productos.stream()
                .mapToDouble(p -> p.getPrecio() * p.getStockDisponible())
                .sum());
        reporte.put("productos", productos);
        
        return reporte;
    }
} 