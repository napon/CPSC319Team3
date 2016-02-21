package cpsc319.team3.com.biosense.models;

/**
 * PElementTouchEvent is a model class that represents a touch action on an element.
 *
 * See the UML Diagram for more implementation details.
 */
public class PElementTouchEvent extends PluriLockEvent {
    private float pressure;
    private float fingerOrientation;
    private float elementRelativeCoord;
    private float screenCoord;

    public PElementTouchEvent(int screenOrientation, float pressure, float fingerOrientation,
                              float elementRelativeCoord, float screenCoord) {
        super(screenOrientation);
        this.pressure = pressure;
        this.fingerOrientation = fingerOrientation;
        this.elementRelativeCoord = elementRelativeCoord;
        this.screenCoord = screenCoord;
    }
}
