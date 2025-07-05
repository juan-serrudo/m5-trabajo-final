package com.tienda.infraestructura.notificacion;

import com.tienda.dominio.modelo.Usuario;
import com.tienda.dominio.servicio.EstrategiaNotificacion;

/**
 * Implementa el envío de notificaciones vía WhatsApp
 */
public class NotificacionWhatsApp implements EstrategiaNotificacion {
    
    @Override
    public boolean enviar(String mensaje, Usuario usuario) {
        if (usuario.getCelular() == null || usuario.getCelular().trim().isEmpty()) {
            System.out.println("❌ No se puede enviar WhatsApp: usuario sin celular válido");
            return false;
        }
        
        // Simulación de envío de WhatsApp
        System.out.println("📱 WHATSAPP enviado a: " + usuario.getCelular());
        System.out.println("   Destinatario: " + usuario.getNombre());
        System.out.println("   Mensaje: " + mensaje);
        System.out.println("   ---");
        
        return true;
    }
    
    @Override
    public String getNombreEstrategia() {
        return "WhatsApp";
    }
} 