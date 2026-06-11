/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  Usuario
 * Created: 11 jun 2026
 */

-- 1. Crear la base de datos
DROP DATABASE IF EXISTS food_store;
CREATE DATABASE food_store;
USE food_store;

-- 2. Tabla Categorías (Épica 1)
CREATE TABLE categorias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion TEXT,
    eliminado BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Tabla Productos (Épica 2)
CREATE TABLE productos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio DOUBLE NOT NULL,
    descripcion TEXT,
    stock INT NOT NULL,
    imagen VARCHAR(255),
    disponible BOOLEAN DEFAULT TRUE,
    categoria_id BIGINT,
    eliminado BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);

-- 4. Tabla Usuarios (Épica 3)
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    apellido VARCHAR(100),
    mail VARCHAR(100) NOT NULL UNIQUE,
    celular VARCHAR(20),
    contrasenia VARCHAR(100) NOT NULL,
    rol VARCHAR(20), -- ADMIN / USUARIO
    eliminado BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 5. RELACIÓN 1 A 1 (Exigida por Rúbrica para no tener 0)
-- Vamos a crear un Perfil Detalle para cada Usuario.
CREATE TABLE perfiles_detalles (
    usuario_id BIGINT PRIMARY KEY,
    direccion VARCHAR(255),
    ciudad VARCHAR(100),
    codigo_postal VARCHAR(10),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- 6. Tabla Pedidos (Épica 4)
CREATE TABLE pedidos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    estado VARCHAR(20), -- PENDIENTE, CONFIRMADO, TERMINADO, CANCELADO
    total DOUBLE DEFAULT 0,
    forma_pago VARCHAR(20), -- TARJETA, TRANSFERENCIA, EFECTIVO
    usuario_id BIGINT,
    eliminado BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- 7. Tabla Detalles Pedidos (Composición de Pedidos)
CREATE TABLE detalles_pedidos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cantidad INT NOT NULL,
    subtotal DOUBLE NOT NULL,
    pedido_id BIGINT,
    producto_id BIGINT,
    eliminado BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (pedido_id) REFERENCES pedidos(id),
    FOREIGN KEY (producto_id) REFERENCES productos(id)
);

