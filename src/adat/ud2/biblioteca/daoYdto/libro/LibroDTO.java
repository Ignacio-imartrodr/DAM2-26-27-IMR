package adat.ud2.biblioteca.daoYdto.libro;

import java.time.LocalDate;

import adat.ud2.biblioteca.daoYdto.AbstractDTO;
import adat.ud2.biblioteca.daoYdto.genero.GeneroDTO;

public class LibroDTO extends AbstractDTO {
    public static final int N_COPIAS_DEFAULT = 1;
    private int id;
    private String titulo;
    private String autor;
    private LocalDate fechaPub;
    private Double valoracion;
    private GeneroDTO genero;
    private int copiasTotales;
    private int copiasDisponibles;//TODO aplicar totales y disponibles
    
    public LibroDTO(int id, String titulo, String autor, LocalDate fechaPub, Double valoracion, GeneroDTO genero, Integer copiasTotales, Integer copiasDisponibles) {
        if (titulo == null || titulo.isBlank() || fechaPub == null || fechaPub.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("El título y la fecha de publicación no pueden ser null o estar vacíos");
        }
        if (copiasTotales != null && copiasTotales < 1) {
            throw new IllegalArgumentException("La cantidad total de copias debe ser mayor a 0");
        }
        if (copiasDisponibles != null && (copiasDisponibles < 0 || copiasDisponibles > copiasTotales)) {
            throw new IllegalArgumentException("La cantidad de copias disponibles debe ser mayor o igual a 0 y menor o igual a las copias totales");
        }
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.fechaPub = fechaPub;
        if (valoracion == null || !setValoracion(valoracion)) {
            this.valoracion = 0.;
        }
        this.genero = genero;
        this.copiasTotales = copiasTotales == null ? N_COPIAS_DEFAULT : copiasTotales;
        this.copiasDisponibles = copiasDisponibles == null ? N_COPIAS_DEFAULT : copiasDisponibles;
    }

    public LibroDTO(String titulo, String autor, LocalDate fechaPub, Double valoracion) {
        this(-1, titulo, autor, fechaPub, valoracion, null, null, null);
    }
    
    public LibroDTO(String titulo, LocalDate fechaPub) {
        this(titulo, null, fechaPub, null);
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
    
    public int getCopiasDisponibles() {
        return copiasDisponibles;
    }

    protected boolean setCopiasDisponibles(Integer nCopias) {
        if (nCopias != null && (nCopias > copiasTotales || nCopias < 0)) {
            return false;
        }
        copiasDisponibles = nCopias == null ? N_COPIAS_DEFAULT : nCopias;
        return true;
    }

    public int getCopiasTotales() {
        return copiasTotales;
    }

    protected boolean setCopiasTotales(Integer nCopias) {
        if (nCopias != null && nCopias < 1) {
            return false;
        }
        copiasTotales = nCopias == null ? N_COPIAS_DEFAULT : nCopias;
        return true;
    }

    public void addCopia(){
        copiasTotales++;
        copiasDisponibles++;
    }

    public boolean prestarCopia(){
        if (copiasDisponibles > 1) {
            copiasDisponibles--;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "LibroDTO [id= " + id + ", título= " + titulo + ", autor= " + (autor == null ? "Autor desconocido" : autor) + ", fecha publicación= " + fechaPub + ", valoración= " + (valoracion == 0. ? "Sin valoración" : valoracion ) + ", Copias totales: " + copiasTotales + ", Copias disponibles: " + copiasDisponibles + "]";
    }
}
