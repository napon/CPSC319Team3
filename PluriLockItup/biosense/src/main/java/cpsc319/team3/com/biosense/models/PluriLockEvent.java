package cpsc319.team3.com.biosense.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * PluriLockEvent is an abstract model class that represents any kind of event tracked by our API.
 * The reason for this class is so that every event recorded by our API can be put into a list and
 * send to the PluriLock Server.
 *
 * A PluriLockEvent is one of:
 * - PElementTouchEvent
 * - PMonoKeyboardTouchEvent
 * - PScrollEvent
 *
 * (There might be more in the future.. They are listed in the models/ package.)
 *
 * This class contains a generic set of properties that are common to all touch events.
 * These include:
 * - eventID
 * - screenOrientation
 * - timestamp
 * - gpsLocation
 *
 * See UML Diagram for more implementation details.
 */
public abstract class PluriLockEvent {
    private static final String TAG = "PluriLockEvent";
    private int screenOrientation; //1 = portrait; 2 = landscape
    private long timestamp;
    private long duration;


    public PluriLockEvent(int screenOrientation, long timestamp, long duration) {
        Log.d(TAG, "PluriLockEvent constructor");
        this.screenOrientation = screenOrientation;
        this.timestamp = timestamp;
        this.duration = duration;
    }

    public int getScreenOrientation() {
        return screenOrientation;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public long getDuration() {
        return duration;
    }

    public JSONObject getJSON() {
        Log.d(TAG, "getJSON");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("orientation", getScreenOrientation());
            jsonObject.put("timestamp", getTimestamp());
            jsonObject.put("duration", getDuration());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
