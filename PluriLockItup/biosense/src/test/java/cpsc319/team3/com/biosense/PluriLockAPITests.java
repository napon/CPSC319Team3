
package cpsc319.team3.com.biosense;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.location.LocationManager;
import android.provider.Settings;

import junit.framework.Assert;

import org.junit.After;
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

import java.net.URI;

import cpsc319.team3.com.biosense.exception.LocationServiceUnavailableException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(RobolectricTestRunner.class)
public class PluriLockAPITests {

    private PluriLockConfig baseConfig;
    Context testContext;

    //=============================HELPER FUNCTIONS =======================
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

    //============================= SETUP and TEARDOWN ================================
    @Before
    public void setup() {
        testContext = createApplicationWithPermission().getApplicationContext();
        baseConfig = new PluriLockConfig();
    }

    @After
    public void tearDown(){
        PluriLockAPI.destroyAPISession();
    }

//===============BASIC BUILD TESTS=====================
   @Test
    public void noBuiltAPIShouldBeNull() throws Exception {
        assertTrue(PluriLockAPI.getInstance() == null);
    }

    @Test
    public void standardBuildTestShouldNotThrowErrors(){

        try{
            PluriLockAPI.createNewSession(this.testContext, "TEST", this.baseConfig);
        }
        catch(Exception e){
            fail();
        }
    }

    @Test
    public void afterBuildShouldNotBeNull() throws LocationServiceUnavailableException {
        assertTrue(PluriLockAPI.getInstance() == null);
        PluriLockAPI.createNewSession(this.testContext, "TEST", this.baseConfig);
        assertTrue(PluriLockAPI.getInstance() != null);
    }

    @Test
    public void destroyingShouldNotThrowException() throws LocationServiceUnavailableException {
        afterBuildShouldNotBeNull();
        try{
            PluriLockAPI.destroyAPISession();

        } catch (Exception e) {
            fail("Threw an exception " + e.getClass());
        }
    }

    @Test
    public void afterDestroyShouldBeNull() throws LocationServiceUnavailableException{
        afterBuildShouldNotBeNull();
        assertTrue(PluriLockAPI.getInstance() != null);
        PluriLockAPI.destroyAPISession();
        assertTrue(PluriLockAPI.getInstance() == null);
    }
    @Test
    public void buildingAgainShouldMakeDifferent() throws LocationServiceUnavailableException{
        PluriLockAPI pluri1 = PluriLockAPI.createNewSession(this.testContext, "TEST", this.baseConfig);
        PluriLockAPI pluri2 = PluriLockAPI.createNewSession(this.testContext, "TEST2", this.baseConfig);
        assertTrue(pluri1 != pluri2);
    }
//===============END OF BASIC BUILD TESTS=====================



    @Test
    public void emptyUserIdTest() throws Exception {
        PluriLockAPI api = PluriLockAPI.createNewSession(testContext, "", baseConfig);
    }

    @Test
    public void longUserIdTest() throws Exception {
        // Strings can be up to Integer.MAX_VALUE, but this is practically impossible to test
        int stringLength = 1000;

        StringBuilder builder = new StringBuilder(stringLength);
        for (int i = 0; i < stringLength; i++) {
            builder.append(".");
        }

        String id = builder.toString();
        PluriLockAPI api = PluriLockAPI.createNewSession(testContext, id, baseConfig);
    }

    @Test
    public void allOptionsDefaultTest() throws Exception {
        PluriLockConfig config = new PluriLockConfig();

        PluriLockAPI api = PluriLockAPI.createNewSession(testContext, "", config);
    }

    @Test
    public void allOptionsSetTest() throws Exception {
        PluriLockConfig config = new PluriLockConfig();
        config.setActionsPerUpload(0);
        config.setAppVersion(0);
        config.setDomain("");
        config.setUrl(new URI(""));

        PluriLockAPI api = PluriLockAPI.createNewSession(testContext, "", config);
    }

    @Test
    public void nullInstanceTest() throws Exception {
        PluriLockAPI api = PluriLockAPI.getInstance();
        Assert.assertNull(api);
    }

    @Test
    public void sameInstanceTest() throws Exception {
        PluriLockAPI api = PluriLockAPI.createNewSession(testContext, "", baseConfig);
        PluriLockAPI instance = PluriLockAPI.getInstance();

        Assert.assertEquals(api, instance);
    }

    @Test
    public void getListenersTest() throws Exception {
        PluriLockAPI api = PluriLockAPI.createNewSession(testContext, "", baseConfig);
        PluriLockKeyListener key = api.createKeyListener();
        PluriLockTouchListener touch = api.createTouchListener();

        Assert.assertNotNull(key);
        Assert.assertNotNull(touch);
    }
}
