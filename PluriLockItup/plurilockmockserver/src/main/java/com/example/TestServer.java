









//package com.example;
//
//import java.io.IOException;
//import javax.websocket.OnClose;
//import javax.websocket.OnMessage;
//import javax.websocket.OnOpen;
//import javax.websocket.Session;
//import javax.websocket.server.ServerEndpoint;
//
//@ServerEndpoint("/websocket")
//public class TestServer {
//
//    public static void main(String[] args) {
//        TestServer server = new TestServer();
//        try {
//            server.onMessage("hi", new Session());
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//    public TestServer() {
//
//    }
//
//    @OnMessage
//    public void onMessage(String message, Session session) throws IOException,
//            InterruptedException {
//        System.out.println("User input: " + message);
//        session.getBasicRemote().sendText("Hello world " + message);
//    }
//
//    @OnOpen
//    public void onOpen() {
//        System.out.println("Client connected");
//    }
//
//    @OnClose
//    public void onClose() {
//        System.out.println("Connection closed");
//    }
//}