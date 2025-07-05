package com.tienda.infraestructura;

import com.tienda.dominio.OrdenCompra;
import com.tienda.dominio.ServicioNotificacion;
import com.tienda.dominio.Usuario;

/**
 * Implementación de notificación por correo electrónico
 */
public class NotificacionEmail implements ServicioNotificacion {
    
    @Override
    public void notificarNuevaOrden(OrdenCompra orden) {
        Usuario usuario = orden.getUsuario();
        String asunto = "Nueva orden de compra creada";
        String mensaje = generarMensajeNuevaOrden(orden);
        
        enviarEmail(usuario.getEmail(), asunto, mensaje);
    }
    
    @Override
    public void notificarCambioEstado(OrdenCompra orden, OrdenCompra.EstadoOrden estadoAnterior) {
        Usuario usuario = orden.getUsuario();
        String asunto = "Estado de orden actualizado";
        String mensaje = generarMensajeCambioEstado(orden, estadoAnterior);
        
        enviarEmail(usuario.getEmail(), asunto, mensaje);
    }
    
    @Override
    public void notificarMensaje(Usuario destinatario, String asunto, String mensaje) {
        enviarEmail(destinatario.getEmail(), asunto, mensaje);
    }
    
    /**
     * Genera el mensaje para una nueva orden
     * @param orden orden creada
     * @return mensaje formateado
     */
    private String generarMensajeNuevaOrden(OrdenCompra orden) {
        StringBuilder sb = new StringBuilder();
        sb.append("Hola ").append(orden.getUsuario().getNombre()).append(",\n\n");
        sb.append("Tu orden de compra ha sido creada exitosamente.\n");
        sb.append("Detalles de la orden:\n");
        sb.append("- ID de orden: ").append(orden.getId()).append("\n");
        sb.append("- Fecha: ").append(orden.getFechaCreacion()).append("\n");
        sb.append("- Estado: ").append(orden.getEstado()).append("\n");
        sb.append("- Total: $").append(orden.getTotal()).append("\n\n");
        
        sb.append("Productos:\n");
        orden.getItems().forEach(item -> 
            sb.append("- ").append(item.getProducto().getNombre())
              .append(" x").append(item.getCantidad())
              .append(" = $").append(item.getSubtotal()).append("\n"));
        
        sb.append("\nGracias por tu compra!");
        
        return sb.toString();
    }
    
    /**
     * Genera el mensaje para cambio de estado
     * @param orden orden con estado cambiado
     * @param estadoAnterior estado anterior
     * @return mensaje formateado
     */
    private String generarMensajeCambioEstado(OrdenCompra orden, OrdenCompra.EstadoOrden estadoAnterior) {
        StringBuilder sb = new StringBuilder();
        sb.append("Hola ").append(orden.getUsuario().getNombre()).append(",\n\n");
        sb.append("El estado de tu orden ha cambiado.\n");
        sb.append("Detalles:\n");
        sb.append("- ID de orden: ").append(orden.getId()).append("\n");
        sb.append("- Estado anterior: ").append(estadoAnterior).append("\n");
        sb.append("- Estado actual: ").append(orden.getEstado()).append("\n");
        sb.append("- Fecha de actualización: ").append(orden.getFechaActualizacion()).append("\n\n");
        
        if (orden.getEstado() == OrdenCompra.EstadoOrden.COMPLETADA) {
            sb.append("¡Tu orden ha sido completada! Gracias por tu compra.\n");
        } else if (orden.getEstado() == OrdenCompra.EstadoOrden.CANCELADA) {
            sb.append("Tu orden ha sido cancelada. Si tienes alguna pregunta, contáctanos.\n");
        }
        
        return sb.toString();
    }
    
    /**
     * Simula el envío de un email
     * @param email dirección de email
     * @param asunto asunto del email
     * @param mensaje contenido del email
     */
    private void enviarEmail(String email, String asunto, String mensaje) {
        // En una implementación real, aquí se integraría con un servicio de email
        // como JavaMail, SendGrid, AWS SES, etc.
        System.out.println("=== EMAIL ENVIADO ===");
        System.out.println("Para: " + email);
        System.out.println("Asunto: " + asunto);
        System.out.println("Mensaje: " + mensaje);
        System.out.println("=====================");
    }
} 