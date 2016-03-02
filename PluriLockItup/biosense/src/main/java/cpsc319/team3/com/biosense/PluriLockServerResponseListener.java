package cpsc319.team3.com.biosense;

import cpsc319.team3.com.biosense.models.PlurilockServerResponse;

/**
 * A Listener class shared between the API and the client.
 * The client is notified via a callback function with a confidence level from the Server.
 *
 * See the UML Diagram for more implementation details.
 */
public interface PluriLockServerResponseListener {

    /**
     * Called when the server sends back a response.
     * @param msg
     */
    void notify(PlurilockServerResponse msg);
}