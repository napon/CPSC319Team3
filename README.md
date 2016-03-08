# CPSC319Team3 [![Build Status](https://travis-ci.com/napon/CPSC319Team3.svg?token=exuRftZEzMFsNKQjHtLJ&branch=master)](https://travis-ci.com/napon/CPSC319Team3)
Noah Sommerfeld | Kelvin Chan | Napon Taratan | Sunny Lee | Karen Guo | Elaine Feng

##How To

### PluriLock Touch Handler
```
PluriLockTouchListener plTouch = PluriLockAPI.getInstance().createTouchListener();
GestureDetector gestD = new GestureDetector(plTouch);
view.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //DO NORMAL TOUCH EVENT ACTIONS HERE
        return gestD.onTouchEvent(event);
    }
});

```