package adat.ud2.biblioteca.old;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class AppBibliotecaOld {
    public static void main(String[] args) {
        try (Connection con = ConexionBDold.conectar()/*; Statement statement = con.createStatement();*/) {
            /*String sql = """
                    CREATE TABLE libros (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      titulo VARCHAR (100),
                      fecha_publicacion DATE,
                      valoracion DECIMAL (4,2)
                    );
                    """;

            statement.executeUpdate(sql);

            sql = "INSERT INTO libros VALUES (DEFAULT, 'Una historia sin fin', '2000-7-13', 5.)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO libros VALUES (DEFAULT, 'Rosa y sus amigos', '1998-5-20', NULL)";
            statement.executeUpdate(sql);*/

            // CREAR DAO
            LibroDAOtranOld libroDAO = new LibroDAOtranOld(con);

            // INSERTAR (Create)
            LibroDTOold libro = new LibroDTOold(3, "Un día entre estrellas", LocalDate.of(2016, 2, 1), 4.25);
            if (libroDAO.insertar(libro)) {
                System.out.println("Libro insertado correctamente");
            } else {
                System.out.println("Error al insertar el libro: " + libro);
            }
            

            // LISTAR ALUMNADO (Read)
            System.out.println("\nLIBROS");
            System.out.println("=======\n");
            List<LibroDTOold> libros = libroDAO.findAll();
            libros.forEach(System.out::println);



            // RECUPERAR (Read)
            Scanner sc = new Scanner(System.in);
            System.out.print("Id del libro a recuperar: ");
            int id = sc.nextInt();
            LibroDTOold libro2 = libroDAO.find(new LibroDTOold(id));
            if (libro2 != null) {
                System.out.println(libro2);
            } else {
                System.out.println("Error al recuperar el libro (o no existe)");
            }


            // ACTUALIZAR (Update)
            System.out.println("Vamos a ponerle una valoración media de 5 estrellas!!");
            libro2.setValoracion(5.);
            if (libroDAO.actualizar(libro2)) {
                System.out.println("Libro actualizado correctamente");
            } else {
                System.out.println("Error al actualizar el libro");
            }            
            
            // BORRAR (Delete)
            System.out.print("Id del libro a borrar: ");
            id = sc.nextInt();
            if (libroDAO.borrar(new LibroDTOold(id))) {
                System.out.println("Libro borrado correctamente");
            } else {
                System.out.println("Error al borrar el libro");
            }
            sc.close();


            // LISTAR ALUMNADO
            System.out.println("\nLIBROS");
            System.out.println("=======\n");
            libros = libroDAO.findAll();
            libros.forEach(System.out::println);            

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
