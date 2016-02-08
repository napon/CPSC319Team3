package cpsc319.team3.com.plurilockitup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

import cpsc319.team3.com.plurilockitup.R;
import cpsc319.team3.com.plurilockitup.model.Customer;

public class MainActivity extends AppCompatActivity {

    Customer customer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //grab the table refs from xml view
        TableLayout dayAcctTable = (TableLayout) findViewById(R.id.dayAcctTable);
        TableLayout investTable = (TableLayout) findViewById(R.id.investmentTable);

        //populate dummy day account data
        customer = new Customer();
        String[] dayAcctList = new String[]{"Sunny Day CHQ", "Rainy Day SAV", "Snowy Day SAV"};
        customer.addAcct(dayAcctList, 10000);

        //populate dummy credit account data
        String[] creditAcctList = new String[]{"Loan2U", "Ca$hUPFront", "Y.U.Broke"};
        customer.addAcct(creditAcctList, 2500);

        //currency String format
        String balanceString;
        DecimalFormat amtString = new DecimalFormat("#.##");
        amtString.setMinimumFractionDigits(2);

        // add day account table rows
        for(int i = 0; i < dayAcctList.length; i++){
            View row = getLayoutInflater().inflate(R.layout.acct_row, null);
            //set account name
            ((TextView) row.findViewById(R.id.acct_name)).setText(dayAcctList[i]);
            //set account balance
            balanceString = "$ " + amtString.format(customer.getBalance(dayAcctList[i]));
            ((TextView) row.findViewById(R.id.balance)).setText(balanceString);
            //set click handler for account
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO go to transfer balance activity
                    startActivity(new Intent(MainActivity.this,TransferActivity.class));
                }
            });
            dayAcctTable.addView(row);
        }

        // add credit account table rows
        for(int i = 0; i < creditAcctList.length; i++){
            View row = getLayoutInflater().inflate(R.layout.acct_row, null);
            ((TextView) row.findViewById(R.id.acct_name)).setText(creditAcctList[i]);
            balanceString = "$ " + amtString.format(customer.getBalance(creditAcctList[i]));
            ((TextView) row.findViewById(R.id.balance)).setText(balanceString);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO goto statements activity
                }
            });
            investTable.addView(row);
        }


    }
}
