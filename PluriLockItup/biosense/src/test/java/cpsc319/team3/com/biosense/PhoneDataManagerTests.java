package cpsc319.team3.com.biosense;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.view.WindowManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.Robolectric.Reflection;
import org.robolectric.RobolectricTestRunner;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for methods in PhoneDataManager.java
 */
@RunWith(RobolectricTestRunner.class)
public class PhoneDataManagerTests {

    private Context context;
    private Resources resources;

    @Before
    public void setup() {
        context = Robolectric.application.getApplicationContext();
        resources = context.getResources();
        Locale loc = new Locale("English", "US");
        Locale.setDefault(loc);
        Configuration c = new Configuration();
        c.locale = loc;
        resources.updateConfiguration(c, resources.getDisplayMetrics());
    }

    @Test
    public void testGetCountry() {
        assertEquals("US", PhoneDataManager.getCountry());
    }

    @Test
    public void testDisplayLanguage() {
        assertEquals("english", PhoneDataManager.getDisplayLanguage());
    }

    @Test
    public void testGetHardwareModel() {
        Reflection.setFinalStaticField(Build.class, "MODEL", "iPhone");
        assertEquals("iPhone", PhoneDataManager.getHardwareModel());
    }

    @Test
    public void testGetLocalDateTime() {
        assertEquals(Calendar.getInstance().getTimeInMillis(), PhoneDataManager.getLocalDateTime(), 20);
    }

    @Test
    public void testGetManufacturer() {
        Reflection.setFinalStaticField(Build.class, "MANUFACTURER", "Samsung");
        assertEquals("Samsung", PhoneDataManager.getManufacturer());
    }

    @Test
    public void testGetNumberOfCPUCores() {
        Runtime r = Runtime.getRuntime();
        assertEquals(r.availableProcessors(), PhoneDataManager.getNumberOfCPUCores());
    }

    @Test
    public void testGetScreenWidth() {
        Point p = new Point();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getSize(p);
        assertEquals(p.x, PhoneDataManager.getScreenWidth(context));
    }

    @Test
    public void testGetScreenHeight() {
        Point p = new Point();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getSize(p);
        assertEquals(p.y, PhoneDataManager.getScreenHeight(context));
    }

    @Test
    public void testGetSDKVersion() {
        Reflection.setFinalStaticField(Build.VERSION.class, "SDK_INT", 15);
        assertEquals(15, PhoneDataManager.getSDKVersion());
    }

    @Test
    public void testGetTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));
        assertEquals("Europe/London", PhoneDataManager.getTimeZone());
    }
}