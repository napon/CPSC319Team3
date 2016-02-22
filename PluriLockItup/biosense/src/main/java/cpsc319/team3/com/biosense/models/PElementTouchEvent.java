package cpsc319.team3.com.biosense.models;

import android.graphics.PointF;

import java.util.GregorianCalendar;

/**
 * PElementTouchEvent is a model class that represents a touch action on an element.
 *
 * See the UML Diagram for more implementation details.
 */
public class PElementTouchEvent extends PluriLockEvent {
    private float pressure;
    private float fingerOrientation;
    private PointF elementRelativeCoord;
    private PointF screenCoord;

    public PElementTouchEvent(int eventID, int screenOrientation, GregorianCalendar timestamp, float pressure,
                              float fingerOrientation, PointF elementRelativeCoord, PointF screenCoord) {
        super(eventID, screenOrientation, timestamp);
        this.pressure = pressure;
        this.fingerOrientation = fingerOrientation;
        this.elementRelativeCoord = elementRelativeCoord;
        this.screenCoord = screenCoord;
    }
}
