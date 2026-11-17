package PSER.ud1.A_examen.entregado;

public class Hucha {
    private int cantidad = 0;
    private int semana = 0;

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
    
    public int getCantidad(){
        return cantidad;
    }
    
    protected synchronized void addSemana(){
        semana++;
    }
    
    protected synchronized int getSemana(){
        return semana;
    }
}
