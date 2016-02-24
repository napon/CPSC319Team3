
package cpsc319.team3.com.biosense;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import cpsc319.team3.com.biosense.exception.LocationServiceUnavailableException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(RobolectricTestRunner.class)
public class PluriLockAPITests {

    private PluriLockServerResponseListener baseListener;
    private PluriLockConfig baseConfig;
    Context testContext;
    @Before
    public void setup() {
        testContext = RuntimeEnvironment.application.getApplicationContext();
        baseConfig = new PluriLockConfig();
        baseListener = new PluriLockServerResponseListener() {
            @Override
            public void notify(String msg) {
                //do nothing.
            }
        };
    }

//===============BASIC BUILD TESTS=====================
   @Test
    public void noBuiltAPIShouldBeNull() throws Exception {
        assertTrue(PluriLockAPI.getInstance() == null);
    }

    @Test
    public void standardBuildTestShouldNotThrowErrors(){

        try{
            PluriLockAPI.createNewSession(this.testContext,this.baseListener,"TEST",this.baseConfig);
        }
        catch(Exception e){
            fail();
        }
    }

    @Test
    public void afterBuildShouldNotBeNull() throws LocationServiceUnavailableException {
        assertTrue(PluriLockAPI.getInstance() == null);
        PluriLockAPI.createNewSession(this.testContext, this.baseListener, "TEST", this.baseConfig);
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
//===============END OF BASIC BUILD TESTS=====================

}
