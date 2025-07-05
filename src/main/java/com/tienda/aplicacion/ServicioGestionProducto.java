package com.tienda.aplicacion;

import com.tienda.dominio.Producto;
import com.tienda.dominio.RepositorioProducto;
import com.tienda.compartido.ExcepcionNegocio;
import com.tienda.compartido.MensajesSistema;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Servicio de aplicación para la gestión de productos
 */
public class ServicioGestionProducto {
    
    private final RepositorioProducto repositorioProducto;
    
    public ServicioGestionProducto(RepositorioProducto repositorioProducto) {
        this.repositorioProducto = repositorioProducto;
    }
    
    /**
     * Registra un nuevo producto
     * @param nombre nombre del producto
     * @param descripcion descripción del producto
     * @param precio precio del producto
     * @param stockInicial stock inicial del producto
     * @return producto creado
     */
    public Producto registrarProducto(String nombre, String descripcion, BigDecimal precio, int stockInicial) {
        validarDatosProducto(nombre, precio, stockInicial);
        
        Producto producto = new Producto(nombre, descripcion, precio, stockInicial);
        return repositorioProducto.guardar(producto);
    }
    
    /**
     * Obtiene el catálogo de productos activos
     * @return lista de productos activos
     */
    public List<Producto> obtenerCatalogo() {
        return repositorioProducto.obtenerTodosActivos();
    }
    
    /**
     * Obtiene todos los productos
     * @return lista de todos los productos
     */
    public List<Producto> obtenerTodosLosProductos() {
        return repositorioProducto.obtenerTodos();
    }
    
    /**
     * Busca un producto por ID
     * @param id ID del producto
     * @return Optional con el producto si existe
     */
    public Optional<Producto> buscarProductoPorId(UUID id) {
        return repositorioProducto.buscarPorId(id);
    }
    
    /**
     * Busca productos por nombre
     * @param nombre nombre o parte del nombre
     * @return lista de productos que coinciden
     */
    public List<Producto> buscarProductosPorNombre(String nombre) {
        return repositorioProducto.buscarPorNombre(nombre);
    }
    
    /**
     * Modifica la información de un producto
     * @param id ID del producto
     * @param nombre nuevo nombre
     * @param descripcion nueva descripción
     * @param precio nuevo precio
     * @return producto modificado
     */
    public Producto modificarProducto(UUID id, String nombre, String descripcion, BigDecimal precio) {
        validarDatosProducto(nombre, precio, 0);
        
        Producto producto = repositorioProducto.buscarPorId(id)
                .orElseThrow(() -> new ExcepcionNegocio(MensajesSistema.PRODUCTO_NO_ENCONTRADO));
        
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        
        return repositorioProducto.guardar(producto);
    }
    
    /**
     * Actualiza el stock de un producto
     * @param id ID del producto
     * @param nuevaCantidad nueva cantidad en stock
     * @return producto actualizado
     */
    public Producto actualizarStock(UUID id, int nuevaCantidad) {
        if (nuevaCantidad < 0) {
            throw new ExcepcionNegocio(MensajesSistema.CANTIDAD_INVALIDA);
        }
        
        Producto producto = repositorioProducto.buscarPorId(id)
                .orElseThrow(() -> new ExcepcionNegocio(MensajesSistema.PRODUCTO_NO_ENCONTRADO));
        
        producto.setStockDisponible(nuevaCantidad);
        return repositorioProducto.guardar(producto);
    }
    
    /**
     * Elimina un producto
     * @param id ID del producto a eliminar
     * @return true si se eliminó correctamente
     */
    public boolean eliminarProducto(UUID id) {
        Producto producto = repositorioProducto.buscarPorId(id)
                .orElseThrow(() -> new ExcepcionNegocio(MensajesSistema.PRODUCTO_NO_ENCONTRADO));
        
        // Aquí se podría agregar validación para verificar si el producto tiene órdenes asociadas
        // Por simplicidad, solo se marca como inactivo
        producto.setActivo(false);
        repositorioProducto.guardar(producto);
        
        return true;
    }
    
    /**
     * Valida los datos básicos de un producto
     * @param nombre nombre del producto
     * @param precio precio del producto
     * @param stock stock del producto
     */
    private void validarDatosProducto(String nombre, BigDecimal precio, int stock) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ExcepcionNegocio(MensajesSistema.NOMBRE_REQUERIDO);
        }
        
        if (precio == null || precio.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ExcepcionNegocio(MensajesSistema.PRECIO_INVALIDO);
        }
        
        if (stock < 0) {
            throw new ExcepcionNegocio(MensajesSistema.CANTIDAD_INVALIDA);
        }
    }
} 