package cpsc319.team3.com.biosense;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import cpsc319.team3.com.biosense.models.PDiKeyboardTouchEvent;
import cpsc319.team3.com.biosense.models.PMonoKeyboardTouchEvent;

/**
 * Created by Karen on 16-02-23.
 *
 * A Listener class for keyboard events.
 * PluriLockEventTracker is notified via method call when an keyboard event occurs
 *
 */
public class PluriLockKeyListener implements android.text.method.KeyListener, TextWatcher{

    private static final String TAG = "PluriLockKeyListener";

    int screenOrientation;
    long timestamp;

    private PluriLockEventTracker eventTracker;

    private PMonoKeyboardTouchEvent lastKey = null;

    public PluriLockKeyListener(PluriLockEventTracker eventTracker) {
        this.eventTracker = eventTracker;
    }

    /**
     * Return the type of text that this key listener is manipulating,
     * as per {@link InputType}.  This is used to
     * determine the mode of the soft keyboard that is shown for the editor.
     * <p/>
     * <p>If you return
     * {@link InputType#TYPE_NULL}
     * then <em>no</em> soft keyboard will provided.  In other words, you
     * must be providing your own key pad for on-screen input and the key
     * listener will be used to handle input from a hard keyboard.
     * <p/>
     * <p>If you
     * return any other value, a soft input method will be created when the
     * user puts focus in the editor, which will provide a keypad and also
     * consume hard key events.  This means that the key listener will generally
     * not be used, instead the soft input method will take care of managing
     * key input as per the content type returned here.
     */
    @Override
    public int getInputType() {
        return 0;
    }

    /**
     * If the key listener wants to handle this key, return true,
     * otherwise return false and the caller (i.e.&nbsp;the widget host)
     * will handle the key.
     *
     * @param view
     * @param text
     * @param keyCode
     * @param event
     */
    @Override
    public boolean onKeyDown(View view, Editable text, int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyDown: " + event.toString());
        screenOrientation =  eventTracker.getContext().getResources().getConfiguration().orientation;
        timestamp = event.getDownTime();
        return true;
    }

    /**
     * If the key listener wants to handle this key release, return true,
     * otherwise return false and the caller (i.e.&nbsp;the widget host)
     * will handle the key.
     *
     * @param view
     * @param text
     * @param keyCode
     * @param event
     */
    @Override
    public boolean onKeyUp(View view, Editable text, int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyUp: " + event.toString());
        long currTimestamp = System.currentTimeMillis();
        long duration = timestamp - currTimestamp;

        PMonoKeyboardTouchEvent pMonoKeyboardTouchEvent =
                new PMonoKeyboardTouchEvent
                        (screenOrientation, timestamp, duration, keyCode);

        eventTracker.notifyOfEvent(pMonoKeyboardTouchEvent);

        if (lastKey == null) {
            lastKey = pMonoKeyboardTouchEvent;
        } else {
            long diDuration = currTimestamp - lastKey.getTimestamp();
            int fromKey = lastKey.getKeyPressed();
            PDiKeyboardTouchEvent pDiKeyboardTouchEvent =
                    new PDiKeyboardTouchEvent
                            (screenOrientation, timestamp, diDuration, fromKey, keyCode);
            eventTracker.notifyOfEvent(pDiKeyboardTouchEvent);
        }
        return true;
    }

    /**
     * If the key listener wants to other kinds of key events, return true,
     * otherwise return false and the caller (i.e.&nbsp;the widget host)
     * will handle the key.
     *
     * @param view
     * @param text
     * @param event
     */
    @Override
    public boolean onKeyOther(View view, Editable text, KeyEvent event) {
        return false;
    }

    /**
     * Remove the given shift states from the edited text.
     *
     * @param view
     * @param content
     * @param states
     */
    @Override
    public void clearMetaKeyState(View view, Editable content, int states) {

    }

    /**
     * Implements Textwatcher to add to EditText view
     * Tracks last keystroke entered with a timestamp
     * Adds event to Plurilock event as a Monokeytouch event
     * @param s
     * @param start
     * @param before
     * @param count
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        int keyEvent = -1;

        if(before < count) { //new line entered
            if(s.subSequence(s.length()-1, s.length()).toString().equalsIgnoreCase("\n")){
                Log.d("onTextChanged", "Char added: enter");
                keyEvent = KeyEvent.KEYCODE_ENTER;
            }
            else { //char entered
                String addedChar = String.valueOf(s.charAt(count - 1));
                keyEvent = KeyEvent.keyCodeFromString("KEYCODE_"+addedChar.toUpperCase());
                Log.d("onTextChanged", "Char added: " + addedChar);
            }
        }
        else{
            if (before == count){
                //ignore the double textwatch fire
            }
            else { //delete key
                Log.d("onTextChanged", "Char added: delete");
                keyEvent = KeyEvent.KEYCODE_DEL;
            }
        }
        if(keyEvent != -1) {
            screenOrientation = eventTracker.getContext().getResources().getConfiguration().orientation;
            this.timestamp = System.currentTimeMillis();

            PMonoKeyboardTouchEvent monoTouchEvent = new PMonoKeyboardTouchEvent(screenOrientation, timestamp, 0, keyEvent);
            eventTracker.notifyOfEvent(monoTouchEvent);
        }

    }

    /**
     * Abstract method needed to be implemented by Textwatcher
     * No additional actions required
     * @param s
     * @param start
     * @param count
     * @param after
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    /**
     * Abstract method needed to be implemented by Textwatcher
     * No additional actions required
     * @param s
     */
    @Override
    public void afterTextChanged(Editable s) {
    }
}
