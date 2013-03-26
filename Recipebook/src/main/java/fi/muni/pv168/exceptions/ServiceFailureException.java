package fi.muni.pv168.exceptions;

/**
 *
 * @author mulan
 */
public class ServiceFailureException extends Exception {

    /**
     * Creates a new instance of
     * <code>ServiceFailureException</code> without detail message.
     */
    public ServiceFailureException() {
    }

    /**
     * Constructs an instance of
     * <code>ServiceFailureException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ServiceFailureException(String msg) {
        super(msg);
    }
    
    public ServiceFailureException(Throwable cause) {
        super(cause);
    }
    
    public ServiceFailureException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
