package cpsc319.team3.com.plurilockitup.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import cpsc319.team3.com.plurilockitup.R;

public class MainActivity extends AppCompatActivity {

    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Noah's test commit
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = (ListView) findViewById(R.id.dayAcctTable);
        listview.setAdapter(new yourAdapter(this, new String[]{"data1",
                "data2"}));
    }
}
