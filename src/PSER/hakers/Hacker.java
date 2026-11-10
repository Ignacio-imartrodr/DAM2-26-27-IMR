package PSER.hakers;

import java.util.Random;

public abstract class Hacker extends Thread {
    protected static final String LETRAS = "qwertyuiopasdfghjklñzxcvbnm";
    protected final int MAX_LENGHT;
    protected static Random rnd = new Random();
    protected boolean verDetalle;

    public Hacker(String name, Tabla tabla, boolean verDetalle, int maxLenght) {
        MAX_LENGHT = maxLenght;
        super(name);
        this.verDetalle = verDetalle;
    }

    @Override
    public void run() {
        String contrasenha = "";
        
    }
    
    private void hablar(String txt) {
        System.out.println(getName() + " > " + txt);
    }

    abstract String generateContrasenha();
}
