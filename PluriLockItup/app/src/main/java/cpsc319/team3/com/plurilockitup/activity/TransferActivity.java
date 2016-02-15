package cpsc319.team3.com.plurilockitup.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import cpsc319.team3.com.plurilockitup.R;
import cpsc319.team3.com.plurilockitup.model.Customer;

public class TransferActivity extends AppCompatActivity {

    Spinner acctSpinner;

    Customer customer;
    String currAcctName;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        //grab xml tags
        acctSpinner = (Spinner) findViewById(R.id.transferAcctType);

        //grab customer
        Intent intent = getIntent();
        customer = Customer.getInstance();
        currAcctName = intent.getStringExtra("acctName");

        //Set current account text
        ((TextView) findViewById(R.id.fromAcct)).setText(currAcctName);
        ((TextView) findViewById(R.id.fromAmt)).setText(customer.getBalanceString(currAcctName));

        //Put account names into drop down
        addAccountsToSpinner();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * Prior to activity finishing, returns the updated customer for Main Activity data
     */
    @Override
    public void onBackPressed() {
        Intent backToMain = new Intent();
        backToMain.putExtra("Customer", customer);
        setResult(RESULT_OK, backToMain);
        super.onBackPressed();
    }

    /*
    Handles button click for transfer
    Parses the amount to transfer from edittext and determines the number amount to be transfered
    Calls transfer amount and updates current account value on screen
     */
    public void transferFunds(View view) {
        String tempText = ((EditText) findViewById(R.id.transferAmt)).getText().toString();
        if (tempText == null || tempText == "" || tempText.length() <= 0) {
            tempText = "0.0"; //will transfer nothing, just like they want
        }
        final String toAcct = String.valueOf(acctSpinner.getSelectedItem().toString());
        final String amtText = tempText;
        //Create custom alert view
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.confirm_alert, null);
        ((TextView) dialogView.findViewById(R.id.fromAcctName)).setText(currAcctName);
        ((TextView) dialogView.findViewById(R.id.fromBalance)).setText(customer.getBalanceString(currAcctName));
        ((TextView) dialogView.findViewById(R.id.withdrawAmt)).setText("-$" + amtText);
        ((TextView) dialogView.findViewById(R.id.toAcctName)).setText(toAcct);
        ((TextView) dialogView.findViewById(R.id.toBalance)).setText(customer.getBalanceString(toAcct));
        ((TextView) dialogView.findViewById(R.id.depositAmt)).setText("+$" + amtText);

        //Create custom Alert with alert view
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    makeTransfer(amtText, toAcct);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setCancelable(true);
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    /**
     * Makes the funds transfer from withdraw acct to deposit acct based on value the user inputs.
     * Resets the transfer amt to ""
     * Updates the current balance string
     *
     * @param withdrawAmt name of acct being drawn from
     * @param depositAcct name of acct being deposited into
     */

    private void makeTransfer(String withdrawAmt, String depositAcct) throws Exception {
        //assume this will come as numbers only (Keyboard restricted to numbers)
        Double transferAmt = Double.valueOf(withdrawAmt);

        //make transfer
        customer.transferFund(currAcctName, depositAcct, transferAmt, getApplicationContext());

        //reset transfer value
        ((EditText) findViewById(R.id.transferAmt)).setText("");

        //update balance
        ((TextView) findViewById(R.id.fromAmt)).setText(customer.getBalanceString(currAcctName));
    }

    /*
    Takes a customers account names and loads them all into the drop down spinner
     */
    private void addAccountsToSpinner() {
        ArrayAdapter<String> dataAdaptor = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, customer.getAccountNameList());
        dataAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        acctSpinner.setAdapter(dataAdaptor);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Transfer Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://cpsc319.team3.com.plurilockitup.activity/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Transfer Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://cpsc319.team3.com.plurilockitup.activity/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
