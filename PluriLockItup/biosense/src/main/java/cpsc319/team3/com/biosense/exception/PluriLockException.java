package cpsc319.team3.com.biosense.exception;

/**
 * An abstract class for any exceptions raised by the API.
 */
public abstract class PluriLockException extends Exception {
    public PluriLockException(String msg) {
        super(msg);
    }
}
