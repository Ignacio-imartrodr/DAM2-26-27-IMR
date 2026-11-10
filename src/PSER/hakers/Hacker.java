package PSER.hakers;

import java.util.Random;

public abstract class Hacker extends Thread {
    protected static final String LETRAS = "qwertyuiopasdfghjklñzxcvbnm";
    protected final int MAX_LENGHT;
    protected static Random rnd = new Random();
    protected boolean verDetalle;
    protected Contrasenha contrasenha;

    public Hacker(String name, Tabla tabla, boolean verDetalle, int maxLenght, Contrasenha contrasenha) {
        MAX_LENGHT = maxLenght;
        super(name);
        this.verDetalle = verDetalle;
        this.contrasenha = contrasenha;
    }

    @Override
    public void run() {
        String key = generateContrasenha();
        String value = Contrasenha.hashear(key);
        try {
            
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    
    private void hablar(String txt) {
        System.out.println(getName() + " > " + txt);
    }

    abstract String generateContrasenha();
}
