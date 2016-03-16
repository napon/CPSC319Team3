package cpsc319.team3.com.biosense.models;

import android.graphics.PointF;
import android.view.MotionEvent;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PElementTouchEventTests {
    @Test
    public void sampleTest() throws Exception {
        PElementTouchEvent p = new PElementTouchEvent(1, 1, 1, 1,
                new PointF(1, 1), new PointF(1, 1), 1, 1, MotionEvent.ACTION_DOWN, 1);
        assertTrue(p.getClass() == PElementTouchEvent.class);
    }
}
