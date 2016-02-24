package cpsc319.team3.com.biosense;

import android.content.Context;
import android.view.View;

import cpsc319.team3.com.biosense.exception.LocationServiceUnavailableException;

/**
 * A class through which the client interacts with our API.
 *
 * See UML Diagram for more implementation details.
 */
public class PluriLockAPI {
    private PluriLockEventManager eventManager;
    private static PluriLockAPI mySession;
    
    //TODO: add listeners
    public static PluriLockAPI getInstance(){
        return mySession;
    }

    public static PluriLockAPI createNewSession(Context context, PluriLockServerResponseListener callback,
                                   String userID, PluriLockConfig config)
                                    throws LocationServiceUnavailableException{
        mySession = new PluriLockAPI(context,callback,userID,config);
        return mySession;
    }

    public PluriLockAPI(Context context, PluriLockServerResponseListener callback, String id,
                        PluriLockConfig config) throws LocationServiceUnavailableException {
        this.eventManager = PluriLockEventManager.getInstance(context, callback, id, config);

    }


}
