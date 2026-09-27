package otros.ajedrez;

public class Partida {
    Tablero tablero;
    public Partida(){
        this.tablero = new Tablero();
    }

    public Tablero getTablero(){
        return tablero;
    }
    public boolean start(){
        return true; //TODO hacer turnos y juego
    }
    public static void main(String[] args) {
        Partida p = new Partida();
        if (p.start()) {
            System.out.println(p.getTablero().getTableroFull());
        }
    }
}
