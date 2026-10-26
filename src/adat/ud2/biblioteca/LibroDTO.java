package adat.ud2.biblioteca;

import java.time.LocalDate;

public class LibroDTO extends AbstractDTO {
    private int id;
    private String titulo;
    private String autor;
    private LocalDate fechaPub;
    private Double valoracion;
    private UsuarioDTO usuario;
    
    public LibroDTO(int id, String titulo, String autor, LocalDate fechaPub, Double valoracion, UsuarioDTO usuario) {
        if (titulo == null || titulo.isBlank() || fechaPub == null || fechaPub.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("El título y la fecha de publicación no pueden ser null o estar vacíos");
        }
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.fechaPub = fechaPub;
        this.usuario = usuario;
        if (valoracion == null || !setValoracion(valoracion)) {
            valoracion = null;
        }
    }

    public LibroDTO(String titulo, String autor, LocalDate fechaNacimiento, Double valoracion, UsuarioDTO usuario) {
        this(-1, titulo, autor, fechaNacimiento, valoracion, usuario);
    }

    public LibroDTO(int id, String titulo, LocalDate fechaPub) {
        this(id, titulo,null, fechaPub, null, null);
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

    public boolean setUsuario(UsuarioDTO usuario) {
        if (usuario != null) {
            if (usuario.getId() < 0) {
                return false;
            }
        }
        this.usuario = usuario;
        return true;
    }

    @Override
    public String toString() {
        return "LibroDTO [id= " + id + ", título= " + titulo + ", autor= " + (autor == null ? "Autor desconocido" : autor) + ", fecha publicación= " + fechaPub + ", valoración= "
                + (valoracion == 0. ? "Sin valoración" : valoracion ) + "Poseedor actual: "+ (usuario == null ? "Ninguno" : usuario.toString()) + "]";
    }
}
