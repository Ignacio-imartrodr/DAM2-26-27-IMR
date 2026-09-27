package otros.ajedrez;

import java.util.ArrayList;
import java.util.List;

import otros.piezas.Alfil;
import otros.piezas.Caballo;
import otros.piezas.Peon;
import otros.piezas.Pieza;
import otros.piezas.Reina;
import otros.piezas.Rey;
import otros.piezas.Torre;

public class Tablero {
    private final static int TAMAÑO_LADO_TABLERO = 8;
    private String[][] tablero;
    public List<Pieza> piezas;

    public Tablero() { //TODO arreglar
        this.tablero = getTableroVacio();
        this.piezas = new ArrayList<>(32);
        Pieza p;
        int[] posicionesX;
        int posXIni;

        p = new Peon();
        for (int i = 0; i < p.getCantPiezas(); i++) {
            this.piezas.add(new Peon(i, p.getPosYIni(), false));
            p.setColor(true);
            this.piezas.add(new Peon(i, p.getPosYIni(), true));
            p.setColor(false);
        }

        p = new Rey();
        posXIni = p.getPosXIni()[0];
        this.piezas.add(new Rey(posXIni, p.getPosYIni(), false));
        p.setColor(true);
        this.piezas.add(new Rey(posXIni, p.getPosYIni(), true));

        p = new Reina();
        posXIni = p.getPosXIni()[0];
        this.piezas.add(new Reina(posXIni, p.getPosYIni(), false));
        p.setColor(true);
        this.piezas.add(new Reina(posXIni, p.getPosYIni(), true));

        p = new Alfil();
        posicionesX = p.getPosXIni();
        for (int i = 0; i < posicionesX.length; i++) {
            this.piezas.add(new Alfil(posicionesX[i], p.getPosYIni(), false));
            p.setColor(true);
            this.piezas.add(new Alfil(posicionesX[i], p.getPosYIni(), true));
            p.setColor(false);
        }

        p = new Caballo();
        posicionesX = p.getPosXIni();
        for (int i = 0; i < posicionesX.length; i++) {
            this.piezas.add(new Caballo(posicionesX[i], p.getPosYIni(), false));
            p.setColor(true);
            this.piezas.add(new Caballo(posicionesX[i], p.getPosYIni(), true));
            p.setColor(false);
        }

        p = new Torre();
        posicionesX = p.getPosXIni();
        for (int i = 0; i < posicionesX.length; i++) {
            this.piezas.add(new Torre(posicionesX[i], p.getPosYIni(), false));
            p.setColor(true);
            this.piezas.add(new Torre(posicionesX[i], p.getPosYIni(), true));
            p.setColor(false);
        }
        
        for (Pieza pieza : this.piezas) {
            Integer[] posiciones = pieza.getPosicion();
            this.tablero[posiciones[Pieza.POS_Y]][posiciones[Pieza.POS_Y]] = pieza.getForma();
        }
    }

    public String[][] getTablero() {
        return tablero;
    }
    public String getTableroFull() {
        String[] filas = getTableroRows();
        String t = "";
        for (int i = 0; i < filas.length - 1; i++) {
            t += filas[i] + "\n";
        }
        t += filas[filas.length -1];
        return t;
    }
    public String[] getTableroRows() {
        String[] filas = new String[8];
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < filas.length - 1; j++) {
                String casilla = tablero[i][j];
                filas[i] += casilla + "\s";
            }
            filas[i] += tablero[i][tablero[i].length - 1];
        }
        return filas;
    }
    public static String[][] getTableroVacio(){
        String[][] tableroVacio = new String[TAMAÑO_LADO_TABLERO][TAMAÑO_LADO_TABLERO];
        for (int i = 0; i < tableroVacio.length; i++) {
            for (int j = 0; j < tableroVacio[i].length; j++) {
                tableroVacio[i][j] = "-";
            }
        }
        return tableroVacio;
    }
}
