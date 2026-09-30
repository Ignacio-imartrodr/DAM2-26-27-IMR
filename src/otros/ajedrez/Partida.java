package otros.ajedrez;

import java.util.Scanner;

import otros.ajedrez.piezas.Alfil;
import otros.ajedrez.piezas.Caballo;
import otros.ajedrez.piezas.Peon;
import otros.ajedrez.piezas.Pieza;
import otros.ajedrez.piezas.Reina;
import otros.ajedrez.piezas.ReySilguenton;

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
        this.isRuning = !tab.isCheckMate(isWhiteTurn);
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
        boolean isInactive = this.isRuning =(this.timerInicio == -1 || !this.tablero.isCheckMate(isWhiteTurn));
        if (isInactive) {
            this.timerInicio = System.currentTimeMillis();
            this.isWhiteTurn = true;
        }
        return isInactive;
    }
    /**
     * @return {@code this} La instancia de esta partida.
     */
    public Partida finish(){
        this.isRuning = false;
        return this;
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
                        pos[i - 1] = Character.toLowerCase(mov.charAt(i - 1)) - 'a';//TODO comprobar que valor debuelve
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
    public static boolean limpiarPantalla() {
        // Detectar el sistema operativo
        String sistemaOperativo = System.getProperty("os.name").toLowerCase();

        if (sistemaOperativo.contains("win")) {
            // Comando para Windows (ejecuta el comando 'cls' en el cmd)
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (Exception e) {
                return false;
            }
        } else {
            // Comando para Linux y Mac (ejecuta el comando 'clear' usando secuencias ANSI)
            System.out.print("\\033[H\\033[2J");
            System.out.flush();
        }
        return true;
    }
    public void mostrar(){
        Thread visualizador = new Thread(new Runnable(){
            @Override
            public void run() {
                synchronized(getTablero()){
                    System.out.println(getTablero().getTableroString());
                    mostrarTimer();
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {System.out.println("Error esperando");}
                    Partida.limpiarPantalla();
                }
            }

        });
        if(isRuning){
            visualizador.start();
        } else {
            System.out.println(getTablero().getTableroString());
            System.out.println((getTimer()/1000) + "s");
        }
    }
    private void mostrarTimer(){
        int sistemaTemp = 60;
        int t = Integer.valueOf(getTimer()/1000 + "");
        int s = t % sistemaTemp;
        int m = (t / sistemaTemp);
        m -= (sistemaTemp * (m / sistemaTemp));
        int h = t / (sistemaTemp * sistemaTemp);
        System.out.println(h + "h " + m + "\' " + s + "\"");
    }
    public static void main(String[] args) {
        Pieza[][] piezas = new Pieza[][] {
            {null, null, ReySilguenton.getInstance(2, 0, false), new Reina(), null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, new Alfil(true), null, null, null, null},
            {null, null, null, null, null, null, new Peon(), null},
            {null, null, null, null, null, null, null, new Caballo()},
            {null, null, null, null, null, null, null, null},
            {null, ReySilguenton.getInstance(1, 7, true), null, null, null, null, null, null}
        };
        Tablero t = new Tablero(piezas);
        Partida p = new Partida(t, -1, true);
        //Partida p = new Partida();
        p.start();
        while (p.isRuning()) {//TODO hacer turnos y juego
            p.mostrar();
            if (p.getTablero().isCheckMate(p.isWhiteTurn())) {
                p.finish();
            }
        }
    }
}
