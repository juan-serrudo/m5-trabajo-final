package com.tienda.dominio.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Representa el inventario general de productos
 * Implementa el patrón Singleton para garantizar una única instancia
 */
public class Inventario {
    private static Inventario instancia;
    private List<Producto> productos;

    /**
     * Constructor privado para implementar Singleton
     */
    private Inventario() {
        this.productos = new ArrayList<>();
    }
    
    /**
     * Obtiene la instancia única del inventario
     * @return instancia del inventario
     */
    public static Inventario getInstancia() {
        if (instancia == null) {
            instancia = new Inventario();
        }
        return instancia;
    }

    /**
     * Añade un producto al inventario
     * @param producto producto a añadir
     */
    public void añadirProducto(Producto producto) {
        if (producto != null && !productos.contains(producto)) {
            productos.add(producto);
        }
    }

    /**
     * Verifica la disponibilidad de stock para un producto
     * @param productoId ID del producto
     * @param cantidad cantidad requerida
     * @return true si hay stock suficiente
     */
    public boolean verificarDisponibilidadStock(String productoId, int cantidad) {
        Optional<Producto> producto = productos.stream()
                .filter(p -> p.getId().toString().equals(productoId))
                .findFirst();
        
        return producto.isPresent() && producto.get().tieneStockSuficiente(cantidad);
    }

    /**
     * Actualiza el stock de un producto
     * @param productoId ID del producto
     * @param nuevaCantidad nueva cantidad en stock
     * @return true si se actualizó correctamente
     */
    public boolean actualizarStockProducto(String productoId, int nuevaCantidad) {
        Optional<Producto> producto = productos.stream()
                .filter(p -> p.getId().toString().equals(productoId))
                .findFirst();
        
        if (producto.isPresent()) {
            producto.get().setStockDisponible(nuevaCantidad);
            return true;
        }
        return false;
    }

    /**
     * Obtiene un producto por ID
     * @param productoId ID del producto
     * @return Optional con el producto si existe
     */
    public Optional<Producto> obtenerProducto(String productoId) {
        return productos.stream()
                .filter(p -> p.getId().toString().equals(productoId))
                .findFirst();
    }

    /**
     * Obtiene todos los productos del inventario
     * @return lista de productos
     */
    public List<Producto> obtenerProductos() {
        return new ArrayList<>(productos);
    }

    /**
     * Obtiene productos con stock disponible
     * @return lista de productos con stock > 0
     */
    public List<Producto> obtenerProductosDisponibles() {
        return productos.stream()
                .filter(p -> p.getStockDisponible() > 0 && p.isActivo())
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    /**
     * Elimina un producto del inventario
     * @param productoId ID del producto a eliminar
     * @return true si se eliminó correctamente
     */
    public boolean eliminarProducto(String productoId) {
        return productos.removeIf(p -> p.getId().toString().equals(productoId));
    }
} 