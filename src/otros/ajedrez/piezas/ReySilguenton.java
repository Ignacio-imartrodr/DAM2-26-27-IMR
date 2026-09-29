package otros.ajedrez.piezas;

public class ReySilguenton extends Pieza {
    // Rey versión "silguentón" doble
    private static final ReySilguenton BLANCO = new ReySilguenton(true);
    private static final ReySilguenton NEGRO = new ReySilguenton(false);

    private ReySilguenton(){}
    private  ReySilguenton(boolean isWhite){
        this.posicion = null;
        this.isWhite = isWhite;
    }
    public ReySilguenton getInstance(int x, int y, boolean isWhite){
        ReySilguenton r = isWhite ? BLANCO : NEGRO;
        r.setPosicion(x, y);
        return r;
    }
    
    private boolean setPosicion(Integer[] pos){
        return super.setPosicion(pos[0], pos[1]);
    }
    @Override
    public boolean setPosicion(Integer x, Integer y) {
        return false;
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
