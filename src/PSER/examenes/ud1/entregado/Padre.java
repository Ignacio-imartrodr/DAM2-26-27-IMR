package PSER.examenes.ud1.entregado;

public class Padre extends Hilo {
    final int INGRESO_HUCHA;

    public Padre(String name, Hucha general, int ingresoHucha) {
        super(name, general);
        INGRESO_HUCHA = ingresoHucha;
    }

    @Override
    protected void manejarHucha() {
        boolean interrumpido = false;
        while (!isInterrupted() && !interrumpido) {
            synchronized(HUCHA){
                HUCHA.ingresar(INGRESO_HUCHA);
                hablar("Ingresó "+ INGRESO_HUCHA + " en la semana " + HUCHA.getSemana() + ", Total actual: " + HUCHA.getCantidad());
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                interrumpido = true;
            }
            HUCHA.addSemana();
        }
    }

}
