package PSER.naves;

import java.util.List;
import java.util.Random;

public class Central {
    private final static Random RND = new Random(); 
    private List<Meteorito> meteoritos;
    private int size;

    public Central(List<Meteorito> meteoritos) {
        this.meteoritos = meteoritos;
        size = meteoritos.size(); 
    }

    public Meteorito getMeteorito(){
        if (size == 0) {
            return null;
        }
        synchronized(meteoritos){
            return meteoritos.get(RND.nextInt(size));
        }
    }

    public boolean destruirMeteorito(Meteorito meteorito){
        if (meteorito == null) {
            return false;
        }
        synchronized(meteoritos) {
            int idMeteorito = meteoritos.indexOf(meteorito);
            if (idMeteorito >= 0) {
                Meteorito m = meteoritos.get(idMeteorito);
                if (m.hasHole() && !m.hasNaveA()) {
                    meteoritos.remove(idMeteorito);
                    size = meteoritos.size();
                    return true;
                }
            }
        }
        return false;
    }
}
