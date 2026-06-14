CREATE DATABASE IF NOT EXISTS valletech_db;
USE valletech_db;

CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    contrasena VARCHAR(100) NOT NULL,
    nombre VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO usuarios (usuario, contrasena, nombre) VALUES
('admin', 'admin123', 'Administrador'),
('oscar', 'oscar123', 'Oscar Developer');

INSERT INTO productos (nombre, categoria, precio, stock) VALUES
('Laptop HP 15"',         'Computadoras',   2499.90, 15),
('Mouse Inalámbrico',     'Periféricos',      49.90, 80),
('Teclado Mecánico',      'Periféricos',     189.90, 45),
('Monitor 24" FHD',       'Monitores',       749.90, 20),
('Auriculares Bluetooth', 'Audio',           129.90, 60),
('SSD 500GB',             'Almacenamiento',  199.90, 50);
SELECT * FROM usuarios;
SELECT * FROM productos;