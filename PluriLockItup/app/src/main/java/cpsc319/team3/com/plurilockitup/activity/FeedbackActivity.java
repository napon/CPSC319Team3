package cpsc319.team3.com.plurilockitup.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cpsc319.team3.com.plurilockitup.R;

public class FeedbackActivity extends AppCompatActivity {

    EditText feedback_address;
    EditText feedback_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        //get xml views
        feedback_address = (EditText) findViewById(R.id.feedback_address);
        feedback_message = (EditText) findViewById(R.id.feedback_message);
    }

    public void sendFeedback(View view){
        String feedbackText = feedback_address.getText().toString();

        //check if address is empty
        if(feedbackText.equals("") || feedbackText.isEmpty()){
            Toast.makeText(this, "Who is the message going to?", Toast.LENGTH_LONG).show();
            return;
        }

        //check if message is empty
        feedbackText = feedback_message.getText().toString();
        if(feedbackText.equals("") || feedbackText.isEmpty()){
            Toast.makeText(this, "Message empty", Toast.LENGTH_LONG).show();
            return;
        }

        //simulate send feedback
        Toast.makeText(this, "Thank you for your feedback", Toast.LENGTH_LONG).show();
        feedback_address.setText("");
        feedback_message.setText("");
        finish();
    }
}
