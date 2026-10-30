package adat.ud2.h2bd.biblioteca.daoYdto.libro;

import java.time.LocalDate;

import adat.ud2.h2bd.biblioteca.daoYdto.AbstractDTO;
import adat.ud2.h2bd.biblioteca.daoYdto.genero.GeneroDTO;

public class LibroDTO extends AbstractDTO {
    public static final int N_COPIAS_DEFAULT = 1;
    private String titulo;
    private String autor;
    private LocalDate fechaPub;
    private Double valoracion;
    private GeneroDTO genero;
    private int copiasTotales;
    private int copiasDisponibles;
    
    public LibroDTO(int id, String titulo, String autor, LocalDate fechaPub, Double valoracion, GeneroDTO genero, Integer copiasTotales, Integer copiasDisponibles) {
        super(id);
        if (!setTitulo(titulo)) {
            throw new IllegalArgumentException("El título no puede ser null o estar vacío");
        }
        if (!setFechaPub(fechaPub)) {
            throw new IllegalArgumentException("La fecha de publicación no pueden ser null o futuro");
        }
        this.copiasTotales = 0;
        this.copiasDisponibles = -1;
        if (!setCopiasTotales(copiasTotales)) {
            throw new IllegalArgumentException("La cantidad total de copias debe ser mayor a 0");
        }
        if (!setCopiasDisponibles(copiasDisponibles)) {
            throw new IllegalArgumentException("La cantidad de copias disponibles debe ser mayor o igual a 0 y menor o igual a las copias totales");
        }
        if (!setValoracion(valoracion)) {
            throw new IllegalArgumentException("La valoración debe ser null o decimal entre 0 y 5");
        }
        setAutor(autor);
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
        return titulo.charAt(0) + titulo.substring(1).toLowerCase();
    }

    public boolean setTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            return false;
        }
        this.titulo = titulo.toUpperCase();
        return true;
    }

    public String getAutor() {
        return autor == null ? null : autor.charAt(0) + autor.substring(1).toLowerCase();
    }

    public void setAutor(String autor) {
        this.autor = autor == null ? null : autor.toUpperCase();
    }

    public LocalDate getFechaPub() {
        return fechaPub;
    }

    public boolean setFechaPub(LocalDate fechaPublicacion) {
        if (fechaPub != null && (fechaPublicacion == null || fechaPub.isAfter(LocalDate.now()))) {
            return false;
        }
        if (fechaPublicacion == null) {
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

    public boolean setCopiasTotales(Integer nCopias) {
        if (nCopias != null && (nCopias < 1 || copiasDisponibles - (copiasTotales - nCopias) < 0)) {
            return false;
        }
        if (copiasDisponibles >= 0) {
            copiasDisponibles -= (copiasTotales - nCopias);
        }
        copiasTotales = nCopias == null ? N_COPIAS_DEFAULT : nCopias;
        return true;
    }

    public void addCopia(){
        copiasTotales++;
        copiasDisponibles++;
    }

    public boolean devolverCopia(){
        if (copiasDisponibles < copiasTotales) {
            copiasDisponibles++;
            return true;
        }
        return false;
    }

    public boolean prestarCopia(){
        if (copiasDisponibles >= 1) {
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
        if (this == o)
            return 0;
        if (o == null)
            return -1;
        int compId = id < 0 || o.getId() < 0 ? 0 : Integer.compare(id, o.getId()); 
        LibroDTO l;
        if (getClass() == o.getClass()){
            l = getClass().cast(o);
        } else {
            return compId;
        }
        if (compId != 0) {
            return compId;
        }
        int compTitulo = this.titulo.compareTo(l.titulo);
        if (compTitulo != 0) {
            return compTitulo;
        }

        int compFecha = fechaPub.compareTo(l.fechaPub);
        if (compFecha != 0) {
            return compFecha;
        }

        int compAutor;
        if (autor != null && l.autor == null) {
            compAutor = -1;
        } else if (autor == null && l.autor != null) {
            compAutor = 1;   
        } else if (autor == l.autor) {
            compAutor = 0;
        } else {
            compAutor = this.autor.compareTo(l.autor);
        }
        if (compAutor != 0) {
            return compAutor;
        }


        int compValoracion;
        if (autor != null && l.autor == null) {
            compValoracion = -1;
        } else if (autor == null && l.autor != null) {
            compValoracion = 1;   
        } else if (autor == l.autor) {
            compValoracion = 0;
        } else {
            compValoracion = Double.compare(valoracion, l.valoracion);
        }
        if (compValoracion != 0) {
            return compValoracion;
        }
        
        int compGenero = this.genero.compareTo(l.genero);
        if (compGenero != 0) {
            return compGenero;
        }
        
        int compCopTot = Integer.compare(copiasTotales, l.copiasTotales);
        if (compCopTot != 0) {
            return compCopTot;
        }

        int compCopDisp = Integer.compare(copiasDisponibles, l.copiasDisponibles);
        return compCopDisp;
    }
}
