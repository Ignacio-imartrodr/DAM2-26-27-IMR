package PSER.examenes.ud1.entregado;

public class Hijo extends Hilo {
    final int MIN_ESPERA;
    final int MAX_ESPERA;
    final int NUM_RETIRADAS;
    
    public Hijo(String name, Hucha general, int minEspera, int maxEspera, int numRetiradas) {
        super(name, general);
        MIN_ESPERA = minEspera;
        MAX_ESPERA = maxEspera;
        NUM_RETIRADAS = numRetiradas;
    }

    @Override
    protected void manejarHucha() {
        int retirada; 
        int retiradaAnterior = 1;
        int retiradaPreAnterior = 0;
        for (int i = 1; i <= NUM_RETIRADAS; i++) {
            retirada = retiradaPreAnterior + retiradaAnterior;
            retiradaPreAnterior = retiradaAnterior;
            retiradaAnterior = retirada;
            HUCHA.retirar(retirada);
            hablar("En la semana " + HUCHA.getSemana() + " retiró: " + retirada);
            try {
                Thread.sleep(MIN_ESPERA, MAX_ESPERA);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
