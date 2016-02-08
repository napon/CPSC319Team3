package cpsc319.team3.com.plurilockitup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;

import cpsc319.team3.com.plurilockitup.R;
import cpsc319.team3.com.plurilockitup.model.Customer;

public class TransferActivity extends AppCompatActivity {
    Customer customer;
    Spinner acctSpinner;
    String currAcctName;
    DecimalFormat amtString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        //grab xml tags
        acctSpinner = (Spinner) findViewById(R.id.transferAcctType);

        //grab customer
        Intent intent = getIntent();
        customer = (Customer) intent.getSerializableExtra("Customer");
        currAcctName = intent.getStringExtra("acctName");

        amtString = new DecimalFormat("#.##");
        amtString.setMinimumFractionDigits(2);

        ((TextView) findViewById(R.id.fromAcct)).setText(currAcctName);
        ((TextView) findViewById(R.id.fromAmt)).setText("$ " + amtString.format(customer.getBalance(currAcctName)));

        addAccountsToSpinner();
    }

    public void transferFunds(View view){
        EditText amtText = (EditText) findViewById(R.id.transferAmt);
        Double transferAmt = Double.valueOf(amtText.getText().toString());
        String toAcct =  String.valueOf(acctSpinner.getSelectedItem().toString());
        customer.transferFund(currAcctName, toAcct, transferAmt);

        //update balance
        ((TextView) findViewById(R.id.fromAmt)).setText("$ " + amtString.format(customer.getBalance(currAcctName)));
    }

    private void addAccountsToSpinner(){
        ArrayAdapter<String> dataAdaptor = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, customer.getAccountNameList());
        dataAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        acctSpinner.setAdapter(dataAdaptor);
    }
}
