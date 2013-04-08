package fi.muni.pv168.exceptions;

/**
 * exception is throws, when some entity has null argument, negative attribute etc.
 * @author mulan
 */
public class InvalidEntityException extends RuntimeException {

    /**
     * parameterless constructor
     */
    public InvalidEntityException() {
    }

    /**
     * constructor with string message
     * @param msg message to show
     */
    public InvalidEntityException(String msg) {
        super(msg);
    }
    
    /**
     * constructor with Throwable cause of exception
     * @param cause what caused exception
     */
    public InvalidEntityException(Throwable cause ) {
        super(cause);
    }
    
    /**
     * constructor with both cause of exception and message
     * @param msg message to show
     * @param cause cause of the exception
     */
    public InvalidEntityException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
