package adat.ud2.biblioteca;

//import java.sql.Connection;
//import java.sql.SQLException;
//import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class AppBiblioteca {
    public static void main(String[] args) {

        //Connection con = ConexionBD.conectar();

        //try (Statement statement = con.createStatement();) {

            /*String sql = """
                    CREATE TABLE libros (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      titulo VARCHAR (100),
                      fecha_publicacion DATE,
                      valoracion DECIMAL (4,2)
                    );
                    """;

            System.out.println(statement.executeUpdate(sql));

            sql = "INSERT INTO libros VALUES (DEFAULT, 'Una historia sin fin', '2000-7-13', 5.)";
            statement.executeUpdate(sql);
            sql = "INSERT INTO libros VALUES (DEFAULT, 'Rosa y sus amigos', '1998-5-20', NULL)";
            statement.executeUpdate(sql);*/

            // INSERTAR (Create)
            LibroDTO libro = new LibroDTO(3, "Un día entre estrellas", LocalDate.of(2016, 2, 1), 4.25);
            if (LibroDAO.insertar(libro)) {
                System.out.println("Libro insertado correctamente");
            } else {
                System.out.println("Error al insertar el libro: " + libro);
            }
            

            // LISTAR ALUMNADO (Read)
            System.out.println("\nLIBROS");
            System.out.println("=======\n");
            List<LibroDTO> libros = LibroDAO.findAll();
            libros.forEach(System.out::println);



            // RECUPERAR (Read)
            Scanner sc = new Scanner(System.in);
            System.out.print("Id del libro a recuperar: ");
            int id = sc.nextInt();
            LibroDTO libro2 = LibroDAO.find(new LibroDTO(id));
            if (libro2 != null) {
                System.out.println(libro2);
            } else {
                System.out.println("Error al recuperar el libro (o no existe)");
            }


            // ACTUALIZAR (Update)
            System.out.println("Vamos a ponerle una valoración media de 5 estrellas!!");
            libro2.setValoracion(5.);
            if (LibroDAO.actualizar(libro2)) {
                System.out.println("Libro actualizado correctamente");
            } else {
                System.out.println("Error al actualizar el libro");
            }            
            
            // BORRAR (Delete)
            System.out.print("Id del libro a borrar: ");
            id = sc.nextInt();
            if (LibroDAO.borrar(new LibroDTO(id))) {
                System.out.println("Libro borrado correctamente");
            } else {
                System.out.println("Error al borrar el libro");
            }
            sc.close();


            // LISTAR ALUMNADO
            System.out.println("\nLIBROS");
            System.out.println("=======\n");
            libros = LibroDAO.findAll();
            libros.forEach(System.out::println);            

        /*} catch (SQLException e) {
            e.printStackTrace();
        }*/
    }
}
