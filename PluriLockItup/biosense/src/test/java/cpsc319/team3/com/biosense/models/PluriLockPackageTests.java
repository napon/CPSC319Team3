package cpsc319.team3.com.biosense.models;

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

    @Before
    public void setUp() {
        b = new PluriLockPackageBuilder()
                .userID("napon")
                .latitude(20.0)
                .longitude(40.0)
                .model("Nexus 6")
                .timeZone("PST")
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
        PluriLockPackage p = b.buildPackage();
        JSONObject jsonRepresentation = p.getJSON();
        try {
            assertEquals("napon", jsonRepresentation.getString("user"));
            assertEquals("Nexus 6", jsonRepresentation.getString("model"));
            assertEquals("LG", jsonRepresentation.getString("manufacturer"));
            assertEquals("Thai", jsonRepresentation.getString("language"));
            assertEquals("CA", jsonRepresentation.getString("countryCode"));
            assertEquals("PST", jsonRepresentation.getString("timeZone"));
            assertEquals(20.0, jsonRepresentation.getDouble("latitude"), DELTA);
            assertEquals(40.0, jsonRepresentation.getDouble("longitude"), DELTA);
            assertEquals(600, jsonRepresentation.getInt("screenHeight"));
            assertEquals(800, jsonRepresentation.getInt("screenWidth"));
            assertEquals(8, jsonRepresentation.getInt("sdkVersion"));
        } catch (JSONException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetJSONWithKeyboardTouchEvent() {
        long time = Calendar.getInstance().getTimeInMillis();
        PKeyboardTouchEvent keyboard = new PKeyboardTouchEvent(1, 0, time, 30, (int) 'a');
        PluriLockPackage p = b.setEvents(new PluriLockEvent[]{keyboard}).buildPackage();
        JSONObject jsonObject = p.getJSON();
        try {
            assertEquals(1, jsonObject.getJSONArray("events").length());
            JSONObject eventObject = jsonObject.getJSONArray("events").getJSONObject(0);
            assertEquals("SINGLE_KEY", eventObject.getString("eventType"));
            assertEquals(30, eventObject.getLong("duration"));
            assertEquals((int) 'a', eventObject.getInt("key"));
            assertEquals(time, eventObject.getLong("timestamp"));
        } catch (JSONException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetJSONWithElementTouchEvent() { /* TODO */ }

    @Test
    public void testGetJSONWithScrollEvent() { /* TODO */ }

    @Test
    public void testGetJSONWithMultipleEvents() { /* TODO */ }
}
