package cpsc319.team3.com.biosense;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Sunny on 2016-02-14.
 */
public class PluriLockEventListenerManager {

    public View.OnClickListener createClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throw new RuntimeException("Not yet implemented");
            }
        };
    }

    public View.OnLongClickListener createLongClickListener() {
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                throw new RuntimeException("Not yet implemented");
//                return false;
            }
        };
    }

    public View.OnKeyListener createKeyListener() {
        return new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                throw new RuntimeException("Not yet implemented");
//                return false;
            }
        };
    }

    public View.OnTouchListener createTouchListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                throw new RuntimeException("Not yet implemented");
//                return false;
            }
        };
    }
}
