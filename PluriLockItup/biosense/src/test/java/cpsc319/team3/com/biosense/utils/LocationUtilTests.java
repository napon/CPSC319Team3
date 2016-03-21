package cpsc319.team3.com.biosense.utils;

import android.app.Activity;
import android.location.Location;
import android.location.LocationManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import cpsc319.team3.com.biosense.exception.LocationServiceUnavailableException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class LocationUtilTests {

    private static final double DELTA = 1e-3;
    LocationUtil locationUtil;

    @Test
    public void testLocationUtilInit() throws LocationServiceUnavailableException {
        Activity a = Mockito.mock(Activity.class);
        LocationUtil.testStartListening(a);
        locationUtil = LocationUtil.getInstance();
        assertNotNull(locationUtil);
    }

    @Test
    public void testOnLocationChanged() throws LocationServiceUnavailableException {
        locationUtil = initLocationUtil();

        Location location = new Location(LocationManager.NETWORK_PROVIDER);
        location.setLatitude(15.1);
        location.setLongitude(14.2);
        location.setTime(System.currentTimeMillis());
        locationUtil.onLocationChanged(location);

        assertEquals(15.1, LocationUtil.getInstance().getLatitude(), DELTA);
        assertEquals(14.2, LocationUtil.getInstance().getLongitude(), DELTA);
    }

    @Test
    public void testOnLocationChangedTwice() throws LocationServiceUnavailableException {
        locationUtil = initLocationUtil();

        Location location = new Location(LocationManager.NETWORK_PROVIDER);
        location.setLatitude(15.1);
        location.setLongitude(14.2);
        location.setTime(System.currentTimeMillis());
        locationUtil.onLocationChanged(location);

        assertEquals(15.1, LocationUtil.getInstance().getLatitude(), DELTA);
        assertEquals(14.2, LocationUtil.getInstance().getLongitude(), DELTA);

        location.setLatitude(24.6);
        location.setLongitude(26.8);
        location.setTime(System.currentTimeMillis());
        locationUtil.onLocationChanged(location);

        assertEquals(24.6, LocationUtil.getInstance().getLatitude(), DELTA);
        assertEquals(26.8, LocationUtil.getInstance().getLongitude(), DELTA);
    }

    private LocationUtil initLocationUtil() {
        LocationUtil.startListening(new Activity());
        return LocationUtil.getInstance();
    }
}
