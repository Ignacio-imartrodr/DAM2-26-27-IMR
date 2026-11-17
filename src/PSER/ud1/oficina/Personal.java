package PSER.oficina;

public abstract class Personal extends Thread {
    Oficina oficina;
    public Personal(String nombre, Oficina oficina) {
        super(nombre);
        this.oficina = oficina;
    }

    @Override
    public void run() {
        synchronized(oficina) {
            oficina.entrar(this);
            aTrabajar();
            oficina.salir(this);
            salirTrabajo();
        }
    }

    protected abstract long getTiempoTrabajoMillis();

    abstract String getSaludo();

    abstract String getDespedida();

    abstract void aTrabajar();
    
    abstract void salirTrabajo();

    public void saludar(){
        System.out.println(getSaludo());
    }

    public void despedirse(){
        System.out.println(getDespedida());
    }
}
