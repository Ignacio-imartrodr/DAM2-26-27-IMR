package ADAT.ud1.flujosTexto;

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

public class AnalisisTexto {
    public static void main(String[] args) {
        File fichero = new File("DATOS\\texto.txt");

        try (FileReader in = new FileReader(fichero)) {

            String cadenaPura = in.readAllAsString();
            int numCaracteres = cadenaPura.length();
            int numVocales = 0;
            int numConsonantes = 0;
            int numDigitos = 0;
            int numEspaciosVacios = 0;

            char[] caracteresTexto = cadenaPura.toCharArray();

            System.out.println(Arrays.toString(caracteresTexto));
            for (char c : caracteresTexto) {
                if (Character.isDigit(c)) {
                    numDigitos++;
                } else if (Character.isLetter(c)) {

                    if ("aeiouáéíóú".indexOf(Character.toLowerCase(c)) == -1) {
                        numConsonantes++;
                    } else {
                        numVocales++;
                    }
                } else {
                    numEspaciosVacios++;
                }
            }

            System.out.println("Número de caracteres: " + numCaracteres);
            System.out.println("Número de vocales: " + numVocales);
            System.out.println("Número de consonantes: " + numConsonantes);
            System.out.println("Número de dígitos: " + numDigitos);
            System.out.println("Número de espacios en blanco: " + numEspaciosVacios);

        } catch (Exception e) {
            System.out.println("Se ha producido un error" + e.getMessage());
        }
    }
}
