# CPSC319Team3 [![Build Status](https://travis-ci.org/napon/CPSC319Team3.svg?branch=master)](https://travis-ci.org/napon/CPSC319Team3)
Noah Sommerfeld | Kelvin Chan | Napon Taratan | Sunny Lee | Karen Guo | Elaine Feng

##How To

#### PluriLock Setup
```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    //...
    Context context = getApplicationContext();
    String id = "<pluriLockIdToken>";
    PluriLockConfig config = new PluriLockConfig();
    PluriLockAPI.createNewSession(context, id, config);
    //...
}
```
#### PluriLock Dispatch Touch Event (for attaching to activity)
```java
@Override
public boolean dispatchTouchEvent(MotionEvent ev){
    if(gestD != null){
        gestD.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }
    return super.dispatchTouchEvent(ev);
}
```

#### PluriLock Touch Handler (for attaching to specific view)
```java
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

#### PluriLock EditText KeyHandler
```java
EditText view = (EditText) findViewById(R.id.edittext_view);
view.addTextChangedListener(PluriLockAPI.getInstance().createKeyListener());
```

#### PluriLock Receive Response
```java
LocalBroadcastManager.getInstance(context).registerReceiver(
    // Server Response receiver
    new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            PlurilockServerResponse response = intent.getParcelableExtra("msg");
            if(response.getConfidenceLevel() < MIN_CONF_LEVEL) {
                //DO UN-AUTHORIZED USER ACTION
            }
        }
    }, new IntentFilter("server-response"));
```
