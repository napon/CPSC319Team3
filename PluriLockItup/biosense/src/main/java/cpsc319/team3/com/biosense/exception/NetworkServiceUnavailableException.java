package cpsc319.team3.com.biosense.exception;

/**
 * Created by Sunny on 2016-03-07.
 */
public class NetworkServiceUnavailableException extends PluriLockException{
    public NetworkServiceUnavailableException(String msg) {
        super(msg);
    }
}
