package cpsc319.team3.com.biosense.utils;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowContextImpl;
import org.robolectric.shadows.ShadowContextWrapper;

import cpsc319.team3.com.biosense.PluriLockConfig;
import cpsc319.team3.com.biosense.exception.LocationServiceUnavailableException;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class LocationUtilTests {

    private static final double DELTA = 1e-3;
    LocationUtil locationUtil;

    @Test
    public void testDefaultLocation() {
        Application application = createApplicationWithPermission();
        locationUtil = new LocationUtil(application, new PluriLockConfig());
        assertEquals(0.0, locationUtil.getLatitude(), DELTA);
        assertEquals(0.0, locationUtil.getLongitude(), DELTA);
    }

    @Test
    public void testLocationUtilNoPermission() throws LocationServiceUnavailableException {
        Application application = (Application) ShadowApplication.getInstance().getApplicationContext();
        ShadowContextWrapper shadowApp = Shadows.shadowOf(application);
        shadowApp.setPackageName("com.cpsc319.team3");
        application.onCreate();
        PluriLockConfig config = new PluriLockConfig();
        locationUtil = new LocationUtil(application, config);
    }

    @Test
    public void testLocationUtilWithPermission() throws LocationServiceUnavailableException {
        Application application = createApplicationWithPermission();
        locationUtil = new LocationUtil(application, new PluriLockConfig());
    }

    @Test
    public void testOnLocationChanged() throws LocationServiceUnavailableException {
        Application application = createApplicationWithPermission();
        locationUtil = new LocationUtil(application, new PluriLockConfig());

        Location location = new Location(LocationManager.NETWORK_PROVIDER);
        location.setLatitude(15.1);
        location.setLongitude(14.2);
        location.setTime(System.currentTimeMillis());
        locationUtil.onLocationChanged(location);

        assertEquals(15.1, locationUtil.getLatitude(), DELTA);
        assertEquals(14.2, locationUtil.getLongitude(), DELTA);
    }

    @Test
    public void testOnLocationChangedTwice() throws LocationServiceUnavailableException {
        Application application = createApplicationWithPermission();
        locationUtil = new LocationUtil(application, new PluriLockConfig());

        Location location = new Location(LocationManager.NETWORK_PROVIDER);
        location.setLatitude(15.1);
        location.setLongitude(14.2);
        location.setTime(System.currentTimeMillis());
        locationUtil.onLocationChanged(location);

        assertEquals(15.1, locationUtil.getLatitude(), DELTA);
        assertEquals(14.2, locationUtil.getLongitude(), DELTA);

        location.setLatitude(24.6);
        location.setLongitude(26.8);
        location.setTime(System.currentTimeMillis());
        locationUtil.onLocationChanged(location);

        assertEquals(24.6, locationUtil.getLatitude(), DELTA);
        assertEquals(26.8, locationUtil.getLongitude(), DELTA);
    }

    private Application createApplicationWithPermission() {
        Application application = (Application) ShadowApplication.getInstance().getApplicationContext();
        ShadowContextWrapper shadowApp = Shadows.shadowOf(application);
        shadowApp.setPackageName("com.cpsc319.team3");
        LocationManager mockLocation= Mockito.mock(LocationManager.class);
        ShadowContextImpl shadowContext = (ShadowContextImpl) Shadows.shadowOf(application.getBaseContext());
        shadowContext.setSystemService(Context.LOCATION_SERVICE, mockLocation);
        shadowApp.grantPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
        Settings.Secure.putInt(RuntimeEnvironment.application.getContentResolver(), Settings.Secure.LOCATION_MODE, Settings.Secure.LOCATION_MODE_HIGH_ACCURACY);
        application.onCreate();
        return application;
    }
}
