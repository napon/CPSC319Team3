package cpsc319.team3.com.plurilockitup.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import cpsc319.team3.com.biosense.PluriLockAPI;
import cpsc319.team3.com.plurilockitup.R;

public class FeedbackActivity extends PluriLockActivity {

    EditText feedback_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        //get xml views
        feedback_message = (EditText) findViewById(R.id.feedback_message);
        feedback_message.addTextChangedListener(PluriLockAPI.getInstance().createKeyListener());

    }

    public void sendFeedback(View view){
        //check if message is empty
        String feedbackText = feedback_message.getText().toString();
        if(feedbackText.equals("") || feedbackText.isEmpty()){
            Toast.makeText(this, "Message empty", Toast.LENGTH_LONG).show();
            return;
        }

        //simulate send feedback
        Toast.makeText(this, "Thank you for your feedback", Toast.LENGTH_LONG).show();
        feedback_message.setText("");
        finish();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}
