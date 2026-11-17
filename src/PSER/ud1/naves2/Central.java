package PSER.naves2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Central {
    private final List<Meteorito> METEORITOS;

    public Central(List<Meteorito> meteoritos) {
        this.METEORITOS = meteoritos;
    }

    public synchronized Meteorito getMeteorito(Nave nave)
        throws InterruptedException {
        while (!METEORITOS.isEmpty()) {
            List<Meteorito> disponibles = new ArrayList<>();

            for (Meteorito meteorito : METEORITOS) {
                if (nave.validarMeteorito(meteorito)) {
                    disponibles.add(meteorito);
                }
            }

            if (!disponibles.isEmpty()) {
                int indice = ThreadLocalRandom.current().nextInt(disponibles.size());
                Meteorito elegido = disponibles.get(indice);

                nave.reservarMeteorito(elegido);
                nave.notificarReserva();
                return elegido;
            }

            wait();
        }
        return null;
    }

    public synchronized void completarPerforacion(
            Meteorito meteorito, NaveA nave) {
        if (METEORITOS.contains(meteorito) && meteorito.getNaveA() == nave) {
            meteorito.setHole(true);
            notifyAll();
        }
    }

    public synchronized void esperarCombustibleYSalir(Meteorito meteorito, NaveA nave) {
        boolean interrumpida = false;

        while (meteorito.getNaveA() == nave && !meteorito.hasBS()) {
            try {
                wait();
            } catch (InterruptedException e) {
                interrumpida = true;
            }
        }

        if (meteorito.getNaveA() == nave) {
            meteorito.setNaveA(null);
            notifyAll();
        }

        if (interrumpida) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized boolean esperarSalidaYDestruir(Meteorito meteorito) {
        boolean interrumpida = false;

        while (meteorito.getNaveA() != null) {
            try {
                wait();
            } catch (InterruptedException e) {
                interrumpida = true;
            }
        }

        boolean exito = meteorito.hasHole()
                && meteorito.hasBS()
                && METEORITOS.remove(meteorito);

        meteorito.setBS(false);
        notifyAll();

        if (interrumpida) {
            Thread.currentThread().interrupt();
        }
        return exito;
    }
}