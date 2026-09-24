package Otros.Ajedrez;

import Otros.Ajedrez.Utilidades.PiezaException;

public class Peon extends Pieza {

    @Override
    public boolean mover(Integer[] pos, Boolean isEating) {
        if (pos.length != 2) {
            return false;
        }
        boolean valido = false;
        boolean isAvanzar = isWhite ? (pos[1] == posicion[1] + 1) : (pos[1] == posicion[1] - 1);
        boolean isDiagonal = (pos[0] == posicion[0] - 1) || (pos[0] == posicion[0] + 1);
        boolean isRecto = (pos[0] == posicion[0]);
        if (isAvanzar) {
            if (isEating == null) {
                if (isRecto || isDiagonal) {
                    valido = true;
                }
            } else {
                if (isEating && isDiagonal) {
                    valido = true;
                } else if (!isEating && isRecto){
                    valido = true;
                }
            }
        }
        return valido;
    }

    public Peon(int x, int y, boolean isWhite){
        if (super.setPosicion(x, y)) {
            this.isWhite = isWhite;
        } else {
            throw new PiezaException("Posición no válida");
        }
    }
}
