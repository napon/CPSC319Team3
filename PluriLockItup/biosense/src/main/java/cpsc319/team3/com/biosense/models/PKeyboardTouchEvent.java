package cpsc319.team3.com.biosense.models;

/**
 * PKeyboardTouchEvent is a model class that represents an input pair of characters on a keyboard.
 *
 * See the UML Diagram for more implementation details.
 */
public class PKeyboardTouchEvent extends PluriLockEvent {
    private float duration;
    private int keyPressed;

    public PKeyboardTouchEvent(int screenOrientation, float duration, int keyPressed) {
        super(screenOrientation);
        this.duration = duration;
        this.keyPressed = keyPressed;
    }

}
