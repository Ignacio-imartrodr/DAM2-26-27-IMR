package otros.ajedrez;

import java.util.Scanner;

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
    private final String letrasPos = "abcdefgh";

    public Partida(){
        this.tablero = new Tablero();
        this.timerInicio = this.timeThrough = -1;
    }
    public Partida(Tablero tab, long tIni, boolean isWhiteTurn){
        this.tablero = tab;
        this.timerInicio = tIni;
        this.isWhiteTurn = isWhiteTurn;
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
     * @return {@code true} si blancas es el ganador de la partida.
     */
    public boolean finish(){
        return isWhiteTurn;
    }
    /**
     * 
     * @return  {@code Integer[]} con el movimiento de la siguiente forma:
     *          <p>
     *          {prev x, prev y, new x, new y}
     */
    public Integer[] pedirMovimiento(){
        String mov;
        Integer[] pos;
        try (Scanner sc = new Scanner(System.in)){
            mov = sc.nextLine();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    public static void main(String[] args) {
        Pieza[][] piezas = new Pieza[][] {
            {null, null, new Rey(), new Reina(), null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, new Alfil(true), null, null, null, null},
            {null, null, null, null, null, null, new Peon(), null},
            {null, null, null, null, null, null, null, new Caballo()},
            {null, null, null, null, null, null, null, null},
            {null, new Rey(true), null, null, null, null, null, null}
        };
        Tablero t = new Tablero(piezas);
        Partida p = new Partida(t, -1, true);
        if (p.start()) {
            System.out.println(p.getTablero().getTableroString());
        }
    }
}
