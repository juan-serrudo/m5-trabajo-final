package com.tienda.compartido.constante;

/**
 * Clase que contiene los mensajes del sistema
 */
public class MensajesSistema {
    
    // Mensajes de Producto
    public static final String PRODUCTO_NO_ENCONTRADO = "Producto no encontrado";
    public static final String PRODUCTO_YA_EXISTE = "El producto ya existe";
    public static final String PRODUCTO_CON_ORDENES = "No se puede eliminar un producto que tiene órdenes asociadas";
    public static final String STOCK_INSUFICIENTE = "Stock insuficiente para el producto";
    
    // Mensajes de Usuario
    public static final String USUARIO_NO_ENCONTRADO = "Usuario no encontrado";
    public static final String USUARIO_YA_EXISTE = "El usuario ya existe";
    public static final String CREDENCIALES_INVALIDAS = "Credenciales inválidas";
    
    // Mensajes de Orden
    public static final String ORDEN_NO_ENCONTRADA = "Orden no encontrada";
    public static final String ORDEN_YA_COMPLETADA = "La orden ya está completada";
    public static final String ORDEN_YA_CANCELADA = "La orden ya está cancelada";
    public static final String ORDEN_SIN_ITEMS = "La orden debe tener al menos un item";
    
    // Mensajes de Validación
    public static final String NOMBRE_REQUERIDO = "El nombre es requerido";
    public static final String EMAIL_REQUERIDO = "El email es requerido";
    public static final String EMAIL_INVALIDO = "El formato del email es inválido";
    public static final String PRECIO_INVALIDO = "El precio debe ser mayor a cero";
    public static final String CANTIDAD_INVALIDA = "La cantidad debe ser mayor a cero";
    
    // Mensajes de Notificación
    public static final String NOTIFICACION_ENVIADA = "Notificación enviada exitosamente";
    public static final String ERROR_NOTIFICACION = "Error al enviar notificación";
    
    // Mensajes de Reporte
    public static final String REPORTE_GENERADO = "Reporte generado exitosamente";
    public static final String ERROR_REPORTE = "Error al generar reporte";
} 