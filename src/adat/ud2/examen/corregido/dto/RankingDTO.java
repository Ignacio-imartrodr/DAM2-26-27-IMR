package adat.ud2.examen.corregido.dto;

public record RankingDTO(String nombreJugador, String nombreEquipo, int totalPuntos) {
    @Override
    public String toString() {
        return nombreJugador + " (" + nombreEquipo + ") → " + totalPuntos + " pts";
    }
}