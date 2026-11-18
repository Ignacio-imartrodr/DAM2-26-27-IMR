package PSER.examenes.ud1.corregido;

public class Hijo extends Familiar {
    final int MIN_ESPERA;
    final int MAX_ESPERA;
    final int NUM_RETIRADAS;
    
    public Hijo(String name, Hucha general, int minEspera, int maxEspera, int numRetiradas, Semana semana) {
        super(name, general, semana);
        MIN_ESPERA = minEspera;
        MAX_ESPERA = maxEspera;
        NUM_RETIRADAS = numRetiradas;
    }

    @Override
    protected void manejarHucha() {
        int retirada = 0;
        int retiradAnterior = 0;
        for (int i = 1; i <= NUM_RETIRADAS; i++) {
            retiradAnterior = retirada;
            retirada = calculoRetirada(retiradAnterior, retirada);
            synchronized(HUCHA){
                HUCHA.retirar(retirada);
                hablar("Semana " + getSemana() + " (retirada " + i + "), retiró: " + retirada);
            }
            try {
                Thread.sleep(MIN_ESPERA, MAX_ESPERA);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private int calculoRetirada(int preAnterior, int anterior){
        if (anterior == 0 && preAnterior == 0) {
            return 1;
        }
        return anterior + preAnterior;
    }
}
