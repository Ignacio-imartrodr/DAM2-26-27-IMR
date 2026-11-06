package PSER.ventas;

public class Coche {
    private String modelo;
    private boolean inVisita;
    private int visitas;
    private int cantidad;

    public Coche(String modelo, int cantidad) {
        this.modelo = modelo;
        this.cantidad = cantidad;
        this.visitas = 0;
        this.inVisita = false;
    }

    public Coche(String modelo) {
        this(modelo, 1);
    }

    public synchronized void addCantidad(int cantidad){
        this.cantidad += cantidad;
    }
    
    public synchronized void addVisita(){
        this.visitas++;
    }
    
    public int getVisitas() {
        return visitas;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getModelo() {
        return modelo;
    }

    public boolean isInVisita() {
        return inVisita;
    }

    public synchronized boolean startVisita() {
        if (inVisita) {
            return false;
        }
        this.inVisita = true;
        return true;
    }

    public synchronized boolean endVisita() {
        if (!inVisita) {
            return false;
        }
        this.inVisita = false;
        visitas++;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((modelo == null) ? 0 : modelo.hashCode());
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
        Coche other = (Coche) obj;
        if (modelo == null) {
            if (other.modelo != null)
                return false;
        } else if (!modelo.equals(other.modelo))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return modelo + cantidad;
    }
}
