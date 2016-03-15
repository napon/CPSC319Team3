package cpsc319.team3.com.biosense.models;

import android.graphics.PointF;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PScrollEventTests {
    @Test
    public void sampleTest() throws Exception {
        PScrollEvent p = new PScrollEvent(1, 1, PScrollEvent.ScrollDirection.UP, new PointF(1, 1), new PointF(1,1), 1, PluriLockEvent.MotionCode.SCROLL);
        assertTrue(p.getClass() == PScrollEvent.class);
    }
}
