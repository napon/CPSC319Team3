package cpsc319.team3.com.biosense.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.glassfish.tyrus.client.ClientManager;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

import cpsc319.team3.com.biosense.models.PluriLockPackage;
import cpsc319.team3.com.biosense.models.PlurilockServerResponse;


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
    private static final String TAG = "PluriLockNetworkUtil";

    private Session userSession;
    private OfflineDatabaseUtil offlineDatabaseUtil;

    private URI endpointURI;
    private Context context;

    private ClientManager client;
    private ClientEndpointConfig config;

    public PluriLockNetworkUtil(URI endpointURI, Context context) {
        Log.d(TAG, "PluriLockNetworkUtil constructor");

        this.endpointURI = endpointURI;
        this.context = context;
        this.config = ClientEndpointConfig.Builder.create().build();
        this.client = ClientManager.createClient();

        this.offlineDatabaseUtil = new OfflineDatabaseUtil(context);

        initiateConnection();
    }

    public void initiateConnection() {
        Log.d(TAG, "initiateConnection");
        final MessageHandler.Whole<String> messageHandler = new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                Log.d(TAG, "onMessage: " + message);
                PluriLockNetworkUtil.this.acceptMessage(message);
            }
        };

        final Endpoint endpoint = new Endpoint() {
            @Override
            public void onOpen(Session session, EndpointConfig config) {
                Log.d(TAG, "onOpen");
                session.addMessageHandler(messageHandler);
                PluriLockNetworkUtil.this.userSession = session;
            }
        };

        final AsyncTask<Void, Void, Void> connectionTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Log.d(TAG, "Connecting to client in AsyncTask..");
                    client.connectToServer(endpoint, config, endpointURI);
                } catch (Exception e) {
                    // TODO: Handle this outside the async
                }
                return null;
            }
        };

        Log.d(TAG, "Executing connectionTask AsyncTask..");
        connectionTask.execute();
        Log.d(TAG, "connecting..");
    }

    public boolean preNetworkCheck(){
        // TODO: Make sure this method is called somewhere
        // TODO: Propagate an exceptions upwards for the client to handle if we're offline?

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null || !activeNetwork.isAvailable()) {
            System.out.println("Network connectivity is not possible.");
            return false;
        } else {
            if (!activeNetwork.isConnectedOrConnecting()) {
                System.out.println("You're not connected to the Internet.");
                return false;
            } else if (activeNetwork.getType() != ConnectivityManager.TYPE_WIFI) {
                System.out.println("You're not connected to WIFI.");
                return false;
            }
        }
        return true;
    }

    void sendMessage(final String message) throws IOException, DeploymentException {
        Log.d(this.getClass().getName(), "Client says: " + message);
        final AsyncTask<Void, Void, Void> sendMessageTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Log.d(TAG, "Sending message in AsyncTask...");
                userSession.getAsyncRemote().sendText(message);
                return null;
            }
        };

        Log.d(TAG, "Executing sendMessage AsyncTask...");
        sendMessageTask.execute();
    }

    private void acceptMessage(String message) {
        Log.d(this.getClass().getName(), "Server says: " + message);
        try {
            PlurilockServerResponse response = PlurilockServerResponse.fromJsonString(message);
            Intent intent = new Intent("server-response");
            intent.putExtra("msg", response);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        } catch (JSONException e) {
            Log.d(this.getClass().getName(), "Could not parse JSON message! " + message);

            // Broadcast the error
            Intent intent = new Intent("server-error");
            intent.putExtra("msg", "Could not parse response from server! " + message);
        }
    }

    public void closeConnection() throws IOException {
        Log.d(TAG, "closeConnection");
        userSession.close();
        userSession = null;
    }

    /**
     * Sends a PluriLockPackage to the server when there is internet connectivity
     * or stores it in the local database when there is no network connection.
     * Sent event relies on a network connection to the PluriLock Servers.
     * If the device is offline, the event is cached on the device. On next
     * send event, if the network is connected, it checks all backlogged events and sends them.
     * @param pluriLockPackage
     */
    public void sendEvent(PluriLockPackage pluriLockPackage) throws IOException, DeploymentException {
        if(preNetworkCheck()) {
            Log.d(TAG, "sendEvent");
            //send any cached pending events
            List<JSONObject> pendingEvents = offlineDatabaseUtil.loadPending();
            for (JSONObject event : pendingEvents) {
                sendMessage(event.toString());
            }
            //send new event
            sendMessage(pluriLockPackage.getJSON().toString());
        }
        else{
            Log.e(TAG, "noEventSent - User offline");
            if(offlineDatabaseUtil.save(pluriLockPackage.getJSON())){
                Log.d(TAG, "cache event");
            }
            else{
                Log.e(TAG, "cache not saved");
            }
        }
    }
}
