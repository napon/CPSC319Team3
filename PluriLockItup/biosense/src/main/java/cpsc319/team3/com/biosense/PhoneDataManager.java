package cpsc319.team3.com.biosense;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * This class provides hardware information of the client's device.
 * Usage example:
 * - String countryCode = PhoneDataManager.getCountry();
 * - int sdkLevel = PhoneDataManager.getAndroidSDKVersion();
 *
 * See the UML Diagram for more implementation details.
 */
public class PhoneDataManager {

    private static Point deviceScreenSize;

    /**
     * @return country code of the device.
     */
    public static String getCountry() {
        return Locale.getDefault().getCountry();
    }

    /**
     * @return language code of the device.
     */
    public static String getDisplayLanguage() {
        return Locale.getDefault().getDisplayLanguage();
    }

    /**
     * @return consumer model name of the device.
     */
    public static String getHardwareModel() {
        return Build.MODEL;
    }

    /**
     * @return current date time as UTC milliseconds from the epoch.
     */
    public static long getLocalDateTime() {
        return Calendar.getInstance().getTimeInMillis();
    }

    /**
     * @return device's manufacturer.
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * @return app's name.
     */
    public static String getAppName(Context context) {
        Resources res = context.getResources();
        if(res != null)
            try{
                return res.getString(R.string.app_name);
            }
            catch (Resources.NotFoundException e){
                Log.e("Data Manager", "App name not found. " + e.getMessage());
                return "";
            }
        return "";

    }

    /**
     * @return number of CPU cores.
     */
    public static int getNumberOfCPUCores() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * @param context
     * @return screen width in pixels.
     */
    public static int getScreenWidth(Context context) {
        if (deviceScreenSize == null) initScreenSize(context);
        return deviceScreenSize.x;
    }

    /**
     * @param context
     * @return screen height in pixels.
     */
    public static int getScreenHeight(Context context) {
        if (deviceScreenSize == null) initScreenSize(context);
        return deviceScreenSize.y;
    }

    /**
     * @return Android SDK version of the device.
     */
    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * @return device's time zone.
     */
    public static String getTimeZone() {
        return TimeZone.getDefault().getID();
    }

    /**
     * Initializes and remembers the coordinate size of the screen.
     * @param context
     */
    private static void initScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        deviceScreenSize = new Point();
        display.getSize(deviceScreenSize);
    }
}