package cpsc319.team3.com.biosense;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.DeploymentException;

import cpsc319.team3.com.biosense.exception.LocationServiceUnavailableException;
import cpsc319.team3.com.biosense.models.PluriLockEvent;
import cpsc319.team3.com.biosense.models.PluriLockPackage.PluriLockPackageBuilder;
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
    private static final String TAG = "PluriLockEventManager";

    private Context context;
    private PluriLockNetworkUtil networkUtil;
    private LocationUtil locationUtil;
    private List<PluriLockEvent> pluriLockEvents;
    private String userID;
    private PluriLockConfig config;

    private static PluriLockEventManager eventManager;

    protected PluriLockEventManager(Context c, String id, PluriLockConfig config)
            throws LocationServiceUnavailableException {
        Log.d(TAG, "PluriLockEventManager constructor");
        this.context = c;
        this.userID = id;
        this.config = config;
        this.pluriLockEvents = new ArrayList<>();
        this.networkUtil = new PluriLockNetworkUtil(config.getUrl(), c);
        this.locationUtil = new LocationUtil(c, config);
    }

    /**
     * Singleton Design Pattern.
     * @param c Context of the application
     * @param id User ID
     * @return
     */
    public static synchronized PluriLockEventManager getInstance(
            Context c, String id, PluriLockConfig config)
            throws LocationServiceUnavailableException {
        Log.d(TAG, "getInstance");
        if (eventManager == null) {
            eventManager = new PluriLockEventManager(c, id, config);
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
        Log.d(TAG, "addPluriLockEvent");
        assert(pluriLockEvents.size() < config.getActionsPerUpload());
        this.pluriLockEvents.add(pluriLockEvent);
        if (pluriLockEvents.size() == config.getActionsPerUpload()) {
            pushAllEvents();
        }
        assert(pluriLockEvents.size() < config.getActionsPerUpload());
    }

    private void pushAllEvents() {
        PluriLockPackageBuilder eventPackage = new PluriLockPackageBuilder()
                .countryCode(PhoneDataManager.getCountry())
                .model(PhoneDataManager.getHardwareModel())
                .manufacturer(PhoneDataManager.getManufacturer())
                .userID(this.userID)
                .domain(this.config.getDomain())
                .language(PhoneDataManager.getDisplayLanguage())
                .timeZone(PhoneDataManager.getTimeZone())
                .appVersion(this.config.getAppVersion())
                .latitude(this.locationUtil.getLatitude())
                .longitude(this.locationUtil.getLongitude())
                .screenWidth(PhoneDataManager.getScreenWidth(context))
                .screenHeight(PhoneDataManager.getScreenHeight(context))
                .cpuCores(PhoneDataManager.getNumberOfCPUCores())
                .sdkVersion(PhoneDataManager.getSDKVersion())
                .setEvents(pluriLockEvents.toArray(new PluriLockEvent[pluriLockEvents.size()]));
        try {
            networkUtil.sendEvent(eventPackage.buildPackage());
        } catch (IOException | DeploymentException e) {
            // TODO: Store this package for sending again later
            Log.w(this.getClass().getName(), e.getClass().getName(), e);
        } finally {
            pluriLockEvents.clear();
        }
    }

    /**
     * Delete the PluriLockEventManager Instance. Used for debugging purposes.
     */
    public static synchronized void deleteInstance() {
        if (eventManager != null) { eventManager = null; }
    }
}
