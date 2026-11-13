package PSER.hakers;

import java.util.Map.Entry;
import java.util.Scanner;

/**
 * Realice un programa que pida por teclado una contraseña (de no más de
 * MAX_CHARS~4) caracteres alfanuméricos, y guárdela hasheada*. Desgraciadamente
 * el servidor que la almacena ha sido atacado, saliendo dicho hash a la luz.
 * 
 * En ese momento NUM_HACKS*3 hackers se ponen a trabajar en obtener una clave
 * candidata, probando combinaciones de caracteres**.
 * 
 * Realice una simulación de ataque en masa de todos los hackers probando sus
 * combinaciones de claves aleatorias y de la construcción de una tabla rainbow
 * común en memoria***
 * 
 * El trabajo de un hacker será generar continuamente claves aleatorias. Tenga
 * en cuenta que un tercio de los hackers son alfabéticos, otro tercio son
 * numéricos y el resto pueden incluir mezcla (alfanuméricos).
 * 
 * Con cada clave deberá comprobar si ya ha sido previamente hasheada (para no
 * tener que rehashear inncesariamente una clave ya generada con anterioridad).
 * 
 * Si algún hacker consigue una clave (candidata):
 * 
 * - se mostrará por pantalla el hacker y la clave, dándole la medalla TIC.
 * 
 * - se mostrará la entrada de la tabla rainbow que más hackers han
 * generado/consultado.
 * 
 * - se vuelve a empezar de nuevo preguntando una nueva contraseña (hasta que
 * esta sea STR_FIN~”fin”), reutilizando la tabla rainbow trabajada durante
 * tanto tiempo (el tiempo de ejecución actual del programa).
 * 
 * AppHakers
 */
public class AppHakers {
    private final static Scanner SC = new Scanner(System.in);
    private final static int NUM_HACKS = 10;
    private final static int NUM_GRUPOS = 3;
    private final static String STR_FIN = "fin";
    private final static boolean VER_DETALLE = true;
    private final static int MAX_LENGTH = 4;

    public static void main(String[] args) {
        ThreadGroup grupoHackers = new ThreadGroup("Hackers");
        Hacker[] hackers = new Hacker[NUM_HACKS*NUM_GRUPOS];
        Tabla t = new Tabla();
        String contra = pedirStringContrasenha();
        Contrasenha contrasenha = new Contrasenha(contra, MAX_LENGTH);
        int pos;
        boolean doIt = true;
        while (!contra.equals(STR_FIN)) {
            contrasenha = new Contrasenha(contra, MAX_LENGTH);
            pos = 0;
            for (int i = 0; i < NUM_GRUPOS; i++) {
                for (int j = 0; j < NUM_HACKS; j++, pos++) {
                    switch (i % 3) {
                        case 0:
                            if (doIt) {
                                hackers[pos]  = new HackerNum(grupoHackers, "hn" + pos, t, VER_DETALLE, MAX_LENGTH, contrasenha);
                            } else {
                                hackers[pos]  = new HackerBago(grupoHackers, "hb" + pos, t, VER_DETALLE, MAX_LENGTH, contrasenha);
                            }
                            doIt = !doIt;
                            break;

                        case 1:
                            hackers[pos]  = new HackerAlfab(grupoHackers, "ha" + pos, t, VER_DETALLE, MAX_LENGTH, contrasenha);
                            break;
                        
                        case 2:
                            hackers[pos]  = new HackerAlfanum(grupoHackers, "hh" + pos, t, VER_DETALLE, MAX_LENGTH, contrasenha);
                            break;

                        default:
                            hackers[pos]  = new HackerAlfanum(grupoHackers, "hh" + pos, t, VER_DETALLE, MAX_LENGTH, contrasenha);
                            break;
                    }
                }
            }
            Hacker.setWasFounded(false);
            System.out.println("Comenzando el ataque de fuerza bruta...");
            for (Hacker hacker : hackers) {
                hacker.start();
            }
            for (Hacker hacker : hackers) {
                try {
                    hacker.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("La contraseña real era: \"" + contra + "\", con hash: " + contrasenha.getHash());
            Entry<String, Integer> e = t.getResultados().getFirst();
            System.out.println("La contraseña más consultada fué \"" + e.getKey() + "\" con " + e.getValue() + " consultas");
            t.clearPruebas();
            contra = pedirStringContrasenha();
        }
    }

    private static String pedirStringContrasenha() {
        String txt;
        do {
            System.out.print("Contraseña de hasta " + MAX_LENGTH + " caracteres o \"" + STR_FIN + "\" para terminar: ");
            txt = SC.nextLine();
        } while (!txt.equals(STR_FIN) && (txt.length() == 0 || txt.length() > MAX_LENGTH));
        return txt.toLowerCase();
    }
}
