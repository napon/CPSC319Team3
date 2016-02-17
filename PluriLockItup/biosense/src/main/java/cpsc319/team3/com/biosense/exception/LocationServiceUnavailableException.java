package cpsc319.team3.com.biosense.exception;

/**
 * An Exception class for when device has Location Access Permission disabled.
 */
public class LocationServiceUnavailableException extends PluriLockException {
    public LocationServiceUnavailableException(String msg) {
        super(msg);
    }
}
