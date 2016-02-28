package cpsc319.team3.com.biosense.models;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.PointF;


/**
 * PScrollEvent is a model class that represents a scroll action by the user.
 *
 * See the UML Diagram for more implementation details.
 */
public class PScrollEvent extends PluriLockEvent {
    private static final String EVENT_TYPE = "SCROLL";
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

    public int getOrientation() { return orientation; }

    public float getStartCoordX() { return startCoord.x; }

    public float getStartCoordY() {
        return startCoord.y;
    }

    public float getEndCoordX() {
        return endCoord.x;
    }

    public float getEndCoordY() {
        return endCoord.y;
    }

    @Override
    public JSONObject getJSON() {
        JSONObject jsonObject = super.getJSON();
        try {
            jsonObject.put("eventType", EVENT_TYPE);
            jsonObject.put("scrollDirection", getOrientation());
            jsonObject.put("startX", getStartCoordX());
            jsonObject.put("startY", getStartCoordY());
            jsonObject.put("endX", getEndCoordX());
            jsonObject.put("endY", getEndCoordY());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
