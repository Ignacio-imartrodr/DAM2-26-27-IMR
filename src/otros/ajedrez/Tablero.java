package otros.ajedrez;

import java.util.ArrayList;
import java.util.List;

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
        List<Pieza> lPiezas = new ArrayList<>(32);
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

        tabPiezas = new Pieza[TAMAÑO_LADO_TABLERO][TAMAÑO_LADO_TABLERO];
        for (Pieza pieza : lPiezas) {
            Integer[] posiciones = pieza.getPosicion();
            this.tabPiezas[posiciones[Pieza.POS_Y]][posiciones[Pieza.POS_X]] = pieza;
        }
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
        List<Pieza> lPiezas = new ArrayList<>();
        for (int i = 0; i < this.tabPiezas.length; i++) {
            for (Pieza pieza : this.tabPiezas[i]) {
                if (pieza != null) {
                    lPiezas.add(pieza);
                }
            }
        }
        int nPiezas = lPiezas.size();
        Pieza[] p = new Pieza[nPiezas];
        for (int i = 0; i < nPiezas; i++) {
            p[i] = lPiezas.get(i);
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
}
