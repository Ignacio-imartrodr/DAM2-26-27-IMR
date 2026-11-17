package PSER.hakers;

import java.util.Random;

public abstract class Hacker extends Thread {
    private static volatile boolean wasFounded = false;
    protected static final String LETRAS = "qwertyuiopasdfghjklñzxcvbnmQWERTYUIOPASDFGHJKLÑZXCVBNM";
    protected final int MAX_LENGHT;
    protected static Random rnd = new Random();
    protected boolean verDetalle;
    protected Contrasenha contrasenha;
    protected Tabla tabla;

    public Hacker(ThreadGroup grupo, String name, Tabla tabla, boolean verDetalle, int maxLenght, Contrasenha contrasenha) {
        super(grupo, name);
        this.tabla = tabla;
        this.verDetalle = verDetalle;
        this.MAX_LENGHT = maxLenght;
        this.contrasenha = contrasenha;
    }

    @Override
    public void run() {
        String key;
        String value;
        int i = 0;
        while (!wasFounded){
            key = generateContrasenha();
            value = tabla.getHash(key);
            if (tabla.addPrueba(key)) {
                if (value == null) {
                    value = Contrasenha.hashear(key);
                    tabla.setNewEntrada(key, value);
                }
                if (verDetalle) {
                    if (!wasFounded()) {
                        hablar("En vuelta " + ++i + " generó: " + key);
                    }
                }
                if (value.equals(contrasenha.getHash())) {
                    if (!wasFounded()) {
                        setWasFounded(true);
                        hablar("Encontré la contraseña!, es: \"" + key + "\", con hash: " + value);
                    }
                }
            }
        }
    }
    
    public static boolean wasFounded() {
        synchronized(Hacker.class){
            return Hacker.wasFounded;
        }
    }

    protected static void setWasFounded(boolean wasFounded) {
        synchronized(Hacker.class){
            Hacker.wasFounded = wasFounded;
        }
    }

    protected void hablar(String txt) {
        System.out.println(getName() + " > " + txt);
    }

    abstract String generateContrasenha();
}
