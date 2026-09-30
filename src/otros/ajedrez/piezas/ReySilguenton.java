package otros.ajedrez.piezas;

public class ReySilguenton extends Pieza {
    // Rey versión "silguentón" doble
    private static ReySilguenton blanco = null;
    private static ReySilguenton negro = null;

    private ReySilguenton(){}
    private ReySilguenton(boolean isWhite){
        this.posicion = null;
        this.isWhite = isWhite;
    }
    private static synchronized void createInstance(boolean isWhite){
        if (isWhite) {
            blanco = new ReySilguenton(true);
        } else {
            negro = new ReySilguenton(false);
        }

    }
    public static ReySilguenton getInstance(int x, int y, boolean isWhite){
        if (isWhite ? (blanco == null) : (negro == null)) {
            createInstance(isWhite);
        }
        ReySilguenton r = isWhite ? blanco : negro;
        r.setPosicion(x, y);
        return r;
    }
    
    @Override
    public boolean setPosicion(Integer x, Integer y) {
        boolean correcto = false;
        if (x != null && y != null) {
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
    @Override
    public boolean validarMov(Integer[] pos, Boolean isEating) {
        if (pos == null) {
            return true;
        }
        if (!validarLimPos(pos) || pos.length != posicion.length) {
            return false;
        }
        boolean valido = false;
        boolean isTranversal = (Math.abs(pos[POS_X] - posicion[POS_X]) == 1 && pos[POS_Y] == posicion[POS_Y])
                            || (Math.abs(pos[POS_Y] - posicion[POS_Y]) == 1 && pos[POS_X] == posicion[POS_X]);
        boolean isDiagonal = Math.abs(pos[POS_X] - posicion[POS_X]) == 1 && Math.abs(pos[POS_Y] - posicion[POS_Y]) == 1;
        if (isTranversal || isDiagonal) {
            valido = true;
        }
        return valido;
    }
    @Override
    protected char getChar() {
        return 'R';
    }
    @Override
    public int[] getPosXIni() {
        return new int[] {4};
    }
}
