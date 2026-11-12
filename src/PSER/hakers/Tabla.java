package PSER.hakers;

import java.util.HashMap;
import java.util.Map;

public class Tabla {
    private Map<String, String> tabla;
    private Map<String, Integer> consultas;

    public Tabla() {
        this.tabla = new HashMap<>();
        this.consultas = new HashMap<>();
    }

    public String getHash(String clave){
        if (tabla.containsKey(clave)) {
            contarUso(clave);
        }
        return tabla.getOrDefault(clave, null);
    }

    private void contarUso(String clave){
        if (consultas.putIfAbsent(clave, 1) != null) {
            synchronized(consultas){
                consultas.put(clave, consultas.get(clave) + 1);
            }
        }
    }

    public boolean setNewEntrada(String clave, String hash){
        boolean add = tabla.putIfAbsent(clave, hash) == null;
        if (add) {
            contarUso(clave);
        }
        return add;
    }
}
