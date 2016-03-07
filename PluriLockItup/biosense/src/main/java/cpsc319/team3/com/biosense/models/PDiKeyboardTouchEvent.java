package cpsc319.team3.com.biosense.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Karen on 16-03-06.
 */
public class PDiKeyboardTouchEvent extends PluriLockEvent {
    private static final String EVENT_TYPE = "DIGRAPH";
    private int fromKey;
    private int toKey;

    public PDiKeyboardTouchEvent(int eventID, int screenOrientation, long timestamp,
                                 long duration, int fromKey, int toKey) {
        super(eventID, screenOrientation, timestamp, duration);
        this.fromKey = fromKey;
        this.toKey = toKey;
    }

    public int getFromKey() { return fromKey; }

    public int getToKey() { return toKey; }

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
