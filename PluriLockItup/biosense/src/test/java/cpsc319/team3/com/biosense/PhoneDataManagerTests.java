package cpsc319.team3.com.biosense;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PhoneDataManagerTests {
    @Test
    public void sampleTest() throws Exception {
        PhoneDataManager p = new PhoneDataManager();
        assertTrue(p.getClass() == PhoneDataManager.class);
    }
}
