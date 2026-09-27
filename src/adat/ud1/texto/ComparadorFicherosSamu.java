package adat.ud1.texto;

import java.io.File;
import java.io.FileReader;
import java.util.List;

public class ComparadorFicherosSamu {
    public static void main(String[] args) {
        File origen1 = new File("DATOS\\alumnos.txt");
        File origen2 = new File("DATOS\\alumnos copy.txt");

        try (FileReader fichero1 = new FileReader(origen1);
                FileReader fichero2 = new FileReader(origen2)) {

            List<String> listaFichero1 = fichero1.readAllLines();
            List<String> listaFichero2 = fichero2.readAllLines();

            if (listaFichero1.equals(listaFichero2)) {// si los ficheron son iguales, sale del programa
                System.out.println("Los ficheros son iguales");
            } else {
                Boolean seguir = true;
                String respuesta = "";

                for (int i = 0; i < listaFichero1.size() && seguir; i++) {

                    if (!listaFichero1.get(i).equals(listaFichero2.get(i))) {

                        respuesta = "Los ficheros no son iguales, el caracter distinto está en la línea " + (i + 1);

                        String cadenaMasCorta = listaFichero1.get(i).compareTo(listaFichero2.get(i)) <= 0
                                ? listaFichero1.get(i)
                                : listaFichero2.get(i);

                        for (int j = 0; j < cadenaMasCorta.length() && seguir; j++) {

                            if (listaFichero1.get(i).charAt(j) != listaFichero2.get(i).charAt(j)) {

                                seguir = false;
                                respuesta += " y en la columna " + (j + 1);

                            }
                        }
                        if (seguir) {
                            respuesta += " y en la columna " + (cadenaMasCorta.length() + 1);
                        }

                    }
                }
                System.out.println(respuesta);

            }

        } catch (Exception e) {
            System.out.println("Se ha producido un error: " + e.getMessage());
        }
    }
}
