package cpsc319.team3.com.biosense.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Karen on 16-03-13.
 *
 * PScaleEvent is a model class that represents a scaling action by the user
 */
public class PScaleEvent extends PluriLockEvent{
    private static final String EVENT_TYPE = "SCALE";
    private ScaleDirection scaleDirection;
    private float spanX;
    private float spanY;
    private int motionEventCode;

    /**
     * INWARDS = zooming in
     * OUTWARDS = zooming out
     */
    public enum ScaleDirection {
        INWARDS, OUTWARDS
    }

    public PScaleEvent(int screenOrientation, long timestamp, long duration,
                       ScaleDirection scaleDirection,
                       float spanX, float spanY, int motionCode) {
        super(screenOrientation, timestamp, duration);
        this.scaleDirection = scaleDirection;
        this.spanX = spanX;
        this.spanY = spanY;
        this.motionEventCode = motionCode;
    }

    /**
     * Direction user is scaling
     * @return enum of INWARDS or OUTWARDS
     */
    public ScaleDirection getScaleDirection() {
        return scaleDirection;
    }

    /**
     * Average distance fingers moved on X axis
     * @return distance in pixels
     */
    public float getSpanX() {
        return spanX;
    }

    /**
     * Average distance fingers moved on Y axis
     * @return distance in pixel
     */
    public float getSpanY() {
        return spanY;
    }

    /**
     * Android generates a code for each MotionEvent
     * @return the Android ActionMotionEvent code generated
     */
    public int getMotionEventCode() {
        return motionEventCode;
    }

    /**
     * Generates JSON object of PScaleEvent
     * @return JSON object PScaleEvent
     */
    @Override
    public JSONObject getJSON() {
        JSONObject jsonObject = super.getJSON();
        try {
            jsonObject.put("eventType", EVENT_TYPE);
            jsonObject.put("ScaleDirection", getScaleDirection());
            jsonObject.put("spanX", getSpanX());
            jsonObject.put("spanY", getSpanY());
            jsonObject.put("motionEventCode", getMotionEventCode());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
