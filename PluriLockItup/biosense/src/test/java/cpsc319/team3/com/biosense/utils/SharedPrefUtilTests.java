package cpsc319.team3.com.biosense.utils;

import org.junit.Test;

import cpsc319.team3.com.biosense.PhoneDataManager;

import static org.junit.Assert.assertTrue;

public class SharedPrefUtilTests {
    @Test
    public void sampleTest() throws Exception {
        SharedPrefUtil p = new SharedPrefUtil();
        assertTrue(p.getClass() == SharedPrefUtil.class);
    }
}
