package com.tienda.dominio.servicio;

import com.tienda.dominio.modelo.OrdenCompra;
import com.tienda.dominio.modelo.Usuario;

/**
 * Interfaz del servicio de notificaciones
 */
public interface ServicioNotificacion {
    
    /**
     * Notifica la creación de una nueva orden
     * @param orden orden creada
     */
    void notificarNuevaOrden(OrdenCompra orden);
    
    /**
     * Notifica el cambio de estado de una orden
     * @param orden orden con estado cambiado
     * @param estadoAnterior estado anterior de la orden
     */
    void notificarCambioEstado(OrdenCompra orden, com.tienda.dominio.modelo.EstadoOrden estadoAnterior);
    
    /**
     * Notifica un mensaje personalizado
     * @param destinatario usuario destinatario
     * @param asunto asunto del mensaje
     * @param mensaje contenido del mensaje
     */
    void notificarMensaje(Usuario destinatario, String asunto, String mensaje);
} 