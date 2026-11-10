package adat.ud2.examen.corregido.dto;

public record JugadorDTO(int idJugador, int idEquipo, int dorsal,
                         String nombre, Integer alturaCm) {
    @Override
    public String toString() {
        return "#" + dorsal + " " + nombre + " (altura=" +
               (alturaCm == null ? "-" : alturaCm) + ")";
    }
}