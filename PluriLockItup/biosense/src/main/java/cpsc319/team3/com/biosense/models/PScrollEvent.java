package cpsc319.team3.com.biosense.models;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * PScrollEvent is a model class that represents a scroll action by the user.
 *
 * See the UML Diagram for more implementation details.
 */
public class PScrollEvent extends PluriLockEvent {
    private static final String EVENT_TYPE = "SCROLL";
    private int orientation;
    private float startCoord;
    private float endCoord;

    public PScrollEvent(int eventID, int screenOrientation, long timestamp,
                        int orientation, float startCoord, float endCoord) {
        super(eventID, screenOrientation, timestamp);
        this.orientation = orientation;
        this.startCoord = startCoord;
        this.endCoord = endCoord;
    }

    public int getOrientation() {
        return orientation;
    }

    public float getStartCoord() {
        return startCoord;
    }

    public float getEndCoord() {
        return endCoord;
    }

    @Override
    public JSONObject getJSON() {
        JSONObject jsonObject = super.getJSON();
        try {
            jsonObject.put("eventType", EVENT_TYPE);
            jsonObject.put("scrollDirection", getOrientation());
            jsonObject.put("start", getStartCoord());
            jsonObject.put("end", getEndCoord());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
