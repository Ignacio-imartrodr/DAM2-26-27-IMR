package PSER.oficina;

import java.util.ArrayList;
import java.util.List;

public class Oficina {
    Jefe j;
    List<Empleado> personalEnOficina;

    public Oficina() {
        this.j = null;
        personalEnOficina = new ArrayList<>();
    }

    public Oficina(Jefe j) {
        this.j = j;
        personalEnOficina = new ArrayList<>();
    }

    public Personal[] getPersonalEnOficinal(){
        return personalEnOficina.toArray(new Personal[0]);
    }

    public synchronized boolean entrar(Personal p) {
        if (p == null) {
            return false;
        }
        if (setJefe(p)) {
            return true;
        }
        try {
            Empleado e = (Empleado) p;
            return personalEnOficina.add(e);
        } catch (Exception e) {
            return false;
        }
    }
    public synchronized boolean salir(Personal p) {
        if (p == null) {
            return false;
        }
        if (removeJefe(p)) {
            return true;
        }
        return personalEnOficina.remove(p);
    }

    private boolean setJefe(Personal jefe){
        if (jefe != null && this.j == null && jefe instanceof Jefe) {
            this.j = (Jefe) jefe;
            return true;
        }
        return false;
    }

    private boolean removeJefe(Personal jefe){
        if (jefe != null && this.j != null && jefe instanceof Jefe && j.equals(jefe)) {
            this.j = null;
            return true;
        }
        return false;
    }

    public boolean hasJefe() {
        return j != null;
    }
}