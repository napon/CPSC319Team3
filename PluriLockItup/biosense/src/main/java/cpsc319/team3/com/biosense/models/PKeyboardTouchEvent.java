package cpsc319.team3.com.biosense.models;

import java.util.GregorianCalendar;

/**
 * PKeyboardTouchEvent is a model class that represents an input pair of characters on a keyboard.
 *
 * See the UML Diagram for more implementation details.
 */
public class PKeyboardTouchEvent extends PluriLockEvent {
    private float duration;
    private int keyPressed;

    public PKeyboardTouchEvent(int eventID, int screenOrientation, GregorianCalendar timestamp,
                               float duration, int keyPressed) {
        super(eventID, screenOrientation, timestamp);
        this.duration = duration;
        this.keyPressed = keyPressed;
    }

}
