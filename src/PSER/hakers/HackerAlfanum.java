package PSER.hakers;

public class HackerAlfanum extends Hacker{
    public HackerAlfanum(String name, Tabla tabla, boolean verDetalle, int maxLenght, Contrasenha contrasenha) {
        super(name, tabla, verDetalle, maxLenght, contrasenha);
    }

    @Override
    String generateContrasenha() {
        String contrasenha = "";
        int lenght = rnd.nextInt(1, MAX_LENGHT + 1);
        for (int i = 0; i < lenght ; i++) {
            
        }
    }

}
