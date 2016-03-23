package cpsc319.team3.com.biosense.models;

import android.view.KeyEvent;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * PDiKeyboardTouchEvent is a model class that represents button press
 *
 *
 */
public class PDiKeyboardTouchEvent extends PluriLockEvent {
    private static final String EVENT_TYPE = "DIGRAPH";
    private String fromKey;
    private String toKey;

    public PDiKeyboardTouchEvent(int screenOrientation, long timestamp,
                                 long duration, int fromKey, int toKey) {
        super(screenOrientation, timestamp, duration);
        this.fromKey = KeyEvent.keyCodeToString(fromKey);
        this.toKey = KeyEvent.keyCodeToString(toKey);
    }

    public PDiKeyboardTouchEvent(int screenOrientation, long timestamp,
                                 long duration, String fromKey, String toKey) {
        super(screenOrientation, timestamp, duration);
        this.fromKey = "KEYCODE_" + fromKey.toUpperCase();
        this.toKey = "KEYCODE_" + toKey.toUpperCase();
    }

    /**
     * Android has an unique int for each key
     * Get the first key pressed
     * @return Android keycode
     */
    public String getFromKey() { return fromKey; }

    /**
     * Android has an unique int for each key
     * Get the second key pressed
     * @return Android keycode
     */
    public String getToKey() { return toKey; }

    /**
     * Generates JSON object of PDiKeyboardTouchEvent
     * @return JSON object PDiKeyboardTouchEvent
     */
    @Override
    public JSONObject getJSON() {
        JSONObject jsonObject = super.getJSON();
        try {
            jsonObject.put("eventType", EVENT_TYPE);
            jsonObject.put("fromKey", getFromKey());
            jsonObject.put("toKey", getToKey());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
