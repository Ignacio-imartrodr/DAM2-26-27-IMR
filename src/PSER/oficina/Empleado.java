package PSER.oficina;

public class Empleado extends Thread {
    private Oficina oficina;

    public Empleado(String nombre, Oficina oficina) {
        super(nombre);
        this.oficina = oficina;
    }

    @Override
    public void run() {
        synchronized (oficina) {
            String id = this.getName() + ": ";
            if (oficina.hasJefe()) {
                System.out.println(id +"Hola jefe, me pongo a trabajar...");
            } else {
                System.out.println(id + "ZZZZZZ");
                try {
                    oficina.wait();
                } catch (InterruptedException e) {
                    System.out.println(id + "Error esperando al jefe");
                }
                System.out.println(id + "buenos días jefe, aquí estoy trabajando");
            }
        }
    }
}