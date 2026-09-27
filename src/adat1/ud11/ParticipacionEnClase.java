package adat.ud1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import otros.ManejarTxt;

public class ParticipacionEnClase {
    final static String RUTA_ALUMNOS = "src\\ADAT\\FicherosDatos\\alumnos.txt";
    final static String RUTA_ALUMNOS_ELEGIDOS = "src\\ADAT\\FicherosDatos\\alumnosElegidos.txt";

    public static void main(String[] args) {
        System.out.println("Lista de Alumnos:");
        List<String> nombres = ManejarTxt.leerTxt(RUTA_ALUMNOS);
        System.out.println(nombres);

        if (nombres.getFirst().equals("-1")) {
            for (int i = 1; i < nombres.size(); i++) {
                System.out.println(nombres.get(i));
            }
        } else {
            System.out.println("Alumno elegido:");
            String elegido = alumnoRnd(nombres);
            System.out.println(elegido);

            List<String> preElegidos = ManejarTxt.leerTxt(RUTA_ALUMNOS_ELEGIDOS);
            if (elegido != null) {
                ManejarTxt.writeInTxt(RUTA_ALUMNOS_ELEGIDOS, elegido, !preElegidos.contains(elegido));
            }
        }
    }

    /**
     * Elige aleatoriamente un nombre de la lista de alumnos teniendo en cuenta la
     * lista de alumnos ya elegidos, no repetirá nombre a menos que todos los nombres de
     * alumnos ya hayan sido elegidos
     * 
     * @param alumnos Lista de nombres del alumnado
     * @return Nombre de alumno elegido
     */
    private static String alumnoRnd(List<String> alumnos) {
        Random rnd = new Random();
        String elegido = null;
        List<String> copAlumnos = new ArrayList<>(alumnos);
        List<String> preElegidos = ManejarTxt.leerTxt(RUTA_ALUMNOS_ELEGIDOS);

        if (preElegidos.size() > 0 && preElegidos.getFirst().equals("-1")) {

            for (int i = 1; i < preElegidos.size() - 1; i++) {
                System.out.println(preElegidos.get(i));
            }

        } else {
            for (String nombre : preElegidos) {
                copAlumnos.remove(nombre);
            }
            if (copAlumnos.size() > 1) {
                elegido = copAlumnos.get(rnd.nextInt(0, copAlumnos.size()));
            } else if (copAlumnos.size() == 1) {
                elegido = copAlumnos.getFirst();
            } else {
                elegido = alumnos.get(rnd.nextInt(0, alumnos.size()));
            }
        }
        return elegido;
    }
}
