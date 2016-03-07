package cpsc319.team3.com.biosense.models;

import android.graphics.PointF;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * PElementTouchEvent is a model class that represents a touch action on an element.
 *
 * See the UML Diagram for more implementation details.
 */
public class PElementTouchEvent extends PluriLockEvent {
    private static final String TAG = "PElementTouchEvent";
    private static final String EVENT_TYPE = "ELEMENT_TOUCH";
    private float pressure;
    private float fingerOrientation;
    private PointF elementRelativeCoord;
    private PointF screenCoord;
    private float touchArea;

    public PElementTouchEvent(int eventID, int screenOrientation, long timestamp, float pressure,
                              float fingerOrientation, PointF elementRelativeCoord, PointF screenCoord,
                              long duration, float touchArea) {
        super(eventID, screenOrientation, timestamp, duration);
        Log.d(TAG, "PElementTouchEvent constructor");
        this.pressure = pressure;
        this.fingerOrientation = fingerOrientation;
        this.elementRelativeCoord = elementRelativeCoord;
        this.screenCoord = screenCoord;
        this.touchArea = touchArea;
    }

    public float getPressure() {
        return pressure;
    }

    public float getFingerOrientation() {
        return fingerOrientation;
    }

    public float getElementRelativeCoordX() {
        return elementRelativeCoord.x;
    }

    public float getElementRelativeCoordY() {
        return elementRelativeCoord.y;
    }

    public float getScreenCoordX() {
        return screenCoord.x;
    }

    public float getScreenCoordY() {
        return screenCoord.y;
    }

    public float getTouchArea() {
        return touchArea;
    }

    public JSONObject getJSON() {
        Log.d(TAG, "getJSON");
        JSONObject jsonObject = super.getJSON();
        try {
            jsonObject.put("eventType", EVENT_TYPE);
            jsonObject.put("pressure", getPressure());
            jsonObject.put("fingerOrientation", getFingerOrientation());
            jsonObject.put("elementRelX", getElementRelativeCoordX());
            jsonObject.put("elementRelY", getElementRelativeCoordY());
            jsonObject.put("screenX", getScreenCoordX());
            jsonObject.put("screenY", getScreenCoordY());
            jsonObject.put("touchArea", getTouchArea());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
