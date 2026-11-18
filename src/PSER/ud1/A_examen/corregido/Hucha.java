package PSER.ud1.A_examen.corregido;

public class Hucha {
    private int cantidad = 0;

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
                System.out.println(Thread.currentThread().getName() + " > Intentó retirar " + retiro + " pero no pudo");
                wait();
            } catch (InterruptedException e) {}
        }
        cantidad -= retiro;
        return true;
    }
    
    public synchronized int getCantidad(){
        return cantidad;
    }
}
