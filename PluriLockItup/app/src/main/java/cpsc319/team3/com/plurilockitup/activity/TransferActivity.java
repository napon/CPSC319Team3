package cpsc319.team3.com.plurilockitup.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import cpsc319.team3.com.plurilockitup.R;
import cpsc319.team3.com.plurilockitup.model.Customer;

public class TransferActivity extends PluriLockActivity {

    Spinner acctSpinner;

    Customer customer;
    String currAcctName;

//    PluriLockAPI plapi;
    boolean auth = false;

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
    }

    /**
     * Prior to activity finishing, returns the updated customer for Main Activity data
     */
    @Override
    public void onBackPressed(){
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
    public void transferFunds(View view){
        String transfAmtTxt = ((EditText) findViewById(R.id.transferAmt)).getText().toString();
        if(transfAmtTxt.equals("") || transfAmtTxt.isEmpty()) {
            Toast.makeText(this, "Please input amount to transfer.", Toast.LENGTH_LONG).show();
            return;
        }
        final String toAcct =  String.valueOf(acctSpinner.getSelectedItem().toString());
        final String amtText = transfAmtTxt;
        //Create custom alert view
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.confirm_alert, null);
        ((TextView)dialogView.findViewById(R.id.fromAcctName)).setText(currAcctName);
        ((TextView)dialogView.findViewById(R.id.fromBalance)).setText(customer.getBalanceString(currAcctName));
        String transfAmt = "-$" + amtText;
        ((TextView)dialogView.findViewById(R.id.withdrawAmt)).setText(transfAmt);
        ((TextView)dialogView.findViewById(R.id.toAcctName)).setText(toAcct);
        ((TextView)dialogView.findViewById(R.id.toBalance)).setText(customer.getBalanceString(toAcct));
        transfAmt = "+$" + amtText;
        ((TextView)dialogView.findViewById(R.id.depositAmt)).setText(transfAmt);

        //Create custom Alert with alert view
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(auth) {
                    makeTransfer(amtText, toAcct);
                    auth = false;
                }
            }
        });
        builder.setCancelable(true);
        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.show();
        if(plapi != null) {
            //Yes button
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    auth = gestD.onTouchEvent(event);
                    return !auth;
                }
            });
            //No button
            dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return !gestD.onTouchEvent(event);
                }
            });
        }
        else
            Toast.makeText(this, "PluriLock is not enabled. Transfer not allowed at this moment. Please reload app", Toast.LENGTH_LONG).show();
    }

    /**
     * Makes the funds transfer from withdraw acct to deposit acct based on value the user inputs.
     * Resets the transfer amt to ""
     * Updates the current balance string
     * @param withdrawAmt name of acct being drawn from
     * @param depositAcct name of acct being deposited into
     */

    private void makeTransfer(String withdrawAmt, String depositAcct){
        //assume this will come as numbers only (Keyboard restricted to numbers)
        Double transferAmt = Double.valueOf(withdrawAmt);

        //make transfer
        customer.transferFund(currAcctName, depositAcct, transferAmt);

        //reset transfer value
        ((EditText)findViewById(R.id.transferAmt)).setText("");

        //update balance
        ((TextView) findViewById(R.id.fromAmt)).setText(customer.getBalanceString(currAcctName));
    }

    /*
    Takes a customers account names and loads them all into the drop down spinner
     */
    private void addAccountsToSpinner(){
        ArrayAdapter<String> dataAdaptor = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, customer.getAccountNameList());
        dataAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        acctSpinner.setAdapter(dataAdaptor);
    }
}
