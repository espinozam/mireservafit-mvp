-- Script SQL para MiReservaFit

-- Crear base de datos
CREATE DATABASE IF NOT EXISTS mireservafit_bd;
USE mireservafit_bd;

-- Tabla usuario
CREATE TABLE usuario (
	id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    rol ENUM('CLIENTE', 'ENTRENADOR') NOT NULL,
    created_at DATETIME,
    updated_at DATETIME
);

-- Tabla cliente
CREATE TABLE cliente (
	id INT PRIMARY KEY,
    fecha_nacimiento DATE,
    FOREIGN KEY (id) REFERENCES usuario(id) ON DELETE CASCADE
);

-- Tabla entrenador
CREATE TABLE entrenador (
	id INT PRIMARY KEY,
    especialidad ENUM('Musculación', 'Crossfit', 'Yoga', 'Pilates', 'Boxeo'),
    FOREIGN KEY (id) REFERENCES usuario(id) ON DELETE CASCADE
);

-- Tabla reserva
CREATE TABLE reserva (
	id INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    id_entrenador INT NOT NULL,
    fecha_reserva DATE NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    estado ENUM('PENDIENTE', 'CONFIRMADO', 'CANCELADO', 'REALIZADO'),
    FOREIGN KEY (id_cliente) REFERENCES cliente(id) ON DELETE CASCADE,
    FOREIGN KEY (id_entrenador) REFERENCES entrenador(id) ON DELETE CASCADE
);
