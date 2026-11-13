package PSER.naves;

import java.util.List;
import java.util.Random;

public class Central {
    private final static Random RND = new Random(); 
    List<Meteorito> meteoritos;

    public Central(List<Meteorito> meteoritos) {
        this.meteoritos = meteoritos;
    }

    public Meteorito getMeteorito(){
        for (int i = 0; i < meteoritos.size(); i++) {
            if (meteoritos.get(i) == null) {
                meteoritos.remove(i);
            }
        }
        if (meteoritos.size() == 0) {
            return null;
        }
        return meteoritos.get(RND.nextInt(meteoritos.size()));
    }
}
