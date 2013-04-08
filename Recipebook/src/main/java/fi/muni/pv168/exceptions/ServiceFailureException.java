package fi.muni.pv168.exceptions;

/**
 * exception is thrown when failure while working with database occurs
 * @author mulan
 */
public class ServiceFailureException extends Exception {

    /**
     * parameterless constructor
     */
    public ServiceFailureException() {
    }

    /**
     * constructor with message
     * @param msg message to show
     */
    public ServiceFailureException(String msg) {
        super(msg);
    }
    
    /**
     * constructor with cause of the exception
     * @param cause cause of the exception
     */
    public ServiceFailureException(Throwable cause) {
        super(cause);
    }
    
    /**
     * constructor with both message and cause of the exception
     * @param msg message to show
     * @param cause cause of the exception
     */
    public ServiceFailureException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
