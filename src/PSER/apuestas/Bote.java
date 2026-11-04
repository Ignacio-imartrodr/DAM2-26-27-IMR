package PSER.apuestas;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Bote
 */
public class Bote {
    Map<Resultado, Set<Tiket>> apuestas;
    int nTiket;
    Resultado resultadoPartido;

    public Bote(Resultado resultadoPartido) {
        this.apuestas = new HashMap<>();
        this.nTiket = 0;
        this.resultadoPartido = resultadoPartido;
    }

    public Set<Tiket> getTikets(Resultado resultado) {
        if (resultadoPartido.getDAM1() < 0 || resultadoPartido.getPRF() < 0 || apuestas == null) {
            return null;
        }
        return apuestas.getOrDefault(resultado, null);
    }

    public Tiket apostar(Resultado resultado, Double cantidad){
        if (resultadoPartido.getDAM1() >= 0 || resultadoPartido.getPRF() >= 0) {
            return null;
        }
        if (resultado != null && cantidad > 0){
            Set<Tiket> set;
            boolean existsBefore;
            synchronized(apuestas){
                set = apuestas.putIfAbsent(resultado, new HashSet<>());
                existsBefore = set == null;
                if (existsBefore) {
                    set = apuestas.get(resultado);
                }
            }
            synchronized(set){
                Tiket t = new Tiket(++nTiket, cantidad);
                if (set.add(t)) {
                    return t;
                } else {
                    if (!existsBefore) {
                        apuestas.remove(resultado);
                    }
                    nTiket--;
                }
            }
        }
        return null;
    }

    public Double getBoteAcumulado(){
        if (resultadoPartido.getDAM1() < 0 || resultadoPartido.getPRF() < 0) {
            return -1.;
        }
        Double total = 0.;
        Set<Entry<Resultado, Set<Tiket>>> entrySet = apuestas.entrySet();
        for (Entry<Resultado,Set<Tiket>> entry : entrySet) {
            for (Tiket t : entry.getValue()) {
                total += t.getValor();
            }
        }
        return total;
    }

    public Tiket[] getTiketsGanadores(){
        Set<Tiket> ganadores = getTikets(resultadoPartido);
        return ganadores == null ? null : ganadores.toArray(new Tiket[0]);
    }
}
