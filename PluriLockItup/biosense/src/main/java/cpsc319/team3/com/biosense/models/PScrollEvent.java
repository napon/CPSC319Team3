package cpsc319.team3.com.biosense.models;

/**
 * PScrollEvent is a model class that represents a scroll action by the user.
 *
 * See the UML Diagram for more implementation details.
 */
public class PScrollEvent extends PluriLockEvent {
    private int orientation;
    private float startCoord;
    private float endCoord;

    public PScrollEvent(int screenOrientation, int orientation, float startCoord, float endCoord) {
        super(screenOrientation);
        this.orientation = orientation;
        this.startCoord = startCoord;
        this.endCoord = endCoord;
    }

}
