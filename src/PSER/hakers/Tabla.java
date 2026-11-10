package PSER.hakers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Tabla {
    private Map<String, Integer> tabla;
    private Map<String, Integer> consultas;

    public Tabla() {
        this.tabla = new HashMap<>();
        this.consultas = new HashMap<>();
    }

    public String getEntrada(String key){
        synchronized(tabla){
            synchronized(consultas){
                
            }
        }
        return null;
    }

    public boolean setEntrada(String key, int value){
        boolean add;
        synchronized(tabla){
            add = tabla.putIfAbsent(key, value) == null;
        }
        if (add) {
            synchronized(consultas){
                Iterator<Entry<String, Integer>> it = tabla.entrySet().iterator();
                while (it.hasNext()) {
                    String entradaKey = it.next().getKey();
                    if (entradaKey.equals(entradaKey)) {
                        consultas.put(entradaKey, (consultas.getOrDefault(entradaKey, 0) + 1));
                    }
                }
            }
        }
        return add;
    }
}
