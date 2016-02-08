package cpsc319.team3.com.plurilockitup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Handler;

import cpsc319.team3.com.plurilockitup.R;

/*
This splash screen was created in case there is pre-loading that needs to take
place before the app loads. This can include connection to Plurilock and any
other pre-loading that needs to happen. If we do not need it, it will be removed
from the project.
 */

public class LaunchActivity extends AppCompatActivity {

    int SPLASH_HOLD_TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        //TODO this is a dummy method for holding a splash screen.
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(LaunchActivity.this, LoginActivity.class));
                finish();
            }
        }, SPLASH_HOLD_TIME);

    }
}
