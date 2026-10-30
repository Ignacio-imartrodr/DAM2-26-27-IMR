CREATE TABLE IF NOT EXISTS usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR (100) NOT NULL,
    correo VARCHAR (100) NOT NULL
);

CREATE TABLE IF NOT EXISTS libros (
    id INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR (100) NOT NULL,
    autor VARCHAR (50),
    fecha_publicacion DATE NOT NULL,
    valoracion DECIMAL (4,2),
    id_usuario INT DEFAULT NULL,
    FOREIGN KEY(id_usuario) REFERENCES usuarios(id)
);