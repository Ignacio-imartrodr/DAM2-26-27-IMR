package Otros.Ajedrez.Utilidades;

/**
 * @author Ignacio MR
 */
/**
 * Thrown when an exceptional pieza condition has occurred. For
 * example, a generation position out of the board limits throws
 * an instance of this class.
 *
 */
public class PiezaException extends RuntimeException {
    
    /**
     * Constructs an {@code PiezaException} with no detail
     * message.
     */
    public PiezaException() {
        super();
    }

    /**
     * Constructs an {@code PiezaException} with the specified
     * detail message.
     *
     * @param   s   the detail message.
     */
    public PiezaException(String s) {
        super(s);
    }
}
