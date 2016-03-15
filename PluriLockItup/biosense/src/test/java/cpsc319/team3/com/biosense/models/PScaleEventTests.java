package cpsc319.team3.com.biosense.models;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Karen on 16-03-13.
 */
public class PScaleEventTests {
    @Test
    public void sampleTest() throws Exception {
        PScaleEvent p = new PScaleEvent(1, 1, 1, PScaleEvent.scaleDirection.INWARDS, 3.7f, 4.5f, PluriLockEvent.MotionCode.SCALE);
        assertTrue(p.getClass() == PScaleEvent.class);
    }
}
