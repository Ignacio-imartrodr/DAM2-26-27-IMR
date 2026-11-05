package PSER.ventas;

import java.util.ArrayList;
import java.util.List;

public class Concesionario {
    private List<Coche> coches;

    public Concesionario() {
        this.coches = new ArrayList<>();
    }
    public boolean addCoche(int id, int cantidad){
        if (id < 0 || id > coches.size()) {
            return false;
        }
    }
    
}
