package com.tienda;

import com.tienda.aplicacion.*;
import com.tienda.dominio.*;
import com.tienda.infraestructura.*;
import com.tienda.compartido.ExcepcionNegocio;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Clase principal que demuestra el uso del sistema de tienda online
 */
public class TiendaOnlineApp {

  public static void main(String[] args) {
    System.out.println("=== SISTEMA DE GESTIÓN DE TIENDA ONLINE ===\n");

    try {
      // Configurar la infraestructura
      RepositorioProducto repositorioProducto = new RepositorioProductoMemoria();
      RepositorioUsuario repositorioUsuario = new RepositorioUsuarioMemoria();
      RepositorioOrdenCompra repositorioOrden = new RepositorioOrdenCompraMemoria();

      // Configurar servicios de notificación
      ServicioNotificacionCompuesto servicioNotificacion = new ServicioNotificacionCompuesto();
      servicioNotificacion.agregarServicio(new NotificacionEmail());
      servicioNotificacion.agregarServicio(new NotificacionWhatsApp());
      servicioNotificacion.agregarServicio(new NotificacionTelegram());

      // Configurar servicios de aplicación
      ServicioGestionProducto servicioProducto = new ServicioGestionProducto(repositorioProducto);
      ServicioGestionUsuario servicioUsuario = new ServicioGestionUsuario(repositorioUsuario);
      ServicioProcesarOrden servicioOrden = new ServicioProcesarOrden(
          repositorioOrden, repositorioProducto, repositorioUsuario, servicioNotificacion);
      GeneradorReporteVentas generadorReportes = new GeneradorReporteVentas(repositorioOrden);

      // Ejecutar demostración
      ejecutarDemostracion(servicioProducto, servicioUsuario, servicioOrden, generadorReportes);

    } catch (Exception e) {
      System.err.println("Error en la aplicación: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private static void ejecutarDemostracion(ServicioGestionProducto servicioProducto,
      ServicioGestionUsuario servicioUsuario,
      ServicioProcesarOrden servicioOrden,
      GeneradorReporteVentas generadorReportes) {

    System.out.println("1. REGISTRANDO PRODUCTOS...");
    Producto producto1 = servicioProducto.registrarProducto(
        "Laptop HP Pavilion", "Laptop de 15 pulgadas con procesador Intel i5",
        new BigDecimal("899.99"), 10);
    System.out.println("Producto registrado: " + producto1.getNombre());

    Producto producto2 = servicioProducto.registrarProducto(
        "Mouse Inalámbrico", "Mouse óptico inalámbrico con sensor de 1200 DPI",
        new BigDecimal("29.99"), 50);
    System.out.println("Producto registrado: " + producto2.getNombre());

    Producto producto3 = servicioProducto.registrarProducto(
        "Teclado Mecánico", "Teclado mecánico RGB con switches Cherry MX",
        new BigDecimal("149.99"), 15);
    System.out.println("Producto registrado: " + producto3.getNombre());

    System.out.println("\n2. REGISTRANDO USUARIOS...");
    Usuario usuario1 = servicioUsuario.registrarUsuario(
        "Juan Serrudo", "juan.serrduo@gmail.com", "pass123");
    servicioUsuario.actualizarUsuario(usuario1.getId(), usuario1.getNombre(), "+59172890159", "@juanserrudo");
    System.out.println("Usuario registrado: " + usuario1.getNombre());

    Usuario usuario2 = servicioUsuario.registrarUsuario(
        "María García", "maria.garcia@gmail.com", "pass456");
    servicioUsuario.actualizarUsuario(usuario2.getId(), usuario2.getNombre(), "+59172890158", null);
    System.out.println("Usuario registrado: " + usuario2.getNombre());

    System.out.println("\n3. CREANDO ÓRDENES DE COMPRA...");

    // Orden 1: Usuario 1 compra laptop y mouse
    List<ItemOrden> itemsOrden1 = Arrays.asList(
        new ItemOrden(producto1, 1),
        new ItemOrden(producto2, 2));

    OrdenCompra orden1 = servicioOrden.crearOrden(usuario1.getId(), itemsOrden1);
    System.out.println("Orden creada: ID " + orden1.getId() + " - Total: Bs" + orden1.getTotal());

    // Orden 2: Usuario 2 compra teclado
    List<ItemOrden> itemsOrden2 = Arrays.asList(
        new ItemOrden(producto3, 1));

    OrdenCompra orden2 = servicioOrden.crearOrden(usuario2.getId(), itemsOrden2);
    System.out.println("Orden creada: ID " + orden2.getId() + " - Total: Bs" + orden2.getTotal());

    System.out.println("\n4. PROCESANDO ÓRDENES...");

    // Completar orden 1
    OrdenCompra ordenCompletada = servicioOrden.completarOrden(orden1.getId());
    System.out.println("Orden " + ordenCompletada.getId() + " completada");

    // Cancelar orden 2
    OrdenCompra ordenCancelada = servicioOrden.cancelarOrden(orden2.getId());
    System.out.println("Orden " + ordenCancelada.getId() + " cancelada");

    System.out.println("\n5. CONSULTANDO INVENTARIO...");
    List<Producto> catalogo = servicioProducto.obtenerCatalogo();
    System.out.println("Productos en catálogo:");
    catalogo.forEach(p -> System.out.println("- " + p.getNombre() + ": " + p.getStockDisponible() + " unidades"));

    System.out.println("\n6. GENERANDO REPORTE DE VENTAS...");
    GeneradorReporteVentas.ReporteVentas reporte = generadorReportes.generarReporteVentasHoy();
    System.out.println(reporte.toString());

    System.out.println("\n7. CONSULTANDO HISTORIAL DE USUARIO...");
    List<OrdenCompra> historialUsuario1 = servicioUsuario.obtenerHistorialOrdenes(usuario1.getId());
    System.out.println("Historial de " + usuario1.getNombre() + ":");
    historialUsuario1
        .forEach(o -> System.out.println("- Orden " + o.getId() + ": " + o.getEstado() + " - Bs" + o.getTotal()));

    System.out.println("\n=== DEMOSTRACIÓN COMPLETADA ===");
  }
}