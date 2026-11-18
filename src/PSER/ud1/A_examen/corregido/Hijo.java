package PSER.ud1.A_examen.corregido;

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
        int retirada; 
        int retiradaAnterior = 0;
        int retiradaPreAnterior = 0;
        for (int i = 1; i <= NUM_RETIRADAS; i++) {
            retirada = calculoRetirada(retiradaPreAnterior, retiradaAnterior);
            retiradaPreAnterior = retiradaAnterior;
            retiradaAnterior = retirada;
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
