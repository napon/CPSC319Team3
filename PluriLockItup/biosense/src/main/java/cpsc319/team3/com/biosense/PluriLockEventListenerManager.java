package cpsc319.team3.com.biosense;

import android.app.Application;
import android.content.res.Configuration;
import android.graphics.PointF;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import java.util.GregorianCalendar;

import cpsc319.team3.com.biosense.exception.LocationServiceUnavailableException;
import cpsc319.team3.com.biosense.models.PElementTouchEvent;
import cpsc319.team3.com.biosense.models.PKeyboardTouchEvent;
import cpsc319.team3.com.biosense.models.PluriLockEvent;

/**
 * Created by Sunny on 2016-02-14.
 */
public class PluriLockEventListenerManager extends Application {
    int currentEventID = 0;

    /**
    public View.OnClickListener createClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    PluriLockServerResponseListener pServerResponseListener =
                            new PluriLockServerResponseListener();

                    PluriLockEventManager pManager =
                            PluriLockEventManager.getInstance(getApplicationContext(), pServerResponseListener, "userid");

                    PElementTouchEvent pElementTouchEvent = new PElementTouchEvent();
                } catch (LocationServiceUnavailableException e) {

                }
            }
        };
    }

    public View.OnLongClickListener createLongClickListener() {
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                try {

                    PluriLockServerResponseListener pluriLockServerResponseListener =
                            new PluriLockServerResponseListener();

                    PluriLockEventManager pManager =
                            PluriLockEventManager.getInstance(getApplicationContext(),
                                    pluriLockServerResponseListener, "userid");

                    PKeyboardTouchEvent pKeyboardTouchEvent = new PElementTouchEvent();

                    pManager.addPluriLockEvent(pKeyboardTouchEvent);

                } catch (LocationServiceUnavailableException e) {

                }
                return true;
            }
        };
    } **/

    public View.OnKeyListener createKeyListener() {
        return new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                int eventID = currentEventID;
                int screenOrientation = getScreenOrientation();
                GregorianCalendar timestamp = new GregorianCalendar();
                float duration = 0; //TODO
                String userid = "USERID"; //TODO

                try {
                    PluriLockServerResponseListener pluriLockServerResponseListener =
                            new PluriLockServerResponseListener();

                    PluriLockEventManager pManager =
                            PluriLockEventManager.getInstance(getApplicationContext(),
                                    pluriLockServerResponseListener, userid);

                    PKeyboardTouchEvent pKeyboardTouchEvent =
                            new PKeyboardTouchEvent
                                    (eventID, screenOrientation, timestamp, duration, keyCode);

                    pManager.addPluriLockEvent(pKeyboardTouchEvent);

                } catch (LocationServiceUnavailableException e) {
                }
                currentEventID++;
                return true;
            }
        };
    }

    public View.OnTouchListener createTouchListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    int eventID = currentEventID;
                    int screenOrientation = getScreenOrientation();
                    GregorianCalendar timestamp = new GregorianCalendar();
                    float pressure = event.getPressure();
                    float fingerOrientation = event.getOrientation();
                    PointF elementRelativeCoord = new PointF(event.getX(), event.getY());
                    PointF screenCoord = new PointF(event.getRawX(), event.getRawY());
                    String userid = "USERID"; //TODO

                    try {
                        PluriLockServerResponseListener pServerResponseListener =
                                new PluriLockServerResponseListener();

                        PluriLockEventManager pManager =
                                PluriLockEventManager.getInstance(getApplicationContext(),
                                        pServerResponseListener, userid);

                        PElementTouchEvent pElementTouchEvent =
                                new PElementTouchEvent(eventID, screenOrientation, timestamp,
                                        pressure, fingerOrientation, screenCoord, screenCoord);

                        pManager.addPluriLockEvent(pElementTouchEvent);

                    } catch (LocationServiceUnavailableException e) {

                    }
                }
                currentEventID++;
                return true;
            }
        };
    }

    public int getScreenOrientation() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return 2;
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            return 1;
        }
        return 0;
    }
}
