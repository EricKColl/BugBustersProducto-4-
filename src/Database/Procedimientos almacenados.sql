-- =========================================================
-- PROCEDIMIENTOS ALMACENADOS - TABLA CLIENTES
-- =========================================================

DELIMITER $$

-- ---------------------------------------------------------
-- Insertar un nuevo cliente en la tabla clientes
-- ---------------------------------------------------------
CREATE PROCEDURE insertar_cliente (
    IN p_email VARCHAR(100),
    IN p_nombre VARCHAR(100),
    IN p_domicilio VARCHAR(150),
    IN p_nif VARCHAR(20),
    IN p_tipo_cliente ENUM('estandar', 'premium')
)
BEGIN
    INSERT INTO clientes (email, nombre, domicilio, nif, tipo_cliente)
    VALUES (p_email, p_nombre, p_domicilio, p_nif, p_tipo_cliente);
END $$

-- ---------------------------------------------------------
-- Actualizar los datos de un cliente existente
-- ---------------------------------------------------------
CREATE PROCEDURE actualizar_cliente (
    IN p_id_cliente INT,
    IN p_email VARCHAR(100),
    IN p_nombre VARCHAR(100),
    IN p_domicilio VARCHAR(150),
    IN p_nif VARCHAR(20),
    IN p_tipo_cliente ENUM('estandar', 'premium')
)
BEGIN
    UPDATE clientes
    SET email = p_email,
        nombre = p_nombre,
        domicilio = p_domicilio,
        nif = p_nif,
        tipo_cliente = p_tipo_cliente
    WHERE id_cliente = p_id_cliente;
END $$

-- ---------------------------------------------------------
-- Eliminar un cliente de la tabla clientes por su ID
-- ---------------------------------------------------------
CREATE PROCEDURE eliminar_cliente (
    IN p_id_cliente INT
)
BEGIN
    DELETE FROM clientes
    WHERE id_cliente = p_id_cliente;
END $$


-- =========================================================
-- PROCEDIMIENTOS ALMACENADOS - TABLA ARTICULOS
-- =========================================================

-- ---------------------------------------------------------
-- Insertar un nuevo artículo en la tabla articulos
-- ---------------------------------------------------------
CREATE PROCEDURE insertar_articulo (
    IN p_codigo VARCHAR(50),
    IN p_descripcion VARCHAR(200),
    IN p_precio_venta DECIMAL(10,2),
    IN p_gastos_envio DECIMAL(10,2),
    IN p_tiempo_preparacion INT
)
BEGIN
    INSERT INTO articulos (
        codigo,
        descripcion,
        precio_venta,
        gastos_envio,
        `tiempo_preparacion minutos`
    )
    VALUES (
        p_codigo,
        p_descripcion,
        p_precio_venta,
        p_gastos_envio,
        p_tiempo_preparacion
    );
END $$

-- ---------------------------------------------------------
-- Actualizar los datos de un artículo existente
-- ---------------------------------------------------------
CREATE PROCEDURE actualizar_articulo (
    IN p_id_articulo INT,
    IN p_codigo VARCHAR(50),
    IN p_descripcion VARCHAR(200),
    IN p_precio_venta DECIMAL(10,2),
    IN p_gastos_envio DECIMAL(10,2),
    IN p_tiempo_preparacion INT
)
BEGIN
    UPDATE articulos
    SET codigo = p_codigo,
        descripcion = p_descripcion,
        precio_venta = p_precio_venta,
        gastos_envio = p_gastos_envio,
        `tiempo_preparacion minutos` = p_tiempo_preparacion
    WHERE id_articulo = p_id_articulo;
END $$

-- ---------------------------------------------------------
-- Eliminar un artículo de la tabla articulos por su ID
-- ---------------------------------------------------------
CREATE PROCEDURE eliminar_articulo (
    IN p_id_articulo INT
)
BEGIN
    DELETE FROM articulos
    WHERE id_articulo = p_id_articulo;
END $$


-- =========================================================
-- PROCEDIMIENTOS ALMACENADOS - TABLA PEDIDOS
-- =========================================================

-- ---------------------------------------------------------
-- Insertar un nuevo pedido en la tabla pedidos
-- La fecha y hora del pedido se asignan automáticamente
-- con la función NOW()
-- ---------------------------------------------------------
CREATE PROCEDURE insertar_pedido (
    IN p_id_cliente INT,
    IN p_id_articulo INT,
    IN p_cantidad INT,
    IN p_estado VARCHAR(20)
)
BEGIN
    INSERT INTO pedidos (id_cliente, id_articulo, cantidad, fecha_hora, estado)
    VALUES (p_id_cliente, p_id_articulo, p_cantidad, NOW(), p_estado);
END $$

-- ---------------------------------------------------------
-- Actualizar los datos principales de un pedido existente
-- ---------------------------------------------------------
CREATE PROCEDURE actualizar_pedido (
    IN p_id_pedido INT,
    IN p_id_cliente INT,
    IN p_id_articulo INT,
    IN p_cantidad INT,
    IN p_estado VARCHAR(20)
)
BEGIN
    UPDATE pedidos
    SET id_cliente = p_id_cliente,
        id_articulo = p_id_articulo,
        cantidad = p_cantidad,
        estado = p_estado
    WHERE id_pedido = p_id_pedido;
END $$

-- ---------------------------------------------------------
-- Eliminar un pedido de la tabla pedidos por su ID
-- ---------------------------------------------------------
CREATE PROCEDURE eliminar_pedido (
    IN p_id_pedido INT
)
BEGIN
    DELETE FROM pedidos
    WHERE id_pedido = p_id_pedido;
END $$

DELIMITER ;