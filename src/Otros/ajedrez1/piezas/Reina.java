package otros.piezas;

import otros.ajedrez.Utilidades.PiezaException;

public class Reina extends Pieza {
    public Reina(){
        this.posicion = null;
        this.isWhite = false;
    }
    public Reina(int x, int y, boolean isWhite){
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
        boolean isDiagonal = Math.abs(pos[POS_X] - posicion[POS_X]) == Math.abs(pos[POS_Y] - posicion[POS_Y]);
        if (isTranversal || isDiagonal) {
            valido = true;
        }
        return valido;
    }
    @Override
    protected char getChar() {
        return 'Q';
    }
    @Override
    public int[] getPosXIni() {
        return new int[] {3};
    }
}
