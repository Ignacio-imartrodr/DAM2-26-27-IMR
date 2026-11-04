package PSER.apuestas;

public class Tiket {
    int id;
    Double valor;
    public Tiket(int id, Double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("El valor no puede ser 0 o negativo");
        }
        this.id = id;
        this.valor = valor;
    }
    public int getId() {
        return id;
    }
    public Double getValor() {
        return valor;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + valor.intValue();
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
        Tiket other = (Tiket) obj;
        if (id != other.id)
            return false;
        if (valor != other.valor)
            return false;
        return true;
    }
}
