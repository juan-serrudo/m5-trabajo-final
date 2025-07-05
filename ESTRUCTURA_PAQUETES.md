# Estructura de Paquetes y Clases - Sistema de Tienda Online

## 📁 **5. Estructura de Paquetes Implementada**

La organización del código fuente sigue los principios de separación de responsabilidades y modularidad especificados, facilitando el mantenimiento, escalabilidad y comprensión del sistema.

### **5.1 Paquete dominio** 🏗️

Contiene las entidades y contratos que representan la lógica de negocio pura, independiente de cualquier tecnología.

#### **dominio.modelo**
Clases que representan los conceptos centrales del negocio:
- `Producto` - Entidad de producto con gestión de stock
- `Usuario` - Entidad de usuario con capacidades de notificación
- `OrdenCompra` - Entidad de orden con estados y gestión de items
- `ItemOrden` - Entidad de item dentro de una orden
- `EstadoOrden` - Enumeración de estados de orden

#### **dominio.repositorio**
Interfaces que definen los contratos para el acceso y manipulación de datos:
- `RepositorioProducto` - Contrato para gestión de productos
- `RepositorioUsuario` - Contrato para gestión de usuarios
- `RepositorioOrdenCompra` - Contrato para gestión de órdenes

#### **dominio.servicio**
Interfaces que definen reglas de negocio complejas:
- `ServicioNotificacion` - Contrato para servicios de notificación

### **5.2 Paquete aplicacion** ⚙️

Contiene la lógica de coordinación de los casos de uso, orquestando la interacción entre entidades, repositorios y servicios de dominio.

#### **aplicacion.servicio**
Clases que implementan la lógica de los casos de uso:
- `ServicioGestionProducto` - Gestión completa de productos (RF1)
- `ServicioGestionUsuario` - Gestión de usuarios y autenticación (RF3)
- `ServicioProcesarOrden` - Procesamiento de órdenes con validaciones (RF4)
- `ServicioGenerarReporte` - Generación de reportes de ventas (RF6)

#### **aplicacion.dto**
Clases Data Transfer Object para transportar información entre capas:
- `ProductoDTO` - DTO para transferir información de productos
- `OrdenCompraDTO` - DTO para transferir información de órdenes
- `ItemOrdenDTO` - DTO para transferir información de items

### **5.3 Paquete infraestructura** 🔧

Incluye las implementaciones técnicas concretas necesarias para que el sistema funcione.

#### **infraestructura.persistencia**
Clases que implementan los repositorios definidos en el dominio:
- `RepositorioProductoMemoria` - Implementación en memoria
- `RepositorioUsuarioMemoria` - Implementación en memoria
- `RepositorioOrdenCompraMemoria` - Implementación en memoria

#### **infraestructura.notificacion**
Clases que implementan estrategias de notificación:
- `NotificacionEmail` - Notificaciones por correo electrónico
- `NotificacionWhatsApp` - Notificaciones por WhatsApp
- `NotificacionTelegram` - Notificaciones por Telegram
- `ServicioNotificacionCompuesto` - Orquestador de múltiples canales

#### **infraestructura.reporte**
Clases encargadas de generar reportes:
- `GeneradorReporteVentas` - Generación de reportes de ventas

### **5.4 Paquete compartido** 🔄

Contiene elementos transversales que pueden ser utilizados en cualquier parte del sistema.

#### **compartido.excepcion**
Clases de excepciones personalizadas:
- `ExcepcionNegocio` - Excepción para errores de negocio

#### **compartido.constante**
Constantes comunes utilizadas en distintas partes del sistema:
- `MensajesSistema` - Constantes con mensajes del sistema

#### **compartido.util**
Clases de utilidades que centralizan lógica de uso común:
- (Pendiente de implementar según necesidades)

## 📊 **5.5 Relación entre Paquetes**

La estructura implementada garantiza las siguientes relaciones:

### **Dependencias Correctas** ✅
- **Aplicación → Dominio**: La capa de aplicación depende exclusivamente de las interfaces del dominio
- **Infraestructura → Dominio**: La capa de infraestructura implementa las interfaces definidas en el dominio
- **Compartido → Todas**: El paquete compartido puede ser utilizado por todas las capas

### **Independencia de Capas** ✅
- **Dominio**: No depende de ninguna otra capa
- **Aplicación**: Solo depende del dominio
- **Infraestructura**: Solo depende del dominio
- **Compartido**: Independiente, usado por todas las capas

## 🎯 **Beneficios de la Estructura**

### **Modularidad** 📦
- Cada paquete tiene responsabilidades claras y específicas
- Fácil localización de funcionalidades
- Separación clara entre lógica de negocio y detalles técnicos

### **Mantenibilidad** 🔧
- Cambios en una capa no afectan otras
- Código organizado y fácil de entender
- Responsabilidades bien definidas

### **Escalabilidad** 📈
- Fácil agregar nuevas funcionalidades
- Implementaciones intercambiables
- Extensibilidad sin modificar código existente

### **Testabilidad** 🧪
- Fácil mockear interfaces
- Pruebas unitarias aisladas
- Dependencias claras y controladas

## 📋 **Estructura Final del Proyecto**

```
src/main/java/com/tienda/
├── dominio/
│   ├── modelo/
│   │   ├── Producto.java
│   │   ├── Usuario.java
│   │   ├── OrdenCompra.java
│   │   ├── ItemOrden.java
│   │   └── EstadoOrden.java
│   ├── repositorio/
│   │   ├── RepositorioProducto.java
│   │   ├── RepositorioUsuario.java
│   │   └── RepositorioOrdenCompra.java
│   └── servicio/
│       └── ServicioNotificacion.java
├── aplicacion/
│   ├── servicio/
│   │   ├── ServicioGestionProducto.java
│   │   ├── ServicioGestionUsuario.java
│   │   ├── ServicioProcesarOrden.java
│   │   └── ServicioGenerarReporte.java
│   └── dto/
│       ├── ProductoDTO.java
│       ├── OrdenCompraDTO.java
│       └── ItemOrdenDTO.java
├── infraestructura/
│   ├── persistencia/
│   │   ├── RepositorioProductoMemoria.java
│   │   ├── RepositorioUsuarioMemoria.java
│   │   └── RepositorioOrdenCompraMemoria.java
│   ├── notificacion/
│   │   ├── NotificacionEmail.java
│   │   ├── NotificacionWhatsApp.java
│   │   ├── NotificacionTelegram.java
│   │   └── ServicioNotificacionCompuesto.java
│   └── reporte/
│       └── GeneradorReporteVentas.java
├── compartido/
│   ├── excepcion/
│   │   └── ExcepcionNegocio.java
│   └── constante/
│       └── MensajesSistema.java
└── TiendaOnlineApp.java
```
