package PSER.ud1.A_examen.corregido;

public class Progenitor extends Familiar {
    final int INGRESO_HUCHA;

    public Progenitor(String name, Hucha general, int ingresoHucha, Semana semana) {
        super(name, general, semana);
        INGRESO_HUCHA = ingresoHucha;
    }

    @Override
    protected void manejarHucha() {
        boolean interrumpido = false;
        while (!isInterrupted() && !interrumpido) {
            try {
                esperarNuevaSemana();
            } catch (InterruptedException e) {
                interrumpido = true;
            }
            if (!isInterrupted() && !interrumpido) {
                synchronized(HUCHA){
                    hablar(HUCHA.ingresar(INGRESO_HUCHA)
                        ? "Ingresó "+ INGRESO_HUCHA + " en la semana " + getSemana() + ", Total actual: " + HUCHA.getCantidad()
                        : "No pudo ingresar en la semana " + getSemana());
                }
            }
        }
    }
}
