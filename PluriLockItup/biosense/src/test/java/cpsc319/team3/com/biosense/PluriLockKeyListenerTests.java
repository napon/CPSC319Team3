package cpsc319.team3.com.biosense;

import android.view.View;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class PluriLockKeyListenerTests {
    @Test
    public void constructorTest() {
        PluriLockEventTracker pluriLockEventTracker = new PluriLockEventTracker(null, null);
        PluriLockKeyListener p = new PluriLockKeyListener(pluriLockEventTracker);
        assertTrue(p.getClass() == PluriLockKeyListener.class);
    }

    @Test
    public void onKeyReturnTrueTest() {
        View v = new View(null);
        PluriLockEventTracker pluriLockEventTracker = new PluriLockEventTracker(null, null);
        PluriLockKeyListener keyListener = new PluriLockKeyListener(pluriLockEventTracker);

        assertTrue(keyListener.onKey(v, 1, null));
    }
}
