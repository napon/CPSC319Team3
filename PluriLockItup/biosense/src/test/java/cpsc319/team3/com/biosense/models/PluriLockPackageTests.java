package cpsc319.team3.com.biosense.models;

import android.graphics.PointF;
import android.view.MotionEvent;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Calendar;

import cpsc319.team3.com.biosense.models.PluriLockPackage.PluriLockPackageBuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(RobolectricTestRunner.class)
public class PluriLockPackageTests {

    private static final double DELTA = 1e-2;

    private PluriLockPackageBuilder b;
    private long time;

    @Before
    public void setUp() {
        time = Calendar.getInstance().getTimeInMillis();

        // Start each test with a newly defined PluriLockPackage.
        b = new PluriLockPackageBuilder()
                .userID("napon")
                .latitude(20.0)
                .longitude(40.0)
                .model("Nexus 6")
                .timeZone("PST")
                .appName("PLURILOCKITUP")
                .countryCode("CA")
                .language("Thai")
                .manufacturer("LG")
                .screenHeight(600)
                .screenWidth(800)
                .sdkVersion(8);
    }

    @Test
    public void testInitialization() throws Exception {
        PluriLockPackage p = new PluriLockPackageBuilder().buildPackage();
        assertTrue(p != null);
    }

    @Test
    public void testGetJSONWithNoEvent() {
        // Create a default PluriLockPackage.
        PluriLockPackage p = b.buildPackage();
        JSONObject jsonRepresentation = p.getJSON();
        try {
            JSONObject data = jsonRepresentation.getJSONObject("data");
            // Test each field from PhoneDataManager is properly defined.
            assertEquals("napon", jsonRepresentation.getString("userID"));
            assertEquals("Nexus 6", data.getString("model"));
            assertEquals("LG", data.getString("manufacturer"));
            assertEquals("Thai", data.getString("language"));
            assertEquals("CA", data.getString("countryCode"));
            assertEquals("PST", data.getString("timeZone"));
            assertEquals("PLURILOCKITUP", data.getString("appName"));
            assertEquals(20.0, data.getDouble("latitude"), DELTA);
            assertEquals(40.0, data.getDouble("longitude"), DELTA);
            assertEquals(600, data.getInt("screenHeight"));
            assertEquals(800, data.getInt("screenWidth"));
            assertEquals(8, data.getInt("sdkVersion"));
        } catch (JSONException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetJSONWithKeyboardTouchEvent() {
        // Create and add a KeyboardTouchEvent to the PluriLockPackage.
        PMonoKeyboardTouchEvent keyboard = new PMonoKeyboardTouchEvent(1, time, 30, "a");
        PluriLockPackage p = b.setEvents(new PluriLockEvent[]{keyboard}).buildPackage();
        JSONObject jsonObject = p.getJSON();
        try {
            JSONObject data = jsonObject.getJSONObject("data");

            // There should only be one object in the events set.
            assertEquals(1, data.getJSONArray("events").length());
            JSONObject eventObject = data.getJSONArray("events").getJSONObject(0);

            // Test each field from KeyboardTouchEvent is properly defined.
            assertEquals("MONOGRAPH", eventObject.getString("eventType"));
            assertEquals(30, eventObject.getLong("duration"));
            assertEquals( "a", eventObject.getString("key"));
            assertEquals(1, eventObject.getInt("orientation"));
            assertEquals(time, eventObject.getLong("timestamp"));
        } catch (JSONException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetJSONWithDiKeyboardTouchEvent() {
        // Create and add a KeyboardTouchEvent to the PluriLockPackage.
        PDiKeyboardTouchEvent keyboard = new PDiKeyboardTouchEvent(2, time, 200, "a","b");
        PluriLockPackage p = b.setEvents(new PluriLockEvent[]{keyboard}).buildPackage();
        JSONObject jsonObject = p.getJSON();
        try {
            JSONObject data = jsonObject.getJSONObject("data");

            // There should only be one object in the events set.
            assertEquals(1, data.getJSONArray("events").length());
            JSONObject eventObject = data.getJSONArray("events").getJSONObject(0);

            // Test each field from KeyboardTouchEvent is properly defined.
            assertEquals("DIGRAPH", eventObject.getString("eventType"));
            assertEquals(200, eventObject.getLong("duration"));
            assertEquals( "a", eventObject.getString("fromKey"));
            assertEquals( "b", eventObject.getString("toKey"));
            assertEquals(2, eventObject.getInt("orientation"));
            assertEquals(time, eventObject.getLong("timestamp"));
        } catch (JSONException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetJSONWithElementTouchEvent() {
        // Create and add a ElementTouchEvent to the PluriLockPackage.
        PElementTouchEvent touch = new PElementTouchEvent(0, time, 3.0f, 4.2f,
                new PointF(1.0f, 2.0f), new PointF(3.0f, 4.0f), 200, 3.7f, MotionEvent.ACTION_UP, 1);
        PluriLockPackage p = b.setEvents(new PluriLockEvent[]{touch}).buildPackage();
        JSONObject jsonObject = p.getJSON();
        try {
            JSONObject data = jsonObject.getJSONObject("data");

            // There should only be one object in the events set.
            assertEquals(1, data.getJSONArray("events").length());
            JSONObject eventObject = data.getJSONArray("events").getJSONObject(0);

            // Test each field from ElementTouchEvent is properly defined.
            assertEquals("ELEMENT_TOUCH", eventObject.getString("eventType"));
            assertEquals(time, eventObject.getLong("timestamp"));
            assertEquals(0, eventObject.getInt("orientation"));
            assertEquals(3, eventObject.getInt("pressure"));
            assertEquals(200, eventObject.getLong("duration"));
            assertEquals(1.0, eventObject.getDouble("elementRelX"), DELTA);
            assertEquals(2.0, eventObject.getDouble("elementRelY"), DELTA);
            assertEquals(3.0, eventObject.getDouble("screenX"), DELTA);
            assertEquals(4.0, eventObject.getDouble("screenY"), DELTA);
            assertEquals(4.2, eventObject.getDouble("fingerOrientation"), DELTA);
            assertEquals(3.7, eventObject.getDouble("touchArea"), DELTA);
            assertEquals(MotionEvent.ACTION_UP, eventObject.getInt("motionEventCode"));
        } catch (JSONException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetJSONWithScrollEvent() {
        // Create and add a ScrollEvent to the PluriLockPackage.
        PScrollEvent scroll = new PScrollEvent(1, time, PScrollEvent.ScrollDirection.UP,
                new PointF(9.5f, 8.3f), new PointF(7.6f, 4.5f), 200, MotionEvent.ACTION_SCROLL);
        PluriLockPackage p = b.setEvents(new PluriLockEvent[]{scroll}).buildPackage();
        JSONObject jsonObject = p.getJSON();
        try {
            JSONObject data = jsonObject.getJSONObject("data");

            // There should only be one object in the events set.
            assertEquals(1, data.getJSONArray("events").length());
            JSONObject eventObject = data.getJSONArray("events").getJSONObject(0);

            // Test each field from ScrollEvent is properly defined.
            assertEquals("SCROLL", eventObject.getString("eventType"));
            assertEquals(time, eventObject.getLong("timestamp"));
            assertEquals(1, eventObject.getInt("orientation"));
            assertEquals(200, eventObject.getLong("duration"));
            assertEquals("UP", eventObject.getString("scrollDirection"));
            assertEquals(9.5, eventObject.getDouble("startX"), DELTA);
            assertEquals(8.3, eventObject.getDouble("startY"), DELTA);
            assertEquals(7.6, eventObject.getDouble("endX"), DELTA);
            assertEquals(4.5, eventObject.getDouble("endY"), DELTA);
            assertEquals(MotionEvent.ACTION_SCROLL, eventObject.getInt("motionEventCode"));
        } catch (JSONException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetJSONWithScaleEvent() {
        // Create and add a ScaleEvent to the PluriLockPackage.
        PScaleEvent scale = new PScaleEvent(1, time, 42, PScaleEvent.ScaleStatus.BEGIN,
                2.5f, 6.7f);
        PluriLockPackage p = b.setEvents(new PluriLockEvent[]{scale}).buildPackage();
        JSONObject jsonObject = p.getJSON();
        try {
            JSONObject data = jsonObject.getJSONObject("data");

            // There should only be one object in the events set.
            assertEquals(1, data.getJSONArray("events").length());
            JSONObject eventObject = data.getJSONArray("events").getJSONObject(0);

            // Test each field from ScaleEvent is properly defined
            assertEquals("SCALE", eventObject.getString("eventType"));
            assertEquals(time, eventObject.getLong("timestamp"));
            assertEquals(1, eventObject.getInt("orientation"));
            assertEquals(42, eventObject.getLong("duration"));
            assertEquals("BEGIN", eventObject.getString("ScaleStatus"));
            assertEquals(2.5f, eventObject.getDouble("spanX"), DELTA);
            assertEquals(6.7f , eventObject.getDouble("spanY"), DELTA);
        } catch (JSONException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetJSONWithMultipleEvents() {
        // Create and add multiple events to the PluriLockPackage.
        PScrollEvent scroll = new PScrollEvent(1, time, PScrollEvent.ScrollDirection.UP,
                new PointF(9.5f, 8.3f), new PointF(7.6f, 4.5f), 1, MotionEvent.ACTION_SCROLL);
        PElementTouchEvent touch = new PElementTouchEvent(0, time, 3.0f, 4.2f,
                new PointF(1.0f, 2.0f), new PointF(3.0f, 4.0f), 1, 1.0f, MotionEvent.ACTION_UP, 1);
        PMonoKeyboardTouchEvent keyboard = new PMonoKeyboardTouchEvent(0, time, 30, (int) 'a');
        PluriLockPackage p = b.setEvents(new PluriLockEvent[]{scroll, touch, keyboard}).buildPackage();
        JSONObject jsonObject = p.getJSON();
        try {
            JSONObject data = jsonObject.getJSONObject("data");

            // There should only be three objects in the events set.
            assertEquals(3, data.getJSONArray("events").length());
        } catch (JSONException e) {
            fail(e.getMessage());
        }
    }
}
