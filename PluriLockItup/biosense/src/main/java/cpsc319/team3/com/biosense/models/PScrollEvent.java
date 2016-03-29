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
    private int pointerCount;

    /**
     * Direction pointers are moving
     */
    public enum ScrollDirection {
        UP, DOWN, LEFT, RIGHT
    }

    public PScrollEvent(int screenOrientation, long timestamp,
                        ScrollDirection scrollDirection, PointF startCoord, PointF endCoord,
                        long duration, int motionCode, int pointerCount) {
        super(screenOrientation, timestamp, duration);
        this.scrollDirection = scrollDirection;
        this.startCoord = startCoord;
        this.endCoord = endCoord;
        this.motionEventCode = motionCode;
        this.pointerCount = pointerCount;
    }

    public int getMotionEventCode() {
        return motionEventCode;
    }

    /**
     * Get direction pointers move
     * @return enum of direction
     */
    public ScrollDirection getScrollDirection() { return scrollDirection; }

    /**
     * Get starting X position
     * @return X in pixels
     */
    public float getStartCoordX() { return startCoord.x; }

    /**
     * Get starting Y position
     * @return Y in pixels
     */
    public float getStartCoordY() {
        return startCoord.y;
    }

    /**
     * Get ending X position
     * @return X in pixels
     */
    public float getEndCoordX() {
        return endCoord.x;
    }

    /**
     * Get ending Y position
     * @return Y in pixels
     */
    public float getEndCoordY() {
        return endCoord.y;
    }

    /**
     * Get number of fingers
     * @return int number of fingers on screen
     */
    public int getPointerCount() { return pointerCount; }

    /**
     * Generates JSON object of PScrollEvent
     * @return JSON object PScrollEvent
     */
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
            jsonObject.put("pointerCount", getPointerCount());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
