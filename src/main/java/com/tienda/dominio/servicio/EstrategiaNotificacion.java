package com.tienda.dominio.servicio;

import com.tienda.dominio.modelo.Usuario;

/**
 * Define el contrato para enviar notificaciones a los usuarios
 */
public interface EstrategiaNotificacion {
    
    /**
     * Envía una notificación al usuario
     * @param mensaje mensaje a enviar
     * @param usuario usuario destinatario
     * @return true si se envió correctamente
     */
    boolean enviar(String mensaje, Usuario usuario);
    
    /**
     * Obtiene el nombre de la estrategia de notificación
     * @return nombre de la estrategia
     */
    String getNombreEstrategia();
} 