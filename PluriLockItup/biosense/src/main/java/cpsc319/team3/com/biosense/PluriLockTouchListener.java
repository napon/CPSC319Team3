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
 */
public class PluriLockTouchListener implements View.OnTouchListener{
    PluriLockEventTracker eventTracker;

    public PluriLockTouchListener(PluriLockEventTracker eventTracker) {
        this.eventTracker = eventTracker;
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
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
