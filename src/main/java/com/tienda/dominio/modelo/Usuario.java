package com.tienda.dominio.modelo;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

/**
 * Entidad de dominio que representa un usuario del sistema
 */
public class Usuario {
    private UUID id;
    private String nombre;
    private String email;
    private String password;
    private String telefono;
    private String telegramId;
    private boolean activo;
    private List<OrdenCompra> historialOrdenes;

    public Usuario() {
        this.id = UUID.randomUUID();
        this.activo = true;
        this.historialOrdenes = new ArrayList<>();
    }

    public Usuario(String nombre, String email, String password) {
        this();
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(String telegramId) {
        this.telegramId = telegramId;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<OrdenCompra> getHistorialOrdenes() {
        return new ArrayList<>(historialOrdenes);
    }

    /**
     * Agrega una orden al historial del usuario
     * @param orden orden a agregar
     */
    public void agregarOrdenAlHistorial(OrdenCompra orden) {
        this.historialOrdenes.add(orden);
    }

    /**
     * Verifica si el usuario puede recibir notificaciones por WhatsApp
     * @return true si tiene teléfono registrado
     */
    public boolean puedeRecibirWhatsApp() {
        return telefono != null && !telefono.trim().isEmpty();
    }

    /**
     * Verifica si el usuario puede recibir notificaciones por Telegram
     * @return true si tiene telegramId registrado
     */
    public boolean puedeRecibirTelegram() {
        return telegramId != null && !telegramId.trim().isEmpty();
    }
} 