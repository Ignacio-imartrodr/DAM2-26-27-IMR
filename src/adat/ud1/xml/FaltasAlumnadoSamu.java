package adat.ud1.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class FaltasAlumnadoSamu {
    public static void main(String[] args) {

        Map<String, Integer> mFaltasInjustificadas = new HashMap<>();
        // List lFaltasInjustificadas = new ArrayList<>();
        File fichero = new File("faltas.csv");

        try (BufferedReader in = new BufferedReader(new FileReader(fichero))) {

            String linea;

            while ((linea = in.readLine()) != null) {

                if (linea.contains("2 DAM")) {

                    String[] cadena = linea.split(",");

                    if (cadena[7].trim().equalsIgnoreCase("non")) {

                        String nombre = cadena[1].trim();

                        mFaltasInjustificadas.put(nombre, mFaltasInjustificadas.getOrDefault(nombre, 0) + 1);

                    }
                }
            }

            System.out.println(mFaltasInjustificadas.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
