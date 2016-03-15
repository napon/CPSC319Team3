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
    private scaleDirection scaleDirection;
    private float spanX;
    private float spanY;
    private int motionEventCode;

    public enum scaleDirection {
        INWARDS, OUTWARDS
    }

    public PScaleEvent(int screenOrientation, long timestamp, long duration,
                       scaleDirection scaleDirection,
                       float spanX, float spanY, int motionCode) {
        super(screenOrientation, timestamp, duration);
        this.scaleDirection = scaleDirection;
        this.spanX = spanX;
        this.spanY = spanY;
        this.motionEventCode = motionCode;
    }

    public PScaleEvent.scaleDirection getScaleDirection() {
        return scaleDirection;
    }

    public float getSpanX() {
        return spanX;
    }

    public float getSpanY() {
        return spanY;
    }

    public int getMotionEventCode() {
        return motionEventCode;
    }

    @Override
    public JSONObject getJSON() {
        JSONObject jsonObject = super.getJSON();
        try {
            jsonObject.put("eventType", EVENT_TYPE);
            jsonObject.put("scaleDirection", getScaleDirection());
            jsonObject.put("spanX", getSpanX());
            jsonObject.put("spanY", getSpanY());
            jsonObject.put("motionEventCode", getMotionEventCode());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
