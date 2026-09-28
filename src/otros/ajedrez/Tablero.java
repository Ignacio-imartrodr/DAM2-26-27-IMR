package otros.ajedrez;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import otros.ajedrez.Utilidades.PiezaException;
import otros.ajedrez.piezas.Alfil;
import otros.ajedrez.piezas.Caballo;
import otros.ajedrez.piezas.Peon;
import otros.ajedrez.piezas.Pieza;
import otros.ajedrez.piezas.Reina;
import otros.ajedrez.piezas.Rey;
import otros.ajedrez.piezas.Torre;

public class Tablero {
    private final static int TAMAÑO_LADO_TABLERO = 8;
    private final static String CASILLA_VACIA = "-";
    private Pieza[][] tabPiezas;

    public Tablero() {
        Set<Pieza> lPiezas = new TreeSet<>();
        Pieza p;
        int[] posicionesX;
        int posXIni;

        p = new Peon();
        for (int i = 0; i < p.getCantPiezas(); i++) {
            lPiezas.add(new Peon(i, p.getPosYIni(), false));
            p.setColor(true);
            lPiezas.add(new Peon(i, p.getPosYIni(), true));
            p.setColor(false);
        }

        p = new Rey();
        posXIni = p.getPosXIni()[0];
        lPiezas.add(new Rey(posXIni, p.getPosYIni(), false));
        p.setColor(true);
        lPiezas.add(new Rey(posXIni, p.getPosYIni(), true));

        p = new Reina();
        posXIni = p.getPosXIni()[0];
        lPiezas.add(new Reina(posXIni, p.getPosYIni(), false));
        p.setColor(true);
        lPiezas.add(new Reina(posXIni, p.getPosYIni(), true));

        p = new Alfil();
        posicionesX = p.getPosXIni();
        for (int i = 0; i < posicionesX.length; i++) {
            lPiezas.add(new Alfil(posicionesX[i], p.getPosYIni(), false));
            p.setColor(true);
            lPiezas.add(new Alfil(posicionesX[i], p.getPosYIni(), true));
            p.setColor(false);
        }

        p = new Caballo();
        posicionesX = p.getPosXIni();
        for (int i = 0; i < posicionesX.length; i++) {
            lPiezas.add(new Caballo(posicionesX[i], p.getPosYIni(), false));
            p.setColor(true);
            lPiezas.add(new Caballo(posicionesX[i], p.getPosYIni(), true));
            p.setColor(false);
        }

        p = new Torre();
        posicionesX = p.getPosXIni();
        for (int i = 0; i < posicionesX.length; i++) {
            lPiezas.add(new Torre(posicionesX[i], p.getPosYIni(), false));
            p.setColor(true);
            lPiezas.add(new Torre(posicionesX[i], p.getPosYIni(), true));
            p.setColor(false);
        }

        this(lPiezas);
    }
    public Tablero(Set<Pieza> lPiezas){
        this.tabPiezas = new Pieza[TAMAÑO_LADO_TABLERO][TAMAÑO_LADO_TABLERO];
        for (Pieza pieza : lPiezas) {
            Integer[] posiciones = pieza.getPosicion();
            this.tabPiezas[posiciones[Pieza.POS_Y]][posiciones[Pieza.POS_X]] = pieza;
        }
    }
    public Tablero(Pieza[][] tablero){
        boolean hasWhiteKing = false;
        boolean hasBlackKing = false;
        boolean hasOneEach = true;
        for (int y = 0; y < tablero.length && hasOneEach; y++) {
            for (int x = 0; x < tablero[y].length && hasOneEach; x++) {
                Pieza p = tablero[y][x];
                if ( p != null) {
                    p.setPosicion(x, y);
                    if (p instanceof Rey) {
                        if (p.isWhite()) {
                            if (!hasWhiteKing) {
                                hasWhiteKing = true;
                            } else {
                                hasOneEach = false;
                            }
                        } else {
                            if (!hasBlackKing) {
                                hasBlackKing = true;
                            } else {
                                hasOneEach = false;
                            }
                        }
                    }
                }
            }
        }
        hasOneEach = (hasBlackKing && hasWhiteKing && hasOneEach);
        boolean isMate = false;
        if (hasOneEach) {
            for (int y = 0; y < tablero.length && !isMate; y++) {
                for (int x = 0; x < tablero[y].length && hasOneEach; x++) {
                    Pieza p = tablero[y][x];
                    if (p != null) {
                        if (p instanceof Rey) {
                            if (isCheckMate(p.isWhite())) {
                                isMate = true;
                            }
                        }
                    }
                }
            }
        }
        if (isMate) {
            throw new PiezaException("En un tablero debe haber mínimo un rey de cada color sin estar en jaque mate");
        }
        this.tabPiezas = tablero;
    }

