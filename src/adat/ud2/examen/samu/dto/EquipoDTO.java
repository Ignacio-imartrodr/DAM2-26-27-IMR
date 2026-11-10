package adat.ud2.examen.samu.dto;

public record EquipoDTO(int idEquipo, String nombre) {
    @Override
    public String toString() {
        return "[" + idEquipo + "] " + nombre;
    }
}