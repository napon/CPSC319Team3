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
    private PointF precisionXY;
    private PointF screenCoord;
    private float touchArea;

    private int motionEventCode;

    public PElementTouchEvent(int screenOrientation, long timestamp, float pressure,
                              float fingerOrientation, PointF precisionXY, PointF screenCoord,
                              long duration, float touchArea, int motionCode) {
        super(screenOrientation, timestamp, duration);
        Log.d(TAG, "PElementTouchEvent constructor");
        this.pressure = pressure;
        this.fingerOrientation = fingerOrientation;
        this.precisionXY = precisionXY;
        this.screenCoord = screenCoord;
        this.touchArea = touchArea;
        this.motionEventCode = motionCode;
    }

    /**
     * Pressure applied to device screen
     * @return value between 0-1 in most cases (it's possible to have values greater than 1)
     */
    public float getPressure() {
        return pressure;
    }

    /**
     * Orientation of finger on device screen
     * @return radian of finger orientation (not supported on for many phones; return 0)
     */
    public float getFingerOrientation() {
        return fingerOrientation;
    }

    /**
     * Precision of reported X coord on screen
     * @return value of precision of reported X coord
     */
    public float getPrecisionX() {
        return precisionXY.x;
    }

    /**
     * Precision of reported Y coord on screen
     * @return value of precision of reported Y coord
     */
    public float getPrecisionY() {
        return precisionXY.y;
    }

    /**
     * X coord of first finger on screen
     * @return value of X coord in pixels
     */
    public float getScreenCoordX() {
        return screenCoord.x;
    }

    /**
     * Y coord of first finger on screen
     * @return value of Y coord in pixels
     */
    public float getScreenCoordY() {
        return screenCoord.y;
    }

    /**
     * Appox area of first finger on screen
     * @return value of contact area normalized to 0-1
     */
    public float getTouchArea() {
        return touchArea;
    }

    /**
     * Android generates a code for each MotionEvent
     * @return the Android ActionMotionEvent code generated
     */
    public int getMotionEventCode() {
        return motionEventCode;
    }

    /**
     * Generates JSON object of PElementTouchEvent
     * @return JSON object PElementTouchEvent
     */
    public JSONObject getJSON() {
        Log.d(TAG, "getJSON");
        JSONObject jsonObject = super.getJSON();
        try {
            jsonObject.put("eventType", EVENT_TYPE);
            jsonObject.put("pressure", getPressure());
            jsonObject.put("fingerOrientation", getFingerOrientation());
            jsonObject.put("precisionX", getPrecisionX());
            jsonObject.put("precisionY", getPrecisionY());
            jsonObject.put("screenX", getScreenCoordX());
            jsonObject.put("screenY", getScreenCoordY());
            jsonObject.put("touchArea", getTouchArea());
            jsonObject.put("motionEventCode", getMotionEventCode());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
