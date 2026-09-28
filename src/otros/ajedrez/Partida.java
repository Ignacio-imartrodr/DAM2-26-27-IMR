package otros.ajedrez;

import otros.ajedrez.piezas.Alfil;
import otros.ajedrez.piezas.Caballo;
import otros.ajedrez.piezas.Peon;
import otros.ajedrez.piezas.Pieza;
import otros.ajedrez.piezas.Reina;
import otros.ajedrez.piezas.Rey;

public class Partida {

    boolean isWhiteTurn;
    long timerInicio;
    long timeThrough;
    Tablero tablero;
    public Partida(){
        this.tablero = new Tablero();
        this.timerInicio = this.timeThrough = -1;
    }
    public Partida(Tablero tab, long tIni){
        this.tablero = tab;
        timerInicio = tIni;
    }

    public Tablero getTablero(){
        return tablero;
    }
    /**
     * @return Tiempo transcurrido en milisegundos
     */
    public long getTimer(){
        if (timerInicio != -1) {
            this.timeThrough = System.currentTimeMillis() - timerInicio;
        }
        return this.timeThrough;
    }

    public boolean start(){
        boolean isInactive = this.timerInicio == -1;
        if (isInactive) {
            this.timerInicio = System.currentTimeMillis();
            this.isWhiteTurn = true;
        }
        return isInactive; //TODO hacer turnos y juego
    }
    /**
     * @return Ganador de la partida.
     */
    public boolean finish(){
        return isWhiteTurn;
    }

    public static void main(String[] args) {
        Pieza[][] piezas = new Pieza[][] {
            {null, null, new Rey(true), new Reina(), null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, new Alfil(true), null, null, null, null},
            {null, null, null, null, null, null, new Peon(), null},
            {null, null, null, null, null, null, null, new Caballo()},
            {null, null, null, null, null, null, null, null},
            {null, new Rey(), null, null, null, null, null, null}
        };
        Tablero t = new Tablero(piezas);
        Partida p = new Partida(t, -1);
        if (p.start()) {
            System.out.println(p.getTablero().getTableroString());
        }
    }
}
