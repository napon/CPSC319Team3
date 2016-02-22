package cpsc319.team3.com.biosense.models;

import java.util.GregorianCalendar;

/**
 * PScrollEvent is a model class that represents a scroll action by the user.
 *
 * See the UML Diagram for more implementation details.
 */
public class PScrollEvent extends PluriLockEvent {
    private int orientation;
    private float startCoord;
    private float endCoord;

    public PScrollEvent(int eventID, int screenOrientation, GregorianCalendar timestamp, int orientation, float startCoord, float endCoord) {
        super(eventID, screenOrientation, timestamp);
        this.orientation = orientation;
        this.startCoord = startCoord;
        this.endCoord = endCoord;
    }

}
