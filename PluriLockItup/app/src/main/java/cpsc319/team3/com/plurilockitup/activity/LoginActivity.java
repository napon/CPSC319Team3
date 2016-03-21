package cpsc319.team3.com.plurilockitup.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import cpsc319.team3.com.plurilockitup.R;
import cpsc319.team3.com.plurilockitup.model.Customer;
import cpsc319.team3.com.plurilockitup.model.Utils;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences mLoginPref;

    EditText cardNumEditText;
    EditText passwordEditText;
    Button loginButton;
    Button registerButton;
    Switch serverMode;

    String savedCardNum;
    String savedPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //grab xml id refs
        cardNumEditText = (EditText) findViewById(R.id.cardNum);
        passwordEditText = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.registerButton);

        //get shared pref for login
        mLoginPref = this.getSharedPreferences(Utils.login, Context.MODE_PRIVATE);

        //try to retrieve registered account
        savedCardNum = mLoginPref.getString(Utils.cardNum, null);
        savedPassword = mLoginPref.getString(Utils.password, null);

        //Server toggle
        serverMode = (Switch) findViewById(R.id.serverSwitch);
    }

    @Override
    protected void onStart(){
        super.onStart();
        String provider = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if(!provider.equals("")){
            //GPS Enabled
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("This app requires location service to keep your banking session secure. Please enable location service before proceeding");
            builder.setTitle("GPS location required");
            builder.setPositiveButton("Location settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Not Right Now", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setCancelable(false);
            builder.show();
        }
    }

    /**
     * Handler for the login button.
     * Takes the card number and password inputs and checks them with the registered values saved on the device.
     * @param view
     */
    public void login(View view){
        String cardNumText = cardNumEditText.getText().toString();
        String passwordText = passwordEditText.getText().toString();

        if(savedCardNum == null || savedPassword == null) { //No saved user found on device
            Toast.makeText(this, "User is not registered. Please register first.", Toast.LENGTH_LONG).show();
        }
        else {
            if(savedCardNum.equals(cardNumText.replace("-",""))){ //correct card#
                if(savedPassword.equals(passwordText)){ //correct LOGIN
                    //get Token ID from Plurilock?? //TODO
                    String purilockToken = getPlToken(savedCardNum.charAt(0));
                    //initiate customer
                    Customer.rebuild(purilockToken);
                    //set server
                    Intent mainActivity = new Intent(this, MainActivity.class);
                    mainActivity.putExtra(Utils.server, serverMode.isChecked());
                    //go to main activity
                    startActivity(mainActivity);
                    //remove from activity stack, prevent going back to screen
                    finish();
                }
                else{ //incorrect password
                    Toast.makeText(this, "Password Incorrect. Please try again", Toast.LENGTH_LONG).show();
                    passwordEditText.requestFocus();
                }
            }
            else{ // incorrect card#
                Toast.makeText(this, "Card Number Incorrect. Please try again", Toast.LENGTH_LONG).show();
                cardNumEditText.requestFocus();
            }
       }
    }

    /**
     * Handler for the Register button
     * Takes the card# and password inputs on screen and saves them as the dummy login
     * Old login will be overwritten with the new button
     * @param view
     */
    public void register(View view){
        String cardNumText = cardNumEditText.getText().toString();
        String passwordText = passwordEditText.getText().toString();

        // Card# is checked to allow only integers, it is saved as a string for convenience
        Integer cardNum = null;
        try {
            cardNum = Integer.valueOf(cardNumText.replace("-",""));
        }
        catch (NumberFormatException e){
            Log.e("Register", e.getMessage());
            Toast.makeText(this, "Valid card# are between 0000-000-000 and 2147-483-647", Toast.LENGTH_LONG).show();
        }


        if(cardNum != null && !passwordText.equals("")) {
            //save register for future login
            SharedPreferences.Editor editor = mLoginPref.edit();
            editor.putString(Utils.cardNum, cardNumText.replace("-",""));
            editor.putString(Utils.password, passwordText);
            editor.commit();

            //go to main activity
            Intent mainActivity = new Intent(this, MainActivity.class);
            mainActivity.putExtra(Utils.server, serverMode.isChecked());
            startActivity(mainActivity);
            //remove from activity stack, prevent going back to screen
            finish();
        }
        else{
            Toast.makeText(this, "Invalid card#/password", Toast.LENGTH_LONG).show();
        }
    }

    private String getPlToken(char cardFirstNum){
        switch (cardFirstNum){
            case '1':
                return "Kelvin";
            case '2':
                return "Elaine";
            case '3':
                return "Noah";
            case '4':
                return "Sunny";
            case '5':
                return "Karen";
            case '6':
                return "Napon";
            default:
                return "team3";
        }
    }
}
