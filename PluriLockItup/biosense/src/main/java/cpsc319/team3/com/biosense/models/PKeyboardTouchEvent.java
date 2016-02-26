package cpsc319.team3.com.biosense.models;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * PKeyboardTouchEvent is a model class that represents an input pair of characters on a keyboard.
 *
 * See the UML Diagram for more implementation details.
 */
public class PKeyboardTouchEvent extends PluriLockEvent {
    private static final String EVENT_TYPE = "SINGLE_KEY";
    private long duration;
    private int keyPressed;

    public PKeyboardTouchEvent(int eventID, int screenOrientation, long timestamp,
                               long duration, int keyPressed) {
        super(eventID, screenOrientation, timestamp);
        this.duration = duration;
        this.keyPressed = keyPressed;
    }

    public long getDuration() {
        return duration;
    }

    public int getKeyPressed() {
        return keyPressed;
    }

    @Override
    public JSONObject getJSON() {
        JSONObject jsonObject = super.getJSON();
        try {
            jsonObject.put("eventType", EVENT_TYPE);
            jsonObject.put("duration", getDuration());
            jsonObject.put("key", getKeyPressed());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
