package cpsc319.team3.com.biosense.models;

import android.graphics.PointF;

import org.junit.Test;

import java.util.GregorianCalendar;

import cpsc319.team3.com.biosense.PhoneDataManager;

import static org.junit.Assert.assertTrue;

public class PMonoKeyboardTouchEventTests {
    @Test
    public void sampleTest() throws Exception {
        PMonoKeyboardTouchEvent p = new PMonoKeyboardTouchEvent
                (1, 1, System.currentTimeMillis(), 1, 1);
        assertTrue(p.getClass() == PMonoKeyboardTouchEvent.class);
    }
}
