CREATE TABLE usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR (100) NOT NULL
);

CREATE TABLE libros (
    id INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR (100),
    autor VARCHAR (50),
    fecha_publicacion DATE,
    valoracion DECIMAL (4,2),
    idUsuario INT DEFAULT NULL,
    FOREIGN KEY(idUsuario) REFERENCES usuarios(id)
);