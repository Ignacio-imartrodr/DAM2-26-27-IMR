package PSER.examenes.ud1.corregido;

public abstract class Familiar extends Thread {
    protected final Hucha HUCHA;
    private final Semana SEMANA;

    public Familiar(String name, Hucha hucha, Semana semana) {
        super(name);
        this.HUCHA = hucha;
        this.SEMANA = semana;
    }

    @Override
    public void run() {
        manejarHucha();
    }
    

    protected abstract void manejarHucha();

    protected void hablar(String msj){
        System.out.println(getName() + " > " + msj);
    }

    protected int getSemana(){
        return SEMANA.getNum();
    }
    
    protected void esperarNuevaSemana() throws InterruptedException {
        SEMANA.esperarNuevaSemana();
    }
}
