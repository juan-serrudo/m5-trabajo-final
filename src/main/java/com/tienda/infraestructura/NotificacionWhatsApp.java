package com.tienda.infraestructura;

import com.tienda.dominio.OrdenCompra;
import com.tienda.dominio.ServicioNotificacion;
import com.tienda.dominio.Usuario;

/**
 * Implementación de notificación por WhatsApp
 */
public class NotificacionWhatsApp implements ServicioNotificacion {
    
    @Override
    public void notificarNuevaOrden(OrdenCompra orden) {
        Usuario usuario = orden.getUsuario();
        
        if (!usuario.puedeRecibirWhatsApp()) {
            System.out.println("Usuario " + usuario.getNombre() + " no tiene teléfono registrado para WhatsApp");
            return;
        }
        
        String mensaje = generarMensajeNuevaOrden(orden);
        enviarWhatsApp(usuario.getTelefono(), mensaje);
    }
    
    @Override
    public void notificarCambioEstado(OrdenCompra orden, OrdenCompra.EstadoOrden estadoAnterior) {
        Usuario usuario = orden.getUsuario();
        
        if (!usuario.puedeRecibirWhatsApp()) {
            System.out.println("Usuario " + usuario.getNombre() + " no tiene teléfono registrado para WhatsApp");
            return;
        }
        
        String mensaje = generarMensajeCambioEstado(orden, estadoAnterior);
        enviarWhatsApp(usuario.getTelefono(), mensaje);
    }
    
    @Override
    public void notificarMensaje(Usuario destinatario, String asunto, String mensaje) {
        if (!destinatario.puedeRecibirWhatsApp()) {
            System.out.println("Usuario " + destinatario.getNombre() + " no tiene teléfono registrado para WhatsApp");
            return;
        }
        
        String mensajeCompleto = asunto + "\n\n" + mensaje;
        enviarWhatsApp(destinatario.getTelefono(), mensajeCompleto);
    }
    
    /**
     * Genera el mensaje para una nueva orden
     * @param orden orden creada
     * @return mensaje formateado
     */
    private String generarMensajeNuevaOrden(OrdenCompra orden) {
        StringBuilder sb = new StringBuilder();
        sb.append("🛒 *Nueva Orden Creada*\n\n");
        sb.append("Hola ").append(orden.getUsuario().getNombre()).append("!\n\n");
        sb.append("Tu orden ha sido creada exitosamente.\n\n");
        sb.append("*Detalles:*\n");
        sb.append("📋 ID: ").append(orden.getId()).append("\n");
        sb.append("📅 Fecha: ").append(orden.getFechaCreacion()).append("\n");
        sb.append("💰 Total: $").append(orden.getTotal()).append("\n\n");
        
        sb.append("*Productos:*\n");
        orden.getItems().forEach(item -> 
            sb.append("• ").append(item.getProducto().getNombre())
              .append(" x").append(item.getCantidad())
              .append(" = $").append(item.getSubtotal()).append("\n"));
        
        sb.append("\n¡Gracias por tu compra! 🎉");
        
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
        sb.append("🔄 *Estado de Orden Actualizado*\n\n");
        sb.append("Hola ").append(orden.getUsuario().getNombre()).append("!\n\n");
        sb.append("El estado de tu orden ha cambiado.\n\n");
        sb.append("*Detalles:*\n");
        sb.append("📋 ID: ").append(orden.getId()).append("\n");
        sb.append("📊 Estado anterior: ").append(estadoAnterior).append("\n");
        sb.append("📊 Estado actual: ").append(orden.getEstado()).append("\n");
        sb.append("📅 Actualizado: ").append(orden.getFechaActualizacion()).append("\n\n");
        
        if (orden.getEstado() == OrdenCompra.EstadoOrden.COMPLETADA) {
            sb.append("✅ ¡Tu orden ha sido completada! ¡Gracias por tu compra!");
        } else if (orden.getEstado() == OrdenCompra.EstadoOrden.CANCELADA) {
            sb.append("❌ Tu orden ha sido cancelada. Si tienes alguna pregunta, contáctanos.");
        }
        
        return sb.toString();
    }
    
    /**
     * Simula el envío de un mensaje por WhatsApp
     * @param telefono número de teléfono
     * @param mensaje contenido del mensaje
     */
    private void enviarWhatsApp(String telefono, String mensaje) {
        // En una implementación real, aquí se integraría con la API de WhatsApp Business
        // o servicios como Twilio, MessageBird, etc.
        System.out.println("=== WHATSAPP ENVIADO ===");
        System.out.println("Para: " + telefono);
        System.out.println("Mensaje: " + mensaje);
        System.out.println("=======================");
    }
} 