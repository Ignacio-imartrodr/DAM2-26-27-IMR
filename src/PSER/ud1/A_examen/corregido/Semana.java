package PSER.ud1.A_examen.corregido;

public class Semana {
    int num = 0;

    public synchronized void incrementar(){
        num++;
        notifyAll();
    }

    protected synchronized void esperarNuevaSemana() throws InterruptedException {
        wait();
    }

    public int getNum(){
        return num;
    }
}
