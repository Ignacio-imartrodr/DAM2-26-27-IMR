package PSER.contador;

/**
 * Contador
 */
class Contador {
    private int contador;

    public Contador(){
        this.contador = 0;
    }

    public synchronized void incrementar(){
        contador++;
    }
    public void reset(){
        contador = 0;
    }
    public int getContador(){
        return contador;
    }
}