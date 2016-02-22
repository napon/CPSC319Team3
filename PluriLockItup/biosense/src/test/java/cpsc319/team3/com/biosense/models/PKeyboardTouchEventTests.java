package cpsc319.team3.com.biosense.models;

import android.graphics.PointF;

import org.junit.Test;

import java.util.GregorianCalendar;

import cpsc319.team3.com.biosense.PhoneDataManager;

import static org.junit.Assert.assertTrue;

public class PKeyboardTouchEventTests {
    @Test
    public void sampleTest() throws Exception {
        PElementTouchEvent p = new PElementTouchEvent(1, 1, new GregorianCalendar(), 1, 1, new PointF(1, 1), new PointF(1, 1));
        assertTrue(p.getClass() == PElementTouchEvent.class);
    }
}
