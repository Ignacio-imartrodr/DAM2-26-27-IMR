package PSER.hakers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Tabla {
    private Map<String, String> tabla;
    private Map<String, Integer> consultas;

    public Tabla() {
        this.tabla = new HashMap<>();
        this.consultas = new HashMap<>();
    }

    public String getValue(String key){
        consultas.putIfAbsent(entradaKey, (consultas.getOrDefault(entradaKey, 0) + 1));
    }
    public Entry<String, String> getEntrada(String key){
        if (!tabla.containsKey(key)) {
            return null;
        }
        Iterator<Entry<String, String>> it = tabla.entrySet().iterator();
        Entry<String, String> entrada;
        while (it.hasNext()) {
            entrada = it.next();
            String entradaKey = entrada.getKey();
            if (entradaKey.equals(key)) {
               return entrada;
            }
        }
        return null;
    }

    public boolean setEntrada(String key, String value){
        boolean add = tabla.putIfAbsent(key, value) == null;
        if (add) {
            Iterator<Entry<String, String>> it = tabla.entrySet().iterator();
            while (it.hasNext()) {
                String entradaKey = it.next().getKey();
                if (entradaKey.equals(entradaKey)) {
                    consultas.putIfAbsent(entradaKey, (consultas.getOrDefault(entradaKey, 0) + 1)));
                }
            }
        }
        return add;
    }
}
