package adat.ud2.biblioteca;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import adat.ud2.biblioteca.daoYdto.genero.GeneroDAOtran;
import adat.ud2.biblioteca.daoYdto.genero.GeneroDTO;
import adat.ud2.biblioteca.daoYdto.libro.LibroDAOtran;
import adat.ud2.biblioteca.daoYdto.libro.LibroDTO;
import adat.ud2.biblioteca.daoYdto.prestamos.PrestamoDAOtran;
import adat.ud2.biblioteca.daoYdto.prestamos.PrestamoDTO;
import adat.ud2.biblioteca.daoYdto.usuario.UsuarioDAOtran;
import adat.ud2.biblioteca.daoYdto.usuario.UsuarioDTO;

public class AppBiblioteca {
    public static Scanner sc;
    public static Connection con;
    public static UsuarioDAOtran usuarioDAO;
    public static GeneroDAOtran generoDAO;
    public static LibroDAOtran libroDAO;
    public static PrestamoDAOtran prestamoDAO;

    public static void main(String[] args) {
        con = ConexionBD.conectar();
        if (con == null) {
            System.out.println("Error conectando con la base de datos");
            System.out.println("Cerrando programa...");
            return;
        }
        usuarioDAO = new UsuarioDAOtran(con);
        generoDAO = new GeneroDAOtran(con);
        libroDAO = new LibroDAOtran(con);
        prestamoDAO = new PrestamoDAOtran(con);
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
            System.out.println("6. TRANSACCION PRUEBA: Insertar usuarios, generos, libros y prestamos");
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
                    
                    //TODO añadir resto de casos
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

                    boolean isAll = false;
                    System.out.println("Que quiere listar?\nOPCIONES");
                    System.out.println("0 - Todo\n1- Libros\n2 - Usuarios\nOtro - Cancelar");
                    System.out.print("Seleccione una opción: ");
                    opcion = pedirInt();
                    opcion = opcion == null ? -1 : opcion;
                    switch (opcion) {
                        case 0: 
                            //Todo

                            isAll = true;

                        case 1:
                            //Libros

                            listarLibros();
                            if (!isAll) {
                                break;
                            }

                        case 2:
                            //Usuarios
                            
                            listarUsuarios();
                            if (!isAll) {
                                break;
                            }
                        
                        case 3:
                            //Generos

                            listarGeneros();
                            if (!isAll) {
                                break;
                            }

                        case 4:
                            //Prestamos
                            
                            listarPrestamos();
                            if (!isAll) {
                                break;
                            }
                        default:
                            if (isAll) {
                                break;
                            }
                            System.out.println("Operacion cancelada");
                            break;
                    }
                    break;
                case 3:
                    // Buscar por ID

                    //TODO añadir resto de casos
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

                    //TODO añadir resto de casos
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
                                        "0- Todos\n1 - Titulo\n2 - Autor\n3 - Fecha\n4 - Valoración\n5 - Genero\n6 - Copias\nOtro - Cancelar");
                                opcion = pedirInt();
                                opcion = opcion == null ? -1 : opcion;
                                switch (opcion) {
                                    case 0:
                                        LibroDTO libro2 = pedirLibro();
                                        libro = new LibroDTO(libro.getId(), libro2.getTitulo(), libro2.getAutor(),
                                                libro2.getFechaPub(), libro2.getValoracion(), libro2.getGenero(), libro2.getCopiasTotales(), libro2.getCopiasDisponibles());
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
                                        //TODO modificar genero
                                        break;

                                    case 6:
                                        //TODO modificar copias
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
                                isAll = false;
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
                    
                    //TODO añadir resto de casos
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
                        &&  generoDAO.insertar(new GeneroDTO("fantasía"))
                        &&  generoDAO.insertar(new GeneroDTO("acción"))
                        &&  generoDAO.insertar(new GeneroDTO("ciencia ficción"))
                        &&  libroDAO.insertar(new LibroDTO(-1, "Don Quijote de la Mancha", "Miguel de Cervantes", LocalDate.of(1605, 1, 16), 4.8,generoDAO.findById(1), 3, null))
                        &&  libroDAO.insertar(new LibroDTO("La casa de Bernarda Alba", "Federico García Lorca", LocalDate.of(1936, 6, 1), 4.6))
                        &&  libroDAO.insertar(new LibroDTO("Niebla", "Miguel de Unamuno", LocalDate.of(1914, 1, 1), 4.2));
                    //TODO añadir prestamos
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

    /* Idea interesante que se me ocurío para generalizar, falta testeo
    private static <T extends AbstractDTO, I extends AbstractDAOtran<T>> void listarDto(I dao) {
        T[] dtos = dao.findAll();
        T a = dtos[0];
        if (dtos != null && dtos.length > 0) {
            System.out.println("\n" + a.getClass().getSimpleName() + "\n==========");
            for (T dtoIt : dtos) {
                System.out.println(dtoIt);
            }
        } else {
            System.out.println("No hay libros guardados");
        }
    } 
    */

    private static void listarGeneros() {
        GeneroDTO[] generos = generoDAO.findAll();
        if (generos != null && generos.length > 0) {
            System.out.println("\nGENEROS\n==========");
            for (GeneroDTO genero : generos) {
                System.out.println(genero);
            }
        } else {
            System.out.println("No hay generos guardados");
        }
    }

    private static void listarPrestamos() {
        PrestamoDTO[] prestamos = prestamoDAO.findAll();
        if (prestamos != null && prestamos.length > 0) {
            System.out.println("\nPRESTAMOS\n==========");
            for (PrestamoDTO prestamo : prestamos) {
                System.out.println(prestamo);
            }
        } else {
            System.out.println("No hay prestamos guardados");
        }
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

            String sqlCreateGeneros = """
                    CREATE TABLE IF NOT EXISTS generos (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        nombre VARCHAR (100) NOT NULL UNIQUE
                    );
                    """;
            statement.executeUpdate(sqlCreateGeneros);

            String sqlCreateLibros = """
                    CREATE TABLE IF NOT EXISTS libros (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        titulo VARCHAR (100) NOT NULL,
                        autor VARCHAR (50),
                        fecha_publicacion DATE NOT NULL,
                        valoracion DECIMAL (4,2),
                        id_genero INT,
                        copias_totales INT DEFAULT 1 NOT NULL,
                        copias_disponibles INT DEFAULT 1 NOT NULL,
                        FOREIGN KEY(id_genero) REFERENCES genero(id)
                    );
                    """;
            statement.executeUpdate(sqlCreateLibros);
            
            String sqlCreatePrestamos = """
                    CREATE TABLE IF NOT EXISTS prestamos (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        id_usuario INT NOT NULL,
                        id_libro INT NOT NULL,
                        FOREIGN KEY(id_usuario) REFERENCES usuarios(id),
                        FOREIGN KEY(id_libro) REFERENCES libros(id)
                    );
                    """;
            statement.executeUpdate(sqlCreatePrestamos);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private static UsuarioDTO pedirUsuario() {
        System.out.print("Nombre del usuario?: ");
        String nombre = pedirStringNoVacía();

        System.out.print("Correo del usuario?: ");
        String correo = pedirStringNoVacía();

        return new UsuarioDTO(-1, nombre, correo);
    }

    private static LibroDTO pedirLibro() {
        System.out.print("Titulo del libro?: ");
        String titulo = pedirStringNoVacía();

        String autor = pedirAutor();

        LocalDate fechaPub = pedirFecha();

        Double valoracion = pedirValoracion();

        GeneroDTO gen = pedirGenero();

        System.out.println("Número de copias totales? [menor a 1 u Otro símbolo para Default(1)]");
        Integer copiasTotales = pedirInt();
        copiasTotales = copiasTotales < 1 ? null : copiasTotales;
        
        return new LibroDTO(titulo, autor, fechaPub, valoracion, gen, copiasTotales, null);
    }
    
    private static GeneroDTO pedirGenero(){
        System.out.print("Nombre del genero?: ");
        String nombre = pedirStringNoVacía();

        return new GeneroDTO(nombre);
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
