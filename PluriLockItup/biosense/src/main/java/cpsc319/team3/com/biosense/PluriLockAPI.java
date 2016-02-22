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
    private PluriLockEventListenerManager listenerManager;

    public PluriLockAPI(Context context, PluriLockServerResponseListener callback, String id,
                        PluriLockConfig config) throws LocationServiceUnavailableException {
        this.eventManager = PluriLockEventManager.getInstance(context, callback, id, config);
        this.listenerManager = new PluriLockEventListenerManager();
    }

    public View.OnClickListener getClickListener() {
        return listenerManager.createClickListener();
    }

    public View.OnLongClickListener getLongClickListener() {
        return listenerManager.createLongClickListener();
    }

    public View.OnKeyListener getKeyListener() {
        return listenerManager.createKeyListener();
    }

    public View.OnTouchListener getTouchListener() {
        return listenerManager.createTouchListener();
    }

}
