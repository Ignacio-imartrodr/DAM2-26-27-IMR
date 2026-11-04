package PSER.apuestas;

public class Resultado {
    int DAM1;
    int PRF;
    
    public Resultado(int dAM1, int pRF) {
        DAM1 = dAM1;
        PRF = pRF;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + DAM1;
        result = prime * result + PRF;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Resultado other = (Resultado) obj;
        if (DAM1 != other.DAM1)
            return false;
        if (PRF != other.PRF)
            return false;
        return true;
    }

    public int getDAM1() {
        return DAM1;
    }

    public void setDAM1(int dAM1) {
        DAM1 = dAM1;
    }

    public int getPRF() {
        return PRF;
    }

    public void setPRF(int pRF) {
        PRF = pRF;
    }

    @Override
    public String toString() {
        return "Resultado DAM1 - PRF : " + DAM1 + " - " + PRF;
    }
}
