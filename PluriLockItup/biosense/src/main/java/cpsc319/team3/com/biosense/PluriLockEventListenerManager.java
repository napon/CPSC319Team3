package cpsc319.team3.com.biosense;

import android.app.Application;
import android.content.Context;
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
public class PluriLockEventListenerManager {
    private static Context applicationContext;
    private String userid;

    public PluriLockEventListenerManager(Context applicationContext, String userid) {
        this.applicationContext = applicationContext;
        this.userid = userid;
    }

    int currentEventID = 0; //TODO: change eventID to something meaningful

    /** Napon's comments (from github):
    we want to create one PKeyboardTouchEvent per TWO key presses and record the time between them.
     We will probably need some sort of a timeout as well in the case when it's not possible to
     construct a pair of key presses (eg. User types odd number of characters).
    **/
     public View.OnKeyListener createKeyListener() {
        return new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                int eventID = currentEventID;
                int screenOrientation = getScreenOrientation();
                Long timestamp = new GregorianCalendar().getTimeInMillis();
                long duration = 0; //TODO

                try {
                    PluriLockServerResponseListener pluriLockServerResponseListener =
                            new PluriLockServerResponseListener();

                    PluriLockEventManager pManager =
                            PluriLockEventManager.getInstance(applicationContext,
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

    //TODO: scroll, swipe, contact area, two-finger press
    public View.OnTouchListener createTouchListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    int eventID = currentEventID;
                    int screenOrientation = getScreenOrientation();
                    long timestamp = new GregorianCalendar().getTimeInMillis();
                    float pressure = event.getPressure();
                    float fingerOrientation = event.getOrientation();
                    PointF elementRelativeCoord = new PointF(event.getX(), event.getY());
                    PointF screenCoord = new PointF(event.getRawX(), event.getRawY());

                    try {
                        PluriLockServerResponseListener pServerResponseListener =
                                new PluriLockServerResponseListener();

                        PluriLockEventManager pManager =
                                PluriLockEventManager.getInstance(applicationContext,
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
        return applicationContext.getResources().getConfiguration().orientation;
    }
}
