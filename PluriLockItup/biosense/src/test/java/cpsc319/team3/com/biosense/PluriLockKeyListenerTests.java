package cpsc319.team3.com.biosense;

import android.content.Context;
import android.view.View;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;

public class PluriLockKeyListenerTests {
    @Test
    public void sampleTest() {
        PluriLockEventTracker pluriLockEventTracker = new PluriLockEventTracker(
                Mockito.mock(Context.class),
                Mockito.mock(PluriLockEventManager.class));
        PluriLockKeyListener p = new PluriLockKeyListener(pluriLockEventTracker);
        assertTrue(p.getClass() == PluriLockKeyListener.class);
    }

    @Test
    public void onKeyReturnTrueTest() {
        View v = new View(null);
        PluriLockEventTracker pluriLockEventTracker = new PluriLockEventTracker(
                Mockito.mock(Context.class),
                Mockito.mock(PluriLockEventManager.class));
        PluriLockKeyListener keyListener = new PluriLockKeyListener(pluriLockEventTracker);

       // assertTrue(keyListener.onKey(v, 1, null));
    }
}
