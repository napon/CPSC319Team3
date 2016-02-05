package cpsc319.team3.com.plurilockitup.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;

import cpsc319.team3.com.plurilockitup.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Noah's test commit
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TableLayout table = (TableLayout) findViewById(R.id.dayAcctTable);
//        View row = getLayoutInflater().inflate(R.layout.acct_row, null);
//        View row2 = getLayoutInflater().inflate(R.layout.acct_row, null);
//        View row3 = getLayoutInflater().inflate(R.layout.acct_row, null);
//        table.addView(row);
//        table.addView(row2);
//        table.addView(row3);

        for(int i=0; i<15; i++){
            View row = getLayoutInflater().inflate(R.layout.acct_row, null);
            table.addView(row);
        }


    }
}
