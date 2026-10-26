package adat.ud2.bibliotecaV2;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class AppBiblioteca {
    public static Scanner sc;
    public static Connection con;
    public static UsuarioDAOtran usuarioDAO;
    public static LibroDAOtran libroDAO;

    public static void main(String[] args) {
        con = ConexionBD.conectar();
        if (con == null) {
            System.out.println("Error conectando con la base de datos");
            System.out.println("Cerrando programa...");
            return;
        }
        usuarioDAO = new UsuarioDAOtran(con);
        libroDAO = new LibroDAOtran(con);
        sc = new Scanner(System.in);
        boolean continuar = true;
        Integer opcion;
        do {
            System.out.println("MENU LIBROS");
            System.out.println("0. Crear Tablas/BD");
            System.out.println("1. Insertar");
            System.out.println("2. Listar");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Actualizar");
            System.out.println("5. Eliminar");
            System.out.println("6. TRANSACCION PRUEBA: Insertar libros y usuarios");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = pedirInt();
            opcion = opcion == null ? -1 : opcion;
            switch (opcion) {
                case 0:
                    // Crear Tablas/BD

                    if (crearTablas(con)) {
                        System.out.println("Operación exitosa");
                    } else {
                        System.out.println("Error creando tablas");
                    }
                    break;
                case 1:
                    // Insertar
                    
                    System.out.println("Que quiere insertar?\nOPCIONES");
                    System.out.println("0- Libro\n1 - Usuario\nOtro - Cancelar");
                    System.out.print("Seleccione una opción: ");
                    opcion = pedirInt();
                    opcion = opcion == null ? -1 : opcion;
                    switch (opcion) {
                        case 0:
                            //Libro

                            LibroDTO libro = pedirLibro();
                            if (libroDAO.insertar(libro)) {
                                System.out.println("Inserción correcta");
                            } else {
                                System.out.println("Error insertando");
                            }
                            break;

                        case 1:
                            //Usuario

                            UsuarioDTO us = pedirUsuario();
                            if (usuarioDAO.insertar(us)) {
                                System.out.println("Inserción correcta");
                            } else {
                                System.out.println("Error insertando");
                            }
                            break;

                        default:
                            System.out.println("Operacion cancelada");
                            break;
                    }
                    break;
                case 2:
                    // Listar

                    System.out.println("Que quiere listar?\nOPCIONES");
                    System.out.println("0 - Todo\n1- Libros\n2 - Usuarios\nOtro - Cancelar");
                    System.out.print("Seleccione una opción: ");
                    opcion = pedirInt();
                    opcion = opcion == null ? -1 : opcion;
                    switch (opcion) {
                        case 0: 
                            //Todo

                            listarLibros();
                            listarUsuarios();
                            break;

                        case 1:
                            //Libro

                            listarLibros();
                            break;

                        case 2:
                            //Usuario
                            
                            listarUsuarios();
                            break;

                        default:
                            System.out.println("Operacion cancelada");
                            break;
                    }
                    break;
                case 3:
                    // Buscar por ID

                    System.out.println("Que quiere buscar?\nOPCIONES");
                    System.out.println("0- Libro\n1 - Usuario\nOtro - Cancelar");
                    System.out.print("Seleccione una opción: ");
                    opcion = pedirInt();
                    opcion = opcion == null ? -1 : opcion;
                    switch (opcion) {
                        case 0:
                            //Libro

                            System.out.print("Id del libro a recuperar: ");
                            Integer id = pedirInt();
                            id = (id == null) ? -1 : id;
                            LibroDTO libro = libroDAO.findById(id);
                            if (libro != null) {
                                System.out.println("LIBRO ENCONTRADO: " + libro);
                            } else {
                                System.out.println("Error al recuperar el libro (o no existe)");
                            }
                            break;

                        case 1:
                            //Usuario

                            System.out.print("Id del libro a recuperar: ");
                            id = pedirInt();
                            id = (id == null) ? -1 : id;
                            UsuarioDTO us = usuarioDAO.findById(id);
                            if (us != null) {
                                System.out.println("USUARIO ENCONTRADO: " + us);
                            } else {
                                System.out.println("Error al recuperar el usuario (o no existe)");
                            }
                            break;

                        default:
                            System.out.println("Operacion cancelada");
                            break;
                    }
                    break;
                case 4:
                    // Actualizar

                    System.out.println("Que quiere insertar?\nOPCIONES");
                    System.out.println("0- Libro\n1 - Usuario\nOtro - Cancelar");
                    System.out.print("Seleccione una opción: ");
                    opcion = pedirInt();
                    opcion = opcion == null ? -1 : opcion;
                    switch (opcion) {
                        case 0:
                            System.out.println("Id del libro que quiere actualizar?");
                            Integer id = pedirInt();
                            id = id == null ? -1 : id;
                            LibroDTO libro = libroDAO.findById(id);
                            boolean isSuccess = libro != null;
                            if (isSuccess) {
                                System.out.println("Que parametro quiere modificar?\nOPCIONES");
                                System.out.println(
                                        "0- Todos\n1 - Titulo\n2 - Autor\n3 - Fecha\n4 - Valoración\n5 - Poseedor\nOtro - Cancelar");
                                opcion = pedirInt();
                                opcion = opcion == null ? -1 : opcion;
                                switch (opcion) {
                                    case 0:
                                        LibroDTO libro2 = pedirLibro();
                                        libro = new LibroDTO(libro.getId(), libro2.getTitulo(), libro2.getAutor(),
                                                libro2.getFechaPub(), libro2.getValoracion(), libro2.getUsuario());
                                        break;

                                    case 1:
                                        System.out.print("Titulo del libro?: ");
                                        libro.setTitulo(pedirStringNoVacía());
                                        break;

                                    case 2:
                                        libro.setAutor(pedirAutor());
                                        break;

                                    case 3:
                                        libro.setFechaPub(pedirFecha());
                                        break;

                                    case 4:
                                        if (!libro.setValoracion(pedirValoracion())) {
                                            System.out.println("Error asignando la Valoración");
                                            isSuccess = false;
                                        }
                                        break;

                                    case 5:
                                        listarUsuarios();
                                        System.out.println("Id del usuario que lo va a poseer?");
                                        opcion = pedirInt();
                                        opcion = opcion == null ? -1 : opcion;
                                        UsuarioDTO us = usuarioDAO.findById(opcion);
                                        if (us == null) {
                                            System.out.println("Error al recuperar el usuario (o no existe)");
                                        } else {
                                            libro.setUsuario(us);
                                        }
                                        break;

                                    default:
                                        isSuccess = false;
                                        break;
                                }
                                if (!isSuccess) {
                                    System.out.println("Operación cancelada");
                                }
                            }
                            if (isSuccess) {
                                if (!libroDAO.actualizar(libro)) {
                                    System.out.println("Error al actualizar el libro");
                                }
                            }
                            break;

                        case 1:
                            System.out.println("Id del usuario que quiere actualizar?");
                            id = pedirInt();
                            id = id == null ? -1 : id;
                            UsuarioDTO us = usuarioDAO.findById(id);
                            isSuccess = us != null;
                            if (isSuccess) {
                                boolean isAll = false;
                                System.out.println("Que parametro quiere modificar?\nOPCIONES");
                                System.out.println("0- Todos\n1 - Nombre\n2 - Correo\nOtro - Cancelar");
                                opcion = pedirInt();
                                opcion = opcion == null ? -1 : opcion;
                                switch (opcion) {
                                    case 0:
                                        isAll = true;
                                    case 1:
                                        System.out.println("Nombre del usuario?");
                                        us.setNombre(pedirStringNoVacía());
                                        if (!isAll) {
                                            break;
                                        }
                                    case 2:
                                        System.out.println("Correo del usuario?");
                                        us.setCorreo(pedirStringNoVacía());
                                        break;

                                    default:
                                        System.out.println("Operacion cancelada");
                                        isSuccess = false;
                                        break;
                                }
                            }
                            if (isSuccess) {
                                if (!usuarioDAO.actualizar(us)) {
                                    System.out.println("Error al actualizar el usuario");
                                }
                            }

                            break;

                        default:
                            System.out.println("Operacion cancelada");
                            break;
                    }
                    break;
                case 5:
                    // Eliminar
                    
                    System.out.println("Que quiere buscar?\nOPCIONES");
                    System.out.println("0- Libro\n1 - Usuario\nOtro - Cancelar");
                    System.out.print("Seleccione una opción: ");
                    opcion = pedirInt();
                    opcion = opcion == null ? -1 : opcion;
                    switch (opcion) {
                        case 0:
                            //Libro

                            System.out.print("Id del libro a borrar: ");
                            opcion = pedirInt();
                            opcion = opcion == null ? -1 : opcion;
                            LibroDTO libro = libroDAO.findById(opcion);
                            if (libro != null) {
                                if(libroDAO.borrarById(opcion)) {
                                    System.out.println("Libro borrado correctamente");
                                } else {
                                    System.out.println("Error al borrar el libro");
                                }
                            } else {
                                System.out.println("Error al encotrar el libro (o no existe)");
                            }
                            break;

                        case 1:
                            //Usuario
                            
                            System.out.print("Id del usuario a borrar: ");
                            opcion = pedirInt();
                            opcion = opcion == null ? -1 : opcion;
                            UsuarioDTO us = usuarioDAO.findById(opcion);
                            if (us != null) {
                                if (usuarioDAO.borrarById(opcion)) {
                                    System.out.println("Usuario borrado correctamente");
                                } else {
                                    System.out.println("Para borrar este usuario primero debe devolver los libros que posee");
                                }
                            } else {
                                System.out.println("Error al encotrar el usuario (o no existe)");
                            }
                            break;

                        default:
                            System.out.println("Operacion cancelada");
                            break;
                    }
                    
                    break;
                case 6:
                    // Transacción

                    if (!usuarioDAO.beginTransaction()) {
                        System.out.println("No se pudo iniciar la transacción");
                        break;
                    }

                    boolean hasSucceded =
                            usuarioDAO.insertar(new UsuarioDTO("Ana García", "anagarcia@exemple.com"))
                        &&  usuarioDAO.insertar(new UsuarioDTO("Luis Pérez","luisperez@exemple.com"))
                        &&  usuarioDAO.insertar(new UsuarioDTO("Marta López","martalopez@example.com"))
                        &&  libroDAO.insertar(new LibroDTO("Don Quijote de la Mancha", "Miguel de Cervantes",
                                LocalDate.of(1605, 1, 16), 4.8, null))
                        &&  libroDAO.insertar(new LibroDTO("La casa de Bernarda Alba", "Federico García Lorca",
                                LocalDate.of(1936, 6, 1), 4.6, null))
                        &&  libroDAO.insertar(new LibroDTO( "Niebla", "Miguel de Unamuno",
                                LocalDate.of(1914, 1, 1), 4.2, null));
                    usuarioDAO.endTransaction(hasSucceded);
                    if (hasSucceded) {
                        System.out.println("Transacción completada: se insertaron 3 usuarios y 3 libros");
                    } else {
                        System.out.println("Error en la transacción, se ha cancelado la inserción");
                    }
                    break;
                case 7:
                    // Salir

                    System.out.println("Saliendo del programa...");
                    sc.close();
                    try {
                        con.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    continuar = false;
                    return;
                default:
                    System.out.println("Opción no válida\n");
            }
        } while (continuar);
        System.out.println("Gracias por usar la biblioteca");
    }

    private static void listarUsuarios() {
        UsuarioDTO[] usuarios = usuarioDAO.findAll();
        if (usuarios != null && usuarios.length > 0) {
            System.out.println("\nUSUARIOS\n==========");
            for (UsuarioDTO usuario : usuarios) {
                System.out.println(usuario);
            }
        } else {
            System.out.println("No hay usuarios guardados");
        }
    }

    private static void listarLibros() {
        LibroDTO[] libros = libroDAO.findAll();
        if (libros != null && libros.length > 0) {
            System.out.println("\nLIBROS\n==========");
            for (LibroDTO libro : libros) {
                System.out.println(libro);
            }
        } else {
            System.out.println("No hay libros guardados");
        }
    }

    private static boolean crearTablas(Connection con) {
        try (Statement statement = con.createStatement();) {
            String sqlCreateUsuarios = """
                    CREATE TABLE IF NOT EXISTS usuarios (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        nombre VARCHAR (100) NOT NULL,
                        correo VARCHAR (100) NOT NULL
                    );
                    """;
            statement.executeUpdate(sqlCreateUsuarios);
            String sqlCreateLibros = """
                    CREATE TABLE IF NOT EXISTS libros (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        titulo VARCHAR (100) NOT NULL,
                        autor VARCHAR (50),
                        fecha_publicacion DATE NOT NULL,
                        valoracion DECIMAL (4,2),
                        id_usuario INT DEFAULT NULL,
                        FOREIGN KEY(id_usuario) REFERENCES usuarios(id)
                    );
                    """;
            statement.executeUpdate(sqlCreateLibros);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private static LibroDTO pedirLibro() {
        System.out.print("Titulo del libro?: ");
        String titulo = pedirStringNoVacía();

        String autor = pedirAutor();

        LocalDate fechaPub = pedirFecha();

        Double valoracion = pedirValoracion();

        return new LibroDTO(-1, titulo, autor, fechaPub, valoracion, null);
    }

    private static UsuarioDTO pedirUsuario() {
        System.out.print("Nombre del usuario?: ");
        String nombre = pedirStringNoVacía();

        System.out.print("Correo del usuario?: ");
        String correo = pedirStringNoVacía();

        return new UsuarioDTO(-1, nombre, correo);
    }

    private static String pedirStringNoVacía(){
        String txt = sc.nextLine();
        while (txt.isBlank()) {
            System.out.println("Debe escribir algo");
            txt = sc.nextLine();
        }
        return txt;
    }

    private static Integer pedirInt(){
        Integer id = null;
        try {
            id = sc.nextInt();
        } catch (Exception e) {
            id = null;
        }
        sc.nextLine();
        return id;
    }

    private static String pedirAutor(){
        System.out.print("Autor del libro? (-1 para desconocido): ");
        String autor = sc.nextLine();
        if (autor.equals("-1") || autor.isEmpty()) {
            autor = null;
        }
        return autor;
    }

    private static LocalDate pedirFecha() {
        LocalDate fechaPub = null;
        String fechaString;
        while (fechaPub == null) {
            System.out.print("Fecha de publicación? [yyyy-MM-dd]: ");
            fechaString = sc.nextLine();
            try {
                fechaPub = LocalDate.parse(fechaString);
            } catch (DateTimeParseException e) {
                System.out.println("Formato incorrecto");
                fechaPub = null;
            }
        }
        return fechaPub;
    }

    private static Double pedirValoracion() {
        Double valoracion = null;
        String opciones = "[0 - 5.0] (0 para dejar sin puntuación)";
        String msjErr = "Valoración no válida, " + opciones;
        while (valoracion == null) {
            try {
                System.out.print("Valoración media del libro? " + opciones + ": ");
                try {
                    valoracion = sc.nextDouble();
                } catch (Exception e) {
                    valoracion = -1.;
                }
                sc.nextLine();
                if (valoracion < 0. || valoracion > 5.) {

                    System.out.println(msjErr);

                    valoracion = null;
                }
            } catch (Exception e) {
                System.out.println(msjErr);
            }
        }
        return valoracion;
    }
}
