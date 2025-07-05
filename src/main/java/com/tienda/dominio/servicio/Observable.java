package com.tienda.dominio.servicio;

import java.util.List;

/**
 * Interfaz Observable del patrón Observer
 * Define el contrato para objetos que pueden ser observados
 */
public interface Observable {
    
    /**
     * Agrega un observer a la lista de observadores
     * @param observer observer a agregar
     */
    void agregarObserver(Observer observer);
    
    /**
     * Elimina un observer de la lista de observadores
     * @param observer observer a eliminar
     */
    void eliminarObserver(Observer observer);
    
    /**
     * Notifica a todos los observers sobre un evento
     * @param evento tipo de evento que ocurrió
     */
    void notificarObservers(String evento);
    
    /**
     * Obtiene la lista de observers registrados
     * @return lista de observers
     */
    List<Observer> getObservers();
} 