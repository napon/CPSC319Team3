package cpsc319.team3.com.biosense.models;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Karen on 16-03-06.
 */
public class PDiKeyboardTouchEventTests {
    @Test
    public void sampleTest() throws Exception {
        PDiKeyboardTouchEvent p = new PDiKeyboardTouchEvent
                (1, 1, System.currentTimeMillis(), 1, 4, 5);
        assertTrue(p.getClass() == PDiKeyboardTouchEvent.class);
    }
}
