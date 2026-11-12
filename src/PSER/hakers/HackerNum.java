package PSER.hakers;

public class HackerNum extends Hacker{
    public HackerNum(ThreadGroup grupo, String name, Tabla tabla, boolean verDetalle, int maxLenght, Contrasenha contrasenha) {
        super(grupo, name, tabla, verDetalle, maxLenght, contrasenha);
    }

    @Override
    String generateContrasenha() {
        String contrasenha = "";
        int lenght = rnd.nextInt(1, MAX_LENGHT + 1);
        int corrector = lenght == 1 ? 1 : 0;
        contrasenha += rnd.nextInt(Math.powExact(10, (lenght - 1)) - corrector, Math.powExact(10, lenght));
        return contrasenha; 
    }

}
