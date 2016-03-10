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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;

import cpsc319.team3.com.biosense.PluriLockAPI;
import cpsc319.team3.com.biosense.PluriLockConfig;
import cpsc319.team3.com.biosense.PluriLockTouchListener;
import cpsc319.team3.com.biosense.exception.LocationServiceUnavailableException;
import cpsc319.team3.com.biosense.models.PlurilockServerResponse;
import cpsc319.team3.com.plurilockitup.R;
import cpsc319.team3.com.plurilockitup.model.Customer;
import cpsc319.team3.com.plurilockitup.model.Utils;

public class MainActivity extends AppCompatActivity {
    Customer customer;
    PluriLockAPI plapi;

    String[] dayAcctList;
    String[] creditAcctList;
    TableLayout dayAcctTable;
    TableLayout creditAcctTable;

    GestureDetector gest;
    boolean authorized = true;
    Double MIN_CONF_LEVEL = 0.25;
    int ACTIONS_PER_UPLOAD = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //grab the table refs from xml view
        dayAcctTable = (TableLayout) findViewById(R.id.dayAcctTable);
        creditAcctTable = (TableLayout) findViewById(R.id.creditTable);

        //will populate balance data as needed
        customer = Customer.getInstance();
        dayAcctList = customer.getDayAccountNames();
        creditAcctList = customer.getCreditAcctNames();

        //Set up PluriLock
        setupPLApi();
        // add day account table rows
        for(int i = 0; i < dayAcctList.length; i++){
            final int j = i; //click handler needs static int
            View row = getLayoutInflater().inflate(R.layout.acct_row, null);

            //set account name
            ((TextView) row.findViewById(R.id.acct_name)).setText(dayAcctList[i]);

            //set account balance
            ((TextView) row.findViewById(R.id.balance)).setText(customer.getBalanceString(dayAcctList[i]));

            if(plapi != null){
                final PluriLockTouchListener plTouch = plapi.createTouchListener();
                row.setOnTouchListener(new View.OnTouchListener() {
                    GestureDetector gestD = new GestureDetector(plTouch);
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if(event.getActionMasked() == MotionEvent.ACTION_UP && authorized) {
                            Intent transferIntent = new Intent(MainActivity.this, TransferActivity.class);
                            transferIntent.putExtra("acctName", dayAcctList[j]);
                            transferIntent.putExtra("Customer", customer);
                            startActivityForResult(transferIntent, Utils.BANK_TRANSFER);
                        }
                        //return false as no other action to listen to
                        return !gestD.onTouchEvent(event);
                    }
                });
            }
            else{ //plurilock not created but action should be unaffected
                Log.e("PluriLockAPI", "Plurilock API not created in Main Activity");
                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent transferIntent = new Intent(MainActivity.this, TransferActivity.class);
                        transferIntent.putExtra("acctName", dayAcctList[j]);
                        transferIntent.putExtra("Customer", customer);
                        startActivityForResult(transferIntent, Utils.BANK_TRANSFER);
                    }
                });
            }

            dayAcctTable.addView(row);
        }

        // add credit account table rows
        for(int i = 0; i < creditAcctList.length; i++){
            View row = getLayoutInflater().inflate(R.layout.acct_row, null);

            //set account name
            ((TextView) row.findViewById(R.id.acct_name)).setText(creditAcctList[i]);

            //set account balance
            ((TextView) row.findViewById(R.id.balance)).setText(customer.getBalanceString(creditAcctList[i]));

            //set click handler
            if (plapi != null) {
                final PluriLockTouchListener plTouch = plapi.createTouchListener();
                row.setOnTouchListener(new View.OnTouchListener() {
                    GestureDetector gestD = new GestureDetector(plTouch);
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getActionMasked() == MotionEvent.ACTION_UP && authorized) {
                            startActivity(new Intent(MainActivity.this, BankStatementActivity.class));
                        }
                        //return false as no other action to listen to
                        return !gestD.onTouchEvent(event);
                    }
                });
            }
            else {
                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, BankStatementActivity.class));
                    }
                });
            }

            creditAcctTable.addView(row);
        }

        //scrollview handler
        ScrollView mainScroll = (ScrollView) findViewById(R.id.mainScrollView);
        if(plapi != null) {
            final PluriLockTouchListener plTouch = plapi.createTouchListener();
            final GestureDetector gestD = new GestureDetector(plTouch);
            mainScroll.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //return false as no other action to listen to
                    return !gestD.onTouchEvent(event);
                }
            });
        }
    }

    private void setupPLApi() {
        Context context = getApplicationContext();

        LocalBroadcastManager.getInstance(context).registerReceiver(
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

        String id = "team3";
        PluriLockConfig config = new PluriLockConfig();
        try {
            config.setActionsPerUpload(ACTIONS_PER_UPLOAD);
//            config.setUrl(URI.create("ws://echo.websocket.org/"));
            config.setUrl(URI.create("ws://129.121.9.44:8001/")); // Mock server.
            config.setAppVersion(1.0);
            config.setDomain("team3");
        } catch(Exception e) {}

        try {
            this.plapi = PluriLockAPI.getInstance();
            if(this.plapi == null) {
                this.plapi = PluriLockAPI.createNewSession(context, id, config);
            }
        } catch (LocationServiceUnavailableException e) {
            // TODO: Display an error message to user telling them to enable location service?
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Utils.BANK_TRANSFER){
            if(resultCode == RESULT_OK){
                this.customer = (Customer) data.getSerializableExtra("Customer");
                populateBalances();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.feedback_menu:
                startActivity(new Intent(this, FeedbackActivity.class));
                break;
            case R.id.location_menu:
                startActivity(new Intent(this, MapLocationActivity.class));
                break;
            case R.id.logout_menu:
                logout();
                break;
            case R.id.about_menu:
                //TODO
                break;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void populateBalances(){
        TableRow tblRow;
        for (int i = 0; i < dayAcctList.length; i++){
            tblRow = (TableRow) dayAcctTable.getChildAt(i);
            ((TextView) tblRow.findViewById(R.id.balance)).setText(customer.getBalanceString(dayAcctList[i]));
        }
        for (int i = 0; i <creditAcctList.length; i++){
            tblRow = (TableRow) creditAcctTable.getChildAt(i);
            ((TextView) tblRow.findViewById(R.id.balance)).setText(customer.getBalanceString(creditAcctList[i]));
        }
    }

    private void clearSession(){
        customer = null;
        PluriLockAPI.destroyAPISession();
    }

    private void logout(){
<<<<<<< HEAD
        Log.d("MainActivity", "Logging out!");
        Intent intent = new Intent(this, LoginActivity.class);
||||||| merged common ancestors
        Intent intent = new Intent(this, LoginActivity.class);
=======
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
>>>>>>> master
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        clearSession();
        finish();
    }
}
