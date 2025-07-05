package com.tienda.infraestructura;

import com.tienda.dominio.OrdenCompra;
import com.tienda.dominio.ServicioNotificacion;
import com.tienda.dominio.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio de notificación compuesto que maneja múltiples canales
 */
public class ServicioNotificacionCompuesto implements ServicioNotificacion {

    private final List<ServicioNotificacion> serviciosNotificacion;

    public ServicioNotificacionCompuesto() {
        this.serviciosNotificacion = new ArrayList<>();
    }

    /**
     * Agrega un servicio de notificación
     * @param servicio servicio a agregar
     */
    public void agregarServicio(ServicioNotificacion servicio) {
        this.serviciosNotificacion.add(servicio);
    }

    @Override
    public void notificarNuevaOrden(OrdenCompra orden) {
        for (ServicioNotificacion servicio : serviciosNotificacion) {
            try {
                servicio.notificarNuevaOrden(orden);
            } catch (Exception e) {
                System.err.println("Error al enviar notificación de nueva orden: " + e.getMessage());
            }
        }
    }

    @Override
    public void notificarCambioEstado(OrdenCompra orden, OrdenCompra.EstadoOrden estadoAnterior) {
        for (ServicioNotificacion servicio : serviciosNotificacion) {
            try {
                servicio.notificarCambioEstado(orden, estadoAnterior);
            } catch (Exception e) {
                System.err.println("Error al enviar notificación de cambio de estado: " + e.getMessage());
            }
        }
    }

    @Override
    public void notificarMensaje(Usuario destinatario, String asunto, String mensaje) {
        for (ServicioNotificacion servicio : serviciosNotificacion) {
            try {
                servicio.notificarMensaje(destinatario, asunto, mensaje);
            } catch (Exception e) {
                System.err.println("Error al enviar mensaje personalizado: " + e.getMessage());
            }
        }
    }
}