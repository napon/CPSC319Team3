package cpsc319.team3.com.biosense;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.MotionEvent;
import android.view.View;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class PluriLockTouchListenerTests {
    @Test
    public void sampleTest() {
        PluriLockEventTracker pluriLockEventTracker = Mockito.mock(PluriLockEventTracker.class);
        PluriLockTouchListener p = new PluriLockTouchListener(pluriLockEventTracker);
        assertTrue(p.getClass() == PluriLockTouchListener.class);
    }

    @Test
    public void onTouchReturnTrueTest() { //can't change name, error
        PluriLockEventTracker eventTrackerMock = Mockito.mock(PluriLockEventTracker.class);
        Context contextMock = Mockito.mock(Context.class);
        Resources resourceMock = Mockito.mock(Resources.class);
        Configuration configMock = Mockito.mock(Configuration.class);

        MotionEvent event = MotionEvent.obtain(1, 1, MotionEvent.ACTION_DOWN, 1, 1, 0);

        when(eventTrackerMock.getContext()).thenReturn(contextMock);
        when(contextMock.getResources()).thenReturn(resourceMock);
        when(resourceMock.getConfiguration()).thenReturn(configMock);

        configMock.orientation = Configuration.ORIENTATION_LANDSCAPE;

        PluriLockTouchListener touchListener = new PluriLockTouchListener(eventTrackerMock);

        assertTrue(touchListener.onDown(event));
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
        verify(eventTrackerMock, times(1));

    }

}
