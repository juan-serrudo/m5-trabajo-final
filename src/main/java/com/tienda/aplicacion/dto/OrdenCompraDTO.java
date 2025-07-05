package com.tienda.aplicacion.dto;

import com.tienda.dominio.modelo.EstadoOrden;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * DTO para transferir información de órdenes de compra entre capas
 */
public class OrdenCompraDTO {
    private UUID id;
    private UUID usuarioId;
    private String nombreUsuario;
    private List<ItemOrdenDTO> items;
    private EstadoOrden estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private BigDecimal total;

    public OrdenCompraDTO() {
    }

    public OrdenCompraDTO(UUID id, UUID usuarioId, String nombreUsuario, List<ItemOrdenDTO> items, 
                         EstadoOrden estado, LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion, 
                         BigDecimal total) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.nombreUsuario = nombreUsuario;
        this.items = items;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
        this.total = total;
    }

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public List<ItemOrdenDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemOrdenDTO> items) {
        this.items = items;
    }

    public EstadoOrden getEstado() {
        return estado;
    }

    public void setEstado(EstadoOrden estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
} 