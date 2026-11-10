package PSER.hakers;

import PSER.Utiles;

public class Cotrasenha {
    private final int MAX_LENGTH;
    private String contrasenha;

    public Cotrasenha(String contrasenha, int maxLenght) throws IllegalArgumentException {
        if ((MAX_LENGTH = maxLenght) < 1 || !setContrasenha(contrasenha)) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cotrasenha other = (Cotrasenha) obj;
        if (contrasenha == null) {
            if (other.contrasenha != null)
                return false;
        } else if (!contrasenha.equals(other.contrasenha))
            return false;
        return true;
    }

    public String getContrasenha() {
        return contrasenha;
    }

    private boolean setContrasenha(String contrasenha) {
        if (contrasenha != null && contrasenha.length() <= MAX_LENGTH) {
            this.contrasenha = Utiles.generateHash(contrasenha);
            return true;
        }
        return false;
    }
    
}
