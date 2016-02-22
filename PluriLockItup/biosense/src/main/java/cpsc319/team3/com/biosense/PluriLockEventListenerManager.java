package cpsc319.team3.com.biosense;

import android.app.Application;
import android.graphics.PointF;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import cpsc319.team3.com.biosense.exception.LocationServiceUnavailableException;
import cpsc319.team3.com.biosense.models.PElementTouchEvent;
import cpsc319.team3.com.biosense.models.PKeyboardTouchEvent;
import cpsc319.team3.com.biosense.models.PluriLockEvent;

/**
 * Created by Sunny on 2016-02-14.
 */
public class PluriLockEventListenerManager extends Application {

    public View.OnClickListener createClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //throw new RuntimeException("Not yet implemented");


                try {
                    PluriLockServerResponseListener pServerResponseListener = new PluriLockServerResponseListener();
                    PluriLockEventManager pManager = PluriLockEventManager.getInstance(getApplicationContext(), pServerResponseListener, "userid");
                } catch (LocationServiceUnavailableException e) {

                }

            }
        };
    }

    public View.OnLongClickListener createLongClickListener() {
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                throw new RuntimeException("Not yet implemented");
//                return false;
            }
        };
    }

    public View.OnKeyListener createKeyListener() {
        return new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                int screenOrientation = 1;
                float duration = 0;

                try {
                    PluriLockServerResponseListener pluriLockServerResponseListener =
                            new PluriLockServerResponseListener();

                    PluriLockEventManager pManager =
                            PluriLockEventManager.getInstance(getApplicationContext(),
                                    pluriLockServerResponseListener, "userid");

                    PKeyboardTouchEvent pKeyboardTouchEvent = new PKeyboardTouchEvent(screenOrientation,
                            duration, keyCode);

                    pManager.addPluriLockEvent(pKeyboardTouchEvent);

                } catch (LocationServiceUnavailableException e) {

                }
                return true;
            }
        };
    }

    public View.OnTouchListener createTouchListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    float xCord = event.getX();
                    float yCord = event.getY();
                    PointF screenCoord = new PointF(xCord, yCord);
                    float pressure = event.getPressure();
                    int screenOrientation = 1; //TODO: get screen orientation
                    float fingerOrientation = event.getOrientation();

                    try {
                        PluriLockServerResponseListener pServerResponseListener =
                                new PluriLockServerResponseListener();

                        PluriLockEventManager pManager =
                                PluriLockEventManager.getInstance(getApplicationContext(),
                                        pServerResponseListener, "userid"); //TODO: get userid

                        PElementTouchEvent pElementTouchEvent =
                                new PElementTouchEvent(screenOrientation, pressure, fingerOrientation,
                                        screenCoord, screenCoord);

                        pManager.addPluriLockEvent(pElementTouchEvent);
                        //TODO: get element's relative coords
                    } catch (LocationServiceUnavailableException e) {

                    }
                }
                return true;
            }
        };
    }
}
