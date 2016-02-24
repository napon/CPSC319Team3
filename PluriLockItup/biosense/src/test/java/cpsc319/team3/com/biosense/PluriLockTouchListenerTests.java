package cpsc319.team3.com.biosense;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class PluriLockTouchListenerTests {
    @Test
    public void sampleTest() throws Exception {
        PluriLockEventTracker pluriLockEventTracker = new PluriLockEventTracker();
        PluriLockTouchListener p = new PluriLockTouchListener(pluriLockEventTracker);
        assertTrue(p.getClass() == PluriLockTouchListener.class);
    }
}
