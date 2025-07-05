package com.tienda.infraestructura.notificacion;

import com.tienda.dominio.modelo.Usuario;
import com.tienda.dominio.servicio.EstrategiaNotificacion;

/**
 * Implementa el envío de notificaciones vía correo electrónico
 */
public class NotificacionEmail implements EstrategiaNotificacion {
    
    @Override
    public boolean enviar(String mensaje, Usuario usuario) {
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            System.out.println("❌ No se puede enviar email: usuario sin email válido");
            return false;
        }
        
        // Simulación de envío de email
        System.out.println("📧 EMAIL enviado a: " + usuario.getEmail());
        System.out.println("   Destinatario: " + usuario.getNombre());
        System.out.println("   Mensaje: " + mensaje);
        System.out.println("   ---");
        
        return true;
    }
    
    @Override
    public String getNombreEstrategia() {
        return "Email";
    }
} 