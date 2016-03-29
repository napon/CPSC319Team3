package cpsc319.team3.com.biosense;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PointF;
import android.view.MotionEvent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;

import cpsc319.team3.com.biosense.models.PElementTouchEvent;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class PluriLockTouchListenerTests {
    @Test
    public void constructorTest() {
        PluriLockEventTracker pluriLockEventTracker = new PluriLockEventTracker(
                Mockito.mock(Context.class),
                Mockito.mock(PluriLockEventManager.class));
        PluriLockTouchListener p = new PluriLockTouchListener(pluriLockEventTracker);
        assertTrue(p.getClass() == PluriLockTouchListener.class);
    }

    @Test
    public void onTouchReturnTrueTest() { //can't change name, error
        PluriLockEventTracker eventTrackerMock = Mockito.mock(PluriLockEventTracker.class);
        Context contextMock = Mockito.mock(Context.class);
        Resources resourceMock = Mockito.mock(Resources.class);
        Configuration configMock = Mockito.mock(Configuration.class);

        MotionEvent event = MotionEvent.obtain(13892, 1, MotionEvent.ACTION_DOWN,
                4.6f, 3.2f, 0.8f, 1.0f, 0, 1.0f, 1.0f, 1, 0);

        when(eventTrackerMock.getContext()).thenReturn(contextMock);
        when(contextMock.getResources()).thenReturn(resourceMock);
        when(resourceMock.getConfiguration()).thenReturn(configMock);

        configMock.orientation = Configuration.ORIENTATION_LANDSCAPE;

        PluriLockTouchListener touchListener = new PluriLockTouchListener(eventTrackerMock);

//        assertEquals(0.8f, event.getPressure()); //FAILS - returns 0
//        assertEquals(13892, event.getDownTime()); //FAILS - returns 0

        assertTrue(touchListener.onDown(event));
//        assertEquals(configMock.orientation, touchListener);
        //assertEquals(13892, touchListener.timestamp);
//        assertEquals(0.8f, touchListener.pressure, 0.01f);
//        assertEquals(0f, touchListener.fingerOrientation, 0.01f);
    }

    @Test
    public void onSingleTapUpTest() {
        PluriLockEventTracker eventTrackerMock = Mockito.mock(PluriLockEventTracker.class);
        Context contextMock = Mockito.mock(Context.class);
        Resources resourceMock = Mockito.mock(Resources.class);
        Configuration configMock = Mockito.mock(Configuration.class);

        MotionEvent eventDown = MotionEvent.obtain(1, 1, MotionEvent.ACTION_DOWN, 1, 1, 0);
        MotionEvent eventUp = MotionEvent.obtain(1, 1, MotionEvent.ACTION_UP, 1, 1, 0);

        when(eventTrackerMock.getContext()).thenReturn(contextMock);
        when(contextMock.getResources()).thenReturn(resourceMock);
        when(resourceMock.getConfiguration()).thenReturn(configMock);

        configMock.orientation = Configuration.ORIENTATION_LANDSCAPE;

        PluriLockTouchListener touchListener = new PluriLockTouchListener(eventTrackerMock);

        touchListener.onDown(eventDown);

        assertTrue(touchListener.onSingleTapUp(eventUp));

        PElementTouchEvent pElementTouchEvent =
                new PElementTouchEvent(Configuration.ORIENTATION_LANDSCAPE, 1,
                        1, 1, new PointF(1,1), new PointF(1,1), 0, 1, MotionEvent.ACTION_DOWN);
//        verify(eventTrackerMock, times(1)).notifyOfEvent(pElementTouchEvent);
    }

}
