package cpsc319.team3.com.biosense.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.glassfish.tyrus.client.ClientManager;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

import cpsc319.team3.com.biosense.PluriLockEventManager;
import cpsc319.team3.com.biosense.exception.NetworkServiceUnavailableException;
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
    private static final String TAG = "PluriLockNetworkUtil";

    private Session userSession;

    private URI endpointURI;
    private Context context;
    private PluriLockEventManager eventManager;

    private ClientManager client;
    private ClientEndpointConfig config;

    public PluriLockNetworkUtil(URI endpointURI, Context context, PluriLockEventManager eventManager)
            throws NetworkServiceUnavailableException {
        preNetworkCheck();
        Log.d(TAG, "PluriLockNetworkUtil constructor");

        this.endpointURI = endpointURI;
        this.context = context;
        this.config = ClientEndpointConfig.Builder.create().build();
        this.client = ClientManager.createClient();
        this.eventManager = eventManager;
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

    public boolean preNetworkCheck() throws NetworkServiceUnavailableException {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (!activeNetwork.isAvailable()) {
            System.out.println("Network connectivity is not possible.");
            return false;
        } else {
            if (activeNetwork == null || !activeNetwork.isConnectedOrConnecting()) {
                throw new NetworkServiceUnavailableException("You're not connected to the Internet!");
            } else if (activeNetwork.getType() != ConnectivityManager.TYPE_WIFI) {
               throw new NetworkServiceUnavailableException("You're not connected to WIFI.");
            }
        }
        return true;
    }

    void sendMessage(final String message) throws IOException, DeploymentException {
        Log.d(TAG, "sendMessage");
//        if (userSession == null) {
//            initiateConnection();
//        }
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
        Log.d(TAG, "acceptMessage");
        // TODO: Process this message, and package it into some sort of object
        Log.d(this.getClass().getName(), "Server says: " + message);
        Intent intent = new Intent("server-response");
        intent.putExtra("msg", message);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public void closeConnection() throws IOException {
        Log.d(TAG, "closeConnection");
        userSession.close();
        userSession = null;
    }

    /**
     * Sends a PluriLockPackage to the server when there is internet connectivity
     * or stores it in the local database when there is no network connection.
     * @param pluriLockPackage
     */
    public void sendEvent(PluriLockPackage pluriLockPackage) throws IOException, DeploymentException {
        Log.d(TAG, "sendEvent");
        sendMessage(pluriLockPackage.getJSON().toString());
    }
}
