package com.tienda.infraestructura;

import com.tienda.dominio.OrdenCompra;
import com.tienda.dominio.ServicioNotificacion;
import com.tienda.dominio.Usuario;

/**
 * Implementación de notificación por Telegram
 */
public class NotificacionTelegram implements ServicioNotificacion {
    
    @Override
    public void notificarNuevaOrden(OrdenCompra orden) {
        Usuario usuario = orden.getUsuario();
        
        if (!usuario.puedeRecibirTelegram()) {
            System.out.println("Usuario " + usuario.getNombre() + " no tiene Telegram ID registrado");
            return;
        }
        
        String mensaje = generarMensajeNuevaOrden(orden);
        enviarTelegram(usuario.getTelegramId(), mensaje);
    }
    
    @Override
    public void notificarCambioEstado(OrdenCompra orden, OrdenCompra.EstadoOrden estadoAnterior) {
        Usuario usuario = orden.getUsuario();
        
        if (!usuario.puedeRecibirTelegram()) {
            System.out.println("Usuario " + usuario.getNombre() + " no tiene Telegram ID registrado");
            return;
        }
        
        String mensaje = generarMensajeCambioEstado(orden, estadoAnterior);
        enviarTelegram(usuario.getTelegramId(), mensaje);
    }
    
    @Override
    public void notificarMensaje(Usuario destinatario, String asunto, String mensaje) {
        if (!destinatario.puedeRecibirTelegram()) {
            System.out.println("Usuario " + destinatario.getNombre() + " no tiene Telegram ID registrado");
            return;
        }
        
        String mensajeCompleto = "*" + asunto + "*\n\n" + mensaje;
        enviarTelegram(destinatario.getTelegramId(), mensajeCompleto);
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
        sb.append("📋 ID: `").append(orden.getId()).append("`\n");
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
        sb.append("📋 ID: `").append(orden.getId()).append("`\n");
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
     * Simula el envío de un mensaje por Telegram
     * @param telegramId ID de Telegram del usuario
     * @param mensaje contenido del mensaje
     */
    private void enviarTelegram(String telegramId, String mensaje) {
        // En una implementación real, aquí se integraría con la API de Telegram Bot
        System.out.println("=== TELEGRAM ENVIADO ===");
        System.out.println("Para: " + telegramId);
        System.out.println("Mensaje: " + mensaje);
        System.out.println("========================");
    }
} 