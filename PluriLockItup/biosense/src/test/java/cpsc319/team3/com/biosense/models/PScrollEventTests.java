package cpsc319.team3.com.biosense.models;

import android.graphics.PointF;
import android.view.MotionEvent;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PScrollEventTests {
    @Test
    public void sampleTest() throws Exception {
        PScrollEvent p = new PScrollEvent(1, 1, PScrollEvent.ScrollDirection.UP,
                new PointF(1, 1), new PointF(1,1), 1, MotionEvent.ACTION_SCROLL);
        assertTrue(p.getClass() == PScrollEvent.class);
    }
}
