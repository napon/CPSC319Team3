package cpsc319.team3.com.biosense;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import cpsc319.team3.com.biosense.exception.LocationServiceUnavailableException;
import cpsc319.team3.com.biosense.models.PluriLockPackage.PluriLockPackageBuilder;
import cpsc319.team3.com.biosense.models.PluriLockEvent;
import cpsc319.team3.com.biosense.utils.LocationUtil;
import cpsc319.team3.com.biosense.utils.PluriLockNetworkUtil;

/**
 * PluriLockEventManager manages the overall flow of the API. This includes:
 * - Initializing the API listeners
 * - Keeping a list of PluriLockEvents
 * - Packaging client's hardware information together with the PluriLockEvents
 *   to be sent to the PluriLockNetworkUtil
 * - Communicating with the PluriLockNetworkUtil
 * - Relaying the response from the server to the client
 *
 * See UML Diagram for more implementation details.
 */
public class PluriLockEventManager {
    private Context context;
    private PluriLockNetworkUtil networkUtil;
    private PluriLockServerResponseListener clientListener;
    private LocationUtil locationUtil;
    private List<PluriLockEvent> pluriLockEvents;
    private String userID;

    private static PluriLockEventManager eventManager;

    private PluriLockEventManager(Context c, PluriLockServerResponseListener l, String id)
            throws LocationServiceUnavailableException {
        this.context = c;
        this.clientListener = l;
        this.userID = id;
        this.pluriLockEvents = new ArrayList<>();
        this.networkUtil = new PluriLockNetworkUtil();
        this.locationUtil = new LocationUtil(c);
    }

    /**
     * Singleton Design Pattern.
     * @param c Context of the applicatio
     * @param l Client Listener for server response
     * @param id User ID
     * @return
     */
    public static synchronized PluriLockEventManager getInstance(
            Context c, PluriLockServerResponseListener l, String id)
            throws LocationServiceUnavailableException {

        if (eventManager == null) {
            eventManager = new PluriLockEventManager(c, l, id);
        }

        return eventManager;
    }

    /**
     * Keeps a temporary list of PluriLockEvent.
     * When size of list < ACTIONS_PER_UPLOAD: Adds the PluriLock event to the temp list.
     * When size of list == ACTIONS_PER_UPLOAD: Creates a PluriLockEventPackage which contains
     *      a bundle of PluriLockEvent objects in the temp list along with
     *      hardware information from the user's device.
     * @param pluriLockEvent
     */
    public void addPluriLockEvent(PluriLockEvent pluriLockEvent) {
        assert(pluriLockEvents.size() < PluriLockConfig.ACTIONS_PER_UPLOAD);
        this.pluriLockEvents.add(pluriLockEvent);
        if (pluriLockEvents.size() == PluriLockConfig.ACTIONS_PER_UPLOAD) {
            PluriLockPackageBuilder eventPackage = new PluriLockPackageBuilder()
                    .countryCode(PhoneDataManager.getCountry())
                    .model(PhoneDataManager.getHardwareModel())
                    .manufacturer(PhoneDataManager.getManufacturer())
                    .userID(this.userID)
                    .language(PhoneDataManager.getDisplayLanguage())
                    .timeZone(PhoneDataManager.getTimeZone())
                    .latitude(this.locationUtil.getLatitude())
                    .longitude(this.locationUtil.getLongitude())
                    .screenWidth(PhoneDataManager.getScreenWidth(context))
                    .screenHeight(PhoneDataManager.getScreenHeight(context))
                    .setEvents(pluriLockEvents.toArray(new PluriLockEvent[pluriLockEvents.size()]));
            networkUtil.sendEvent(eventPackage.buildPackage());
            pluriLockEvents = new ArrayList<>();
        }
        assert(pluriLockEvents.size() < PluriLockConfig.ACTIONS_PER_UPLOAD);
    }

    /**
     * Passes response from the Server to the client application via the listener.
     * @param message from the Server.
     */
    public void notifyClient(String message) {
        clientListener.notify(message);
    }

    /**
     * Delete the PluriLockEventManager Instance. Used for debugging purposes.
     */
    public static synchronized void deleteInstance() {
        if (eventManager != null) { eventManager = null; }
    }
}
