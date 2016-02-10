package cpsc319.team3.com.biosense.models;

import org.junit.Test;

import cpsc319.team3.com.biosense.PhoneDataManager;

import static org.junit.Assert.assertTrue;

public class PScrollEventTests {
    @Test
    public void sampleTest() throws Exception {
        PScrollEvent p = new PScrollEvent();
        assertTrue(p.getClass() == PScrollEvent.class);
    }
}
