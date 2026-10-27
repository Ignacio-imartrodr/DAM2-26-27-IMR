package adat.ud2.biblioteca.daoYdto.libro;

import java.time.LocalDate;

import adat.ud2.biblioteca.daoYdto.AbstractDTO;
import adat.ud2.biblioteca.daoYdto.genero.GeneroDTO;

public class LibroDTO extends AbstractDTO {
    public static final int N_COPIAS_DEFAULT = 1;
    private String titulo;
    private String autor;
    private LocalDate fechaPub;
    private Double valoracion;
    private GeneroDTO genero;
    private int copiasTotales;
    private int copiasDisponibles;//TODO aplicar totales y disponibles
    
    public LibroDTO(int id, String titulo, String autor, LocalDate fechaPub, Double valoracion, GeneroDTO genero, Integer copiasTotales, Integer copiasDisponibles) {
        super(id);
        if (!setTitulo(titulo)) {
            throw new IllegalArgumentException("El título no puede ser null o estar vacío");
        }
        if (!setFechaPub(fechaPub)) {
            throw new IllegalArgumentException("La fecha de publicación no pueden ser null o futuro");
        }
        if (!setCopiasTotales(copiasTotales)) {
            throw new IllegalArgumentException("La cantidad total de copias debe ser mayor a 0");
        }
        if (!setCopiasDisponibles(copiasDisponibles)) {
            throw new IllegalArgumentException("La cantidad de copias disponibles debe ser mayor o igual a 0 y menor o igual a las copias totales");
        }
        if (!setValoracion(valoracion)) {
            throw new IllegalArgumentException("La valoración debe ser null o decimal entre 0 y 5");
        }
        this.autor = autor;
        this.genero = genero;
    }

    public LibroDTO(String titulo, String autor, LocalDate fechaPub, Double valoracion, GeneroDTO genero, Integer copiasTotales, Integer copiasDisponibles) {
        this(-1, titulo, autor, fechaPub, valoracion, genero, copiasTotales, copiasDisponibles);
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
        return titulo.charAt(0) + ti;
    }

    public boolean setTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            return false;
        }
        this.titulo = titulo.toUpperCase();
        return true;
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

    public boolean setFechaPub(LocalDate fechaPublicacion) {
        if (fechaPub == null || fechaPub.isAfter(LocalDate.now())) {
            return false;
        }
        this.fechaPub = fechaPublicacion;
        return true;
    }

    public Double getValoracion() {
        return valoracion;
    }

    public boolean setValoracion(Double valoracion) {
        if (valoracion != null && (valoracion < 0 || valoracion > 5)) {
            return false;
        }
        this.valoracion = valoracion;
        return true;
    }
    
    public GeneroDTO getGenero() {
        return genero;
    }

    public void setGenero(GeneroDTO genero) {
        this.genero = genero;
    }

    public int getCopiasDisponibles() {
        return copiasDisponibles;
    }

    protected boolean setCopiasDisponibles(Integer nCopias) {
        if (nCopias != null && (nCopias > copiasTotales || nCopias < 0)) {
            return false;
        }
        copiasDisponibles = nCopias == null ? this.copiasTotales : nCopias;
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
        return "LibroDTO [id= " + id + ", título= " + titulo + ", autor= " + (autor == null ? "Autor desconocido" : autor) + ", fecha publicación= " + fechaPub + ", valoración= " + (valoracion == null ? "Sin valoración" : valoracion) + ", genero= " + (genero == null ? "Sin género" : genero.getNombre()) + ", Copias totales: " + copiasTotales + ", Copias disponibles: " + copiasDisponibles + "]";
    }
    
    @Override
    public int compareTo(AbstractDTO o) {
        if (o instanceof LibroDTO) {
            LibroDTO l = (LibroDTO) o;
            
            String titulo;
            String autor;
            LocalDate fechaPub;
            int titulo = Integer.compare(this.getIdUsuario(), l.getIdUsuario());
            if (titulo != 0) {
                return titulo;
            }

            int compLibro = Integer.compare(this.getIdLibro(), l.getIdLibro());
            if (compLibro != 0) {
                return compLibro;
            }
        }
        return Integer.compare(this.getId(), o.getId());
    }
}
