package PSER;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class EleccionesMunicipales {
    final static int N_PARTIDOS = 3;
    final static int CENSO = 9;
    public static void main(String[] args) {
        Urna urna = new Urna(N_PARTIDOS);
        for (int i = 0; i < N_PARTIDOS; i++) {
            urna.addPartido(new Partido(String.valueOf(i)));
        }
        Votante[] votantes = new Votante[CENSO];
        for (int i = 0; i < CENSO; i++) {
            votantes[i] = new Votante("Votante " + i, urna);
            votantes[i].start();
        }
        try {
            Thread.sleep(15);
        } catch (Exception e) {
            // TODO: handle exception
        }
        boolean add = urna.addPartido(new Partido(String.valueOf(N_PARTIDOS)));
        for (int i = 0; i < votantes.length; i++) {
            try {
                votantes[i].join();
            } catch (Exception e) {}
        }
        System.out.println(add);
        System.out.println("Votos finales: " + urna.getVotosTotales());
        System.out.println("Votantes: " + CENSO);
        System.out.println("Resultados:");
        urna.mostrarVotos();
        String[] ganadores = urna.getGanadores();
        if (ganadores.length == 1) {
            System.out.println("El ganador de la votacion es el partido: "  + ganadores[0]);
        } else {
            System.out.println("Los ganadores empatados son: " + Arrays.toString(ganadores));
        }
    }
}
/**
 * Votante
 */
class Votante extends Thread {
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
        System.out.println(getName() + " > Partido " + partido + " + 1");
        urna.votar(partido);
    }
    

    private int escogerVoto(){
        int partido = urna.getNumPartidos() - 1;//rnd.nextInt(urna.getNumPartidos());
        return partido;
    }
}

/**
 * Contador
 */
class Partido {
    private String nombre;
    private int contador;

    public Partido(String nombre){
        this.nombre = nombre;
        this.contador = 0;
    }

    public synchronized void incrementar(){
        contador++;
    }
    public void reset(){
        contador = 0;
    }
    public synchronized int getContador(){
        return contador;
    }
    public String getNombre(){
        return nombre;
    }
}

/**
 * Urna
 */
class Urna {
    List<Partido> partidos;

    public Urna(){
        this.partidos = new ArrayList<>();
    }

    public Urna(int nPartidos){
        this.partidos = new ArrayList<>(nPartidos);
    }

    public synchronized int getVotosTotales(){
        int nVotos = 0;
        for (int i = 0; i < partidos.size(); i++) {
            nVotos += partidos.get(i).getContador();
        }
        return nVotos;
    }
    public synchronized int getNumPartidos(){
        return partidos.size();
    }
    public synchronized int getVotosGanador(){
        int maxVotos = partidos.get(0).getContador();
        int votosAct;
        for (int i = 1; i < partidos.size(); i++) {
            votosAct = partidos.get(i).getContador();
            if (votosAct > maxVotos) {
                maxVotos = votosAct;
            }
        }
        return maxVotos;
    }
    public synchronized String[] getGanadores(){
        List<Integer> ganadores = new ArrayList<>();
        int max = getVotosGanador();
        for (int i = 0; i < partidos.size(); i++) {
            if (partidos.get(i).getContador() == max) {
                ganadores.add(i);
            }
        }
        String[] res = new String[ganadores.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = partidos.get(ganadores.get(i)).getNombre();
        }
        return res;
    }

    public synchronized void mostrarVotos(){
        for (Partido p : partidos) {
            System.out.println("Partido " + p.getNombre() + " -> " + p.getContador() + " votos");
        }
    }
    
    public boolean votar(int partido){
        synchronized(partidos){
            if (partido < 0 || partido >= partidos.size()) {
                return false;
            }
        }
        partidos.get(partido).incrementar();
        return true;
    }
    public synchronized boolean addPartido(Partido partido) {
        synchronized(partidos){
        if (getVotosTotales() > 0) {
            return false;
        }
        return this.partidos.add(partido);}
    }
}