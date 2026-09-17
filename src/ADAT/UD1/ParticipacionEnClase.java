package ADAT.UD1;

import java.util.List;
import java.util.Random;

import Otros.ManejarTxt;

public class ParticipacionEnClase {
    final static String RUTA_ALUMNOS = "src\\ADAT\\FicherosDatos\\alumnos.txt";
    final static String RUTA_ALUMNOS_ELEGIDOS = "src\\ADAT\\FicherosDatos\\alumnosElegidos.txt";
    public static void main(String[] args) {
        List<String> nombres = ManejarTxt.leerTxt(RUTA_ALUMNOS);
        if (nombres.getFirst().equals("-1")) {
            for (int i = 1; i < nombres.size(); i++) {
                System.out.println(nombres.get(i));
            }
        } else {

        }
    }

    private static String alumnoRnd(List<String> alumnos){
        Random rnd = new Random();
        String elegido = alumnos.get(rnd.nextInt(0, alumnos.size()));
        List<String> preElegidos = ManejarTxt.leerTxt(RUTA_ALUMNOS_ELEGIDOS);

        if (preElegidos.getFirst().equals("-1")) {

            for (int i = 1; i < preElegidos.size(); i++) {
                System.out.println(preElegidos.get(i));
            }
            
        } if (preElegidos.contains(elegido)) {
            alumnos.remove(elegido);
            return alumnoRnd(alumnos);
        }
       return null;
        
    }
    
}
