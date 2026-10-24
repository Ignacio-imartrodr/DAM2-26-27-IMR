package adat.ud2.CRUD_DAO_DTO_Profesor_Scarbmora.CRUD_DAO_DTO_Profesor_Scarbmora;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class tablaProfesor {
        static ProfesorDAO dao = new ProfesorDAO();

        public static void main(String[] args) {
                /*
                 * String url = "jdbc:h2:file:./BDs/Instituto/instituto;";
                 * String user = "sa";
                 * String password = "";
                 * 
                 * 
                 * try (Connection conexion = DriverManager.getConnection(url, user, password);
                 * Statement ejecutor = conexion.createStatement()) {
                 * 
                 * String sql =
                 * "INSERT INTO profesores VALUES (0, 'Pepe', 'García', '2000-12-12','Calle Sol','344565431')"
                 * ;
                 * ejecutor.executeUpdate(sql);
                 * sql =
                 * "INSERT INTO profesores VALUES (1, 'Marta', 'Castro', '2001-2-12','Avenida de la Paz','435465871')"
                 * ;
                 * ejecutor.executeUpdate(sql);
                 * sql =
                 * "INSERT INTO profesores VALUES (2, 'Lolo', 'Martínez', '2002-1-12','Gran Vía 45','894567238')"
                 * ;
                 * ejecutor.executeUpdate(sql);
                 * sql =
                 * "INSERT INTO profesores VALUES (3, 'Lola', 'López', '2003-4-20','Calle Luna 19','985634652')"
                 * ;
                 * ejecutor.executeUpdate(sql);
                 * sql =
                 * "INSERT INTO profesores VALUES (4, 'Juan', 'Vázquez', '2004-5-10','Calle Sol','656789324')"
                 * ;
                 * ejecutor.executeUpdate(sql);
                 * 
                 * } catch (Exception e) {
                 * e.printStackTrace();
                 * }
                 */

                Scanner sc = new Scanner(System.in);
                do {
                        System.out.println("MENU ALUMNOS");
                        System.out.println("0. Crear Tabla/BD");
                        System.out.println("1. Insertar");
                        System.out.println("2. Listar");
                        System.out.println("3. Buscar por ID");
                        System.out.println("4. Actualizar");
                        System.out.println("5. Eliminar");
                        System.out.println("6. Salir");
                        System.out.print("Seleccione una opción:");
                        int opcion = sc.nextInt();
                        sc.nextLine();
                        switch (opcion) {
                                case 0:
                                        // Crear Tabla/BD
                                        String url;
                                        String user;
                                        String password;
                                        System.out.print("Datos de tabla por defecto? [S/n]: ");
                                        Boolean datosUser = sc.nextLine().equalsIgnoreCase("n");

                                        if (datosUser) {
                                                url = "jdbc:h2:file:./";
                                                System.out.print("Introduce la url: ");
                                                url += sc.nextLine();
                                                System.out.print("Introduce el usuario: ");
                                                user = sc.nextLine();
                                                System.out.print("Introduce la contraseña: ");
                                                password = sc.nextLine();

                                        } else {
                                                url = "jdbc:h2:file:./BDs/Instituto/instituto;";
                                                user = "sa";
                                                password = "";
                                        }

                                        System.out.println(
                                                        crearTabla(url, user, password) >= 0 ? "Tabla creada con éxito"
                                                                        : "Error creando la tabla");
                                        break;
                                case 1:
                                        // Insertar
                                        System.out.print("Introduce el nombre: ");
                                        String nombre = sc.nextLine();
                                        System.out.print("Introduce los apellidos: ");
                                        String apellidos = sc.nextLine();
                                        System.out.print("Introduce la fecha de nacimiento: ");
                                        LocalDate fechaNacimiento = Date.valueOf(sc.nextLine()).toLocalDate();
                                        System.out.print("Introduce la dirección: ");
                                        String direccion = sc.nextLine();
                                        System.out.print("Introduce el teléfono :");
                                        String telefono = sc.nextLine();

                                        System.out.println(insertarProfesor(nombre, apellidos, fechaNacimiento,
                                                        direccion, telefono) ? "Profesor insertado con éxito"
                                                                        : "Eror insertando el profesor");
                                        break;
                                case 2:
                                        // Listar

                                        List<ProfesorDTO> lista = listarProfesor();

                                        for (ProfesorDTO profesorDTO : lista) {
                                                System.out.println(profesorDTO.toString());
                                        }
                                        break;
                                case 3:
                                        // Buscar por ID+
                                        System.out.print("Introduce el id del profesor: ");
                                        int id = sc.nextInt();
                                        ProfesorDTO profesor = buscarProfesorPorId(id);
                                        if (profesor == null) {
                                                System.out.println("Profesor no encontrado");
                                        } else {
                                                System.out.println(profesor.toString());
                                        }

                                        break;
                                case 4:
                                        // Actualizar

                                        System.out.println("Introduce el id del profesor a modificar: ");
                                        id = sc.nextInt();
                                        sc.nextLine();
                                        System.out.print("Introduce el nombre: ");
                                        nombre = sc.nextLine();
                                        System.out.print("Introduce los apellidos: ");
                                        apellidos = sc.nextLine();
                                        System.out.print("Introduce la fecha de nacimiento [yyyy-MM-dd]: ");
                                        fechaNacimiento = Date.valueOf(sc.nextLine()).toLocalDate();
                                        System.out.print("Introduce la dirección: ");
                                        direccion = sc.nextLine();
                                        System.out.print("Introduce el teléfono :");
                                        telefono = sc.nextLine();

                                        System.out.println(actualizarProfesor(id, nombre, apellidos, fechaNacimiento,
                                                        direccion, telefono) ? "Profesor actualizado con éxito"
                                                                        : "Eror actualizándo el profesor");
                                        break;
                                case 5:
                                        // Eliminar

                                        System.out.println("Introduce el id del profesor a borrar");
                                        id = sc.nextInt();
                                        System.out.println(eliminarProfesor(id) ? "Profesor borrado correctamente"
                                                        : "Error borrando el profesor");

                                        break;
                                case 6:
                                        // Salir
                                        sc.close();
                                        System.out.println("Saliendo del programa...");
                                        return;
                                default:
                                        System.out.println("Opción no válida");
                        }
                } while (true);

        }

        private static boolean eliminarProfesor(int id) {
                return dao.borrarProfesor(id);
        }

        private static boolean actualizarProfesor(int id, String nombre, String apellidos, LocalDate fechaNacimiento,
                        String direccion, String telefono) {

                ProfesorDTO profesor = new ProfesorDTO(id, nombre, apellidos, fechaNacimiento, direccion, telefono);
                return dao.actualizarProfesor(profesor);
        }

        private static ProfesorDTO buscarProfesorPorId(int id) {
                return dao.buscarProfesor(id);
        }

        private static List<ProfesorDTO> listarProfesor() {
                return dao.listarProfesores();

        }

        private static boolean insertarProfesor(String nombre, String apellidos, LocalDate fechaNacimiento,
                        String direccion, String telefono) {

                ProfesorDTO profesor = new ProfesorDTO(nombre, apellidos,
                                fechaNacimiento, direccion, telefono);

                return dao.insertarProfesor(profesor);
        }

        private static int crearTabla(String url, String user, String password) {

                try (Connection conexion = DriverManager.getConnection(url, user, password);
                                Statement ejecutor = conexion.createStatement()) {

                        String crearTablasProfesores = """
                                        Create table if not exists profesores(
                                        id int primary key auto_increment,
                                        nombre VARCHAR (50),
                                        apellidos VARCHAR (100),
                                        fecha_nacimiento DATE,
                                        direccion varchar(150),
                                        telefono char(9)
                                        )
                                        """;
                        return ejecutor.executeUpdate(crearTablasProfesores);

                } catch (Exception e) {
                        e.printStackTrace();
                }
                return 0;
        }
}
