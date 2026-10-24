Create table if not exists profesores (
    id int primary key auto_increment,
    nombre VARCHAR(50),
    apellidos VARCHAR(100),
    fecha_nacimiento DATE,
    direccion varchar(150),
    telefono char(9)
)