package cpsc319.team3.com.biosense;

import android.view.MotionEvent;
import android.view.View;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class PluriLockTouchListenerTests {
    @Test
    public void constructorTest() {
        PluriLockEventTracker pluriLockEventTracker = new PluriLockEventTracker(null, null);
        PluriLockTouchListener p = new PluriLockTouchListener(pluriLockEventTracker);
        assertTrue(p.getClass() == PluriLockTouchListener.class);
    }

    @Test
    public void onTouchReturnTrueTest() {
        View v = new View(null);
        PluriLockEventTracker pluriLockEventTracker = new PluriLockEventTracker(null, null);
        PluriLockTouchListener touchListener = new PluriLockTouchListener(pluriLockEventTracker);

        assertTrue(touchListener.onDoubleTap(null));
    }

}
