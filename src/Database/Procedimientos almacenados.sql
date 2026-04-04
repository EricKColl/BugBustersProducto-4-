-- =========================================================
-- BUGBUSTERS · PRODUCTO 3
-- ESTRUCTURA DE BASE DE DATOS MYSQL
-- =========================================================
-- Este script crea la base de datos y sus tablas principales
-- para el proyecto Online Store:
--   - clientes
--   - articulos
--   - pedidos
--
-- Versión actualizada con:
--   - stock en artículos (cantidad_disponible)
--   - relación completa entre clientes, artículos y pedidos
-- =========================================================

CREATE DATABASE IF NOT EXISTS producto3;
USE producto3;

-- =========================
-- TABLA CLIENTES
-- =========================
CREATE TABLE IF NOT EXISTS clientes (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    domicilio VARCHAR(150),
    nif VARCHAR(20),
    tipo_cliente ENUM('estandar', 'premium') NOT NULL
);

-- =========================
-- TABLA ARTÍCULOS
-- =========================
CREATE TABLE IF NOT EXISTS articulos (
    id_articulo INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(200) NOT NULL,
    precio_venta DECIMAL(10,2) NOT NULL,
    gastos_envio DECIMAL(10,2) NOT NULL,
    tiempo_preparacion INT NOT NULL,
    cantidad_disponible INT NOT NULL DEFAULT 0
);

-- =========================
-- TABLA PEDIDOS
-- =========================
CREATE TABLE IF NOT EXISTS pedidos (
    id_pedido INT AUTO_INCREMENT PRIMARY KEY,

    id_cliente INT NOT NULL,
    id_articulo INT NOT NULL,

    cantidad INT NOT NULL,
    fecha_hora DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(50) NOT NULL DEFAULT 'PENDIENTE',

    CONSTRAINT fk_cliente
        FOREIGN KEY (id_cliente)
        REFERENCES clientes(id_cliente)
        ON DELETE CASCADE,

    CONSTRAINT fk_articulo
        FOREIGN KEY (id_articulo)
        REFERENCES articulos(id_articulo)
        ON DELETE CASCADE
);