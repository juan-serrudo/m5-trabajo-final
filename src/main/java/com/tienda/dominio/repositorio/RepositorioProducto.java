package com.tienda.dominio.repositorio;

import com.tienda.dominio.modelo.Producto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interfaz del repositorio de productos
 */
public interface RepositorioProducto {
    
    /**
     * Guarda un producto
     * @param producto producto a guardar
     * @return producto guardado
     */
    Producto guardar(Producto producto);
    
    /**
     * Busca un producto por su ID
     * @param id ID del producto
     * @return Optional con el producto si existe
     */
    Optional<Producto> buscarPorId(UUID id);
    
    /**
     * Obtiene todos los productos activos
     * @return lista de productos activos
     */
    List<Producto> obtenerTodosActivos();
    
    /**
     * Obtiene todos los productos
     * @return lista de todos los productos
     */
    List<Producto> obtenerTodos();
    
    /**
     * Elimina un producto
     * @param id ID del producto a eliminar
     * @return true si se eliminó correctamente
     */
    boolean eliminar(UUID id);
    
    /**
     * Verifica si un producto existe
     * @param id ID del producto
     * @return true si existe
     */
    boolean existe(UUID id);
    
    /**
     * Busca productos por nombre
     * @param nombre nombre o parte del nombre
     * @return lista de productos que coinciden
     */
    List<Producto> buscarPorNombre(String nombre);
} 