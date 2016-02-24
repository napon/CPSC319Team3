package cpsc319.team3.com.biosense;

import android.content.Context;
import android.view.View;

import cpsc319.team3.com.biosense.exception.LocationServiceUnavailableException;

/**
 * A class through which the client interacts with our API.
 *
 * See UML Diagram for more implementation details.
 */
public class PluriLockAPI {
    private PluriLockEventManager eventManager;
    //TODO: add listeners

    public PluriLockAPI(Context context, PluriLockServerResponseListener callback, String id,
                        PluriLockConfig config) throws LocationServiceUnavailableException {
        this.eventManager = PluriLockEventManager.getInstance(context, callback, id, config);

    }


}
