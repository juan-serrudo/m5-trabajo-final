package com.tienda.infraestructura.persistencia;

import com.tienda.dominio.modelo.Producto;
import com.tienda.dominio.repositorio.RepositorioProducto;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Implementación en memoria del repositorio de productos
 */
public class RepositorioProductoMemoria implements RepositorioProducto {
    
    private final Map<UUID, Producto> productos = new ConcurrentHashMap<>();
    
    @Override
    public Producto guardar(Producto producto) {
        if (producto.getId() == null) {
            producto.setId(UUID.randomUUID());
        }
        productos.put(producto.getId(), producto);
        return producto;
    }
    
    @Override
    public Optional<Producto> buscarPorId(UUID id) {
        return Optional.ofNullable(productos.get(id));
    }
    
    @Override
    public List<Producto> obtenerTodosActivos() {
        return productos.values().stream()
                .filter(Producto::isActivo)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Producto> obtenerTodos() {
        return new ArrayList<>(productos.values());
    }
    
    @Override
    public boolean eliminar(UUID id) {
        Producto producto = productos.get(id);
        if (producto != null) {
            producto.setActivo(false);
            productos.put(id, producto);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean existe(UUID id) {
        return productos.containsKey(id);
    }
    
    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        String nombreLower = nombre.toLowerCase();
        return productos.values().stream()
                .filter(producto -> producto.isActivo() && 
                        producto.getNombre().toLowerCase().contains(nombreLower))
                .collect(Collectors.toList());
    }
} 