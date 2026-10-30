CREATE TABLE IF NOT EXISTS alumnos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre VARCHAR(50) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    curso VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS registro_ejercicios (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre VARCHAR(50) NOT NULL,
    fecha DATE NOT NULL,
    id_alumno INTEGER NOT NULL,
    puntuacion INTEGER CHECK (puntuacion BETWEEN 0 AND 5),

    FOREIGN KEY id_alumno REFERENCES alumno(id)
);