package cpsc319.team3.com.biosense;

import android.graphics.PointF;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import java.util.GregorianCalendar;

import cpsc319.team3.com.biosense.models.PElementTouchEvent;
import cpsc319.team3.com.biosense.models.PScrollEvent;

/**
 * Created by Karen on 16-02-23.
 *
 * A Listener class for touch events.
 * PluriLockEventTracker is notified via method call when touch event occurs
 * A PElementTouchEvent, PScrollEvent or a PScaleEvent is created depending on the type of touch
 *
 */
public class PluriLockTouchListener implements
        GestureDetector.OnGestureListener,
        ScaleGestureDetector.OnScaleGestureListener
{
    private static final String TAG = "PluriLockTouchListener";

    int screenOrientation;
    long timestamp;
    float pressure;
    float fingerOrientation;
    float touchArea;

    PluriLockEventTracker eventTracker;

    public PluriLockTouchListener(PluriLockEventTracker eventTracker) {
        this.eventTracker = eventTracker;
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
        screenOrientation = eventTracker.getContext().getResources().getConfiguration().orientation;
        timestamp = e.getDownTime();
        pressure = e.getPressure();
        fingerOrientation = e.getOrientation();
        touchArea = e.getSize();

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
        try {
            Log.d(TAG, "onSingleTapUp: " + e.toString());
            long currTimestamp = System.currentTimeMillis();
            long duration = timestamp - currTimestamp;
            PointF elementRelativeCoord = new PointF(e.getX(), e.getY());
            PointF screenCord = new PointF(e.getX(), e.getY());

            PElementTouchEvent pElementTouchEvent =
                    new PElementTouchEvent(screenOrientation, timestamp,
                            pressure, fingerOrientation, elementRelativeCoord, screenCord, duration, touchArea);
            eventTracker.notifyOfEvent(pElementTouchEvent);
        }
        catch (NullPointerException nullEx){
            Log.d("Single touch error", nullEx.getMessage());
        }
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
        try {
            Log.d(TAG, "onScroll: " + e1.toString() + e2.toString());

            PointF startCoord = new PointF(e1.getX(), e1.getY());
            PointF endCoord = new PointF(e2.getX(), e2.getY());

            PScrollEvent.scrollDirection scrollDirection = getScrollDirection(startCoord, endCoord);

            long currTimestamp = new GregorianCalendar().getTimeInMillis();
            long duration = timestamp - currTimestamp;

            PScrollEvent pScrollEvent =
                    new PScrollEvent(screenOrientation, timestamp, scrollDirection,
                            startCoord, endCoord, duration);
            eventTracker.notifyOfEvent(pScrollEvent);
        }
        catch (NullPointerException e){
            Log.d("Scroll error", e.getMessage());
        }
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
        try {
            Log.d(TAG, "onFling: " + e1.toString() + e2.toString());
            PointF startCoord = new PointF(e1.getX(), e1.getY());
            PointF endCoord = new PointF(e2.getX(), e2.getY());

            PScrollEvent.scrollDirection scrollDirection = getScrollDirection(startCoord, endCoord);

            long currTimestamp = new GregorianCalendar().getTimeInMillis();
            long duration = timestamp - currTimestamp;

            PScrollEvent pScrollEvent =
                    new PScrollEvent(screenOrientation, timestamp, scrollDirection,
                            startCoord, endCoord, duration);
            eventTracker.notifyOfEvent(pScrollEvent);
        }
        catch (NullPointerException e){
            Log.d("Fling error", e.getMessage());
        }
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
            if (startCoord.y > endCoord.y) { //scroll down
                return PScrollEvent.scrollDirection.DOWN;
            } else {
                return PScrollEvent.scrollDirection.UP;
            }
        }
    }

    /**
     * Responds to scaling events for a gesture in progress.
     * Reported by pointer motion.
     *
     * @param detector The detector reporting the event - use this to
     *                 retrieve extended info about event state.
     * @return Whether or not the detector should consider this event
     * as handled. If an event was not handled, the detector
     * will continue to accumulate movement until an event is
     * handled. This can be useful if an application, for example,
     * only wants to update scaling factors if the change is
     * greater than 0.01.
     */
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        return false;
    }

    /**
     * Responds to the beginning of a scaling gesture. Reported by
     * new pointers going down.
     *
     * @param detector The detector reporting the event - use this to
     *                 retrieve extended info about event state.
     * @return Whether or not the detector should continue recognizing
     * this gesture. For example, if a gesture is beginning
     * with a focal point outside of a region where it makes
     * sense, onScaleBegin() may return false to ignore the
     * rest of the gesture.
     */
    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return false;
    }

    /**
     * Responds to the end of a scale gesture. Reported by existing
     * pointers going up.
     * <p/>
     * Once a scale has ended, {@link ScaleGestureDetector#getFocusX()}
     * and {@link ScaleGestureDetector#getFocusY()} will return focal point
     * of the pointers remaining on the screen.
     *
     * @param detector The detector reporting the event - use this to
     *                 retrieve extended info about event state.
     */
    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }
}
