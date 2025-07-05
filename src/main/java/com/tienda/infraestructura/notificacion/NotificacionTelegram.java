package com.tienda.infraestructura.notificacion;

import com.tienda.dominio.modelo.Usuario;
import com.tienda.dominio.servicio.EstrategiaNotificacion;

/**
 * Implementa el envío de notificaciones vía Telegram
 */
public class NotificacionTelegram implements EstrategiaNotificacion {
    
    @Override
    public boolean enviar(String mensaje, Usuario usuario) {
        if (usuario.getTelegramId() == null || usuario.getTelegramId().trim().isEmpty()) {
            System.out.println("❌ No se puede enviar Telegram: usuario sin Telegram ID válido");
            return false;
        }
        
        // Simulación de envío de Telegram
        System.out.println("📬 TELEGRAM enviado a: " + usuario.getTelegramId());
        System.out.println("   Destinatario: " + usuario.getNombre());
        System.out.println("   Mensaje: " + mensaje);
        System.out.println("   ---");
        
        return true;
    }
    
    @Override
    public String getNombreEstrategia() {
        return "Telegram";
    }
} 