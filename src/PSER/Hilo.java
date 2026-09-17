package PSER;

public class Hilo extends Thread {
    public Hilo(String nombre) {
        super(nombre);
    }

    @Override 
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(getName() + " > " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
            }
        }
    }

    public static void main(String[] args) { // el main suele estar situado en otra clase
        Hilo hilo1 = new Hilo("hilo1");
        Hilo hilo2 = new Hilo("hilo2");
        hilo1.start();
        hilo2.start();
    }
}
