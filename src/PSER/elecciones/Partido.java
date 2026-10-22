package PSER.elecciones;

/**
 * Partido
 */
class Partido {
    private String nombre;
    private int contador;

    public Partido(String nombre) {
        this.nombre = nombre;
        this.contador = 0;
    }

    public synchronized void incrementar() {
        contador++;
    }

    /*
     * public void reset() {
     * contador = 0;
     * }
     */

    public synchronized int getContador() {
        return contador;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre + ", votos: " + contador;
    }
    
}