package adat.ud2.biblioteca;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class AppBiblioteca {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Connection con = ConexionBD.conectar();
        LibroDAOtran libroDAO = new LibroDAOtran(con);
        boolean continuar = true;
        int opcion = -1;
        do {
            System.out.println("MENU LIBROS");
            System.out.println("0. Crear Tabla/BD");
            System.out.println("1. Insertar");
            System.out.println("2. Listar");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Actualizar");
            System.out.println("5. Eliminar");
            System.out.println("6. TRANSACCION PRUEBA: Insertar Lista de alumnos");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");
            try {
                opcion = sc.nextInt();
            } catch (Exception e) { 
                opcion = -1;
            }
            sc.nextLine();
            switch (opcion) {
                case 0:
                    // Crear Tabla/BD

                    if (crearTablas(con)) {
                        System.out.println("Tabla creada con exito");
                    } else {
                        System.out.println("La tabla ya existe");
                    }
                    break;
                case 1:
                    // Insertar
                    
                    if (insertar(sc, libroDAO)) {
                        System.out.println("Inserción correcta");
                    } else {
                        System.out.println("Error insertando");
                    }
                case 2:
                    // Listar

                    listar(sc, libroDAO);
                    break;
                case 3:
                    // Buscar por ID

                    LibroDTO libro = findById(sc, libroDAO);
                    if (libro != null) {
                        System.out.println("LIBRO ENCONTRADO: " + libro);
                    } else {
                        System.out.println("Error al recuperar el libro (o no existe)");
                    }
                    break;
                case 4:
                    // Actualizar

                    if (actualizar(sc, libroDAO)) {
                        System.out.println("Actualización correcta");
                    } else {
                        System.out.println("Operación cancelada");
                    }
                    break;
                case 5:
                    // Eliminar
                    
                    if(eliminar(sc, libroDAO)) {
                        System.out.println("Libro borrado correctamente");
                    } else {
                        System.out.println("Error al borrar el libro");
                    }
                    break;
                case 6:
                    // Transacción

                    boolean hasSucceded = false;
                    libroDAO.beginTransaction();
                    //TODO añadir varios usuarios y/o libros
                    libroDAO.endTransaction(hasSucceded);
                    break;
                case 7:
                    // Salir
                    
                    continuar = false;
                    System.out.println("Saliendo del programa...");
                    return;
                default:
                    System.out.println("Opción no válida\n");
            }
        } while (continuar);

        sc.close();
        try {
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void listar(Scanner sc, LibroDAOtran libroDAO) {
        System.out.println("Que quiere listar?\nOPCIONES");
        System.out.println("0 - Todo\n1- Libros\n2 - Usuarios\nOtro - Cancelar");
        System.out.print("Seleccione una opción: ");
        int opcion;
        try {
            opcion = sc.nextInt();
        } catch (Exception e) { 
            opcion = -1;
        }
        sc.nextLine();
        switch (opcion) {
            case 0:
                listarLibros(libroDAO);
                listarUsuarios(libroDAO);
                break;

            case 1:
                listarLibros(libroDAO);
                break;

            case 2:
                listarUsuarios(libroDAO);
                break;

            default:
                System.out.println("Operacion cancelada");
                break;
        }
    }

    private static void listarUsuarios(LibroDAOtran libroDAO) {
        List<UsuarioDTO> usuarios = libroDAO.findAllUsuarios();
        if (usuarios.size() > 0) {
            System.out.println("\nUSUARIOS\n==========");
            usuarios.forEach(System.out::println);
        } else {
            System.out.println("No hay libros guardados");
        }
    }

    private static void listarLibros(LibroDAOtran libroDAO) {
        List<LibroDTO> libros = libroDAO.findAllLibros();
        if (libros.size() > 0) {
            System.out.println("\nLIBROS\n==========");
            libros.forEach(System.out::println);
        } else {
            System.out.println("No hay libros guardados");
        }
    }


    private static boolean insertar(Scanner sc, LibroDAOtran libroDAO) {
        boolean hasSucceded = false;
        System.out.println("Que quiere insertar?\nOPCIONES");
        System.out.println("0- Libro\n1 - Usuario\nOtro - Cancelar");
        System.out.print("Seleccione una opción: ");
        int opcion;
        try {
            opcion = sc.nextInt();
        } catch (Exception e) { 
            opcion = -1;
        }
        sc.nextLine();
        switch (opcion) {
            case 0:
                LibroDTO libro = pedirLibro(sc);
                if (libroDAO.insertar(libro)) {
                    hasSucceded = true;
                }
                break;

            case 1:
                UsuarioDTO us = pedirUsuario(sc);
                if (libroDAO.insertar(us)) {
                    hasSucceded = true;
                }
                break;

            default:
                System.out.println("Operacion cancelada");
                break;
        }
        return hasSucceded;
    }

    private static boolean eliminar(Scanner sc, LibroDAOtran libroDAO) {
        System.out.print("Id del libro a borrar: ");
        try {
            Integer id = pedirInt(sc);
            id = id == null ? -1 : id;
            return libroDAO.borrar(new LibroDTO(id));
        } catch (Exception e) {
            sc.nextLine();
            System.out.println("Id no válida");
            return false;
        }
    }

    private static boolean actualizar(Scanner sc, LibroDAOtran libroDAO) {
        boolean hasSucceded = false;
        System.out.println("Que quiere insertar?\nOPCIONES");
        System.out.println("0- Libro\n1 - Usuario\nOtro - Cancelar");
        System.out.print("Seleccione una opción: ");
        int opcion;
        try {
            opcion = sc.nextInt();
        } catch (Exception e) { 
            opcion = -1;
        }
        sc.nextLine();
        switch (opcion) {
            case 0:
                System.out.println("Id del libro que quiere actualizar?");
                Integer id = pedirInt(sc);
                id = id == null ? -1 : id;
                LibroDTO libro = libroDAO.find(new LibroDTO(id));
                boolean isSuccess = libro != null;
                if (isSuccess) {
                    System.out.println("Que parametro quiere modificar?\nOPCIONES");
                    System.out.println("0- Todos\n1 - Titulo\n2 - Autor\n3 - Fecha\n4 - Valoración\n5 - Poseedor\nOtro - Cancelar");
                    int nParam;
                    try {
                        nParam = sc.nextInt();
                    } catch (Exception e) {
                        nParam = -1;
                    }
                    sc.nextLine();
                    switch (nParam) {
                        case 0:
                            LibroDTO libro2 = pedirLibro(sc);
                            libro = new LibroDTO(libro.getId(), libro2.getTitulo(), libro2.getAutor(), libro2.getFechaPub(), libro2.getValoracion(), libro2.getUsuario());
                            break;

                        case 1:
                            libro.setTitulo(sc.nextLine());
                            break;

                        case 2:
                            libro.setAutor(pedirAutor(sc));
                            break;

                        case 3:
                            libro.setFechaPub(pedirFecha(sc));
                            break;

                        case 4:
                            if (!libro.setValoracion(pedirValoracion(sc))) {
                                System.out.println("Error asignando la Valoración");
                                isSuccess = false;
                            }
                            break;

                        case 5:
                            //TODO elegirPoseedor
                            break;

                        default:
                            isSuccess = false;
                            break;
                    }
                }
                if (isSuccess) {
                    if (!libroDAO.actualizar(libro)) {
                        isSuccess = false;
                        System.out.println("Error al actualizar el libro");
                    }
                    hasSucceded = isSuccess;
                }
                break;

            case 1:
                //TODO acutaulizar usuario
                break;

            default:
                System.out.println("Operacion cancelada");
                break;
        }
        
        return hasSucceded;
    }
    private static LibroDTO findById(Scanner sc, LibroDAOtran libroDAO) {
        System.out.print("Id del libro a recuperar: ");
        Integer id = pedirInt(sc);
        id = (id == null) ? -1 : id;
        return libroDAO.find(new LibroDTO(id));
    }

    private static boolean crearTablas(Connection con) {
        try (Statement statement = con.createStatement();) {
            String sqlCreateUsuarios = """
                    CREATE TABLE usuarios (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      nombre VARCHAR (100),
                    );
                    """;
            statement.executeUpdate(sqlCreateUsuarios);
            String sqlCreateLibros = """
                    CREATE TABLE libros (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      titulo VARCHAR (100),
                      autor VARCHAR (50),
                      fecha_publicacion DATE,
                      valoracion DECIMAL (4,2),
                      idUsuario INT DEFAULT NULL,
                    FOREIGN KEY(idUsuario) REFERENCES usuarios(id)
                    );
                    """;
            statement.executeUpdate(sqlCreateLibros);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private static LibroDTO pedirLibro(Scanner sc) {
        System.out.println("Titulo del libro?");
        String titulo = sc.nextLine();

        String autor = pedirAutor(sc);

        LocalDate fechaPub = pedirFecha(sc);

        Double valoracion = pedirValoracion(sc);

        return new LibroDTO(-1, titulo, autor, fechaPub, valoracion, null);
    }

    private static UsuarioDTO pedirUsuario(Scanner sc) {
        System.out.println("Nombre del usuario?");
        String nombre = sc.nextLine();

        return new UsuarioDTO(-1, nombre);
    }

    private static Integer pedirInt(Scanner sc){
        Integer id = null;
        try {
            id = sc.nextInt();
        } catch (Exception e) {
            id = null;
        }
        sc.nextLine();
        return id;
    }

    private static String pedirAutor(Scanner sc){
        System.out.println("Autor del libro? (-1 para desconocido)");
        String autor = sc.nextLine();
        if (autor.equals("-1") || autor.isEmpty()) {
            autor = null;
        }
        return autor;
    }

    private static LocalDate pedirFecha(Scanner sc) {
        LocalDate fechaPub = null;
        String fechaString;
        while (fechaPub == null) {
            System.out.println("Fecha de publicación? [yyyy-MM-dd]");
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

    private static Double pedirValoracion(Scanner sc) {
        Double valoracion = null;
        String opciones = "[0 - 5.0] (0 para dejar sin puntuación)";
        String msjErr = "Valoración no válida, " + opciones;
        while (valoracion == null) {
            try {
                System.out.println("Valoración media del libro? " + opciones);
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
