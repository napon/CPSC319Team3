package cpsc319.team3.com.biosense;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PluriLockEventTrackerTests {
    @Test
    public void sampleTest() throws Exception {
        PluriLockEventTracker p = new PluriLockEventTracker(null, null);
        assertTrue(p.getClass() == PluriLockEventTracker.class);
    }
}
