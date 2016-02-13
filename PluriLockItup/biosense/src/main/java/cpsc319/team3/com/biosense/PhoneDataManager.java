package cpsc319.team3.com.biosense;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
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

    // Returns country code of the device.
    public static String getCountry() {
        return Locale.getDefault().getCountry();
    }

    // Returns language code of the device.
    public static String getDisplayLanguage() {
        return Locale.getDefault().getDisplayLanguage();
    }

    // Returns consumer model name of the device.
    public static String getHardwareModel() {
        return Build.MODEL;
    }

    // Returns the current date time as UTC milliseconds from the epoch.
    public static long getLocalDateTime() {
        return Calendar.getInstance().getTimeInMillis();
    }

    // Returns device's manufacturer.
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    // Returns number of CPU cores.
    public static int getNumberOfCPUCores() {
        return Runtime.getRuntime().availableProcessors();
    }

    // Returns screen width in pixels.
    public static int getScreenWidth(Context context) {
        if (deviceScreenSize == null) initScreenSize(context);
        return deviceScreenSize.x;
    }

    // Returns screen height in pixels.
    public static int getScreenHeight(Context context) {
        if (deviceScreenSize == null) initScreenSize(context);
        return deviceScreenSize.y;
    }

    // Returns the Android SDK Version of the device.
    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    // Returns the device's timezone.
    public static String getTimeZone() {
        return TimeZone.getDefault().getID();
    }

    // --------- HELPERS ---------
    private static void initScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        deviceScreenSize = new Point();
        display.getSize(deviceScreenSize);
    }
}
