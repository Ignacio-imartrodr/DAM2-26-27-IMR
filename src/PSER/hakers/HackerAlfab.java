package PSER.hakers;

public class HackerAlfab extends Hacker{
    

    public HackerAlfab(ThreadGroup grupo, String name, Tabla tabla, boolean verDetalle, int maxLenght, Contrasenha contrasenha) {
        super(grupo, name, tabla, verDetalle, maxLenght, contrasenha);
    }

    @Override
    String generateContrasenha() {
        String contrasenha = "";
        int lenght = rnd.nextInt(1, MAX_LENGHT + 1);
        for (int i = 0; i < lenght ; i++) {
            contrasenha += LETRAS.charAt(rnd.nextInt(LETRAS.length()));
        }
        return contrasenha;
    }

}
