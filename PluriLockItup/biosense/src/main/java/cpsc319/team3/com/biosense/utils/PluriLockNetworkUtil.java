package cpsc319.team3.com.biosense.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.text.format.Formatter;
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


    private ConnectivityManager cm;

    public PluriLockNetworkUtil(URI endpointURI, Context context) {
        Log.d(TAG, "PluriLockNetworkUtil constructor");

        this.endpointURI = endpointURI;
        this.context = context;
        this.config = ClientEndpointConfig.Builder.create().build();
        this.client = ClientManager.createClient();

        this.offlineDatabaseUtil = new OfflineDatabaseUtil(context);

        cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (preNetworkCheck()) {
            initiateConnection();
        }

        BroadcastReceiver connectionStatusReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (preNetworkCheck()) {
                    Log.d(TAG, "Network connection reestablished, attempting reconnection to server...");
                    initiateConnection();
                    sendCachedEvents();
                } else {
                    Log.d(TAG, "Connection lost!!");
                    closeConnection();
                }
            }
        };

        IntentFilter connectionFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        context.registerReceiver(connectionStatusReceiver, connectionFilter);
    }



    public void initiateConnection() {
        Log.d(TAG, "Initiating new connection session to server...");
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
                    broadcastNetworkError(e.toString());
                }
                return null;
            }
        };

        Log.d(TAG, "Executing connectionTask AsyncTask..");
        connectionTask.execute();
        Log.d(TAG, "connecting..");
    }

    public boolean preNetworkCheck() {
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null || !activeNetwork.isAvailable()) {
            broadcastNetworkError("Network connectivity is not possible!");
            return false;
        } else if (!activeNetwork.isConnectedOrConnecting()) {
            broadcastNetworkError("You're not connected to the Internet!");
            return false;
        } else if (activeNetwork.getType() != ConnectivityManager.TYPE_WIFI) {
            broadcastNetworkError("You're not connected to WIFI.");
            return false;
        }

        return true;
    }

    private void broadcastNetworkError(String s) {
        String msg = "Could connect to PluriLock Server! " + s;
        Log.e(TAG, msg);

        // Broadcast the error
        Intent intent = new Intent("network-error");
        intent.putExtra("msg", msg);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    void sendJsonMessage(JSONObject event) {
        final String message = event.toString();
        Log.d("PluriLockNetworkUtil", "Client says: " + message);
        final AsyncTask<Void, Void, Void> sendMessageTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Log.d(TAG, "Sending message in AsyncTask...");
                userSession.getAsyncRemote().sendText(message);
                return null;
            }
        };

        Log.d(TAG, "Executing sendJsonMessage AsyncTask...");
        try {
            sendMessageTask.execute();
        } catch (Exception e) {
            Log.e(TAG, "Something's wrong... saving the event to cache..." + e.toString());
            cacheEvent(event);
        }
    }

    private void acceptMessage(String message) {
        Log.d("PluriLockNetworkUtil", "Server says: " + message);
        try {
            PlurilockServerResponse response = PlurilockServerResponse.parse(message);
            Intent intent = new Intent("server-response");
            intent.putExtra("msg", response);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        } catch (JSONException e) {
            Log.d("PluriLockNetworkUtil", "Could not parse JSON message! " + message);

            // Broadcast the error
            Intent intent = new Intent("server-error");
            intent.putExtra("msg", "Could not parse response from server! " + message);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        }
    }

    public void closeConnection() {
        Log.d(TAG, "Closing server connection session...");
        try {
            if (userSession != null) {
                userSession.close();
                userSession = null;
            }
        } catch (IOException e) {
            Log.e(TAG, "Unexpected error when closing connection", e);
        }
    }

    /**
     * Sends a PluriLockPackage to the server when there is internet connectivity
     * or stores it in the local database when there is no network connection.
     * Sent event relies on a network connection to the PluriLock Servers.
     * If the device is offline, the event is cached on the device. On next
     * send event, if the network is connected, it checks all backlogged events and sends them.
     *
     * @param pluriLockPackage
     */
    public void sendEvent(PluriLockPackage pluriLockPackage)  {
        JSONObject event = pluriLockPackage.getJSON();
        if (preNetworkCheck()) {
            Log.d(TAG, "sendEvent");
            //send any cached pending events
            sendCachedEvents();
            //send new event
            sendJsonMessage(event);
        } else {
            cacheEvent(event);
        }
    }

    private void cacheEvent(JSONObject event) {
        Log.e(TAG, "noEventSent - User offline");
        if (offlineDatabaseUtil.save(event)) {
            Log.d(TAG, "Cached event!");
        } else {
            Log.e(TAG, "Cache not saved!");
        }
    }

    private void sendCachedEvents() {
        Log.d(TAG, "Looking for cached events...");
        List<JSONObject> pendingEvents = offlineDatabaseUtil.loadPending();
        Log.i(TAG, "Found " + pendingEvents.size() + " cached events, attempting to resend...");
        for (JSONObject event : pendingEvents) {
            sendJsonMessage(event);
        }
    }

    /**
     * Returns the current IP Address of the device.
     * @param c Context object.
     * @return IP Address string of the device.
     */
    public static String getIPAddress(Context c) {
        WifiManager wm = (WifiManager) c.getSystemService(c.WIFI_SERVICE);
        return Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
    }
}
