package com.tienda.compartido;

/**
 * Excepción personalizada para errores de negocio
 */
public class ExcepcionNegocio extends RuntimeException {
    
    public ExcepcionNegocio(String mensaje) {
        super(mensaje);
    }
    
    public ExcepcionNegocio(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
} 