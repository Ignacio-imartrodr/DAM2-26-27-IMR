package PSER.oficina;

public class Jefe extends Personal {
    
    public Jefe(String nombre, Oficina oficina) {
        super(nombre, oficina);
    }

    @Override
    String getSaludo() {
        return getName() + ": EL JEFE HA LLEGADO!!!";
    }

    @Override
    void aTrabajar() {
        saludar();
        for (int i = 0; i < oficina.getPersonalEnOficinal().length; i++) {
            oficina.notify();   
        }
        //oficina.notifyAll();
        try {
            oficina.wait(getTiempoTrabajoMillis());
        } catch (InterruptedException e) {
            System.out.println(getName() + ": Error trabajando");
        }
    }

    @Override
    void salirTrabajo() {
        despedirse();
        for (int i = 0; i < oficina.getPersonalEnOficinal().length; i++) {
            oficina.notify();   
        }
        //oficina.notifyAll();
    }

    @Override
    String getDespedida() {
        return getName() + ": EL JEFE MARCHA!!";
    }

    @Override
    protected long getTiempoTrabajoMillis() {
        return 1000L;
    }
}