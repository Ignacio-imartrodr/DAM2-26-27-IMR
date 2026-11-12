package PSER.hakers;

import java.util.Random;

public abstract class Hacker extends Thread {
    private static volatile boolean wasFounded = false;
    protected static final String LETRAS = "qwertyuiopasdfghjklñzxcvbnm";//QWERTYUIOPASDFGHJKLÑZXCVBNM";
    protected final int MAX_LENGHT;
    protected static Random rnd = new Random();
    protected boolean verDetalle;
    protected Contrasenha contrasenha;
    protected Tabla tabla;

    public Hacker(ThreadGroup grupo, String name, Tabla tabla, boolean verDetalle, int maxLenght, Contrasenha contrasenha) {
        super(grupo, name);
        this.tabla = tabla;
        this.verDetalle = verDetalle;
        MAX_LENGHT = maxLenght;
        this.contrasenha = contrasenha;
    }

    @Override
    public void run() {
        String key;
        String value;
        int i = 1;
        while (!wasFounded){
            key = generateContrasenha();
            value = tabla.getHash(key);
            if (value == null) {
                value = Contrasenha.hashear(key);
                tabla.setNewEntrada(key, value);
            }
            if (verDetalle) {
                hablar("En vuelta " + i + " generó: " + key);
                i++;
            }
            if (value.equals(contrasenha.getHash())) {
                synchronized(Hacker.class){
                    if (!wasFounded) {
                        wasFounded = true;
                        hablar("Encontré la contraseña!, es: " + key);
                    }
                } 
            }
        }
    }
    
    public static void setWasFounded(boolean wasFounded) {
        Hacker.wasFounded = wasFounded;
    }

    private void hablar(String txt) {
        System.out.println(getName() + " > " + txt);
    }

    abstract String generateContrasenha();
}
