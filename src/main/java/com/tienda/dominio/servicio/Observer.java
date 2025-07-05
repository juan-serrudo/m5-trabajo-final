package com.tienda.dominio.servicio;

import com.tienda.dominio.modelo.OrdenCompra;

/**
 * Interfaz Observer del patrón Observer
 * Define el contrato para objetos que observan cambios en órdenes
 */
public interface Observer {
    
    /**
     * Método llamado cuando se actualiza el estado de una orden
     * @param orden orden que ha cambiado
     * @param evento tipo de evento que ocurrió
     */
    void actualizar(OrdenCompra orden, String evento);
    
    /**
     * Obtiene el identificador del observer
     * @return identificador único del observer
     */
    String getIdentificador();
} 