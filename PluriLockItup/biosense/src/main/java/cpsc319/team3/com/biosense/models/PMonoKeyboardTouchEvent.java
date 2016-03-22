package cpsc319.team3.com.biosense.models;

import android.view.KeyEvent;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * PMonoKeyboardTouchEvent is a model class that represents a button press on a soft keyboard.
 *
 * See the UML Diagram for more implementation details.
 */
public class PMonoKeyboardTouchEvent extends PluriLockEvent {
    private static final String EVENT_TYPE = "MONOGRAPH";
    private String keyPressed;

    public PMonoKeyboardTouchEvent(int screenOrientation, long timestamp,
                                   long duration, int keyPressed) {
        super(screenOrientation, timestamp, duration);
        this.keyPressed = KeyEvent.keyCodeToString(keyPressed);
    }

    public PMonoKeyboardTouchEvent(int screenOrientation, long timestamp,
                                   long duration, String keyPressed) {
        super(screenOrientation, timestamp, duration);
        this.keyPressed = "KEYCODE_" + keyPressed.toUpperCase();
    }

    /**
     * Android has an unique int for each key
     * Get the key pressed
     * @return Android keycode
     */
    public String getKeyPressed() {
        return keyPressed;
    }

    /**
     * Generates JSON object of PMonoKeyboardTouchEvent
     * @return JSON object PMonoKeyboardTouchEvent
     */
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
