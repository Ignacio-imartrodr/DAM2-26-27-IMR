package otros.piezas;

import otros.ajedrez.Utilidades.PiezaException;

public class Peon extends Pieza {
    public Peon(){
        this.posicion = null;
        this.isWhite = false;
    }
    public Peon(int x, int y, boolean isWhite){
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
        boolean isAvanzar = isWhite ? (pos[POS_Y] == posicion[POS_Y] + 1) : (pos[POS_Y] == posicion[POS_Y] - 1);
        boolean isDiagonal = Math.abs(pos[POS_X] - posicion[POS_X]) == 1;
        boolean isMismaCol = pos[POS_X] == posicion[POS_X];
        if (isAvanzar) {
            if (isEating == null) {
                if (isMismaCol || isDiagonal) {
                    valido = true;
                }
            } else {
                if (isEating && isDiagonal) {
                    valido = true;
                } else if (!isEating && isMismaCol){
                    valido = true;
                }
            }
        }
        return valido;
    }
    @Override
    protected char getChar() {
        return 'P';
    }
    @Override
    public int[] getPosXIni() {
        int[] posicionesX = new int[8];
        for (int i = 0; i < posicionesX.length; i++) {
            posicionesX[i] = i;
        }
        return posicionesX;
    }
    @Override
    public int getPosYIni() {
        return isWhite  ? TAMAÑO_TABLERO - 2 : 1;
    }
}
