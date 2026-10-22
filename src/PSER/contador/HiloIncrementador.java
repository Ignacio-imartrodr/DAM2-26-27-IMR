package PSER.contador;

/**
 * HiloIncrementador
 */
class HiloIncrementador extends Thread {
    Contador contador;
    int nAumentos;

    public HiloIncrementador(String nombre, int nAumentos, Contador contador) {
        super(nombre);
        this.nAumentos = nAumentos;
        this.contador = contador;
    }

    @Override
    public void run() {
        for (int i = 0; i < nAumentos; i++) {
            synchronized(contador){
                System.out.println(getName() + " > " + contador.getContador() + " + 1");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                }
                contador.incrementar();
            }
        }
    }
}