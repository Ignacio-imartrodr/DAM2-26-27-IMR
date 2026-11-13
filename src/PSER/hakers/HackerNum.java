package PSER.hakers;

public class HackerNum extends Hacker{
    public HackerNum(ThreadGroup grupo, String name, Tabla tabla, boolean verDetalle, int maxLenght, Contrasenha contrasenha) {
        super(grupo, name, tabla, verDetalle, maxLenght, contrasenha);
    }

    @Override
    String generateContrasenha() {
        String contrasenha = "";
        contrasenha += rnd.nextInt(Math.powExact(10, MAX_LENGHT));
        return contrasenha; 
    }
}
