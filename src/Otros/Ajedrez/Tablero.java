package Otros.Ajedrez;

import java.util.List;

public class Tablero {
    private final static int TAMAÑO_TABLERO = 8;
    private String[][] tablero;
    public List<Pieza> piezasBlancas;
    public List<Pieza> piezasNegras;

    public Tablero() {
        this.tablero = new String[TAMAÑO_TABLERO][TAMAÑO_TABLERO];
        for (int i = 0; i < TAMAÑO_TABLERO; i++) {
            this.piezasBlancas.add(new Peon(i, 1, true));
            this.piezasNegras.add(new Peon(i, 6, false));
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
}
