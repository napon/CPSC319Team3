package cpsc319.team3.com.biosense;

import android.content.Context;

import cpsc319.team3.com.biosense.models.PluriLockEvent;

/**
 * This class is responsible for
 * - Initializing specific Android listeners
 * - Contructing corresponding PluriLockEvent objects
 * - Send all constructed model objects up to PluriLockEventManager
 *
 * See UML Diagram for more implementation details.
 * !!! The functionality of this class is based on the first UML draft by @napon.
 * !!! Subject to change by @leesunny.
 */
public class PluriLockEventTracker {
    private PluriLockEventManager eventManager;
    private Context context;

    public PluriLockEventManager getEventManager() {
        return eventManager;
    }

    public Context getContext() {
        return context;
    }

    public PluriLockEventTracker(Context context, PluriLockEventManager eventManager) {
        this.context = context;
        this.eventManager = eventManager;
    }

    void notifyOfEvent(PluriLockEvent pEvent) {
        eventManager.addPluriLockEvent(pEvent);
    }

}
