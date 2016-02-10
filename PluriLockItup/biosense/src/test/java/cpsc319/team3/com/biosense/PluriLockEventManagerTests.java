package cpsc319.team3.com.biosense;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PluriLockEventManagerTests {
    @Test
    public void sampleTest() throws Exception {
        PluriLockEventManager p = new PluriLockEventManager();
        assertTrue(p.getClass() == PluriLockEventManager.class);
    }
}