    private boolean isCheckMate(boolean toWhite) {
        boolean isCheck = isCheck(toWhite);
        if (isCheck) {
            //TODO compobar si es Mate
        }
        return isCheck;
    }
    private boolean isCheck(boolean toWhite){
        Pieza[] p = getPiezasActivas();
        Pieza reyAtacked = null;
        int posDifColores = -1;
        for (int i = 0; i < p.length; i++) {
            if (p[i] instanceof Rey) {
                if (toWhite) {
                    if (p[i].isWhite()) {
                        reyAtacked = p[i];
                    }
                } else {
                    if (!p[i].isWhite()) {
                        reyAtacked = p[i];
                    }
                }
            }
            if (p[i].isWhite()) {
                posDifColores = (toWhite ? i : p.length - i);
            }
        }
        boolean isCheck = false;
        if (toWhite) {
            for (int i = 0; i < posDifColores && !isCheck; i++) { // Solo las piezas negras
                validarMovimiento(p[i].getPosicion(), reyAtacked.getPosicion(), !reyAtacked.isWhite());
            }
        } else {
            for (int i = posDifColores; i < p.length && !isCheck; i++) { // Solo las piezas blancas
                validarMovimiento(p[i].getPosicion(), reyAtacked.getPosicion(), !reyAtacked.isWhite());
            }
        }
        return isCheck;
    }

    public  String[][] getTableroVacio() {
        String[][] tabVacio = new String[TAMAÑO_LADO_TABLERO][TAMAÑO_LADO_TABLERO];
        for (int i = 0; i < tabVacio.length; i++) {
            for (int j = 0; j < tabVacio[i].length; j++) {
                tabVacio[i][j] = CASILLA_VACIA;
            }
        }
        return tabVacio;
    }
    public Pieza[] getPiezasActivas() {
        Set<Pieza> lPiezas = new TreeSet<>();
        for (int i = 0; i < this.tabPiezas.length; i++) {
            for (Pieza pieza : this.tabPiezas[i]) {
                if (pieza != null) {
                    lPiezas.add(pieza);
                }
            }
        }
        Pieza[] p = new Pieza[lPiezas.size()];
        Iterator<Pieza> it = lPiezas.iterator();
        for (int i = 0; it.hasNext(); i++) {
            p[i] = it.next();
        }
        return p;
    }
    public String getTableroString() {
        String res = "";
        Pieza[] piezasActivas = getPiezasActivas();
        String[][] tablero = getTableroVacio();
        for (Pieza pieza : piezasActivas) {
            Integer[] posiciones = pieza.getPosicion();
            tablero[posiciones[Pieza.POS_Y]][posiciones[Pieza.POS_X]] = pieza.getForma();
        }

        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if (j == tablero[i].length - 1) {
                    res += tablero[i][j];
                } else {
                    res += tablero[i][j] + " ";
                }
            }
            if (i != tablero.length - 1) {
                res += "\n";
            }
        }
        return res;
    }
    public Pieza getPieza(int x, int y){
        return this.tabPiezas[y][x];
    }

    public boolean validarMovimiento(Integer[] posPieza, Integer[] posXY, boolean isWhiteTurn){ //TODO comprobar si funciona
        if (posXY == null || posPieza == null) {
            return false;
        }
        Pieza p = getPieza(posPieza[Pieza.POS_X], posPieza[Pieza.POS_Y]);
        if (p == null || (p.isWhite() != isWhiteTurn)) {
            return false;
        }
        boolean isValido = true;
        boolean isEating = getPieza(posXY[Pieza.POS_X], posXY[Pieza.POS_Y]) != null;
        if (p.validarMov(posXY, isEating)) {
            if (!(p.getForma().equalsIgnoreCase("C"))) {
                for (int i = p.getPosicion()[Pieza.POS_X]; i < posXY[Pieza.POS_X]; i++) {
                    for (int j = p.getPosicion()[Pieza.POS_Y]; j < posXY[Pieza.POS_Y]; j++) {
                        if (getPieza(i, j) != null){
                            if ((p.getForma().equalsIgnoreCase("R"))) {
                                Pieza[] piezas = getPiezasActivas();
                                for (int k = 0; k < piezas.length && isValido; k++) {
                                    Pieza pieza = piezas[k];
                                    if (pieza.isWhite() != p.isWhite() && !pieza.getClass().equals(p.getClass())) {
                                        if (validarMovimiento(pieza.getPosicion(), p.getPosicion(), pieza.isWhite())) {
                                            isValido = false;
                                        }
                                    }
                                }
                            } else {
                                isValido = false;
                            }
                        }
                    }
                }
            }
        } else {
            isValido = false;
        }
        return isValido;
    }
}
