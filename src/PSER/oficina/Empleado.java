package PSER.oficina;

public class Empleado extends Personal {
    long leftJornada = getTiempoTrabajoMillis();
    public Empleado(String nombre, Oficina oficina) {
        super(nombre, oficina);
    }

    @Override
    String getSaludo() {
        String saludo = this.getName() + ": ";
        if (oficina.hasJefe()) {
                saludo += "Hola jefe, me pongo a trabajar...";
        } else {
            saludo +=  "ZZZZZZ";
        }
        return saludo;
    }

    @Override
    void aTrabajar() {
        saludar();
        try {
            long inicioJornada = System.currentTimeMillis();
            oficina.wait(leftJornada);
            long finJornada = System.currentTimeMillis();
            leftJornada -= (finJornada - inicioJornada);
        } catch (InterruptedException e) {
            System.err.println(getName() + ": Error durmiendo");
        }
        if (oficina.hasJefe() && leftJornada > 0) {
            System.out.println(this.getName() + ": Buenas jefe, aquí trabajando");
            try {
                long inicioJornada = System.currentTimeMillis();
                oficina.wait(leftJornada);
                long finJornada = System.currentTimeMillis();
                leftJornada -= (finJornada - inicioJornada);
            } catch (InterruptedException e) {
                System.err.println(getName() + ": Error trabajando");
            }
        }
    }

    @Override
    String getDespedida() {
        String saludo = this.getName() + ": ";
        if (leftJornada <= 0) {
            if (oficina.hasJefe()) {
                saludo += "Adiós jefe, me toca irme";
            } else {
                saludo += "Marcho, ya es mi hora";
            }
        } else {
            saludo += "Me marcho pronto...";
        }
        return saludo;
    }

    @Override
    void salirTrabajo() {
        despedirse();
    }

    @Override
    protected long getTiempoTrabajoMillis() {
        return 1000L;
    }
}