CREATE TABLE IF NOT EXISTS usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR (100) NOT NULL,
    correo VARCHAR (100) NOT NULL
);

CREATE TABLE IF NOT EXISTS generos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR (100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS libros (
    id INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR (100) NOT NULL,
    autor VARCHAR (50),
    fecha_publicacion DATE NOT NULL,
    valoracion DECIMAL (4,2),
    id_genero INT,
    copias_totales INT DEFAULT 1 NOT NULL,
    copias_disponibles INT DEFAULT 1 NOT NULL,
    FOREIGN KEY(id_genero) REFERENCES generos(id),
    UNIQUE (titulo, autor, fecha_publicacion)
);

CREATE TABLE IF NOT EXISTS prestamos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT NOT NULL,
    id_libro INT NOT NULL,
    FOREIGN KEY(id_usuario) REFERENCES usuarios(id),
    FOREIGN KEY(id_libro) REFERENCES libros(id),
    UNIQUE (id_usuario, id_libro)
);
