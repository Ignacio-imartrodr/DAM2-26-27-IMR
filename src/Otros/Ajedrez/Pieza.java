package Otros.Ajedrez;

public abstract class Pieza {
    protected final static int TAMAÑO_TABLERO = 8;
    protected static char forma;
    protected Integer[] posicion; //Guarda columna(y) - fila(x), que es más util para poner en un doble array[y][x]
    protected boolean isWhite;

    public String getForma(){
        return String.valueOf(forma);
    }
    public Integer[] getPosicion() {
        return posicion;
    }
    public boolean isWhite() {
        return isWhite;
    }

    public boolean setPosicion(Integer x, Integer y) {
        boolean correcto = false;
        if (x == null && y == null) {
            correcto = true;
        } else if (x != null && y != null) {
            if ((x >= 0 && x < TAMAÑO_TABLERO) && (y >= 0 && y < TAMAÑO_TABLERO)) {
                correcto = true;
                this.posicion = new Integer[] {y, x};
            }
        }
        return correcto;
    }

    abstract public boolean mover(Integer[] pos, Boolean isEating);
    
}
