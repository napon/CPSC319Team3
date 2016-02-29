package cpsc319.team3.com.biosense.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.json.Json;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import cpsc319.team3.com.biosense.models.PluriLockPackage;

/**
 * This class is responsible for:
 * - Establishing connection with the PluriLock Server
 * - Sending PluriLockEvent information using the protocol specified by PluriLock
 * - Establishing connection with the OfflineDatabaseUtil
 * - Storing PluriLockEvent information in the local database when there is no network connection
 * - Fetching data from the OfflineDatabaseUtil
 * - Reacting to changes in the network connectivity of the client's device
 * - Anonymizing client's data before sending it to the server
 * - Parsing the response from the server and passing it to the corresponding listener
 *
 * See the UML Diagram for more implementation details.
 */

@ClientEndpoint
public class PluriLockNetworkUtil {

//    /**
//     * Sends data to PluriLock server and returns a response string
//     * @param data JSONObject including client's hardware information and PluriLockEvents
//     * @return response string from Plurilock server containing confidence level
//     */
//    public String sendEvent(JSONObject data) {
//
//    }

    Session userSession = null;
    private MessageHandler messageHandler;
    private Context context;
    private WebSocketContainer container;
    private URI endpointURI;

    public PluriLockNetworkUtil(URI endpointURI) {
        this.container = ContainerProvider.getWebSocketContainer();
        this.endpointURI = endpointURI;
        if(endpointURI != null)
            initiateConnection(endpointURI);
        else
            initiateConnection();
        //this.context = c;
    }

    private void initiateConnection() {
        String uri = "ws://localhost:8080" + "/websocket";
        try {
            container.connectToServer(this, new URI(uri));
        }
        catch (URISyntaxException e) {
            System.out.println(e.getMessage());
        }
        catch (DeploymentException e) {
            System.out.println(e.getMessage());
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    private void initiateConnection(URI endpointURI) {
        try {
            if(endpointURI != null)
                container.connectToServer(this, endpointURI);
        }
        catch (DeploymentException e){
            System.out.println(e.getMessage());
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

//    public static void main(String[] args) throws Exception {
//        PluriLockNetworkUtil test = new PluriLockNetworkUtil(new URI("wss://129.121.9.44:8001/"));
//        test.sendMessage("hi");
//    }


    /**
     * Create a json representation.
     *
     * @param message
     * @return
     */
    private static String getMessage(String message) {
        return Json.createObjectBuilder()
                .add("user", "bot")
                .add("message", message)
                .build()
                .toString();
    }

    /**
     * Callback hook for Connection open events.
     *
     * @param userSession the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession) {
        System.out.println("opening websocket");
        this.userSession = userSession;
    }

    /**
     * Callback hook for Connection close events.
     *
     * @param userSession the userSession which is getting closed.
     * @param reason the reason for connection close
     */
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("closing websocket");
        this.userSession = null;
    }

    /**
     * Callback hook for Message Events. This method will be invoked when a client sends a message.
     *
     * @param message The text message
     */
    @OnMessage
    public void onMessage(String message) {
        if (this.messageHandler != null) {
//            try {
            this.messageHandler.handleMessage(message);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        }
    }

    public boolean preNetworkCheck(){
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //boolean flag = false;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (!activeNetwork.isAvailable()) {
            System.out.println("Network connectivity is not possible.");
            return false;
        } else {
            if (activeNetwork == null || !activeNetwork.isConnectedOrConnecting()) {
                System.out.println("You're not connected to the Internet.");
                return false;
            } else if (activeNetwork.getType() != ConnectivityManager.TYPE_WIFI) {
                System.out.println("You're not connected to WIFI.");
                return false;
            }
        }
        return true;
    }


    /**
     * register message handler
     *
     * @param msgHandler
     */
    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }

    /**
     * Send a message.
     *
     * @param message
     */
    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }

    /**
     * Message handler.
     *
     */
    public static interface MessageHandler {

        public void handleMessage(String message);
    }

    /**
     * Sends a PluriLockPackage to the server when there is internet connectivity
     * or stores it in the local database when there is no network connection.
     * @param pluriLockPackage
     */
    public void sendEvent(PluriLockPackage pluriLockPackage) {
        // TODO
    }


    public static void main(String[] args) throws Exception {
        final PluriLockNetworkUtil clientEndPoint = new PluriLockNetworkUtil(new URI("ws://129.121.9.44:8001/"));
        clientEndPoint.addMessageHandler(new PluriLockNetworkUtil.MessageHandler() {
            public void handleMessage(String message) {
                System.out.println(message);
            }
        });

        int countMessages = 0;
        while (countMessages < 3) {
            clientEndPoint.sendMessage("hello");
            countMessages++;
            Thread.sleep(30000);
        }
    }

}
