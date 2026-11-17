package PSER.ud1.naves1;

import java.util.List;

public class Central {
    private List<Meteorito> meteoritos;

    public Central(List<Meteorito> meteoritos) {
        this.meteoritos = meteoritos;
    }

    public Meteorito getMeteorito(Nave nave){
        while (true) {
            synchronized (this) {
                if (meteoritos.isEmpty()) {
                    return null;
                }
                for (Meteorito meteorito : meteoritos) {
                    if (nave.validarMeteorito(meteorito)) {
                        return meteorito;
                    }
                }
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean destruirMeteorito(Meteorito meteorito){
        if (meteorito == null) {
            return false;
        }
        synchronized(this) {
            int idMeteorito = meteoritos.indexOf(meteorito);
            if (idMeteorito >= 0) {
                Meteorito m = meteoritos.get(idMeteorito);
                if (m.hasHole() && !m.hasNaveA() && !m.hasBS()) {
                    meteoritos.remove(idMeteorito);
                    if (meteoritos.isEmpty()) {
                        notifyAll();
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
