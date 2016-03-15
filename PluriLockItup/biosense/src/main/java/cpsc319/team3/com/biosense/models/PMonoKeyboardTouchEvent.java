package cpsc319.team3.com.biosense.models;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * PMonoKeyboardTouchEvent is a model class that represents a button press on a soft keyboard.
 *
 * See the UML Diagram for more implementation details.
 */
public class PMonoKeyboardTouchEvent extends PluriLockEvent {
    private static final String EVENT_TYPE = "MONOGRAPH";
    private int keyPressed;

    public PMonoKeyboardTouchEvent(int screenOrientation, long timestamp,
                                   long duration, int keyPressed) {
        super(screenOrientation, timestamp, duration, PluriLockEvent.MotionCode.KEY);
        this.keyPressed = keyPressed;
    }


    public int getKeyPressed() {
        return keyPressed;
    }

    @Override
    public JSONObject getJSON() {
        JSONObject jsonObject = super.getJSON();
        try {
            jsonObject.put("eventType", EVENT_TYPE);
            jsonObject.put("key", getKeyPressed());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
