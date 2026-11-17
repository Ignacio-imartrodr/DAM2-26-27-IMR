package PSER.oficina;

public class Empleado extends Personal {
    long timeLeftJornada = getTiempoTrabajoMillis();
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
            oficina.wait(timeLeftJornada);
            long finJornada = System.currentTimeMillis();
            timeLeftJornada -= (finJornada - inicioJornada);
        } catch (InterruptedException e) {
            System.err.println(getName() + ": Error durmiendo");
        }
        if (timeLeftJornada > 0 && oficina.hasJefe()) {
            System.out.println(this.getName() + ": Buenas jefe, aquí trabajando");
            try {
                while (oficina.hasJefe() && timeLeftJornada > 0) {
                    long inicioJornada = System.currentTimeMillis();
                    oficina.wait(timeLeftJornada);
                    long finJornada = System.currentTimeMillis();
                    timeLeftJornada -= (finJornada - inicioJornada);
                }
            } catch (InterruptedException e) {
                System.err.println(getName() + ": Error trabajando");
            }
        }
    }

    @Override
    String getDespedida() {
        String saludo = this.getName() + ": ";
        if (timeLeftJornada <= 0) {
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