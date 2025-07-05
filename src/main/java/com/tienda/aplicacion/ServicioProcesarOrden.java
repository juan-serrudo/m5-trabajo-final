package com.tienda.aplicacion;

import com.tienda.dominio.*;
import com.tienda.compartido.ExcepcionNegocio;
import com.tienda.compartido.MensajesSistema;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Servicio de aplicación para el procesamiento de órdenes de compra
 */
public class ServicioProcesarOrden {
    
    private final RepositorioOrdenCompra repositorioOrden;
    private final RepositorioProducto repositorioProducto;
    private final RepositorioUsuario repositorioUsuario;
    private final ServicioNotificacion servicioNotificacion;
    
    public ServicioProcesarOrden(RepositorioOrdenCompra repositorioOrden,
                                RepositorioProducto repositorioProducto,
                                RepositorioUsuario repositorioUsuario,
                                ServicioNotificacion servicioNotificacion) {
        this.repositorioOrden = repositorioOrden;
        this.repositorioProducto = repositorioProducto;
        this.repositorioUsuario = repositorioUsuario;
        this.servicioNotificacion = servicioNotificacion;
    }
    
    /**
     * Crea una nueva orden de compra
     * @param usuarioId ID del usuario
     * @param items lista de items de la orden
     * @return orden creada
     */
    public OrdenCompra crearOrden(UUID usuarioId, List<ItemOrden> items) {
        if (items == null || items.isEmpty()) {
            throw new ExcepcionNegocio(MensajesSistema.ORDEN_SIN_ITEMS);
        }
        
        Usuario usuario = repositorioUsuario.buscarPorId(usuarioId)
                .orElseThrow(() -> new ExcepcionNegocio(MensajesSistema.USUARIO_NO_ENCONTRADO));
        
        // Validar stock de todos los productos
        validarStockDisponible(items);
        
        OrdenCompra orden = new OrdenCompra(usuario);
        
        // Agregar items a la orden
        for (ItemOrden item : items) {
            orden.agregarItem(item);
        }
        
        // Descontar stock de los productos
        descontarStockProductos(items);
        
        // Guardar la orden
        OrdenCompra ordenGuardada = repositorioOrden.guardar(orden);
        
        // Agregar la orden al historial del usuario
        usuario.agregarOrdenAlHistorial(ordenGuardada);
        repositorioUsuario.guardar(usuario);
        
        // Notificar la creación de la orden
        servicioNotificacion.notificarNuevaOrden(ordenGuardada);
        
        return ordenGuardada;
    }
    
    /**
     * Obtiene una orden por ID
     * @param ordenId ID de la orden
     * @return Optional con la orden si existe
     */
    public Optional<OrdenCompra> obtenerOrden(UUID ordenId) {
        return repositorioOrden.buscarPorId(ordenId);
    }
    
    /**
     * Obtiene todas las órdenes de un usuario
     * @param usuarioId ID del usuario
     * @return lista de órdenes del usuario
     */
    public List<OrdenCompra> obtenerOrdenesUsuario(UUID usuarioId) {
        return repositorioOrden.obtenerPorUsuario(usuarioId);
    }
    
    /**
     * Obtiene todas las órdenes
     * @return lista de todas las órdenes
     */
    public List<OrdenCompra> obtenerTodasLasOrdenes() {
        return repositorioOrden.obtenerTodas();
    }
    
    /**
     * Obtiene órdenes por estado
     * @param estado estado de las órdenes
     * @return lista de órdenes con el estado especificado
     */
    public List<OrdenCompra> obtenerOrdenesPorEstado(OrdenCompra.EstadoOrden estado) {
        return repositorioOrden.obtenerPorEstado(estado);
    }
    
    /**
     * Completa una orden
     * @param ordenId ID de la orden
     * @return orden completada
     */
    public OrdenCompra completarOrden(UUID ordenId) {
        OrdenCompra orden = repositorioOrden.buscarPorId(ordenId)
                .orElseThrow(() -> new ExcepcionNegocio(MensajesSistema.ORDEN_NO_ENCONTRADA));
        
        if (orden.estaCompletada()) {
            throw new ExcepcionNegocio(MensajesSistema.ORDEN_YA_COMPLETADA);
        }
        
        if (orden.estaCancelada()) {
            throw new ExcepcionNegocio(MensajesSistema.ORDEN_YA_CANCELADA);
        }
        
        OrdenCompra.EstadoOrden estadoAnterior = orden.getEstado();
        orden.completar();
        
        OrdenCompra ordenActualizada = repositorioOrden.guardar(orden);
        
        // Notificar el cambio de estado
        servicioNotificacion.notificarCambioEstado(ordenActualizada, estadoAnterior);
        
        return ordenActualizada;
    }
    
    /**
     * Cancela una orden
     * @param ordenId ID de la orden
     * @return orden cancelada
     */
    public OrdenCompra cancelarOrden(UUID ordenId) {
        OrdenCompra orden = repositorioOrden.buscarPorId(ordenId)
                .orElseThrow(() -> new ExcepcionNegocio(MensajesSistema.ORDEN_NO_ENCONTRADA));
        
        if (orden.estaCompletada()) {
            throw new ExcepcionNegocio(MensajesSistema.ORDEN_YA_COMPLETADA);
        }
        
        if (orden.estaCancelada()) {
            throw new ExcepcionNegocio(MensajesSistema.ORDEN_YA_CANCELADA);
        }
        
        OrdenCompra.EstadoOrden estadoAnterior = orden.getEstado();
        orden.cancelar();
        
        // Restaurar stock de los productos
        restaurarStockProductos(orden.getItems());
        
        OrdenCompra ordenActualizada = repositorioOrden.guardar(orden);
        
        // Notificar el cambio de estado
        servicioNotificacion.notificarCambioEstado(ordenActualizada, estadoAnterior);
        
        return ordenActualizada;
    }
    
    /**
     * Valida que haya stock suficiente para todos los items
     * @param items lista de items a validar
     */
    private void validarStockDisponible(List<ItemOrden> items) {
        for (ItemOrden item : items) {
            Producto producto = repositorioProducto.buscarPorId(item.getProducto().getId())
                    .orElseThrow(() -> new ExcepcionNegocio(MensajesSistema.PRODUCTO_NO_ENCONTRADO));
            
            if (!producto.tieneStockSuficiente(item.getCantidad())) {
                throw new ExcepcionNegocio(MensajesSistema.STOCK_INSUFICIENTE + ": " + producto.getNombre());
            }
        }
    }
    
    /**
     * Descuenta stock de los productos
     * @param items lista de items
     */
    private void descontarStockProductos(List<ItemOrden> items) {
        for (ItemOrden item : items) {
            Producto producto = repositorioProducto.buscarPorId(item.getProducto().getId())
                    .orElseThrow(() -> new ExcepcionNegocio(MensajesSistema.PRODUCTO_NO_ENCONTRADO));
            
            producto.descontarStock(item.getCantidad());
            repositorioProducto.guardar(producto);
        }
    }
    
    /**
     * Restaura stock de los productos
     * @param items lista de items
     */
    private void restaurarStockProductos(List<ItemOrden> items) {
        for (ItemOrden item : items) {
            Producto producto = repositorioProducto.buscarPorId(item.getProducto().getId())
                    .orElseThrow(() -> new ExcepcionNegocio(MensajesSistema.PRODUCTO_NO_ENCONTRADO));
            
            producto.agregarStock(item.getCantidad());
            repositorioProducto.guardar(producto);
        }
    }
} 