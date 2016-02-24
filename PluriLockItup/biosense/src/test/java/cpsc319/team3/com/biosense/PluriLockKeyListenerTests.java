package cpsc319.team3.com.biosense;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class PluriLockKeyListenerTests {
    @Test
    public void sampleTest() throws Exception {
        PluriLockEventTracker pluriLockEventTracker = new PluriLockEventTracker();
        PluriLockKeyListener p = new PluriLockKeyListener(pluriLockEventTracker);
        assertTrue(p.getClass() == PluriLockKeyListener.class);
    }
}
