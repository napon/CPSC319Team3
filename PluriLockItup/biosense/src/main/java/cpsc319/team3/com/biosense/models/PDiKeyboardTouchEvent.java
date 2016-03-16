package cpsc319.team3.com.biosense.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * PDiKeyboardTouchEvent is a model class that represents button press
 *
 *
 */
public class PDiKeyboardTouchEvent extends PluriLockEvent {
    private static final String EVENT_TYPE = "DIGRAPH";
    private int fromKey;
    private int toKey;

    public PDiKeyboardTouchEvent(int screenOrientation, long timestamp,
                                 long duration, int fromKey, int toKey) {
        super(screenOrientation, timestamp, duration);
        this.fromKey = fromKey;
        this.toKey = toKey;
    }

    /**
     * Android has an unique int for each key
     * Get the first key pressed
     * @return Android keycode
     */
    public int getFromKey() { return fromKey; }

    /**
     * Android has an unique int for each key
     * Get the second key pressed
     * @return Android keycode
     */
    public int getToKey() { return toKey; }

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
