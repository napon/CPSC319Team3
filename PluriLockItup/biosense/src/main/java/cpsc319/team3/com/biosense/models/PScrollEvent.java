package cpsc319.team3.com.biosense.models;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.PointF;


/**
 * PScrollEvent is a model class that represents a scroll action by the user.
 *
 * See the UML Diagram for more implementation details.
 */
public class PScrollEvent extends PluriLockEvent {
    private static final String EVENT_TYPE = "SCROLL";
    private ScrollDirection scrollDirection;
    private PointF startCoord;
    private PointF endCoord;
    private int motionEventCode;

    public enum ScrollDirection {
        UP, DOWN, LEFT, RIGHT
    }

    public PScrollEvent(int screenOrientation, long timestamp,
                        ScrollDirection scrollDirection, PointF startCoord, PointF endCoord,
                        long duration, int motionCode) {
        super(screenOrientation, timestamp, duration);
        this.scrollDirection = scrollDirection;
        this.startCoord = startCoord;
        this.endCoord = endCoord;
        this.motionEventCode = motionCode;
    }

    public int getMotionEventCode() {
        return motionEventCode;
    }

    public ScrollDirection getScrollDirection() { return scrollDirection; }

    public float getStartCoordX() { return startCoord.x; }

    public float getStartCoordY() {
        return startCoord.y;
    }

    public float getEndCoordX() {
        return endCoord.x;
    }

    public float getEndCoordY() {
        return endCoord.y;
    }

    @Override
    public JSONObject getJSON() {
        JSONObject jsonObject = super.getJSON();
        try {
            jsonObject.put("eventType", EVENT_TYPE);
            jsonObject.put("scrollDirection", getScrollDirection());
            jsonObject.put("startX", getStartCoordX());
            jsonObject.put("startY", getStartCoordY());
            jsonObject.put("endX", getEndCoordX());
            jsonObject.put("endY", getEndCoordY());
            jsonObject.put("motionEventCode", getMotionEventCode());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
