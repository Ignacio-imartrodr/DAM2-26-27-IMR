package adat.ud2.h2bd.oldBiblioteca;

import java.time.LocalDate;

public class LibroDTOold {
    private int id;
    private String titulo;
    private LocalDate fechaPublicación;
    private Double valoracion;
    
    public LibroDTOold(int id, String titulo, LocalDate fechaNacimiento, Double valoracion) {
        this.id = id;
        this.titulo = titulo;
        this.fechaPublicación = fechaNacimiento;
        if (valoracion == null || !setValoracion(valoracion)) {
            valoracion = null;
        }
    }

    public LibroDTOold() {
    }

    public LibroDTOold(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getFechaPublicación() {
        return fechaPublicación;
    }

    public void setFechaPublicación(LocalDate fechaPublicacion) {
        this.fechaPublicación = fechaPublicacion;
    }

    public Double getValoracion() {
        return valoracion;
    }

    public boolean setValoracion(Double valoracion) {
        if (valoracion == null || valoracion < 0 || valoracion > 5) {
            return false;
        }
        this.valoracion = valoracion;
        return true;
    }

    @Override
    public String toString() {
        return "LibroDTO [id= " + id + ", titulo= " + titulo + ", fechaPublicación= " + fechaPublicación + ", valoracion= "
                + (valoracion == 0. ? "Sin valoración" : valoracion ) + "]";
    }
}
