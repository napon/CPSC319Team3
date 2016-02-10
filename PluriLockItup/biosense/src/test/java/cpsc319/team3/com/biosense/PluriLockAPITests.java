package cpsc319.team3.com.biosense;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PluriLockAPITests {
    @Test
    public void sampleTest() throws Exception {
        PluriLockAPI p = new PluriLockAPI();
        assertTrue(p.getClass() == PluriLockAPI.class);
    }
}
