package PSER.oficina;

public class Jefe extends Thread {
    Oficina oficina;

    public Jefe(String nombre, Oficina oficina) {
        super(nombre);
        this.oficina = oficina;
    }

    @Override
    public void run() {
        synchronized (oficina) {
            oficina.entrar(this);
            System.out.println(this.getName() + ": EL JEFE HA LLEGADO!!!");
            oficina.notifyAll();
        }
    }
}