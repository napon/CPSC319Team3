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
    private float averageSpanX;
    private float averageSpanY;

    public enum scaleDirection {
        INWARDS, OUTWARDS
    }

    public PScaleEvent(int screenOrientation, long timestamp, long duration,
                       scaleDirection scaleDirection,
                       float averageSpanX, float averageSpanY) {
        super(screenOrientation, timestamp, duration);
        this.scaleDirection = scaleDirection;
        this.averageSpanX = averageSpanX;
        this.averageSpanY = averageSpanY;
    }

    public PScaleEvent.scaleDirection getScaleDirection() {
        return scaleDirection;
    }

    public float getAverageSpanX() {
        return averageSpanX;
    }

    public float getAverageSpanY() {
        return averageSpanY;
    }

    @Override
    public JSONObject getJSON() {
        JSONObject jsonObject = super.getJSON();
        try {
            jsonObject.put("eventType", EVENT_TYPE);
            jsonObject.put("scaleDirection", getScaleDirection());
            jsonObject.put("averageSpanX", getAverageSpanX());
            jsonObject.put("averageSpanY", getAverageSpanY());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
