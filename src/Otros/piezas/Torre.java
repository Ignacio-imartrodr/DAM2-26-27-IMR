package otros.piezas;

import otros.ajedrez.Utilidades.PiezaException;

public class Torre extends Pieza {
    public Torre(){
        this.posicion = null;
        this.isWhite = false;
    }
    public Torre(int x, int y, boolean isWhite){
        if (super.setPosicion(x, y)) {
            this.isWhite = isWhite;
        } else {
            throw new PiezaException("Posición no válida");
        }
    }
    
    @Override
    public boolean mover(Integer[] pos, Boolean isEating) {
        if (pos == null) {
            return true;
        }
        if (!validarLimPos(pos) || pos.length != posicion.length) {
            return false;
        }
        boolean valido = false;
        boolean isTranversal = (Math.abs(pos[POS_X] - posicion[POS_X]) != 0 && pos[POS_Y] == posicion[POS_Y])
                            || (Math.abs(pos[POS_Y] - posicion[POS_Y]) != 0 && pos[POS_X] == posicion[POS_X]);
        if (isTranversal) {
            valido = true;
        }
        return valido;
    }
    @Override
    protected char getChar() {
        return 'T';
    }
    @Override
    public int[] getPosXIni() {
        return new int[] {0, 7};
    }
}
