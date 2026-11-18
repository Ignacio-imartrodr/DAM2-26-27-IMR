package PSER.examenes.ud1.entregado;

public abstract class Hilo extends Thread {
    protected final Hucha HUCHA;

    public Hilo(String name, Hucha hucha) {
        super(name);
        this.HUCHA = hucha;
    }

    @Override
    public void run() {
        manejarHucha();
    }
    

    protected abstract void manejarHucha();

    protected void hablar(String msj){
        System.out.println(getName() + " > " + msj);
    }
}
