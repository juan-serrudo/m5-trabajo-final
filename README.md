# Sistema de Gestión de Tienda Online

## Descripción

Este proyecto implementa un sistema de gestión integral de pedidos para una tienda en línea, siguiendo los principios de Arquitectura Limpia (Clean Architecture) y cumpliendo con los requerimientos funcionales especificados.

## Arquitectura

El sistema está estructurado en cuatro capas principales:

### 1. Dominio
Contiene las entidades de negocio y las interfaces de los repositorios:
- `Producto`: Entidad que representa un producto en la tienda
- `Usuario`: Entidad que representa un usuario del sistema
- `OrdenCompra`: Entidad que representa una orden de compra
- `ItemOrden`: Entidad que representa un item dentro de una orden
- Interfaces de repositorios: `RepositorioProducto`, `RepositorioUsuario`, `RepositorioOrdenCompra`
- `ServicioNotificacion`: Interfaz para el servicio de notificaciones

### 2. Aplicación
Contiene los servicios de aplicación que orquestan la lógica de negocio:
- `ServicioGestionProducto`: Gestión de productos (RF1)
- `ServicioGestionUsuario`: Gestión de usuarios (RF3)
- `ServicioProcesarOrden`: Procesamiento de órdenes de compra (RF4)
- `GeneradorReporteVentas`: Generación de reportes de ventas (RF6)

### 3. Infraestructura
Contiene las implementaciones concretas:
- Repositorios en memoria: `RepositorioProductoMemoria`, `RepositorioUsuarioMemoria`, `RepositorioOrdenCompraMemoria`
- Servicios de notificación: `NotificacionEmail`, `NotificacionWhatsApp`, `NotificacionTelegram`
- `ServicioNotificacionCompuesto`: Orquesta múltiples canales de notificación

### 4. Compartido
Contiene elementos compartidos entre todas las capas:
- `ExcepcionNegocio`: Excepción personalizada para errores de negocio
- `MensajesSistema`: Constantes con mensajes del sistema

## Requerimientos Funcionales Implementados

### RF1. Gestión de Productos ✅
- Registro de nuevos productos con ID único, nombre, descripción, precio y stock inicial
- Consulta del catálogo de productos existentes
- Modificación de información de productos
- Eliminación de productos (marcado como inactivo)

### RF2. Gestión de Inventario ✅
- Vinculación de productos con inventario
- Actualización automática del stock
- Validación de stock antes de confirmar órdenes
- Descuento automático de stock al procesar órdenes

### RF3. Gestión de Usuarios ✅
- Registro de nuevos usuarios
- Autenticación de usuarios
- Historial de órdenes por usuario

### RF4. Gestión de Órdenes de Compra ✅
- Generación de órdenes de compra con múltiples productos
- Verificación de disponibilidad de stock
- Consulta de estado de órdenes
- Estados: Pendiente, Completada, Cancelada

### RF5. Notificaciones al Usuario ✅
- Notificaciones por correo electrónico
- Notificaciones por WhatsApp (si tiene teléfono registrado)
- Notificaciones por Telegram (si tiene Telegram ID registrado)
- Notificaciones automáticas para nuevas órdenes y cambios de estado

### RF6. Generación de Reportes de Ventas ✅
- Listado de productos vendidos en período determinado
- Cantidades vendidas por producto
- Volumen total de ventas
- Reportes del día y del mes

### RF7. Integridad de Datos ✅
- Validación de stock antes de generar órdenes
- Consistencia entre órdenes, usuarios y productos
- Manejo de transacciones y rollback en cancelaciones

### RF8. Facilidad de Mantenimiento y Extensión ✅
- Arquitectura modular que permite agregar nuevas funcionalidades
- Diseño extensible para nuevos métodos de notificación
- Separación clara de responsabilidades

## Estructura del Proyecto

```
src/main/java/com/tienda/
├── dominio/
│   ├── Producto.java
│   ├── Usuario.java
│   ├── OrdenCompra.java
│   ├── ItemOrden.java
│   ├── RepositorioProducto.java
│   ├── RepositorioUsuario.java
│   ├── RepositorioOrdenCompra.java
│   └── ServicioNotificacion.java
├── aplicacion/
│   ├── ServicioGestionProducto.java
│   ├── ServicioGestionUsuario.java
│   ├── ServicioProcesarOrden.java
│   └── GeneradorReporteVentas.java
├── infraestructura/
│   ├── RepositorioProductoMemoria.java
│   ├── RepositorioUsuarioMemoria.java
│   ├── RepositorioOrdenCompraMemoria.java
│   ├── NotificacionEmail.java
│   ├── NotificacionWhatsApp.java
│   ├── NotificacionTelegram.java
│   └── ServicioNotificacionCompuesto.java
├── compartido/
│   ├── ExcepcionNegocio.java
│   └── MensajesSistema.java
└── TiendaOnlineApp.java
```

## Tecnologías Utilizadas

- **Java 11**: Lenguaje de programación principal
- **Maven**: Gestión de dependencias y build
- **JUnit 5**: Framework de pruebas unitarias
- **Mockito**: Framework para mocks en pruebas

## Cómo Ejecutar

### Prerrequisitos
- Java 11 o superior
- Maven 3.6 o superior

### Compilación
```bash
mvn clean compile
```

### Ejecución
```bash
mvn exec:java -Dexec.mainClass="com.tienda.TiendaOnlineApp"
```

### Ejecutar JAR
```bash
mvn package
java -jar target/tienda-online-1.0.0.jar
```

## Características Técnicas

### Patrones de Diseño Implementados
- **Repository Pattern**: Para acceso a datos
- **Strategy Pattern**: Para diferentes tipos de notificación
- **Composite Pattern**: Para servicio de notificación compuesto
- **Factory Pattern**: Para creación de entidades
- **Observer Pattern**: Para notificaciones automáticas

### Principios SOLID Aplicados
- **Single Responsibility**: Cada clase tiene una responsabilidad específica
- **Open/Closed**: Extensible sin modificar código existente
- **Liskov Substitution**: Implementaciones intercambiables
- **Interface Segregation**: Interfaces específicas y cohesivas
- **Dependency Inversion**: Dependencias hacia abstracciones

### Manejo de Errores
- Excepciones personalizadas para errores de negocio
- Validaciones en capa de aplicación
- Mensajes de error descriptivos y localizados

## Extensibilidad

El sistema está diseñado para ser fácilmente extensible:

### Nuevos Métodos de Notificación
Para agregar un nuevo canal de notificación, solo se necesita:
1. Implementar la interfaz `ServicioNotificacion`
2. Agregar la implementación al `ServicioNotificacionCompuesto`

### Nuevos Tipos de Reportes
Para agregar nuevos reportes:
1. Crear un nuevo servicio en la capa de aplicación
2. Implementar la lógica de generación de reportes

### Persistencia de Datos
Para cambiar de memoria a base de datos:
1. Implementar las interfaces de repositorio con JPA/Hibernate
2. Configurar la inyección de dependencias

## Próximos Pasos

1. **Implementar persistencia real**: Reemplazar repositorios en memoria con base de datos
2. **Agregar API REST**: Exponer servicios a través de endpoints HTTP
3. **Implementar autenticación JWT**: Sistema de autenticación más robusto
4. **Agregar validaciones avanzadas**: Validaciones más complejas de negocio
5. **Implementar cache**: Cache para mejorar rendimiento
6. **Agregar logging**: Sistema de logs estructurado
7. **Implementar métricas**: Monitoreo y métricas de la aplicación 