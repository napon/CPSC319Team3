package cpsc319.team3.com.biosense;

import android.graphics.PointF;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.GregorianCalendar;

import cpsc319.team3.com.biosense.exception.LocationServiceUnavailableException;
import cpsc319.team3.com.biosense.models.PElementTouchEvent;
import cpsc319.team3.com.biosense.models.PScrollEvent;
import cpsc319.team3.com.biosense.models.PluriLockEvent;

/**
 * Created by Karen on 16-02-23.
 *
 * A Listener class for touch events.
 * PluriLockEventTracker is notified via method call when touch event occurs
 * A PElementTouchEvent or a PScrollEvent is created depending on the type of touch
 *
 */
public class PluriLockTouchListener implements
        GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener
{
    private static final String TAG = "PluriLockTouchListener";

    int eventID;
    int screenOrientation;
    long timestamp;
    float pressure;
    float fingerOrientation;

    PluriLockEventTracker eventTracker;

    public PluriLockTouchListener(PluriLockEventTracker eventTracker) {
        this.eventTracker = eventTracker;
    }

    /**
     * Notified when a single-tap occurs.
     * <p/>
     * Unlike { OnGestureListener#onSingleTapUp(MotionEvent)}, this
     * will only be called after the detector is confident that the user's
     * first tap is not followed by a second tap leading to a double-tap
     * gesture.
     *
     * @param e The down motion event of the single-tap.
     * @return true if the event is consumed, else false
     */
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.d(TAG, "onSingleTapConfirmed: " + e.toString());
        return true;
    }

    /**
     * Notified when a double-tap occurs.
     *
     * @param e The down motion event of the first tap of the double-tap.
     * @return true if the event is consumed, else false
     */
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d(TAG, "onDoubleTap: " + e.toString());
        return true;
    }

    /**
     * Notified when an event within a double-tap gesture occurs, including
     * the down, move, and up events.
     *
     * @param e The motion event that occurred during the double-tap gesture.
     * @return true if the event is consumed, else false
     */
    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.d(TAG, "onDoubleTapEvent: " + e.toString());
        return true;
    }

    /**
     * Notified when a tap occurs with the down {@link MotionEvent}
     * that triggered it. This will be triggered immediately for
     * every down event. All other events should be preceded by this.
     *
     * Collect the initial time, screen orientation, pressure and finger orientation of the event
     * @param e The down motion event.
     */
    @Override
    public boolean onDown(MotionEvent e) {
        Log.d(TAG,"onDown: " + e.toString());
        eventID = 0;
        screenOrientation = eventTracker.getContext().getResources().getConfiguration().orientation;
        timestamp = e.getDownTime();
        pressure = e.getPressure();
        fingerOrientation = e.getOrientation();

        return true;
    }

    /**
     * The user has performed a down {@link MotionEvent} and not performed
     * a move or up yet. This event is commonly used to provide visual
     * feedback to the user to let them know that their action has been
     * recognized i.e. highlight an element.
     *
     * @param e The down motion event
     */
    @Override
    public void onShowPress(MotionEvent e) {
        Log.d(TAG, "onShowPress: " + e.toString());
    }

    /**
     * Notified when a tap occurs with the up {@link MotionEvent}
     * that triggered it.
     *
     * Assumes that onDown has been called first
     *
     * @param e The up motion event that completed the first tap
     * @return true if the event is consumed, else false
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d(TAG, "onSingleTapUp: " + e.toString());
        long currTimestamp = new GregorianCalendar().getTimeInMillis();
        long duration = timestamp - currTimestamp;
        PointF elementRelativeCoord = new PointF(e.getX(), e.getY());
        PointF screenCord = new PointF(e.getX(), e.getY());

        PElementTouchEvent pElementTouchEvent =
                new PElementTouchEvent(eventID, screenOrientation, timestamp,
                        pressure, fingerOrientation, elementRelativeCoord, screenCord, duration);
        eventTracker.notifyOfEvent(pElementTouchEvent);
        return true;
    }

    /**
     * Notified when a scroll occurs with the initial on down {@link MotionEvent} and the
     * current move {@link MotionEvent}. The distance in x and y is also supplied for
     * convenience.
     *
     * Creates a PScrollEvent and calls the eventTracker.
     * Assumes that onDown has been called first
     *
     * @param e1        The first down motion event that started the scrolling.
     * @param e2        The move motion event that triggered the current onScroll.
     * @param distanceX The distance along the X axis that has been scrolled since the last
     *                  call to onScroll. This is NOT the distance between {@code e1}
     *                  and {@code e2}.
     * @param distanceY The distance along the Y axis that has been scrolled since the last
     *                  call to onScroll. This is NOT the distance between {@code e1}
     *                  and {@code e2}.
     * @return true if the event is consumed, else false
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d(TAG, "onScroll: " + e1.toString()+e2.toString());

        PointF startCoord = new PointF(e1.getX(), e1.getY());
        PointF endCoord = new PointF(e2.getX(), e2.getY());

        PScrollEvent.scrollDirection scrollDirection = getScrollDirection(startCoord, endCoord);

        long currTimestamp = new GregorianCalendar().getTimeInMillis();
        long duration = timestamp - currTimestamp;

        PScrollEvent pScrollEvent =
                new PScrollEvent(eventID, screenOrientation, timestamp, scrollDirection,
                        startCoord, endCoord, duration);
        eventTracker.notifyOfEvent(pScrollEvent);
        return true;
    }

    /**
     * Notified when a long press occurs with the initial on down {@link MotionEvent}
     * that trigged it.
     *
     * @param e The initial on down motion event that started the longpress.
     */
    @Override
    public void onLongPress(MotionEvent e) {
        Log.d(TAG, "onLongPress:" + e.toString());
    }

    /**
     * Notified of a fling event when it occurs with the initial on down {@link MotionEvent}
     * and the matching up {@link MotionEvent}. The calculated velocity is supplied along
     * the x and y axis in pixels per second.
     *
     * Treated the same as a scroll (subject to change)
     * Creates a PScrollEvent and calls the eventTracker.
     * Assumes that onDown has been called first
     *
     * @param e1        The first down motion event that started the fling.
     * @param e2        The move motion event that triggered the current onFling.
     * @param velocityX The velocity of this fling measured in pixels per second
     *                  along the x axis.
     * @param velocityY The velocity of this fling measured in pixels per second
     *                  along the y axis.
     * @return true if the event is consumed, else false
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(TAG, "onFling: " + e1.toString() + e2.toString());
        PointF startCoord = new PointF(e1.getX(), e1.getY());
        PointF endCoord = new PointF(e2.getX(), e2.getY());

        PScrollEvent.scrollDirection scrollDirection = getScrollDirection(startCoord, endCoord);

        long currTimestamp = new GregorianCalendar().getTimeInMillis();
        long duration = timestamp - currTimestamp;

        PScrollEvent pScrollEvent =
                new PScrollEvent(eventID, screenOrientation, timestamp, scrollDirection,
                        startCoord, endCoord, duration);
        eventTracker.notifyOfEvent(pScrollEvent);
        return true;
    }

    public PScrollEvent.scrollDirection getScrollDirection(PointF startCoord, PointF endCoord) {
        //Swipe left or right
        if (Math.abs(startCoord.x - endCoord.x) > Math.abs(startCoord.y - endCoord.y)) {
            if (startCoord.x > endCoord.x) { //scroll left
                return PScrollEvent.scrollDirection.LEFT;
            } else { //scroll right
                return PScrollEvent.scrollDirection.RIGHT;
            }
        } else { //Scroll up or down
            if (startCoord.y > startCoord.y) { //scroll down
                return PScrollEvent.scrollDirection.DOWN;
            } else {
                return PScrollEvent.scrollDirection.UP;
            }
        }
    }
}
