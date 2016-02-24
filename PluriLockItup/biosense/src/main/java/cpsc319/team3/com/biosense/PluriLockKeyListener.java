package cpsc319.team3.com.biosense;

import android.view.KeyEvent;
import android.view.View;

import java.util.GregorianCalendar;

import cpsc319.team3.com.biosense.models.PKeyboardTouchEvent;

/**
 * Created by Karen on 16-02-23.
 *
 * A Listener class for keyboard events.
 * PluriLockEventTracker is notified via method call when an keyboard event occurs
 *
 */
public class PluriLockKeyListener implements View.OnKeyListener {
    private PluriLockEventTracker eventTracker;

    public PluriLockKeyListener(PluriLockEventTracker eventTracker) {
        this.eventTracker = eventTracker;
    }

    /**
     * Override of default onKey method.
     * Listens for key events and notifies the tracker when key event occurs
     * @param v View PluriLockKeyListener is attached to
     * @param keyCode key that triggered the onKey call
     * @param event event that triggered the onKey call
     * @return true since listener has consumed the event
     */
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        int eventID = 0; //TODO
        int screenOrientation = 0; //TODO
        Long timestamp = new GregorianCalendar().getTimeInMillis();
        long duration = 0; //TODO

        PKeyboardTouchEvent pKeyboardTouchEvent =
                new PKeyboardTouchEvent
                        (eventID, screenOrientation, timestamp, duration, keyCode);

        eventTracker.notifyOfEvent(pKeyboardTouchEvent);

        return true;
    }
}
