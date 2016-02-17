package cpsc319.team3.com.biosense;

/**
 * A Listener class shared between the API and the client.
 * The client is notified via a callback function with a confidence level from the Server.
 *
 * See the UML Diagram for more implementation details.
 */
public class PluriLockServerResponseListener {

    /**
     * Called when the server sends back a response.
     * @param msg
     */
    public void notify(String msg) {
        // TODO: Client should handle the response.
    }
}