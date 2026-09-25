package otros.piezas;

import otros.ajedrez.Utilidades.PiezaException;

public class Alfil extends Pieza {
    public Alfil(){
        this.posicion = null;
        this.isWhite = false;
    }
    public Alfil(int x, int y, boolean isWhite){
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
        boolean isDiagonal = Math.abs(pos[POS_X] - posicion[POS_X]) == Math.abs(pos[POS_Y] - posicion[POS_Y]);
        if (isDiagonal) {
            valido = true;
        }
        return valido;
    }
    @Override
    protected char getChar() {
        return 'A';
    }
    @Override
    public int[] getPosXIni() {
        return new int[] {2, 5};
    }
}
