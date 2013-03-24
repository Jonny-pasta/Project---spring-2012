/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.muni.pv168.exceptions;

/**
 *
 * @author mulan
 */
public class InvalidEntityException extends RuntimeException {

    /**
     * Creates a new instance of
     * <code>InvalidEntityException</code> without detail message.
     */
    public InvalidEntityException() {
    }

    /**
     * Constructs an instance of
     * <code>InvalidEntityException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidEntityException(String msg) {
        super(msg);
    }
    
    public InvalidEntityException(Throwable cause ) {
        super(cause);
    }
    
    public InvalidEntityException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
