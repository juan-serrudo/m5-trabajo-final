package com.tienda.dominio.modelo;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Entidad de dominio que representa un producto en la tienda
 */
public class Producto {
    private UUID id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private int stockDisponible;
    private boolean activo;

    public Producto() {
        this.id = UUID.randomUUID();
        this.activo = true;
    }

    public Producto(String nombre, String descripcion, BigDecimal precio, int stockInicial) {
        this();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stockDisponible = stockInicial;
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

    /**
     * Descuenta stock del producto
     * @param cantidad cantidad a descontar
     * @throws IllegalArgumentException si no hay suficiente stock
     */
    public void descontarStock(int cantidad) {
        if (cantidad > stockDisponible) {
            throw new IllegalArgumentException("Stock insuficiente para el producto: " + nombre);
        }
        this.stockDisponible -= cantidad;
    }

    /**
     * Agrega stock al producto
     * @param cantidad cantidad a agregar
     */
    public void agregarStock(int cantidad) {
        if (cantidad > 0) {
            this.stockDisponible += cantidad;
        }
    }

    /**
     * Verifica si hay stock suficiente
     * @param cantidad cantidad requerida
     * @return true si hay stock suficiente
     */
    public boolean tieneStockSuficiente(int cantidad) {
        return stockDisponible >= cantidad;
    }
} 