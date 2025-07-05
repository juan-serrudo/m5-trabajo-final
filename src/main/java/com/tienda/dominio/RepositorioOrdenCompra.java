package com.tienda.dominio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interfaz del repositorio de órdenes de compra
 */
public interface RepositorioOrdenCompra {
    
    /**
     * Guarda una orden de compra
     * @param orden orden a guardar
     * @return orden guardada
     */
    OrdenCompra guardar(OrdenCompra orden);
    
    /**
     * Busca una orden por su ID
     * @param id ID de la orden
     * @return Optional con la orden si existe
     */
    Optional<OrdenCompra> buscarPorId(UUID id);
    
    /**
     * Obtiene todas las órdenes de un usuario
     * @param usuarioId ID del usuario
     * @return lista de órdenes del usuario
     */
    List<OrdenCompra> obtenerPorUsuario(UUID usuarioId);
    
    /**
     * Obtiene todas las órdenes
     * @return lista de todas las órdenes
     */
    List<OrdenCompra> obtenerTodas();
    
    /**
     * Obtiene órdenes por estado
     * @param estado estado de las órdenes
     * @return lista de órdenes con el estado especificado
     */
    List<OrdenCompra> obtenerPorEstado(OrdenCompra.EstadoOrden estado);
    
    /**
     * Obtiene órdenes en un rango de fechas
     * @param fechaInicio fecha de inicio
     * @param fechaFin fecha de fin
     * @return lista de órdenes en el rango
     */
    List<OrdenCompra> obtenerPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    /**
     * Elimina una orden
     * @param id ID de la orden a eliminar
     * @return true si se eliminó correctamente
     */
    boolean eliminar(UUID id);
    
    /**
     * Verifica si una orden existe
     * @param id ID de la orden
     * @return true si existe
     */
    boolean existe(UUID id);
} 