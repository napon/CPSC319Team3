package cpsc319.team3.com.biosense.utils;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import java.net.URI;
import java.nio.ByteBuffer;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;

import javax.websocket.CloseReason;
import javax.websocket.Extension;
import javax.websocket.MessageHandler;
import javax.websocket.RemoteEndpoint;
import javax.websocket.SendHandler;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import cpsc319.team3.com.biosense.models.PluriLockPackage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class PluriLockNetworkUtilTests {

    private URI uri;

    private Context context;
    private Application app;
    private Session userSession;

    private PluriLockNetworkUtil networkUtil;
    private PluriLockPackage testPackage;

    @Before
    public void setUp() throws Exception {
        uri = URI.create("ws://129.121.9.44:8001/");
        app = createApplicationWithPermission();
        context = app.getApplicationContext();

        userSession = new testSession();
        networkUtil = new PluriLockNetworkUtil(uri, context, Mockito.mock(OfflineDatabaseUtil.class));
        testPackage = new PluriLockPackage.PluriLockPackageBuilder().buildPackage();
    }

    @Test
    public void testNotNull() throws Exception {
        assertNotNull(app);
        assertNotNull(context);
        assertNotNull(networkUtil);
        assertNotNull(testPackage);
    }

    @Test
    public void testSend() throws Exception {

        //mock connection
        Context c = Mockito.mock(Context.class);
        Field injectedContext = PluriLockNetworkUtil.class.getDeclaredField("context");
        injectedContext.setAccessible(true);
        injectedContext.set(networkUtil, c);
        ConnectivityManager cm = Mockito.mock(ConnectivityManager.class);
        NetworkInfo network = Mockito.mock(NetworkInfo.class);
        when(c.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(cm);
        when(cm.getActiveNetworkInfo()).thenReturn(network);

        //network on
        when(network.isAvailable()).thenReturn(true);
        when(network.isConnectedOrConnecting()).thenReturn(true);
        when(network.getType()).thenReturn(ConnectivityManager.TYPE_WIFI);

        //mock session
        Field injected = PluriLockNetworkUtil.class.getDeclaredField("userSession");
        injected.setAccessible(true);
        injected.set(networkUtil, userSession);
        assertEquals(0, ((testSession) userSession).getEndpoint().getNumTimesSendTextIsCalled());
        networkUtil.sendJsonMessage(testPackage.getJSON());
        assertEquals(1, ((testSession) userSession).getEndpoint().getNumTimesSendTextIsCalled());
    }

    @Test
    public void testPreNetworkCheck() throws Exception {
        ConnectivityManager cm = Mockito.mock(ConnectivityManager.class);
        Field injected = PluriLockNetworkUtil.class.getDeclaredField("cm");
        injected.setAccessible(true);
        injected.set(networkUtil, cm);

        NetworkInfo network = Mockito.mock(NetworkInfo.class);
        when(cm.getActiveNetworkInfo()).thenReturn(network);

        assertFalse(networkUtil.networkCheck());

        when(network.isAvailable()).thenReturn(true);
        when(network.isConnectedOrConnecting()).thenReturn(true);
        when(network.getType()).thenReturn(ConnectivityManager.TYPE_WIFI);

        assertTrue(networkUtil.networkCheck());
    }

    @Test
    public void testCloseConnection() throws Exception {
        Field injected = PluriLockNetworkUtil.class.getDeclaredField("userSession");
        injected.setAccessible(true);
        injected.set(networkUtil, userSession);
        assertEquals(0, ((testSession) userSession).getNumTimesCloseIsCalled());
        networkUtil.closeConnection();
        assertEquals(1, ((testSession) userSession).getNumTimesCloseIsCalled());
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

    private class testSession implements Session {

        int numTimesCloseIsCalled = 0;

        testEndpoint endPoint = new testEndpoint();

        public testEndpoint getEndpoint() {
            return endPoint;
        }

        public int getNumTimesCloseIsCalled() {
            return numTimesCloseIsCalled;
        }

        @Override
        public WebSocketContainer getContainer() {
            return null;
        }

        @Override
        public void addMessageHandler(MessageHandler handler) throws IllegalStateException {

        }

        @Override
        public Set<MessageHandler> getMessageHandlers() {
            return null;
        }

        @Override
        public void removeMessageHandler(MessageHandler handler) {

        }

        @Override
        public String getProtocolVersion() {
            return null;
        }

        @Override
        public String getNegotiatedSubprotocol() {
            return null;
        }

        @Override
        public List<Extension> getNegotiatedExtensions() {
            return null;
        }

        @Override
        public boolean isSecure() {
            return false;
        }

        @Override
        public boolean isOpen() {
            return false;
        }

        @Override
        public long getMaxIdleTimeout() {
            return 0;
        }

        @Override
        public void setMaxIdleTimeout(long milliseconds) {

        }

        @Override
        public void setMaxBinaryMessageBufferSize(int length) {

        }

        @Override
        public int getMaxBinaryMessageBufferSize() {
            return 0;
        }

        @Override
        public void setMaxTextMessageBufferSize(int length) {

        }

        @Override
        public int getMaxTextMessageBufferSize() {
            return 0;
        }

        @Override
        public RemoteEndpoint.Async getAsyncRemote() {
            return endPoint;
        }

        @Override
        public RemoteEndpoint.Basic getBasicRemote() {
            return null;
        }

        @Override
        public String getId() {
            return null;
        }

        @Override
        public void close() throws IOException {
            numTimesCloseIsCalled++;
        }

        @Override
        public void close(CloseReason closeReason) throws IOException {

        }

        @Override
        public URI getRequestURI() {
            return null;
        }

        @Override
        public Map<String, List<String>> getRequestParameterMap() {
            return null;
        }

        @Override
        public String getQueryString() {
            return null;
        }

        @Override
        public Map<String, String> getPathParameters() {
            return null;
        }

        @Override
        public Map<String, Object> getUserProperties() {
            return null;
        }

        @Override
        public Principal getUserPrincipal() {
            return null;
        }

        @Override
        public Set<Session> getOpenSessions() {
            return null;
        }

    }

    private class testEndpoint implements RemoteEndpoint.Async {

        private int numCall = 0;

        public int getNumTimesSendTextIsCalled() {
            return numCall;
        }

        @Override
        public long getSendTimeout() {
            return 0;
        }

        @Override
        public void setSendTimeout(long timeoutmillis) {

        }

        @Override
        public void sendText(String text, SendHandler handler) {

        }

        @Override
        public Future<Void> sendText(String text) {
            numCall++;
            return null;
        }

        @Override
        public Future<Void> sendBinary(ByteBuffer data) {
            return null;
        }

        @Override
        public void sendBinary(ByteBuffer data, SendHandler handler) {

        }

        @Override
        public Future<Void> sendObject(Object data) {
            return null;
        }

        @Override
        public void sendObject(Object data, SendHandler handler) {

        }

        @Override
        public void setBatchingAllowed(boolean allowed) throws IOException {

        }

        @Override
        public boolean getBatchingAllowed() {
            return false;
        }

        @Override
        public void flushBatch() throws IOException {

        }

        @Override
        public void sendPing(ByteBuffer applicationData) throws IOException, IllegalArgumentException {

        }

        @Override
        public void sendPong(ByteBuffer applicationData) throws IOException, IllegalArgumentException {

        }
    }
}
