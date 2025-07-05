package com.tienda.infraestructura.notificacion;

import com.tienda.dominio.servicio.EstrategiaNotificacion;

/**
 * Factory para crear diferentes tipos de estrategias de notificación
 * Implementa el patrón Factory Method
 */
public class NotificacionFactory {
    
    /**
     * Crea una estrategia de notificación según el tipo especificado
     * @param tipo tipo de notificación (email, whatsapp, telegram)
     * @return estrategia de notificación
     * @throws IllegalArgumentException si el tipo no es soportado
     */
    public static EstrategiaNotificacion crearNotificacion(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de notificación no puede ser nulo o vacío");
        }
        
        switch (tipo.toLowerCase().trim()) {
            case "email":
                return new NotificacionEmail();
            case "whatsapp":
                return new NotificacionWhatsApp();
            case "telegram":
                return new NotificacionTelegram();
            default:
                throw new IllegalArgumentException("Tipo de notificación no soportado: " + tipo);
        }
    }
    
    /**
     * Crea múltiples estrategias de notificación
     * @param tipos lista de tipos de notificación
     * @return array de estrategias de notificación
     */
    public static EstrategiaNotificacion[] crearNotificaciones(String... tipos) {
        if (tipos == null || tipos.length == 0) {
            throw new IllegalArgumentException("Debe especificar al menos un tipo de notificación");
        }
        
        EstrategiaNotificacion[] estrategias = new EstrategiaNotificacion[tipos.length];
        for (int i = 0; i < tipos.length; i++) {
            estrategias[i] = crearNotificacion(tipos[i]);
        }
        return estrategias;
    }
    
    /**
     * Verifica si un tipo de notificación es soportado
     * @param tipo tipo de notificación
     * @return true si es soportado
     */
    public static boolean esTipoSoportado(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            return false;
        }
        
        String tipoLower = tipo.toLowerCase().trim();
        return tipoLower.equals("email") || 
               tipoLower.equals("whatsapp") || 
               tipoLower.equals("telegram");
    }
    
    /**
     * Obtiene los tipos de notificación soportados
     * @return array con los tipos soportados
     */
    public static String[] getTiposSoportados() {
        return new String[]{"email", "whatsapp", "telegram"};
    }
} 