-- ============================================
--  ValleTech - Script de Base de Datos
-- ============================================

CREATE DATABASE IF NOT EXISTS valletech_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE valletech_db;

-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    contrasena VARCHAR(100) NOT NULL,
    nombre VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de productos
CREATE TABLE IF NOT EXISTS productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Usuarios de prueba (contraseña: admin123 / user123)
INSERT INTO usuarios (usuario, contrasena, nombre) VALUES
('admin', 'admin123', 'Administrador'),
('oscar', 'oscar123', 'Oscar Developer');

-- Productos de ejemplo
INSERT INTO productos (nombre, categoria, precio, stock) VALUES
('Laptop HP 15"',        'Computadoras',  2499.90, 15),
('Mouse Inalámbrico',    'Periféricos',     49.90, 80),
('Teclado Mecánico',     'Periféricos',    189.90, 45),
('Monitor 24" FHD',      'Monitores',      749.90, 20),
('Auriculares Bluetooth','Audio',          129.90, 60),
('Webcam HD 1080p',      'Periféricos',     89.90, 35),
('SSD 500GB',            'Almacenamiento', 199.90, 50),
('RAM 16GB DDR4',        'Componentes',    149.90, 40);
