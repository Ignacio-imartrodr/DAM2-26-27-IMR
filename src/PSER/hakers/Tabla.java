package PSER.hakers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Tabla {
    private Map<String, String> tabla;
    private Map<String, Integer> consultas;
    private Set<String> probadas;

    public Tabla() {
        this.tabla = new HashMap<>();
        this.consultas = new HashMap<>();
        this.probadas = new HashSet<>();
    }

    public String getHash(String clave){
        synchronized (tabla) {
            if (tabla.containsKey(clave)) {
                contarUso(clave);
                return tabla.get(clave);
            }
            return null;
        }
    }

    private void contarUso(String clave){
        synchronized(consultas){
            consultas.put(clave, consultas.getOrDefault(clave, 0) + 1);
        }
    }

    public boolean setNewEntrada(String clave, String hash){
        boolean add;
        synchronized(tabla){
            add = tabla.putIfAbsent(clave, hash) == null;
        }
        if (add) {
            contarUso(clave);
            addPrueba(clave);
        }
        return add;
    }

    public List<Entry<String, Integer>> getResultados(){
        synchronized (consultas) {
            List<Entry<String, Integer>> resultados = new ArrayList<>(consultas.entrySet());
            resultados.sort((o1, o2) -> {
                int compUsos = Integer.compare(o2.getValue(), o1.getValue());
                return compUsos != 0 ? compUsos : o2.getKey().compareTo(o1.getKey());
            });
            return resultados;
        }
    }

    public boolean addPrueba(String clave){
        synchronized(probadas){
            return probadas.add(clave);
        }
    }

    public void clearPruebas() {
        synchronized(probadas){
            probadas.clear();
        }
    }

    public String[] getKeys() {
        synchronized(tabla){
            return tabla.keySet().toArray(new String[0]);
        }
    }
}
