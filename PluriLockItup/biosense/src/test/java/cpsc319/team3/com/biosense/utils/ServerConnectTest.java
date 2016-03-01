package cpsc319.team3.com.biosense.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.view.WindowManager;

import org.glassfish.tyrus.core.DataFrame;
import org.glassfish.tyrus.core.ProtocolHandler;
import org.glassfish.tyrus.core.TyrusWebSocket;
import org.glassfish.tyrus.core.WebSocket;
import org.glassfish.tyrus.core.WebSocketListener;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.util.ReflectionHelpers;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.Future;

import javax.websocket.CloseReason;
import javax.websocket.SendHandler;

import cpsc319.team3.com.biosense.PhoneDataManager;
import cpsc319.team3.com.biosense.PluriLockEventManager;
import cpsc319.team3.com.biosense.utils.PluriLockNetworkUtil;

import static org.junit.Assert.*;

/**
 * Unit tests for methods in PhoneDataManager.java
 */

public class ServerConnectTest {

   /* @Test
    public void echoTest() throws Exception {
        final List<String> responses = new ArrayList();

        PluriLockEventManager eventManager = Mockito.mock(PluriLockEventManager.class);
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String arg = invocation.getArgumentAt(0, String.class);
                System.out.println(arg);
                responses.add(arg);
                return null;
            }
        }).when(eventManager).notifyClient(Mockito.anyString());

        PluriLockNetworkUtil clientEndPoint = new PluriLockNetworkUtil(
                new URI("ws://echo.websocket.org/"),
                Mockito.mock(Context.class),
                eventManager
        );


        clientEndPoint.sendMessage("hello");
        Thread.sleep(3000);
        clientEndPoint.sendMessage("hi");
        Thread.sleep(3000);
        clientEndPoint.sendMessage("hey");
        Thread.sleep(3000);

        assertEquals(responses.get(0), "hello");
        assertEquals(responses.get(1), "hi");
        assertEquals(responses.get(2), "hey");
    }*/

    @Test
    public void connectToPlurilockWSserviceDocumentedPort() throws IOException{
    Socket test = null;
        try{
            test = new Socket("184.66.140.77", 8095);
        }
        catch(IOException e){
            e.printStackTrace();
            fail("threw IOException");
        }

        BufferedReader stdIn =
                new BufferedReader(
                        new InputStreamReader(test.getInputStream()));
        String read;
        while((read = stdIn.readLine())!=null){
            System.out.println(read);
        }
        assertTrue(true); //if it gets here it worked
    }

    @Test
    public void connectToPlurilockWSserviceExamplePort() throws IOException{
        Socket test = null;
        try{
            test = new Socket("184.66.140.77", 8080);
        }
        catch(IOException e){
            e.printStackTrace();
            fail("threw IOException");
        }

        BufferedReader stdIn =
                new BufferedReader(
                        new InputStreamReader(test.getInputStream()));
        String read;
        while((read = stdIn.readLine())!=null){
            System.out.println(read);
        }
        assertTrue(true); //if it gets here it worked
    }

    @Test
    public void connectToPlurilockRESTserviceNoPath() throws IOException{
        Socket test = null;
        try{
            test = new Socket("184.66.140.77", 8090);
        }
        catch(IOException e){
            e.printStackTrace();
            fail("threw IOException");
        }

        assertTrue(true); //if it gets here it worked
    }

    @Test
    public void connectToPlurilockRESTserviceWithPathNoUser() throws IOException {
        Socket test = null;
        try{
            test = new Socket("184.66.140.77/api/users", 8090);
        }
        catch(IOException e){
            e.printStackTrace();
            fail("threw IOException");
        }
        assertTrue(true); //if it gets here it worked
    }

    @Test
    public void connectToPlurilockRESTserviceWithPathWithUser() throws IOException {
        Socket test = null;
        try{
            test = new Socket("184.66.140.77/api/users/ios_bassam_uvic", 8090);
        }

        catch(IOException e){
            e.printStackTrace();
            fail("threw IOException");
        }
        assertTrue(true); //if it gets here it worked
    }


    @Test
    public void connectToWebSocketEcho() throws IOException {
        Socket test = null;
        try{
            test =  new Socket("echo.websocket.org", 80);
        }

        catch(IOException e){
            e.printStackTrace();
            fail("threw IOException");
        }
        assertTrue(true); //if it gets here it worked
    }

}