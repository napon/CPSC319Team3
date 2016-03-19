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
    private ScaleStatus scaleStatus;
    private float spanX;
    private float spanY;

    /**
     * BEGIN = zooming in
     * END = zooming out
     */
    public enum ScaleStatus {
        BEGIN, END
    }

    public PScaleEvent(int screenOrientation, long timestamp, long duration,
                       ScaleStatus scaleStatus,
                       float spanX, float spanY) {
        super(screenOrientation, timestamp, duration);
        this.scaleStatus = scaleStatus;
        this.spanX = spanX;
        this.spanY = spanY;
    }

    /**
     * Direction user is scaling
     * @return enum of BEGIN or END
     */
    public ScaleStatus getScaleStatus() {
        return scaleStatus;
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
     * Generates JSON object of PScaleEvent
     * @return JSON object PScaleEvent
     */
    @Override
    public JSONObject getJSON() {
        JSONObject jsonObject = super.getJSON();
        try {
            jsonObject.put("eventType", EVENT_TYPE);
            jsonObject.put("ScaleStatus", getScaleStatus());
            jsonObject.put("spanX", getSpanX());
            jsonObject.put("spanY", getSpanY());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
