package cpsc319.team3.com.plurilockitup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import cpsc319.team3.com.plurilockitup.R;
import cpsc319.team3.com.plurilockitup.model.Customer;
import cpsc319.team3.com.plurilockitup.model.Utils;

public class MainActivity extends AppCompatActivity {


    Customer customer;
    String[] dayAcctList;
    String[] creditAcctList;
    TableLayout dayAcctTable;
    TableLayout creditAcctTable;

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


        // add day account table rows
        for(int i = 0; i < dayAcctList.length; i++){
            final int j = i; //click handler needs static int
            View row = getLayoutInflater().inflate(R.layout.acct_row, null);

            //set account name
            ((TextView) row.findViewById(R.id.acct_name)).setText(dayAcctList[i]);

            //set account balance
            ((TextView) row.findViewById(R.id.balance)).setText(customer.getBalanceString(dayAcctList[i]));

            //set click handler for account
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent transferIntent = new Intent(MainActivity.this,TransferActivity.class);
                    transferIntent.putExtra("acctName", dayAcctList[j]);
                    transferIntent.putExtra("Customer", customer);
                    startActivityForResult(transferIntent, Utils.BANK_TRANSFER);
                }
            });

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
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO goto statements activity
                }
            });

            creditAcctTable.addView(row);
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
}
