package cpsc319.team3.com.biosense;

import android.graphics.PointF;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.GregorianCalendar;

import cpsc319.team3.com.biosense.exception.LocationServiceUnavailableException;
import cpsc319.team3.com.biosense.models.PElementTouchEvent;
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
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener
//View.OnTouchListener
{
    private static final String TAG = "PluriLockTouchListener";

    PluriLockEventTracker eventTracker;

    public PluriLockTouchListener(PluriLockEventTracker eventTracker) {
        this.eventTracker = eventTracker;
    }

//    /**
//     * Override of default onTouch method.
//     * Listens for touch events and notifies the tracker when touch event occurs
//     * @param v View PluriLockTouchListener is attached to
//     * @param event even that trigged the onTouch call
//     * @return true since listener has consumed the event
//     */
//    public boolean onTouch(View v, MotionEvent event) {
//        if (event == null) {
//            //do nothing
//        }
//
//        switch (event.getActionMasked()) {
//            case MotionEvent.ACTION_DOWN:
//                break;
//            case MotionEvent.ACTION_MOVE:
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//            case MotionEvent.ACTION_CANCEL:
//                break;
//            default:
//                break;
//        }
//
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            int eventID = 0; //TODO
//            int screenOrientation = 0; //TODO
//            long timestamp = new GregorianCalendar().getTimeInMillis();
//            float pressure = event.getPressure();
//            float fingerOrientation = event.getOrientation();
//            PointF elementRelativeCoord = new PointF(event.getX(), event.getY());
//            PointF screenCoord = new PointF(event.getRawX(), event.getRawY());
//
//            PElementTouchEvent pElementTouchEvent =
//                                new PElementTouchEvent(eventID, screenOrientation, timestamp,
//                                        pressure, fingerOrientation, screenCoord, screenCoord);
//
//            eventTracker.notifyOfEvent(pElementTouchEvent);
//
//        }
//        return true;
//
//    }

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
     * @param e The down motion event.
     */
    @Override
    public boolean onDown(MotionEvent e) {
        Log.d(TAG,"onDown: " + e.toString());
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
     * @param e The up motion event that completed the first tap
     * @return true if the event is consumed, else false
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d(TAG, "onSingleTapUp: " + e.toString());
        return true;
    }

    /**
     * Notified when a scroll occurs with the initial on down {@link MotionEvent} and the
     * current move {@link MotionEvent}. The distance in x and y is also supplied for
     * convenience.
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
        return true;
    }
}
