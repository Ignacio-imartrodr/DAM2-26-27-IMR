package otros.piezas;

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
    public boolean isWhite() {
        return isWhite;
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
    public abstract boolean mover(Integer[] pos, Boolean isEating);
    protected abstract char getChar();
    
    @Override
    public int compareTo(Pieza o) {
        int compCol = Boolean.compare(this.isWhite(), o.isWhite());
        if (compCol != 0) {
            return compCol;
        }
        return Character.compare(this.getChar(), o.getChar());
    }
}
