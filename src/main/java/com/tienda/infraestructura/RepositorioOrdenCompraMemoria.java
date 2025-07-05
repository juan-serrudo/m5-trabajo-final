package com.tienda.infraestructura;

import com.tienda.dominio.OrdenCompra;
import com.tienda.dominio.RepositorioOrdenCompra;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Implementación en memoria del repositorio de órdenes de compra
 */
public class RepositorioOrdenCompraMemoria implements RepositorioOrdenCompra {
    
    private final Map<UUID, OrdenCompra> ordenes = new ConcurrentHashMap<>();
    
    @Override
    public OrdenCompra guardar(OrdenCompra orden) {
        if (orden.getId() == null) {
            orden.setId(UUID.randomUUID());
        }
        ordenes.put(orden.getId(), orden);
        return orden;
    }
    
    @Override
    public Optional<OrdenCompra> buscarPorId(UUID id) {
        return Optional.ofNullable(ordenes.get(id));
    }
    
    @Override
    public List<OrdenCompra> obtenerPorUsuario(UUID usuarioId) {
        return ordenes.values().stream()
                .filter(orden -> orden.getUsuario().getId().equals(usuarioId))
                .sorted(Comparator.comparing(OrdenCompra::getFechaCreacion).reversed())
                .collect(Collectors.toList());
    }
    
    @Override
    public List<OrdenCompra> obtenerTodas() {
        return new ArrayList<>(ordenes.values());
    }
    
    @Override
    public List<OrdenCompra> obtenerPorEstado(OrdenCompra.EstadoOrden estado) {
        return ordenes.values().stream()
                .filter(orden -> orden.getEstado() == estado)
                .sorted(Comparator.comparing(OrdenCompra::getFechaCreacion).reversed())
                .collect(Collectors.toList());
    }
    
    @Override
    public List<OrdenCompra> obtenerPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return ordenes.values().stream()
                .filter(orden -> {
                    LocalDateTime fechaOrden = orden.getFechaCreacion();
                    return !fechaOrden.isBefore(fechaInicio) && !fechaOrden.isAfter(fechaFin);
                })
                .sorted(Comparator.comparing(OrdenCompra::getFechaCreacion).reversed())
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean eliminar(UUID id) {
        return ordenes.remove(id) != null;
    }
    
    @Override
    public boolean existe(UUID id) {
        return ordenes.containsKey(id);
    }
} 