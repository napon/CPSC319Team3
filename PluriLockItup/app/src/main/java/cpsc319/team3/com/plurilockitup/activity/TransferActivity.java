package cpsc319.team3.com.plurilockitup.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cpsc319.team3.com.plurilockitup.R;

public class TransferActivity extends AppCompatActivity {

    Spinner acctSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        //grab xml tags
        acctSpinner = (Spinner) findViewById(R.id.transferAcctType);

        addAccountsToSpinner();
    }

    public void transferFunds(View view){
        Toast.makeText(this, "Acct name: " + String.valueOf(acctSpinner.getSelectedItem()), Toast.LENGTH_LONG).show();

        //TODO transfer funds
    }

    private void addAccountsToSpinner(){
        List<String> list = new ArrayList<String>();
        //TODO replace dummy list with customer accounts
        list.add("list 1");
        list.add("list 2");
        list.add("list 3");
//        Spinner spinner = (Spinner) findViewById(R.id.transferAcctType);
        ArrayAdapter<String> dataAdaptor = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        acctSpinner.setAdapter(dataAdaptor);
    }
}
