package com.tienda.aplicacion.dto;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO para transferir información de productos entre capas
 */
public class ProductoDTO {
    private UUID id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private int stockDisponible;
    private boolean activo;

    public ProductoDTO() {
    }

    public ProductoDTO(UUID id, String nombre, String descripcion, BigDecimal precio, int stockDisponible, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stockDisponible = stockDisponible;
        this.activo = activo;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getStockDisponible() {
        return stockDisponible;
    }

    public void setStockDisponible(int stockDisponible) {
        this.stockDisponible = stockDisponible;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
} 