CREATE TABLE libros (
    id INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR (100),
    autor VARCHAR (50),
    fecha_publicacion DATE,
    valoracion DECIMAL (4,2)
);