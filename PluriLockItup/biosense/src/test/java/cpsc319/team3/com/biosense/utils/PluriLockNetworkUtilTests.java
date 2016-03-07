package cpsc319.team3.com.biosense.utils;

import android.content.Context;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import cpsc319.team3.com.biosense.PluriLockEventManager;

import static org.junit.Assert.assertEquals;

public class PluriLockNetworkUtilTests {

//    @Test
//    public void echoTest() throws Exception {
//        final List<String> responses = new ArrayList();
//
//        PluriLockEventManager eventManager = Mockito.mock(PluriLockEventManager.class);
//        Mockito.doAnswer(new Answer() {
//            @Override
//            public Object answer(InvocationOnMock invocation) throws Throwable {
//                String arg = invocation.getArgumentAt(0, String.class);
//                System.out.println(arg);
//                responses.add(arg);
//                return null;
//            }
//        }).when(eventManager).notifyClient(Mockito.anyString());
//
//        PluriLockNetworkUtil clientEndPoint = new PluriLockNetworkUtil(
//                new URI("ws://napontaratan.com:8001"),
//                Mockito.mock(Context.class),
//                eventManager
//        );
//
//
//        clientEndPoint.sendMessage("hello");
//        Thread.sleep(3000);
//        clientEndPoint.sendMessage("hi");
//        Thread.sleep(3000);
//        clientEndPoint.sendMessage("hey");
//        Thread.sleep(3000);
//
//        assertEquals(responses.get(0), "hello");
//        assertEquals(responses.get(1), "hi");
//        assertEquals(responses.get(2), "hey");
//    }

//    @Test
//    public void sampleTest() throws Exception {
//        final List<String> responses = new ArrayList();
//
//        PluriLockEventManager eventManager = Mockito.mock(PluriLockEventManager.class);
//        Mockito.doAnswer(new Answer() {
//            @Override
//            public Object answer(InvocationOnMock invocation) throws Throwable {
//                String arg = invocation.getArgumentAt(0, String.class);
//                System.out.println(arg);
//                responses.add(arg);
//                return null;
//            }
//        }).when(eventManager).notifyClient(Mockito.anyString());
//
//        PluriLockNetworkUtil clientEndPoint = new PluriLockNetworkUtil(
//                new URI("ws://129.121.9.44:8001/"),
//                Mockito.mock(Context.class),
//                eventManager
//        );
//
//
//        clientEndPoint.sendMessage("foo");
//        Thread.sleep(3000);
//        assertEquals("{\"confidenceLevel\":0.1234}", responses.get(0));
//    }
}
