package cpsc319.team3.com.biosense;

import android.view.KeyEvent;
import android.view.View;

import java.util.GregorianCalendar;

import cpsc319.team3.com.biosense.models.PKeyboardTouchEvent;

/**
 * Created by Karen on 16-02-23.
 */
public class PluriLockKeyListener implements View.OnKeyListener {
    private PluriLockEventTracker eventTracker;

    public PluriLockKeyListener(PluriLockEventTracker eventTracker) {
        this.eventTracker = eventTracker;
    }

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
