package com.tienda.dominio;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Entidad de dominio que representa un item dentro de una orden de compra
 */
public class ItemOrden {
    private UUID id;
    private Producto producto;
    private int cantidad;
    private BigDecimal precioUnitario;

    public ItemOrden() {
        this.id = UUID.randomUUID();
    }

    public ItemOrden(Producto producto, int cantidad) {
        this();
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecio();
    }

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        if (producto != null) {
            this.precioUnitario = producto.getPrecio();
        }
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    /**
     * Calcula el subtotal del item
     * @return subtotal del item
     */
    public BigDecimal getSubtotal() {
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }
} 