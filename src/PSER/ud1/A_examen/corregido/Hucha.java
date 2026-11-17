package PSER.ud1.A_examen.corregido;

public class Hucha {
    private int cantidad = 0;
    private Semana semana;
    private boolean fin = false;
    private Thread tiempo;

    public Hucha(Semana semana) {
        this.semana = semana;
    }

    public synchronized boolean ingresar(int ingreso) {
        if (ingreso < 0) {
            return false;
        }
        cantidad += ingreso;
        notifyAll();
        return true;
    }

    public synchronized boolean retirar(int retiro){
        
        if (retiro < 0) {
            return false;
        }
        while (retiro > cantidad) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        cantidad -= retiro;
        return true;
    }
    
    public synchronized int getCantidad(){
        return cantidad;
    }
    
    public synchronized void startTiempo(){
        tiempo = Thread.startVirtualThread(new Runnable() {
            @Override
            public void run() {
                while (!fin && !Thread.interrupted()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        fin = true;
                    }
                    addSemana();
                    synchronized(semana){
                        semana.notifyAll();
                    }
                }
            }
        });
        tiempo.start();
    }

    public synchronized void endTiempo(){
        fin = true;
        tiempo.interrupt();
    }
    
    private synchronized void addSemana(){
        semana.incrementar();
    }
}
