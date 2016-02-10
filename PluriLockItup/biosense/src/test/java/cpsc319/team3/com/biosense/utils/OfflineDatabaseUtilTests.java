package cpsc319.team3.com.biosense.utils;

import org.junit.Test;

import cpsc319.team3.com.biosense.PhoneDataManager;

import static org.junit.Assert.assertTrue;

public class OfflineDatabaseUtilTests {
    @Test
    public void sampleTest() throws Exception {
        OfflineDatabaseUtil p = new OfflineDatabaseUtil();
        assertTrue(p.getClass() == OfflineDatabaseUtil.class);
    }
}
