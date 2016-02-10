package cpsc319.team3.com.biosense.models;

import org.junit.Test;

import cpsc319.team3.com.biosense.PhoneDataManager;

import static org.junit.Assert.assertTrue;

public class PKeyboardTouchEventTests {
    @Test
    public void sampleTest() throws Exception {
        PElementTouchEvent p = new PElementTouchEvent();
        assertTrue(p.getClass() == PElementTouchEvent.class);
    }
}
