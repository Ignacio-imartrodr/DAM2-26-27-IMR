package PSER.apuestas;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

/**
 * La empresa CDM festeja sus 30 años de existencia con un porra sobre la
 * celebración de un partido de fútbol entre los equipos DM2 - PRF (No se han
 * publicado las alineaciones de cada equipo para disponer de algo de variedad
 * en las apuestas, debido a la superioridad del equipo PRF sobre el resto de
 * rivales).
 * En dicha porra participarán los NUM_EMPLEADOS (hilos) de la empresa.
 * Para ello realizarán el siguiente procedimiento:
 *      - leen de la porra el importe actual esperan un pequeño tiempo aleatorio (100~ 300 ms.)
 *      - generamos un resultado aleatorio teniendo en cuenta que: 
 *              DM2 nunca ha metido más de MAX_GOLES~4 goles en ningún partido de su historia
 *              PRF no quiere abusar de su calidad y se compromete a no meter más de MAX_GOLES
 *      - cada empleado apuesta 1€ cada vez (MEJORA: incrementa en cada apuesta en 1€) 
 * Cada empleado realizará NUM_APUESTAS_EMPLEADO (~ 5)
 * 
 * Finalmente se juega el partido y se visualizarán los resultados:
 *      - del partido 
 *      - total de € ingresados (~ de apuestas) 
 *      - empleados que ganaron la porra 
 *      - cuánto le toca a cada uno
 * AppApuesta
 */
public class AppApuesta {
    private static Random rnd = new Random();
    private final static int MAX_GOLES = 9;
    private final static int NUM_EMPLEADOS = 100;
    private final static int NUM_APUESTAS_EMPLEADO = 3;
    public static void main(String[] args) {
        Resultado resultadoPartido = new Resultado(-1, -1);
        Bote bote = new Bote(resultadoPartido);
        Empleado[] empleados = new Empleado[NUM_EMPLEADOS];
        for (int i = 0; i < NUM_EMPLEADOS; i++) {
            empleados[i] = new Empleado("E" + i, bote, NUM_APUESTAS_EMPLEADO, MAX_GOLES);
            empleados[i].start();
        }
        for (Empleado empleado : empleados) {
            try {
                empleado.join();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        resultadoPartido.setDAM1(rnd.nextInt(MAX_GOLES));
        resultadoPartido.setPRF(rnd.nextInt(MAX_GOLES));
        System.out.println(resultadoPartido);
        Double totBote = bote.getBoteAcumulado();
        System.out.printf("Total apostado: %.2f eur%n", totBote);
        Tiket[] ticketsGanadores = bote.getTiketsGanadores();
        Map<Empleado, Double> premiados;
        if (ticketsGanadores == null) {
            System.out.println("Nadie acertó el resultado");
        } else {
            premiados = new HashMap<>();
            for (Empleado empleado : empleados) {
                empleado.validarTickets(ticketsGanadores);
                if (empleado.getPremiados() != null) {
                    premiados.putIfAbsent(empleado, null);
                }
            }
            Double apostadoGanadores = 0.;
            for (Entry<Empleado, Double> entry : premiados.entrySet()) {
                for (Tiket tiket : entry.getKey().getPremiados()) {
                    apostadoGanadores += tiket.getValor();
                }
            }
            for (Entry<Empleado, Double> entry : premiados.entrySet()) {
                Double apostado = 0.;
                for (Tiket tiket : entry.getKey().getPremiados()) {
                    apostado += tiket.getValor();
                }
                entry.setValue(((apostado)/apostadoGanadores) * totBote);
                System.out.printf("-" + entry.getKey().getName() + " ganó: %.2f eur%n", entry.getValue());
            }
        }
    }
}
