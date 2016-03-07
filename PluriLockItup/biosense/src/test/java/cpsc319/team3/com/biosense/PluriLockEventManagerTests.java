package cpsc319.team3.com.biosense;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.graphics.PointF;
import android.location.LocationManager;
import android.provider.Settings;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowContextImpl;
import org.robolectric.shadows.ShadowContextWrapper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.GregorianCalendar;
import java.net.URI;
import java.net.URISyntaxException;


import javax.websocket.DeploymentException;

import cpsc319.team3.com.biosense.exception.LocationServiceUnavailableException;
import cpsc319.team3.com.biosense.models.PElementTouchEvent;
import cpsc319.team3.com.biosense.models.PMonoKeyboardTouchEvent;
import cpsc319.team3.com.biosense.models.PScrollEvent;
import cpsc319.team3.com.biosense.models.PluriLockPackage;
import cpsc319.team3.com.biosense.utils.PluriLockNetworkUtil;

@RunWith(RobolectricTestRunner.class)
public class PluriLockEventManagerTests {

    @Before
    public void setUp() {
        PluriLockEventManager.deleteInstance();
    }

    @Test
    public void testPluriLockEventManagerWithLocationPermissions() throws LocationServiceUnavailableException {
        Application application = createApplicationWithPermissions();
        PluriLockServerResponseListener listener = new PluriLockServerResponseListener() {
            @Override
            public void notify(String msg) {

            }
        };
        PluriLockConfig config = new PluriLockConfig();
        PluriLockEventManager p = PluriLockEventManager.getInstance(application, listener, "user-foo", config);
    }

    @Test (expected = LocationServiceUnavailableException.class)
    public void testPluriLockEventManagerNoLocationPermission() throws LocationServiceUnavailableException {
        Application application = (Application) ShadowApplication.getInstance().getApplicationContext();
        ShadowContextWrapper shadowApp = Shadows.shadowOf(application);
        shadowApp.setPackageName("com.cpsc319.team3");
        application.onCreate();
        PluriLockServerResponseListener listener = new PluriLockServerResponseListener() {
            @Override
            public void notify(String msg) {

            }
        };
        PluriLockConfig config = new PluriLockConfig();
        PluriLockEventManager p = PluriLockEventManager.getInstance(application, listener, "user-foo", config);
    }

    @Test
    public void testAddPluriLockEventWithNetworkCall() throws LocationServiceUnavailableException,
            NoSuchFieldException, IllegalAccessException, URISyntaxException, IOException, DeploymentException {
        Application application = createApplicationWithPermissions();
        PluriLockServerResponseListener listener = new PluriLockServerResponseListener() {
            @Override
            public void notify(String msg) {

            }
        };
        PluriLockConfig config = new PluriLockConfig();
        PluriLockEventManager p = PluriLockEventManager.getInstance(application, listener, "user-foo", config);
        PluriLockNetworkUtil network = Mockito.spy(
                new PluriLockNetworkUtil(new URI(""), Mockito.mock(Context.class), p)
        );
        Field injected = PluriLockEventManager.class.getDeclaredField("networkUtil");
        injected.setAccessible(true);
        injected.set(p, network);

        int numActions = config.getActionsPerUpload();

        // Add numActions - 1 number of PluriLockEvents.
        for (int i = 0; i < numActions - 1; i++) {
            p.addPluriLockEvent(new PElementTouchEvent(1, 1, System.currentTimeMillis(),
                    1, 1, new PointF(0, 0), new PointF(0,0), 1));
        }

        // Verify that the network call hasn't been invoked.
        Mockito.verify(network, Mockito.never()).sendEvent(Mockito.any(PluriLockPackage.class));

        // Add one more PluriLockEvents.
        p.addPluriLockEvent(new PScrollEvent(1, 1, System.currentTimeMillis(),
                PScrollEvent.scrollDirection.UP, new PointF(1,1), new PointF(1,1), 1));

        // Verify the network call has been invoked 1 time.
        Mockito.verify(network, Mockito.times(1)).sendEvent(Mockito.any(PluriLockPackage.class));
    }

    @Test
    public void testAddPluriLockEventNoNetworkCall() throws LocationServiceUnavailableException, NoSuchFieldException, IllegalAccessException, URISyntaxException, IOException, DeploymentException {
        Application application = createApplicationWithPermissions();
        PluriLockServerResponseListener listener = new PluriLockServerResponseListener() {
            @Override
            public void notify(String msg) {

            }
        };
        PluriLockConfig config = new PluriLockConfig();
        PluriLockEventManager p = PluriLockEventManager.getInstance(application, listener, "user-foo", config);
        PluriLockNetworkUtil network = Mockito.spy(
                new PluriLockNetworkUtil(new URI(""), Mockito.mock(Context.class), p)
        );
        Field injected = PluriLockEventManager.class.getDeclaredField("networkUtil");
        injected.setAccessible(true);
        injected.set(p, network);

        // Verify that the network call hasn't been invoked.
        Mockito.verify(network, Mockito.never()).sendEvent(Mockito.any(PluriLockPackage.class));

        // Add one PluriLockEvent.
        p.addPluriLockEvent(new PMonoKeyboardTouchEvent(0, 1, System.currentTimeMillis(), 0, 1));

        // Verify that the network call hasn't been invoked.
        Mockito.verify(network, Mockito.never()).sendEvent(Mockito.any(PluriLockPackage.class));
    }

    @Test
    public void testNotifyClient() throws LocationServiceUnavailableException {
        PluriLockServerResponseListener listener = new PluriLockServerResponseListener() {
            @Override
            public void notify(String msg) {

            }
        };
        PluriLockConfig config = new PluriLockConfig();
        PluriLockServerResponseListener spy = Mockito.spy(listener);
        Application application = createApplicationWithPermissions();
        PluriLockEventManager p = PluriLockEventManager.getInstance(application, spy, "user-foo", config);

        p.notifyClient("hello-world");
        Mockito.verify(spy, Mockito.atLeastOnce()).notify("hello-world");
    }

    private Application createApplicationWithPermissions() {
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
