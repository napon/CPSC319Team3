package cpsc319.team3.com.biosense.models;

import android.graphics.PointF;

import java.util.GregorianCalendar;

/**
 * PScrollEvent is a model class that represents a scroll action by the user.
 *
 * See the UML Diagram for more implementation details.
 */
public class PScrollEvent extends PluriLockEvent {
    private int orientation;
    private PointF startCoord;
    private PointF endCoord;

    public PScrollEvent(int eventID, int screenOrientation, long timestamp,
                        int orientation, PointF startCoord, PointF endCoord) {
        super(eventID, screenOrientation, timestamp);
        this.orientation = orientation;
        this.startCoord = startCoord;
        this.endCoord = endCoord;
    }

    public int getOrientation() {
        return orientation;
    }

    public PointF getStartCoord() {
        return startCoord;
    }

    public PointF getEndCoord() { return endCoord; }
}
