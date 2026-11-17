package PSER.elecciones;

import java.util.Arrays;

public class EleccionesMunicipales {
    final static String[] NOMBRE_PARTIDOS = new String[] {"PP", "PSOE", "VOX", "SUMAR","JUNX"};
    final static int CENSO = 10000;

    public static void main(String[] args) {
        Urna urna = new Urna(NOMBRE_PARTIDOS);
        Votante[] votantes = new Votante[CENSO];
        for (int i = 0; i < CENSO; i++) {
            votantes[i] = new Votante("Votante " + i, urna);
            votantes[i].start();
        }
        /*
         * try {
         * Thread.sleep(15);
         * } catch (Exception e) {
         * System.out.println(e.getMessage());
         * }
         * boolean add;
         * synchronized (urna){
         * add = urna.addPartido(new Partido(String.valueOf(N_PARTIDOS)));
         * }
         */
        for (Votante votante : votantes) {
            try {
                votante.join();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        // System.out.println(add);
        System.out.println("Votos finales: " + urna.getVotosTotales());
        System.out.println("Votantes: " + CENSO);
        System.out.println("Resultados:");
        urna.mostrarVotos();
        Partido[] ganadores = urna.getGanadores();
        if (ganadores.length == 1) {
            System.out.println("El ganador de la votacion es el partido " + ganadores[0]);
        } else {
            System.out.println("Los ganadores empatados son:\n" + Arrays.toString(ganadores));
        }
    }
}