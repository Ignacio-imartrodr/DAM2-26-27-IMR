package adat.ud2.biblioteca;

import java.time.LocalDate;

public class LibroDTO {
    private int id;
    private String titulo;
    private String autor;
    private LocalDate fechaPub;
    private Double valoracion;
    private UsuarioDTO usuario;
    
    public LibroDTO(int id, String titulo, String autor, LocalDate fechaNacimiento, Double valoracion, UsuarioDTO usuario) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.fechaPub = fechaNacimiento;
        this.usuario = usuario;
        if (valoracion == null || !setValoracion(valoracion)) {
            valoracion = null;
        }
    }

    public LibroDTO(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public LocalDate getFechaPub() {
        return fechaPub;
    }

    public void setFechaPub(LocalDate fechaPublicacion) {
        this.fechaPub = fechaPublicacion;
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
    
    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "LibroDTO [id= " + id + ", título= " + titulo + ", autor= " + (autor == null ? "Autor desconocido" : autor) + ", fecha publicación= " + fechaPub + ", valoración= "
                + (valoracion == 0. ? "Sin valoración" : valoracion ) + "Poseedor actual: "+ (usuario == null ? "Ninguno" : usuario.toString()) + "]";
    }
}
