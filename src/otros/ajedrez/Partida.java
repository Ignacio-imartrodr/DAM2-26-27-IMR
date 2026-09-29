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
    boolean isRuning;
    long timerInicio;
    long timeThrough;
    Tablero tablero;

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
    public boolean isWhiteTurn() {
        return isWhiteTurn;
    }
    public boolean isRuning() {
        return isRuning;
    }
    
    public boolean start(){
        boolean isInactive = this.timerInicio == -1;
        if (!isInactive) {
            this.timerInicio = System.currentTimeMillis();
            this.isWhiteTurn = this.isRuning = true;
        }
        return isInactive;
    }
    /**
     * @return {@code true} si blancas es el ganador de la partida.
     */
    public boolean finish(){
        this.isRuning = false;
        return isWhiteTurn;
    }
    /**
     * Pide por terminal un movimiento con formato {@code [A-Ha-h]\d\s?[A-Ha-h]\d}
     * 
     * @return  {@code Integer[]} con el movimiento de la siguiente forma:
     *          <p>
     *          {old x, old y, new x, new y}
     */
    public Integer[] pedirMovimiento(){
        String mov;
        Integer[] pos = new Integer[4];
        String regex = "[A-Ha-h]\\d[A-Ha-h]\\d";
        try (Scanner sc = new Scanner(System.in)){
            mov = sc.nextLine();
            mov = mov.replaceAll(" ", "");
            if (mov.matches(regex)) {
                for (int i = 1; i <= pos.length; i++) {
                    if (i % 2 == 0) {
                        pos[i - 1] = Character.toLowerCase(mov.charAt(i - 1)) - 'a';//TODO comprovar que valor devuelve
                    } else {
                        pos[i - 1] = Integer.valueOf(String.valueOf(mov.charAt(i - 1)));
                    }
                }
            } else {
                pos = null;
            }
        } catch (Exception e) {
            pos = null;
        }
        return pos;
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
        p.start();
        //TODO hacer turnos y juego
        while (p.isRuning()) {
            System.out.println(p.getTablero().getTableroString());
            System.out.println((p.getTimer()/1000) + "s");
            p.finish();
        }
    }
}
