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
        int retirada;
        for (int i = 1; i <= NUM_RETIRADAS; i++) {
            retirada = fibonacchi(i);
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

    
    private int fibonacchi(int n) {
        if (n > 46) {
            return Integer.MAX_VALUE;
        }
        if (n <= 0) return 0;
        int a = 1;
        int b = 1;
        int temp;
        for (int i = 2; i < n; i++) {
            temp = b;
            b = a + b;
            a = temp;
        }
        return b;
    }
    
    /* 
    private int calculofibonacchi(int preAnterior, int anterior){
        if (anterior == 0 && preAnterior == 0) {
            return 1;
        }
        return anterior + preAnterior;
    } */
}
