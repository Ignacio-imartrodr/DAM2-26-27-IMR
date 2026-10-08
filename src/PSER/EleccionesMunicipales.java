package PSER;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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

    /**
     * Votante
     */
    private static class Votante extends Thread {
        static Random rnd = new Random();
        Urna urna;

        public Votante(String nombre, Urna urna) {
            super(nombre);
            this.urna = urna;

        }

        @Override
        public void run() {
            try {
                Thread.sleep(rnd.nextLong(100));
            } catch (InterruptedException ex) {
                System.out.println("Error mientras pensaba");
            }
            int partido = escogerVoto();
            System.out.println(getName() + " > Partido " + urna.getNombrePartido(partido) + " + 1");
            urna.votar(partido);
        }

        private int escogerVoto() {
            int partido = rnd.nextInt(urna.getNumPartidos());// urna.getNumPartidos() - 1;
            return partido;
        }
    }

    /**
     * Contador
     */
    private static class Partido {
        private String nombre;
        private int contador;

        public Partido(String nombre) {
            this.nombre = nombre;
            this.contador = 0;
        }

        public synchronized void incrementar() {
            contador++;
        }

        /*
         * public void reset() {
         * contador = 0;
         * }
         */

        public synchronized int getContador() {
            return contador;
        }

        public String getNombre() {
            return nombre;
        }

        @Override
        public String toString() {
            return nombre + ", votos: " + contador;
        }
        
    }

    /**
     * Urna
     */
    private static class Urna {
        Partido[] partidos;

        public Urna(String[] partidos) {
            this.partidos = new Partido[partidos.length];
            int i = 0;
            for (String partido : partidos) {
                this.partidos[i] = new Partido(partido);
                i++;
            }
        }

        public synchronized int getVotosTotales() {
            int nVotos = 0;
            for (Partido partido : partidos) {
                nVotos += partido.getContador();
            }
            return nVotos;
        }
        public String getNombrePartido(int partido) {
            return partidos[partido].getNombre();
        }
        public int getNumPartidos() {
            return partidos.length;
        }

        public synchronized Partido[] getGanadores() {
            List<Partido> ganadores = new ArrayList<>();
            int max = partidos[0].getContador();
            ganadores.add(partidos[0]);
            Partido partido;
            for (int i = 1; i < partidos.length; i++) {
                partido = partidos[i];
                if (partido.getContador() > max) {
                    ganadores.clear();
                    ganadores.add(partidos[i]);
                    max = partido.getContador();
                } else if (partido.getContador() == max) {
                    ganadores.add(partidos[i]);
                }
            }
            Partido[] res = new Partido[ganadores.size()];
            for (int i = 0; i < res.length; i++) {
                res[i] = ganadores.get(i);
            }
            return res;
        }

        public synchronized void mostrarVotos() {
            for (Partido p : partidos) {
                System.out.println("Partido " + p.getNombre() + " -> " + p.getContador() + " votos");
            }
        }

        public boolean votar(int partido) {
            synchronized (partidos[partido]) {
                if (partido < 0 || partido >= partidos.length) {
                    return false;
                }
            }
            partidos[partido].incrementar();
            return true;
        }
    }
}