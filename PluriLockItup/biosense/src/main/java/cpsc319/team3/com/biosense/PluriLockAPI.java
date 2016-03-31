package cpsc319.team3.com.biosense;

import android.content.Context;
import android.util.Log;

import cpsc319.team3.com.biosense.exception.LocationServiceUnavailableException;
import cpsc319.team3.com.biosense.utils.LocationUtil;
import cpsc319.team3.com.biosense.utils.OfflineDatabaseUtil;


/**
 * A class through which the client interacts with our API.
 *
 * See UML Diagram for more implementation details.
 */
public class PluriLockAPI {
    private static final String TAG = "PluriLockAPI";

    private PluriLockEventManager eventManager;
    private PluriLockEventTracker eventTracker;
    private static PluriLockAPI mySession;
    private Context context;

    /**
     *ﾖ
     * @return existing PluriLockAPI session, or null if one has not yet been made.
     */
    public static PluriLockAPI getInstance(){
        Log.d(TAG, "getInstance");
        return mySession;
    }

    /**
     *
     * @param context - application context
     * @param userID - the Plurlock UserID of the app
     * @param config - the settings to use for this API session
     * @return - the new PluriLockAPI session
     */
    public static PluriLockAPI createNewSession(Context context, String userID, PluriLockConfig config) {
        Log.d(TAG, "createNewSession");
        if(mySession != null){
            destroyAPISession();
        }
        mySession = new PluriLockAPI(context,userID,config);
        return mySession;
    }

    /**
     * Constructor
     * @param context - appcliation context
     * @param userID - the Plurlock UserID of the app
     * @param config - the settings to use for this API session
     */
    private PluriLockAPI(Context context, String userID, PluriLockConfig config) {
        Log.d(TAG, "PluriLockAPI constructor");
        this.eventManager = PluriLockEventManager.getInstance(context, userID, config);
        this.eventTracker = new PluriLockEventTracker(context, eventManager);
        this.context = context;
    }

    /**
     *
     * @return a new PluriLockKeyListener
     */
    public PluriLockKeyListener createKeyListener() {
        return new PluriLockKeyListener(eventTracker);
    }

    /**
     *
     * @return a new PluriLockTouchListener
     */
    public PluriLockTouchListener createTouchListener() {
        return new PluriLockTouchListener(eventTracker);
    }


    /**
     * Destroys the existing session (in case of logout, etc)
     * Severs websocket connection
     */
    public static void destroyAPISession(){
        Log.d(TAG, "destroyAPISession");
        if(getInstance() != null) {
            getInstance().nullEvent();
            OfflineDatabaseUtil.deleteCache(getInstance().context, OfflineDatabaseUtil.CACHE_DIR);
        }
        PluriLockEventManager.deleteInstance();

        mySession = null; //add any destruction methods here as well.
        // Stop listening for location.
        LocationUtil.stopListening();
    }

    /**
     * Destroys the event objects
     */
    private void nullEvent(){
        eventManager = null;
        eventTracker = null;
    }
}
