package PSER.hakers;

public class HackerAlfanum extends Hacker{
    public HackerAlfanum(ThreadGroup grupo, String name, Tabla tabla, boolean verDetalle, int maxLenght, Contrasenha contrasenha) {
        super(grupo, name, tabla, verDetalle, maxLenght, contrasenha);
    }

    @Override
    String generateContrasenha() {
        String contrasenha = "";
        int lenght = rnd.nextInt(1, MAX_LENGHT + 1);
        boolean isNum;
        for (int i = 0; i < lenght ; i++) {
            isNum = rnd.nextBoolean();
            if (isNum) {
                contrasenha += rnd.nextInt(10);
            } else {
                contrasenha += LETRAS.charAt(rnd.nextInt(LETRAS.length()));
            }
        }
        return contrasenha;
    }

}
