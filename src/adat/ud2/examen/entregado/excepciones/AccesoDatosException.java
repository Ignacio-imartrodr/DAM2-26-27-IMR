package adat.ud2.examen.entregado.excepciones;

public class AccesoDatosException extends RuntimeException {
    public AccesoDatosException(String msg) { super(msg); }
    public AccesoDatosException(String msg, Throwable causa) { super(msg, causa); }
}