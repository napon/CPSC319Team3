package cpsc319.team3.com.biosense.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.glassfish.tyrus.client.ClientManager;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

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
 */


public class PluriLockNetworkUtil {
    private Session userSession;
    private Context context;
    private URI endpointURI;

    private ClientManager client;
    private ClientEndpointConfig config;

    public PluriLockNetworkUtil(URI endpointURI, Context context) {
        this.endpointURI = endpointURI;
        this.context = context;
        this.config = ClientEndpointConfig.Builder.create().build();
        this.client = ClientManager.createClient();
    }

    private void initiateConnection() throws DeploymentException, IOException {
        final MessageHandler.Whole<String> messageHandler = new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                PluriLockNetworkUtil.this.acceptMessage(message);
            }
        };

        Endpoint endpoint = new Endpoint() {
            @Override
            public void onOpen(Session session, EndpointConfig config) {
                session.addMessageHandler(messageHandler);
                PluriLockNetworkUtil.this.userSession = session;
            }
        };

        client.connectToServer(endpoint, config, endpointURI);
    }

    public boolean preNetworkCheck(){
        // TODO: Make sure this method is called
        // TODO: Propagate the exception upwards for the client to handle

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
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

    private void sendMessage(String message) throws IOException {
        System.out.println("Client says: " + message);
        if (userSession != null) {
            userSession.getBasicRemote().sendText(message);
        } else {
            throw new RuntimeException("foo"); // TODO: Find the correct exception to use
        }
    }

    private void acceptMessage(String message) {
        // TODO: Process this message, and callback the app
        System.out.println("Server says: " + message);
    }

    private void closeConnection() throws IOException {
        userSession.close();
        userSession = null;
    }

    /**
     * Sends a PluriLockPackage to the server when there is internet connectivity
     * or stores it in the local database when there is no network connection.
     * @param pluriLockPackage
     */
    public void sendEvent(PluriLockPackage pluriLockPackage) {
        // TODO: Process this package and call sendMessage()
    }


    // DEBUG
    public static void main(String[] args) throws Exception {
        PluriLockNetworkUtil net = new PluriLockNetworkUtil(new URI("ws://echo.websocket.org"), null);
        net.initiateConnection();
        net.sendMessage("hi");
        net.sendMessage("hello?");
        net.sendMessage("is anyone there?");
        net.sendMessage("HEEELLLLLOOOO!!!");
        net.sendMessage("ok bye");
        net.closeConnection();
    }
}
