package Otros.Ajedrez;

public class Partida {
    Tablero tablero;
    public Partida(){
        this.tablero = new Tablero();
    }
    public boolean start(){
        return false; //TODO hacer turnos y juego
    }
    public static void main(String[] args) {
        Partida p = new Partida();
        p.start();
    }
}
