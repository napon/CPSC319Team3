package cpsc319.team3.com.biosense;

import android.graphics.PointF;
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
public class PluriLockTouchListener implements View.OnTouchListener{
    PluriLockEventTracker eventTracker;

    public PluriLockTouchListener(PluriLockEventTracker eventTracker) {
        this.eventTracker = eventTracker;
    }

    /**
     * Override of default onTouch method.
     * Listens for touch events and notifies the tracker when touch event occurs
     * @param v View PluriLockTouchListener is attached to
     * @param event even that trigged the onTouch call
     * @return true since listener has consumed the event
     */
    public boolean onTouch(View v, MotionEvent event) {
        if (event == null) {
            //do nothing
        }
        else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int eventID = 0; //TODO
            int screenOrientation = 0; //TODO
            long timestamp = new GregorianCalendar().getTimeInMillis();
            float pressure = event.getPressure();
            float fingerOrientation = event.getOrientation();
            PointF elementRelativeCoord = new PointF(event.getX(), event.getY());
            PointF screenCoord = new PointF(event.getRawX(), event.getRawY());

            PElementTouchEvent pElementTouchEvent =
                                new PElementTouchEvent(eventID, screenOrientation, timestamp,
                                        pressure, fingerOrientation, screenCoord, screenCoord);

            eventTracker.notifyOfEvent(pElementTouchEvent);

        }
        return true;

    }

}
