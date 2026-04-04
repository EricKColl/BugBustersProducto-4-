# BugBusters Producto 3

## Descripción general

**BugBusters Producto 3** es una aplicación desarrollada en **Java** con una arquitectura organizada por capas y conectada a **MySQL** mediante **JDBC**. Este proyecto forma parte del desarrollo académico del equipo **BugBusters** y representa la evolución del sistema de gestión de una tienda online, incorporando persistencia real en base de datos.

En esta fase del proyecto, el objetivo principal es dotar a la aplicación de una estructura de almacenamiento sólida, organizada y funcional, permitiendo trabajar con entidades como **clientes**, **artículos** y **pedidos**, así como establecer sus relaciones mediante un modelo relacional correctamente diseñado.

Además de la persistencia básica, en la versión actual del proyecto también se ha incorporado la gestión de **stock de artículos**, de forma que ahora el sistema controla la cantidad disponible de cada producto, descuenta unidades al crear pedidos, devuelve stock al eliminar pedidos cancelables y permite añadir más unidades a artículos ya existentes.

El proyecto incluye tanto la estructura del código Java como los scripts SQL necesarios para crear la base de datos, insertar datos de prueba y trabajar con procedimientos almacenados.

---

## Objetivos del producto

Los principales objetivos de este producto son:

- Implementar la persistencia de datos en MySQL.
- Conectar la aplicación Java con la base de datos mediante JDBC.
- Diseñar correctamente las tablas y sus relaciones.
- Definir claves primarias y foráneas.
- Configurar adecuadamente los campos autoincrementales.
- Preparar procedimientos almacenados para inserción, actualización y eliminación.
- Aplicar transacciones en las operaciones DML principales.
- Permitir la gestión estructurada de clientes, artículos y pedidos.
- Incorporar control de stock real sobre los artículos.
- Mantener una organización clara del proyecto para facilitar su comprensión y mantenimiento.

---

## Funcionalidades principales

El sistema está orientado a la gestión básica de una tienda online y contempla las siguientes áreas funcionales:

### Gestión de clientes

- Almacenamiento de datos de clientes.
- Diferenciación entre clientes **estándar** y **premium**.
- Registro de información básica como **email**, **nombre**, **domicilio** y **NIF**.
- Búsqueda, listado y eliminación de clientes.
- Control para impedir eliminar clientes con pedidos asociados.

### Gestión de artículos

- Almacenamiento de artículos con código único.
- Descripción del artículo.
- Precio de venta.
- Gastos de envío.
- Tiempo de preparación.
- Cantidad disponible en stock.
- Listado de artículos mostrando también el stock actual.
- Inserción de artículos nuevos con cantidad inicial disponible.
- Posibilidad de añadir más stock a un artículo ya existente.
- Eliminación de artículos.
- Control para impedir eliminar artículos con pedidos asociados.

### Gestión de pedidos

- Asociación entre clientes y artículos.
- Almacenamiento de cantidad, fecha y estado del pedido.
- Relación entre tablas mediante claves foráneas.
- Inserción, eliminación y consulta de pedidos.
- Cambio manual de estado del pedido.
- Cálculo automático del paso de **pendiente** a **enviado** según el tiempo de preparación del artículo.
- Validación de cancelación de pedidos solo cuando aún son cancelables.
- Descuento automático de stock al crear un pedido.
- Devolución automática de stock al eliminar un pedido cancelable.
- Restricción lógica para impedir cambiar un pedido de **ENVIADO** a **PENDIENTE**.

---

## Tecnologías utilizadas

Este proyecto ha sido desarrollado utilizando las siguientes tecnologías:

- **Java**
- **JDBC**
- **MySQL**
- **MySQL Workbench**
- **IntelliJ IDEA**
- **Git**
- **GitHub**

---

## Arquitectura y organización del proyecto

El proyecto está organizado en distintos paquetes para mantener una estructura clara, modular y fácil de mantener.

    src/
    ├── Controlador/
    ├── DAO/
    │   ├── Interfaces/
    │   └── MySQL/
    ├── Database/
    │   ├── Tablas Base de Datos.sql
    │   ├── Procedimientos almacenados.sql
    │   └── Datos-Prueba.sql
    ├── Excepciones/
    ├── Factory/
    ├── Modelo/
    ├── Util/
    └── Vista/

