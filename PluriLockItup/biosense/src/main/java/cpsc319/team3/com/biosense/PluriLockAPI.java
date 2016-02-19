package cpsc319.team3.com.biosense;

import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

/**
 * A class through which the client interacts with our API.
 *
 * See UML Diagram for more implementation details.
 */
public class PluriLockAPI {
    private PluriLockEventManager eventManager;
    private PluriLockEventListenerManager listenerManager;

    public PluriLockAPI(Context context, PluriLockServerResponseListener callback,
                        PluriLockConfig config) throws Exception {
//        this.eventManager = PluriLockEventManager.getInstance(context, callback, config);
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
