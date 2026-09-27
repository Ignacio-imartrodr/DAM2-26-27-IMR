package otros.piezas;

import otros.ajedrez.Utilidades.PiezaException;

public class Rey extends Pieza {
    public Rey(){
        this.posicion = null;
        this.isWhite = false;
    }
    public Rey(int x, int y, boolean isWhite){
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
