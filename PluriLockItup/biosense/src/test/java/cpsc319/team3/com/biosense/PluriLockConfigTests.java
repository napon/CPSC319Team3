package cpsc319.team3.com.biosense;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PluriLockConfigTests {
    @Test
    public void sampleTest() throws Exception {
        PluriLockConfig p = new PluriLockConfig();
        assertTrue(p.getClass() == PluriLockConfig.class);
    }
}
