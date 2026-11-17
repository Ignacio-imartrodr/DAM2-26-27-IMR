package PSER.hakers;

public class HackerBago extends Hacker {


    public HackerBago(ThreadGroup grupo, String name, Tabla tabla, boolean verDetalle, int maxLenght, Contrasenha contrasenha) {
        super(grupo, name, tabla, verDetalle, maxLenght, contrasenha);
    }

    @Override
    public void run() {
        String[] keys = tabla.getKeys();
        String key;
        String value;
        for (int i = 0; i < keys.length && !wasFounded(); i++) {
            key = keys[i];
            value = tabla.getHash(key);
            if (tabla.addPrueba(key)) {
                if (value == null) {
                    value = Contrasenha.hashear(key);
                    tabla.setNewEntrada(key, value);
                }
                if (verDetalle) {
                    if (!wasFounded()) {
                        hablar("En vuelta " + i + " generó: " + key);
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

    @Override
    String generateContrasenha() {
        return null;
    }

}
