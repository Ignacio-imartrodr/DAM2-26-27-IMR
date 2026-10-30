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
        String pregunta;
        String opciones;
        boolean continuar = true;
        Integer opcionMenu;
        do {
            pregunta = "MENU LIBROS";
            opciones = """
                            0. Crear Tablas/BD
                            1. Insertar
                            2. Listar
                            3. Buscar por ID
                            4. Actualizar
                            5. Eliminar
                            6. TRANSACCION PRUEBA: Insertar usuarios, generos, libros y prestamos
                            7. Salir""";
            opcionMenu = pedirOpcion(pregunta, opciones);
            switch (opcionMenu) {
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
                    
                    pregunta ="Que quiere insertar?";
                    opciones = "OPCIONES\n0 - Libro\n1 - Usuario\n2 - Género\n3 - Préstamo\nOtro - Cancelar";
                    Integer opcionEleccion = pedirOpcion(pregunta, opciones);
                    switch (opcionEleccion) {
                        case 0:
                            //Libro

                            LibroDTO libro = pedirLibro();
                            if (libroDAO.insertar(libro) >= 0) {
                                System.out.println("Inserción correcta");
                            } else {
                                System.out.println("Error insertando libro");
                            }
                            break;

                        case 1:
                            //Usuario

                            UsuarioDTO us = pedirUsuario();
                            if (usuarioDAO.insertar(us) >= 0) {
                                System.out.println("Inserción correcta");
                            } else {
                                System.out.println("Error insertando usuario");
                            }
                            break;

                        case 2:
                            //Género

                            GeneroDTO genero = pedirGenero();
                            if (generoDAO.insertar(genero) >= 0) {
                                System.out.println("Inserción correcta");
                            } else {
                                System.out.println("Error insertando género");
                            }
                            break;

                        case 3:
                            //Préstamo

                            PrestamoDTO prest = pedirPrestamo();
                            if (prestamoDAO.insertar(prest) >= 0) {
                                System.out.println("Inserción correcta");
                            } else {
                                System.out.println("Error insertando préstamo");
                            }
                            break;
                        default:
                            System.out.println("Operación cancelada");
                            break;
                    }
                    break;
                case 2:
                    // Listar

                    boolean isAll = false;
                    
                    opcionEleccion = pedirOpcion("Que quiere listar?", "OPCIONES\n0 - Todo\n1 - Libros\n2 - Usuarios\n3 - Géneros\n4 - Préstamos\nOtro - Cancelar");
                    
                    switch (opcionEleccion) {
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
                            System.out.println("Operación cancelada");
                            break;
                    }
                    break;
                case 3:
                    // Buscar por ID

                    pregunta = "Que quiere buscar?";
                    opciones = "OPCIONES\n0 - Libro\n1 - Usuario\n2 - Género\n3 - Préstamo\nOtro - Cancelar";
                    opcionEleccion = pedirOpcion(pregunta, opciones);
                    
                    switch (opcionEleccion) {
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

                            System.out.print("Id del usuario a recuperar: ");
                            id = pedirInt();
                            id = (id == null) ? -1 : id;
                            UsuarioDTO us = usuarioDAO.findById(id);
                            if (us != null) {
                                System.out.println("USUARIO ENCONTRADO: " + us);
                            } else {
                                System.out.println("Error al recuperar el usuario (o no existe)");
                            }
                            break;

                        case 2:
                            //Género

                            System.out.print("Id del género a recuperar: ");
                            id = pedirInt();
                            id = (id == null) ? -1 : id;
                            GeneroDTO g = generoDAO.findById(id);
                            if (g != null) {
                                System.out.println("GÉNERO ENCONTRADO: " + g);
                            } else {
                                System.out.println("Error al recuperar el género (o no existe)");
                            }
                            break;

                        case 3:
                            //Préstamo

                            System.out.print("Id del préstamo a recuperar: ");
                            id = pedirInt();
                            id = (id == null) ? -1 : id;
                            PrestamoDTO p = prestamoDAO.findById(id);
                            if (p != null) {
                                System.out.println("PRÉSTAMO ENCONTRADO: " + p);
                            } else {
                                System.out.println("Error al recuperar el préstamo (o no existe)");
                            }
                            break;

                        default:
                            System.out.println("Operación cancelada");
                            opcionEleccion = -1;
                            break;
                    }
                    break;
                case 4:
                    // Actualizar
                    pregunta = "Que quiere insertar?";
                    opciones = "OPCIONES\n0 - Libro\n1 - Usuario\n2 - Género\n3 - Préstamo\nOtro - Cancelar";
                    opcionEleccion = pedirOpcion(pregunta, opciones);
                    
                    boolean isSuccess;
                    switch (opcionEleccion) {
                        case 0:
                            //Libro

                            System.out.println("Id del libro que quiere actualizar?");
                            Integer id = pedirInt();
                            id = id == null ? -1 : id;
                            LibroDTO libro = libroDAO.findById(id);
                            isSuccess = libro != null;
                            if (isSuccess) {
                                isAll = false;
                                System.out.println(libro.toString());
                                pregunta = "Que parámetro quiere modificar?";
                                opciones = "OPCIONES\n0 - Todos\n1 - Titulo\n2 - Autor\n3 - Fecha\n4 - Valoración\n5 - Género\n6 - Copias\nOtro - Cancelar";
                                opcionEleccion = pedirOpcion(pregunta, opciones);
                                
                                switch (opcionEleccion) {
                                    case 0:
                                        isAll = true;
                                    case 1:
                                        LibroDTO libro2 = pedirLibro();
                                        libro = new LibroDTO(libro.getId(), libro2.getTitulo(), libro2.getAutor(),
                                                libro2.getFechaPub(), libro2.getValoracion(), libro2.getGenero(), libro2.getCopiasTotales(), libro2.getCopiasDisponibles());
                                        if (!isAll) {
                                            break;
                                        }

                                    case 2:
                                        System.out.print("Titulo del libro?: ");
                                        libro.setTitulo(pedirStringNoVacía());
                                        if (!isAll) {
                                            break;
                                        }

                                    case 3:
                                        libro.setAutor(pedirAutor());
                                        if (!isAll) {
                                            break;
                                        }

                                    case 4:
                                        libro.setFechaPub(pedirFecha());
                                        if (!isAll) {
                                            break;
                                        }

                                    case 5:
                                        if (!libro.setValoracion(pedirValoracion())) {
                                            System.out.println("Error asignando la Valoración");
                                            isSuccess = false;
                                        }
                                        if (!isAll || !isSuccess) {
                                            break;
                                        }

                                    case 6:
                                        System.out.println("Id del género que quiere asignar? (Otro o -1 para quitarlo)");
                                        id = pedirInt();
                                        id = id == null ? -1 : id;
                                        libro.setGenero(generoDAO.findById(id));
                                        if (!isAll) {
                                            break;
                                        }

                                    case 7:
                                        System.out.println("Nuevo número de copias totales?");
                                        id = pedirInt();
                                        id = id == null ? -1 : id;
                                        if (libro.setCopiasTotales(id)) {
                                            System.out.println("Valor no válido o menor a la cantidad ya prestada");
                                            isSuccess = false;
                                        }
                                        if (!isAll || !isSuccess) {
                                            break;
                                        }

                                    default:
                                        if (isAll) {
                                            break;
                                        }
                                        isSuccess = false;
                                        break;
                                }
                                if (!isSuccess) {
                                    System.out.println("Operación cancelada");
                                    
                                }
                            }
                            if (isSuccess && !libroDAO.actualizar(libro)) {
                                System.out.println("Error al actualizar el préstamo");
                            }
                            break;

                        case 1:
                            //Usuario

                            System.out.println("Id del usuario que quiere actualizar?");
                            id = pedirInt();
                            id = id == null ? -1 : id;
                            UsuarioDTO us = usuarioDAO.findById(id);
                            isSuccess = us != null;
                            if (isSuccess) {
                                System.out.println(us.toString());
                                isAll = false;
                                pregunta = "Que parámetro quiere modificar?";
                                opciones = "OPCIONES\n0 - Todos\n1 - Nombre\n2 - Correo\nOtro - Cancelar";
                                opcionEleccion = pedirOpcion(pregunta, opciones);
                                switch (opcionEleccion) {
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
                                        if (isAll) {
                                            break;
                                        }
                                        isSuccess = false;
                                        break;
                                }
                                if (!isSuccess) {
                                    System.out.println("Operación cancelada");
                                    
                                }
                            }
                            if (isSuccess && !usuarioDAO.actualizar(us)) {
                                System.out.println("Error al actualizar el usuario");
                            }

                            break;

                        case 2:
                            //Género

                            System.out.println("Id del género que quiere actualizar?");
                            id = pedirInt();
                            id = id == null ? -1 : id;
                            GeneroDTO g = generoDAO.findById(id);
                            isSuccess = g != null;
                            if (isSuccess) {
                                System.out.println(g.toString());
                                System.out.println("Nombre del género?");
                                g.setNombre(pedirStringNoVacía());
                                /* 
                                isAll = false;
                                System.out.println("Que parámetro quiere modificar?\nOPCIONES");
                                System.out.println("0 - Todos\n1 - Nombre\n2 - Correo\nOtro - Cancelar");
                                opcion = pedirInt();
                                opcion = opcion == null ? -1 : opcion;
                                switch (opcion) {
                                    case 0:
                                        isAll = true;
                                    case 1:
                                        System.out.println("Nombre del género?");
                                        g.setNombre(pedirStringNoVacía());
                                        if (!isAll) {
                                            break;
                                        }
                                    default:
                                        if (isAll) {
                                            break;
                                        }
                                        isSuccess = false;
                                        break;
                                }
                                if (!isSuccess) {
                                    System.out.println("Operación cancelada");
                                    
                                }*/
                                if (isSuccess && !generoDAO.actualizar(g)) {
                                    System.out.println("Error al actualizar el género");
                                }
                            }

                            break;

                        case 3:
                            //Préstamo

                            System.out.println("Id del préstamo que quiere actualizar?");
                            id = pedirInt();
                            id = id == null ? -1 : id;
                            PrestamoDTO p = prestamoDAO.findById(id);
                            isSuccess = p != null;
                            if (isSuccess) {
                                System.out.println(p.toString());
                                isAll = false;
                                pregunta = "Que parámetro quiere modificar?";
                                opciones = "OPCIONES\n0 - Todos\n1 - Libro\n2 - Usuario\nOtro - Cancelar";
                                opcionEleccion = pedirOpcion(pregunta, opciones);
                                
                                switch (opcionEleccion) {
                                    case 0:
                                        isAll = true;
                                    case 1:
                                        listarUsuarios();
                                        System.out.println("Id del usuario que quiere asignar?");
                                        id = pedirInt();
                                        id = id == null ? -1 : id;
                                        us = usuarioDAO.findById(id);
                                        isSuccess = us != null;
                                        if (isSuccess) {
                                            p.setIdUsuario(us.getId());
                                        }
                                        if (!isAll || !isSuccess) {
                                            break;
                                        }
                                    case 2:
                                        listarLibros();
                                        System.out.println("Id del libro que quiere asignar?");
                                        id = pedirInt();
                                        id = id == null ? -1 : id;
                                        libro = libroDAO.findById(id);
                                        isSuccess = libro != null;
                                        if (isSuccess) {
                                            p.setIdLibro(libro.getId());
                                        }
                                        if (!isAll || !isSuccess) {
                                            break;
                                        }

                                    default:
                                        if (isAll) {
                                            break;
                                        }
                                        isSuccess = false;
                                        break;
                                }
                                if (!isSuccess) {
                                    System.out.println("Operación cancelada");
                                    
                                }
                            }
                            if (isSuccess && !prestamoDAO.actualizar(p)) {
                                System.out.println("Error al actualizar el préstamo");
                            }
                            break;

                        default:
                            System.out.println("Operación cancelada");
                            break;
                    }
                    break;
                case 5:
                    // Eliminar
                    
                    pregunta = "Que quiere eliminar?";
                    opciones = "OPCIONES\n0 - Libro\n1 - Usuario\n2 - Género\n3 - Préstamo\nOtro - Cancelar";
                    opcionEleccion = pedirOpcion(pregunta, opciones);
                    
                    switch (opcionEleccion) {
                        case 0:
                            //Libro

                            System.out.print("Id del libro a borrar: ");
                            opcionEleccion = pedirInt();
                            opcionEleccion = opcionEleccion == null ? -1 : opcionEleccion;
                            LibroDTO libro = libroDAO.findById(opcionEleccion);
                            if (libro != null) {
                                if(libroDAO.borrarById(opcionEleccion)) {
                                    System.out.println("Libro borrado correctamente");
                                } else {
                                    System.out.println("Para borrar este libro primero deben ser devueltas todas las copias");
                                }
                            } else {
                                System.out.println("Error al encotrar el libro (o no existe)");
                            }
                            break;

                        case 1:
                            //Usuario
                            
                            System.out.print("Id del usuario a borrar: ");
                            opcionEleccion = pedirInt();
                            opcionEleccion = opcionEleccion == null ? -1 : opcionEleccion;
                            UsuarioDTO us = usuarioDAO.findById(opcionEleccion);
                            if (us != null) {
                                if (usuarioDAO.borrarById(opcionEleccion)) {
                                    System.out.println("Usuario borrado correctamente");
                                } else {
                                    System.out.println("Para borrar este usuario primero debe devolver los libros que posee");
                                }
                            } else {
                                System.out.println("Error al encotrar el usuario (o no existe)");
                            }
                            break;

                        case 2:
                            //Género
                            
                            System.out.print("Id del género a borrar: ");
                            opcionEleccion = pedirInt();
                            opcionEleccion = opcionEleccion == null ? -1 : opcionEleccion;
                            GeneroDTO g = generoDAO.findById(opcionEleccion);
                            if (g != null) {
                                if (generoDAO.borrar(g)) {
                                    System.out.println("Genero borrado correctamente");
                                } else {
                                    System.out.println("Para borrar este género primero debe quitarlo de los libros que tiene asigandos");
                                }
                            } else {
                                System.out.println("Error al encotrar el genero (o no existe)");
                            }
                            break;

                        case 3:
                            //Prestamo
                            
                            System.out.print("Id del préstamo a borrar: ");
                            opcionEleccion = pedirInt();
                            opcionEleccion = opcionEleccion == null ? -1 : opcionEleccion;
                            PrestamoDTO p = prestamoDAO.findById(opcionEleccion);
                            if (p != null) {
                                if (prestamoDAO.borrarById(opcionEleccion)) {
                                    System.out.println("Préstamo borrado correctamente");
                                } else {
                                    System.out.println("Error borrando préstamo");
                                }
                            } else {
                                System.out.println("Error al encotrar el préstamo (o no existe)");
                            }
                            break;

                        default:
                            System.out.println("Operación cancelada");
                            opcionEleccion = -1;
                            break;
                    }
                    
                    break;
                case 6:
                    // Transacción

                    try {
                        con.setAutoCommit(false);
                    } catch (SQLException e) {
                        System.out.println("No se pudo iniciar la transacción");
                        break;
                    }
                    boolean hasSucceded = false;
                    int[] idLibros = new int[3];
                    int[] idUsuarios = new int[3];
                    int idGenero;
                    try {
                        hasSucceded = (idUsuarios[0] = usuarioDAO.insertar(new UsuarioDTO("Ana García", "anagarcia@exemple.com"))) >= 0
                        &&  (idUsuarios[1] = usuarioDAO.insertar(new UsuarioDTO("Luis Pérez","luisperez@exemple.com"))) >= 0
                        &&  (idUsuarios[2] = usuarioDAO.insertar(new UsuarioDTO("Marta López","martalopez@example.com"))) >= 0
                        &&  (idGenero = generoDAO.insertar(new GeneroDTO("fantasía"))) >= 0
                        &&  (generoDAO.insertar(new GeneroDTO("acción")) >= 0)
                        &&  (generoDAO.insertar(new GeneroDTO("ciencia ficción")) >= 0)
                        &&  (idLibros[0] = libroDAO.insertar(new LibroDTO(-1, "Don Quijote de la Mancha", "Miguel de Cervantes", LocalDate.of(1605, 1, 16), 4.8, generoDAO.findById(idGenero), 3, null))) >= 0
                        &&  (idLibros[1] = libroDAO.insertar(new LibroDTO("La casa de Bernarda Alba", "Federico García Lorca", LocalDate.of(1936, 6, 1), 4.6))) >= 0
                        &&  (idLibros[2] = libroDAO.insertar(new LibroDTO("Niebla", "Miguel de Unamuno", LocalDate.of(1914, 1, 1), 4.2))) >= 0
                        &&  (prestamoDAO.insertar(new PrestamoDTO(idUsuarios[0], idLibros[1])) >= 0)
                        &&  (prestamoDAO.insertar(new PrestamoDTO(idUsuarios[0],  idLibros[2])) >= 0)
                        &&  (prestamoDAO.insertar(new PrestamoDTO(idUsuarios[2],  idLibros[0])) >= 0);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    
                    try {
                        if (hasSucceded) {
                            con.commit();
                            System.out.println("Transacción completada: se insertaron 3 libros, 3 usuarios, 3 géneros y 3 préstamos");
                        } else {
                            con.rollback();
                            System.out.println("Error en la transacción, se ha cancelado la inserción");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        try {
                            con.rollback();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    } finally {
                        try {
                            con.setAutoCommit(true);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
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

    private static int pedirOpcion(String pregunta, String opciones) {
        Integer opcion;
        System.out.println("\n" + pregunta + "\n" + opciones);
        System.out.print("Seleccione una opción: ");
        opcion = pedirInt();
        System.out.println();
        opcion = opcion == null ? -1 : opcion;
        return opcion;
    }

    /* Idea interesante que se me ocurío para generalizar, falta testeo
    private static <T extends AbstractDTO, I extends AbstractDAOtran<T>> void listarDto(I dao) {
        T[] dtos = dao.findAll();
        if (dtos != null && dtos.length > 0) {
            System.out.println("\n" + dao.getTableName().toUppercase + "\n==========");
            for (T dtoIt : dtos) {
                System.out.println(dtoIt);
            }
        } else {
            System.out.println("No hay " + dao.getTableName() + " guardados");
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
                    );""";
            statement.executeUpdate(sqlCreateUsuarios);

            String sqlCreateGeneros = """
                    CREATE TABLE IF NOT EXISTS generos (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        nombre VARCHAR (100) NOT NULL UNIQUE
                    );""";
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
                        FOREIGN KEY(id_genero) REFERENCES generos(id),
                        UNIQUE (titulo, autor, fecha_publicacion)
                    );""";
            statement.executeUpdate(sqlCreateLibros);
            
            String sqlCreatePrestamos = """
                    CREATE TABLE IF NOT EXISTS prestamos (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        id_usuario INT NOT NULL,
                        id_libro INT NOT NULL,
                        FOREIGN KEY(id_usuario) REFERENCES usuarios(id),
                        FOREIGN KEY(id_libro) REFERENCES libros(id),
                        UNIQUE (id_usuario, id_libro)
                    );""";
            statement.executeUpdate(sqlCreatePrestamos);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
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

    private static PrestamoDTO pedirPrestamo() {
        listarLibros();
        System.out.print("Id del libro a recuperar: ");
        Integer id = pedirInt();
        id = (id == null) ? -1 : id;
        LibroDTO libro = libroDAO.findById(id);
        if (libro == null) {
            System.out.println("Error al recuperar el libro (o no existe)");
            return null;
        }

        listarUsuarios();
        System.out.print("Id del usuario a recuperar: ");
        id = pedirInt();
        id = (id == null) ? -1 : id;
        UsuarioDTO us = usuarioDAO.findById(id);
        if (us == null) {
            System.out.println("Error al recuperar el usuario (o no existe)");
            return null;
        }

        return new PrestamoDTO(us.getId(), libro.getId());
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
