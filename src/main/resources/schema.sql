CREATE TABLE IF NOT EXISTS
    producto (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR2(100),
    descripcion VARCHAR2(150),
    activo BOOLEAN DEFAULT true,
    id_Almacen INT,
    almacen VARCHAR2(100),
    precio NUMERIC(16,2),
    cantidad_Inventario INT,
    PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS
    almacen(
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR2(100),
    descripcion VARCHAR2(150),
    activo BOOLEAN DEFAULT true,
    PRIMARY KEY (id));


CREATE TABLE IF NOT EXISTS
    venta(
    id INT NOT NULL AUTO_INCREMENT,
    nombre_Producto VARCHAR2(100),
    id_Producto INT,
    precio_Venta NUMERIC(16,2),
    cantidad_Vendida INT,
    PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS
    entrega_Proveedor(
    id INT NOT NULL AUTO_INCREMENT,
    nombre_Producto VARCHAR2(100),
    id_Producto INT,
    precio_Compra NUMERIC(16,2),
    cantidad_Pedida INT,
    PRIMARY KEY (id));