### Descripción de la estructura

**Controlador/**

Contiene la lógica de coordinación entre la vista y el modelo.

**DAO/**

Incluye la capa de acceso a datos, separada en interfaces y en sus implementaciones para MySQL.

**Database/**

Contiene los scripts SQL fundamentales del proyecto:

- Creación de la base de datos y de las tablas.
- Procedimientos almacenados.
- Datos de prueba.

**Excepciones/**

Incluye excepciones personalizadas para el control de errores.

**Factory/**

Contiene clases relacionadas con la creación o abstracción de objetos DAO.

**Modelo/**

Incluye las clases que representan las entidades del sistema.

**Util/**

Contiene utilidades generales, especialmente la conexión a la base de datos.

**Vista/**

Incluye la parte de ejecución o interacción principal del programa en modo consola.

---

## Modelo de base de datos

La base de datos del proyecto se estructura en torno a tres tablas principales.

### Tabla `clientes`

Contiene la información de los clientes del sistema.

**Campos principales:**

- `id_cliente`
- `email`
- `nombre`
- `domicilio`
- `nif`
- `tipo_cliente`

### Tabla `articulos`

Contiene la información de los artículos disponibles.

**Campos principales:**

- `id_articulo`
- `codigo`
- `descripcion`
- `precio_venta`
- `gastos_envio`
- `tiempo_preparacion`
- `cantidad_disponible`

### Tabla `pedidos`

Relaciona clientes y artículos mediante los pedidos realizados.

**Campos principales:**

- `id_pedido`
- `id_cliente`
- `id_articulo`
- `cantidad`
- `fecha_hora`
- `estado`

### Relaciones entre tablas

La base de datos implementa integridad referencial mediante claves foráneas:

- Cada pedido está asociado a un cliente.
- Cada pedido está asociado a un artículo.

Esto permite mantener la consistencia de los datos y asegurar que no existan pedidos que hagan referencia a registros inexistentes.

---

## Scripts SQL incluidos

### `Tablas Base de Datos.sql`

Archivo encargado de crear la estructura de la base de datos, incluyendo:

- Creación de la base de datos.
- Creación de tablas.
- Claves primarias.
- Claves foráneas.
- Relaciones entre tablas.
- Configuración de campos autoincrementales.
- Inclusión del campo `cantidad_disponible` en la tabla de artículos.

### `Procedimientos almacenados.sql`

Archivo que contiene los procedimientos almacenados del proyecto.

Incluye procedimientos para:

#### Inserción

- `insertar_articulo`
- `insertar_cliente`
- `insertar_pedido`

#### Actualización

- `actualizar_articulo`
- `actualizar_cliente`

#### Eliminación

- `eliminar_articulo`
- `eliminar_cliente`
- `eliminar_pedido`

#### Gestión adicional de stock

- `sumar_stock_articulo`

### `Datos-Prueba.sql`

Archivo que contiene datos de prueba preparados para poblar la base de datos y facilitar las comprobaciones del sistema.

En la versión actual, este script ya tiene en cuenta la inserción de artículos con stock inicial y la creación de pedidos mediante procedimiento almacenado para que el descuento de stock quede reflejado correctamente.

---

## Requisitos previos

Para poder ejecutar correctamente el proyecto, se recomienda disponer de lo siguiente:

- **Java JDK**
- **MySQL Server**
- **MySQL Workbench** o cualquier cliente compatible con MySQL
- **IntelliJ IDEA** o cualquier IDE compatible con Java
- Conexión configurada correctamente en `ConexionBD.java`
- Dependencias JDBC añadidas al proyecto

---

## Instalación de dependencias en IntelliJ IDEA

Antes de ejecutar el proyecto, es necesario añadir manualmente las librerías `.jar` utilizadas por la conexión JDBC.

### Dependencias necesarias

- `mysql-connector-j-8.0.33.jar`
- `protobuf-java-3.21.9.jar`

### Cómo añadirlas en IntelliJ IDEA

1. Abrir el proyecto en IntelliJ IDEA.
2. Ir a **File > Project Structure**.
3. Entrar en **Modules**.
4. Seleccionar el módulo del proyecto.
5. Abrir la pestaña **Dependencies**.
6. Pulsar el botón **+**.
7. Seleccionar **JARs or Directories**.
8. Buscar y añadir los archivos:
   - `mysql-connector-j-8.0.33.jar`
   - `protobuf-java-3.21.9.jar`
9. Asegurarse de que ambas dependencias queden con alcance **Compile**.
10. Pulsar **Apply** y después **OK**.

Una vez añadidas, IntelliJ podrá compilar y ejecutar correctamente el proyecto con acceso a MySQL.

---

## Preparación de la base de datos

Para dejar la base de datos lista, se recomienda seguir este orden de ejecución:

1. Ejecutar la estructura de la base de datos: `Tablas Base de Datos.sql`
2. Ejecutar los procedimientos almacenados: `Procedimientos almacenados.sql`
3. Insertar los datos de prueba: `Datos-Prueba.sql`

Este orden permite dejar preparada una base de datos completamente funcional para realizar pruebas y validaciones.

---

## Configuración de la conexión

La conexión a la base de datos se realiza mediante la clase:

`src/Util/ConexionBD.java`

En este proyecto, la conexión está configurada actualmente con los siguientes datos:

    private static final String URL =
            "jdbc:mysql://autorack.proxy.rlwy.net:13802/producto3" +
            "?connectionTimeZone=LOCAL" +
            "&forceConnectionTimeZoneToSession=true";

    private static final String USER = "root";
    private static final String PASSWORD = "SppuTCrhvoNHXhezDpJcwTINkOenYool";

### Explicación de la configuración

- **URL**: dirección de la base de datos MySQL remota utilizada por el proyecto.
- **USER**: usuario de acceso a la base de datos.
- **PASSWORD**: contraseña asociada al usuario.
- **connectionTimeZone=LOCAL**: fuerza el uso de la zona horaria local del sistema.
- **forceConnectionTimeZoneToSession=true**: aplica esa zona horaria a la sesión activa de la conexión.

Esta configuración permite que las fechas y horas registradas en los pedidos queden alineadas con la hora local utilizada por la aplicación Java.

---

## Ejecución del proyecto

Una vez preparada la base de datos y añadidas las dependencias, el proyecto puede ejecutarse desde el entorno Java configurado en el IDE.

### Pasos recomendados

1. Abrir el proyecto en IntelliJ IDEA.
2. Comprobar que las dependencias `.jar` están añadidas correctamente.
3. Verificar que la conexión a MySQL está correctamente configurada en `ConexionBD.java`.
4. Ejecutar los scripts SQL necesarios.
5. Compilar el proyecto.
6. Ejecutar la clase principal correspondiente.

### Clase principal

La ejecución del programa se realiza desde:

`src/Vista/Main.java`

---

## Funcionamiento general del sistema

El programa se ejecuta en modo consola y ofrece un menú principal con tres bloques de gestión:

- Gestión de artículos
- Gestión de clientes
- Gestión de pedidos

### Gestión de artículos

Permite:

- Añadir artículos.
- Mostrar artículos.
- Añadir stock a artículos existentes.
- Eliminar artículos.
- Bloquear la eliminación si existen pedidos asociados.
- Visualizar el stock disponible de cada artículo.

### Gestión de clientes

Permite:

- Añadir clientes.
- Buscar clientes por email.
- Mostrar todos los clientes.
- Mostrar clientes estándar.
- Mostrar clientes premium.
- Eliminar clientes.
- Bloquear la eliminación si existen pedidos asociados.

### Gestión de pedidos

Permite:

- Añadir pedidos.
- Eliminar pedidos cancelables.
- Mostrar pedidos pendientes.
- Mostrar pedidos enviados.
- Cambiar manualmente el estado de un pedido.

Además, el sistema:

- Asigna fecha y hora al crear el pedido.
- Recupera el número real del pedido generado en base de datos.
- Calcula el estado del pedido según el tiempo de preparación.
- Impide cancelar pedidos que ya no sean cancelables.
- Sincroniza automáticamente el paso a estado **ENVIADO** cuando corresponde.
- Impide cambiar manualmente un pedido de **ENVIADO** a **PENDIENTE**.
- Impide indicar un cambio de estado si el pedido ya se encuentra en ese mismo estado.
- Descuenta automáticamente el stock del artículo al crear el pedido.
- Devuelve automáticamente el stock al eliminar un pedido cancelable.

---

## Persistencia, JDBC, DAO, Factory y transacciones

La persistencia del sistema se ha implementado utilizando **Java + JDBC** sobre **MySQL**, manteniendo el patrón de diseño **MVC** y separando la lógica de acceso a datos mediante **DAO** y **Factory**.

### DAO

Se utilizan objetos DAO para encapsular el acceso a base de datos de:

- clientes
- artículos
- pedidos

### Factory

Se utiliza una factoría para instanciar los DAO y desacoplar la lógica del sistema del motor de persistencia.

### JDBC seguro

Se utilizan:

- `PreparedStatement`
- `CallableStatement`

para evitar concatenaciones inseguras y reducir el riesgo de **SQL Injection**.

### Transacciones

Las operaciones principales que modifican datos se han diseñado con control transaccional, tanto desde Java como dentro de los procedimientos almacenados que gestionan operaciones críticas relacionadas con stock y pedidos.

Esto permite mantener coherencia en situaciones como:

- creación de pedidos
- descuento de stock
- eliminación de pedidos
- devolución de stock
- inserción de clientes y artículos
- suma de stock sobre artículos existentes

### Procedimientos almacenados

El proyecto incluye procedimientos almacenados integrados en la solución de persistencia. Los más importantes en la versión actual son:

- Inserción de clientes
- Actualización de clientes
- Eliminación de clientes
- Inserción de artículos con stock inicial
- Actualización de artículos
- Eliminación de artículos
- Suma de stock sobre artículos existentes
- Inserción de pedidos devolviendo el identificador generado
- Eliminación de pedidos con devolución automática del stock

---

## Mejoras incorporadas en la versión actual

Respecto a la versión inicial del producto, en esta versión se han incorporado varias mejoras funcionales importantes:

- Control de stock real en artículos.
- Inclusión de la columna `cantidad_disponible` en base de datos.
- Descuento automático del stock al registrar pedidos.
- Devolución del stock al eliminar pedidos cancelables.
- Opción de sumar stock a artículos ya existentes.
- Inserción de pedidos recuperando el `id` generado desde MySQL.
- Corrección del registro de fecha y hora del pedido para respetar la fecha enviada desde Java.
- Control lógico para impedir pasar un pedido enviado a pendiente.
- Mejora de la presentación visual en consola para listados y tarjetas.

---

## Estado del proyecto

Este producto representa la fase del proyecto en la que se integra la persistencia real en base de datos. Se ha trabajado especialmente en:

- El diseño relacional.
- La conexión entre Java y MySQL.
- La organización por capas.
- La preparación de procedimientos almacenados.
- La creación de un entorno reproducible mediante datos de prueba.
- La implementación del control de stock.
- La gestión automática y manual del estado de los pedidos.
- La mejora de la visualización en consola.

---

## Buenas prácticas aplicadas

Durante el desarrollo se ha intentado seguir una estructura clara y ordenada, prestando atención a aspectos como:

- Separación de responsabilidades.
- Organización modular del código.
- Uso de nombres comprensibles.
- Preparación de scripts reutilizables.
- Mantenimiento de integridad referencial.
- Uso de procedimientos almacenados para mejorar la gestión SQL.
- Control transaccional en operaciones críticas.
- Validación funcional antes de realizar borrados sobre datos relacionados.
- Restricciones de lógica de negocio para evitar cambios de estado incoherentes.
- Comprobación de stock antes de aceptar pedidos.

---

## Finalidad académica

Este proyecto ha sido desarrollado con finalidad académica como parte del trabajo del equipo **BugBusters**, dentro del contexto del **Producto 3**, centrado en la persistencia de datos y la integración con base de datos relacional.

---

## Autoría

Proyecto realizado por el equipo **BugBusters**.

---

## Observaciones finales

Este repositorio recoge el trabajo correspondiente al desarrollo del producto orientado a base de datos y persistencia, ofreciendo una base sólida para continuar con futuras fases del proyecto.

La estructura planteada permite comprender con claridad cómo se organiza la aplicación, cómo se almacenan los datos, cómo se relacionan entre sí las distintas entidades del sistema y cómo se controla ahora también el stock de los artículos.

Se ha buscado una solución formal, ordenada y funcional, coherente con los requisitos del producto y adecuada para su evaluación académica.