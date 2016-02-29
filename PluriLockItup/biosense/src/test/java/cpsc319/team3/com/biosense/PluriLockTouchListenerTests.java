package cpsc319.team3.com.biosense;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;

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
    public void onTouchReturnTrueTest() {
        View v = new View(null);
        PluriLockEventTracker pluriLockEventTracker = new PluriLockEventTracker(
                Mockito.mock(Context.class),
                Mockito.mock(PluriLockEventManager.class));
        PluriLockTouchListener touchListener = new PluriLockTouchListener(pluriLockEventTracker);

        assertTrue(touchListener.onDoubleTap(null));
    }

}
