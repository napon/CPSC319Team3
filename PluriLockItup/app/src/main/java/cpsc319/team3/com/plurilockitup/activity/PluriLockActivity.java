package cpsc319.team3.com.plurilockitup.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import java.net.URI;

import cpsc319.team3.com.biosense.PluriLockAPI;
import cpsc319.team3.com.biosense.PluriLockConfig;
import cpsc319.team3.com.biosense.exception.LocationServiceUnavailableException;
import cpsc319.team3.com.biosense.models.PlurilockServerResponse;
import cpsc319.team3.com.plurilockitup.model.Customer;

/**
 * Created by kelvinchan on 16-03-10.
 */
public abstract class PluriLockActivity extends AppCompatActivity {
    PluriLockAPI plapi;
    GestureDetector gestD;

    Double MIN_CONF_LEVEL = 0.25;
    int ACTIONS_PER_UPLOAD = 1;
    protected boolean authorized = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //sets up the Plurilock API
        plapi = PluriLockAPI.getInstance();
        if(plapi == null) {
            setupPLApi();
        }
        if(plapi != null) {
            //PLA gesture detector for touch event
            gestD = new GestureDetector(plapi.createTouchListener());
        }
    }

    /*
        One simple overriding method covers all touch event handles for PLA
        Additional overriding of this method is allowed in individual classes
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        if(gestD != null){
            gestD.onTouchEvent(ev);
            return super.dispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    private void setupPLApi() {
        Context context = getApplicationContext();

        LocalBroadcastManager.getInstance(context).registerReceiver(
                // Server Response receiver
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        PlurilockServerResponse response = intent.getParcelableExtra("msg");
                        Log.d("BroadcastReceiver", "Received broadcast: " + response.toString());
                        if(response.getConfidenceLevel() < MIN_CONF_LEVEL) {
                            Log.d("BroadcastReceiver",
                                    "Confidence level failed: " + Double.toString(response.getConfidenceLevel()));
                            authorized = false;
                            Toast.makeText(context,
                                    "Unauthorized user detected. You have been PluriLockedOut!",
                                    Toast.LENGTH_LONG).show();
                            logout();
                        }
                    }
                },
                new IntentFilter("server-response")
        );


        LocalBroadcastManager.getInstance(context).registerReceiver(
                // Server error receiver
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        String errorMessage = intent.getStringExtra("msg");
                        Log.d("BroadcastReceiver",
                                "Server-error broadcast: " + errorMessage);
                        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
                    }
                },
                new IntentFilter("server-error")
        );

        LocalBroadcastManager.getInstance(context).registerReceiver(
                // Network Connection error receiver
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        // TODO: Logout the user if can't connect after X number of attempts?
                        String errorMessage = intent.getStringExtra("msg");
                        Log.d("BroadcastReceiver",
                                "Network error broadcast: " + errorMessage);
                        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
                    }
                },
                new IntentFilter("network-error")
        );

        LocalBroadcastManager.getInstance(context).registerReceiver(
                // Server error receiver
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        String errorMessage = intent.getStringExtra("msg");
                        Log.d("BroadcatReceiver", "Location-disabled broadcast: " + errorMessage);
                        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
                    }
                },
                new IntentFilter("location-disabled")
        );

//        String id = "team3";
        String userId = Customer.getInstance().getuId();
        PluriLockConfig config = new PluriLockConfig();
        config.setActionsPerUpload(ACTIONS_PER_UPLOAD);
//        config.setUrl(URI.create("ws://echo.websocket.org/"));
//        config.setUrl(URI.create("ws://129.121.9.44:8001/")); // Mock server.
        config.setUrl(URI.create("ws://btdemo.plurilock.com:8095/")); // Plurilock server.
        config.setAppVersion(1.0);
        config.setDomain("team3");

        this.plapi = PluriLockAPI.getInstance();
        if(this.plapi == null) {
            this.plapi = PluriLockAPI.createNewSession(context, userId, config);
        }
    }

    protected void logout() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        PluriLockAPI.destroyAPISession();
        finish();
    }

}
