package cpsc319.team3.com.plurilockitup.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cpsc319.team3.com.plurilockitup.R;
import cpsc319.team3.com.plurilockitup.model.Utils;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences mLoginPref;

    EditText cardNumEditText;
    EditText passwordEditText;
    Button loginButton;
    Button registerButton;

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
            if(savedCardNum.equals(cardNumText)){ //correct card#
                if(savedPassword.equals(passwordText)){ //correct LOGIN
                    //go to main activity
                    startActivity(new Intent(this, MainActivity.class));
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
            cardNum = Integer.valueOf(cardNumText);
        }
        catch (NumberFormatException e){
            Log.e("Register", e.getMessage());
            Toast.makeText(this, "Invalid card#/password", Toast.LENGTH_LONG).show();
        }


        if(cardNum != null && passwordText!= null) {
            //save register for future login
            SharedPreferences.Editor editor = mLoginPref.edit();
            editor.putString(Utils.cardNum, cardNumText);
            editor.putString(Utils.password, passwordText);
            editor.commit();

            //go to main activity
            startActivity(new Intent(this, MainActivity.class));
            //remove from activity stack, prevent going back to screen
            finish();
        }
        else{
            Toast.makeText(this, "Invalid card#/password", Toast.LENGTH_LONG).show();
        }
    }
}
