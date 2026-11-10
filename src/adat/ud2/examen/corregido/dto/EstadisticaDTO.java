package adat.ud2.examen.corregido.dto;

public record EstadisticaDTO(int idPartido, int idJugador, int puntos) {
    @Override
    public String toString() {
        return "partido=" + idPartido + " jugador=" + idJugador + " puntos=" + puntos;
    }
}