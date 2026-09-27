package otros.ajedrez;

public class Partida {

    boolean isWhiteTurn;
    long timerInicio;
    long timeThrough;
    Tablero tablero;
    public Partida(){
        this.tablero = new Tablero();
        this.timerInicio = this.timeThrough = -1;
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
        Partida p = new Partida();
        if (p.start()) {
            System.out.println(p.getTablero().getTableroString());
        }
    }
}
