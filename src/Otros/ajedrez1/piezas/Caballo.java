package otros.piezas;

import otros.ajedrez.Utilidades.PiezaException;

public class Caballo extends Pieza {
    public Caballo(){
        this.posicion = null;
        this.isWhite = false;
    }
    public Caballo(int x, int y, boolean isWhite){
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
        boolean isLShape = (Math.abs(pos[POS_X] - posicion[POS_X]) == 2 && Math.abs(pos[POS_Y] - posicion[POS_Y]) == 1)
                            || (Math.abs(pos[POS_Y] - posicion[POS_Y]) == 2 && Math.abs(pos[POS_X] - posicion[POS_X]) == 1);
        if (isLShape) {
            valido = true;
        }
        return valido;
    }
    @Override
    protected char getChar() {
        return 'C';
    }
    @Override
    public int[] getPosXIni() {
        return new int[] {1, 6};
    }
}
