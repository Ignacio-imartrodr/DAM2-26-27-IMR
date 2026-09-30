package otros.ajedrez.piezas;

import java.util.Arrays;

public abstract class Pieza implements Comparable<Pieza>{
    //TODO validar que no se salten entre si (quizas implementar en tablero)
    protected final static int TAMAÑO_TABLERO = 8;
    protected Integer[] posicion; //Guarda fila(x) - columna(y), ten cuidado al ponerlo en un doble array[y][x]
    protected boolean isWhite;
    protected int cantPiezas = getPosXIni().length;
    public final static int POS_X = 0;
    public final static int POS_Y = 1;

    public Integer[] getPosicion() {
        return posicion;
    }
    public String getForma(){
        return isWhite ? String.valueOf(getChar()).toUpperCase() : String.valueOf(getChar()).toLowerCase();
    }
    public int getCantPiezas() {
        return cantPiezas;
    }
    public int getPosYIni() {
        return isWhite  ? TAMAÑO_TABLERO - 1 : 0;
    }
    public boolean isWhite() {
        return isWhite;
    }

    public void setColor(boolean isWhite) {
        this.isWhite = isWhite;
    }
    public boolean setPosicion(Integer x, Integer y) {
        boolean correcto = false;
        if (x == null && y == null) {
            correcto = true;
        } else if (x != null && y != null) {
            if (validarLimPos(new Integer[]{x, y})) {
                correcto = true;
                Integer[] pos = new Integer[2];
                pos[POS_X] = x;
                pos[POS_Y] = y;
                this.posicion = pos;
            }
        }
        return correcto;
    }

    protected static boolean validarLimPos(Integer[] pos){
        if (pos == null) {
            return true;
        }
        int x = pos[POS_X];
        int y = pos[POS_Y];
        return ((x >= 0 && x < TAMAÑO_TABLERO) && (y >= 0 && y < TAMAÑO_TABLERO));
    }

    public abstract int[] getPosXIni();
    public abstract boolean validarMov(Integer[] pos, Boolean isEating);
    protected abstract char getChar();
    
    @Override
    public int compareTo(Pieza o) {
        int compColor = Boolean.compare(o.isWhite(), this.isWhite());// Primero el color negro y luego el blanco
        if (compColor != 0) {
            return compColor;
        }
        int compLetra = Character.compare(this.getChar(), o.getChar());// Ordena alfabeticamente
        if (compLetra != 0) {
            return compLetra;
        }
        return Integer.compare(o.getPosicion()[POS_X], this.getPosicion()[POS_X]);// De izquierda a derecha en el tablero
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(posicion);
        result = prime * result + (isWhite ? 1237 : 1231);
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pieza other = (Pieza) obj;
        if (isWhite != other.isWhite)
            return false;
        if (!Arrays.equals(posicion, other.posicion))
            return false;
        return true;
    }
    
}
