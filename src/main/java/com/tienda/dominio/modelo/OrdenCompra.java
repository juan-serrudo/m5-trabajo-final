package com.tienda.dominio.modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

/**
 * Entidad de dominio que representa una orden de compra
 */
public class OrdenCompra {
    private UUID id;
    private Usuario usuario;
    private List<ItemOrden> items;
    private EstadoOrden estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private BigDecimal total;

    public OrdenCompra() {
        this.id = UUID.randomUUID();
        this.items = new ArrayList<>();
        this.estado = EstadoOrden.PENDIENTE;
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
        this.total = BigDecimal.ZERO;
    }

    public OrdenCompra(Usuario usuario) {
        this();
        this.usuario = usuario;
    }

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ItemOrden> getItems() {
        return new ArrayList<>(items);
    }

    public void setItems(List<ItemOrden> items) {
        this.items = new ArrayList<>(items);
        calcularTotal();
    }

    public EstadoOrden getEstado() {
        return estado;
    }

    public void setEstado(EstadoOrden estado) {
        this.estado = estado;
        this.fechaActualizacion = LocalDateTime.now();
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

    /**
     * Agrega un item a la orden
     * @param item item a agregar
     */
    public void agregarItem(ItemOrden item) {
        this.items.add(item);
        calcularTotal();
    }

    /**
     * Calcula el total de la orden
     */
    private void calcularTotal() {
        this.total = items.stream()
                .map(item -> item.getPrecioUnitario().multiply(BigDecimal.valueOf(item.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Verifica si la orden está en estado pendiente
     * @return true si está pendiente
     */
    public boolean estaPendiente() {
        return estado == EstadoOrden.PENDIENTE;
    }

    /**
     * Verifica si la orden está completada
     * @return true si está completada
     */
    public boolean estaCompletada() {
        return estado == EstadoOrden.COMPLETADA;
    }

    /**
     * Verifica si la orden está cancelada
     * @return true si está cancelada
     */
    public boolean estaCancelada() {
        return estado == EstadoOrden.CANCELADA;
    }

    /**
     * Marca la orden como completada
     */
    public void completar() {
        this.estado = EstadoOrden.COMPLETADA;
        this.fechaActualizacion = LocalDateTime.now();
    }

    /**
     * Marca la orden como cancelada
     */
    public void cancelar() {
        this.estado = EstadoOrden.CANCELADA;
        this.fechaActualizacion = LocalDateTime.now();
    }
} 