package cpsc319.team3.com.plurilockitup.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cpsc319.team3.com.plurilockitup.R;
import cpsc319.team3.com.plurilockitup.model.Utils;

public class LoginActivity extends AppCompatActivity {

    EditText cardNumEditText;
    EditText passwordEditText;
    Button loginButton;
    Button registerButton;

    String savedCardNum;
    String savedPassword;
    Boolean hasSavedLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //grab xml id refs
        cardNumEditText = (EditText) findViewById(R.id.cardNum);
        passwordEditText = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.registerButton);

        //Try to get user registration, if failed, the first inputs are used as registration
        SharedPreferences pref = this.getSharedPreferences(Utils.login, Context.MODE_PRIVATE);
        savedCardNum = pref.getString(Utils.cardNum, null);
        savedPassword = pref.getString(Utils.password, null);
    }

    /*
    Handler for the login button.
    Takes the card number and password inputs and checks them with the registered values saved on the device.
    param: View:view
     */
    public void login(View view){
        Toast.makeText(this, "Login", Toast.LENGTH_LONG).show();
        String cardNumText = cardNumEditText.getText().toString();
        String passwordText = passwordEditText.getText().toString();

        if(savedCardNum == null || savedPassword == null) { //No saved user found on device
            Toast.makeText(this, "User is not registered. Please register first.", Toast.LENGTH_LONG).show();
        }
        else {
            if(savedCardNum.equals(cardNumText)){ //correct card#
                if(savedPassword.equals(passwordText)){ //correct LOGIN
                    //TODO log user in
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

    /*
    Handler for the Register button
    Takes the card# and password inputs on screen and saves them as the dummy login
    Old login will be overwritten with the new button
    param: View:view
     */
    public void register(View view){
        
    }
}
