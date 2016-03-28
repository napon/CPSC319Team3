package cpsc319.team3.com.biosense.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Random;
import java.util.UUID;

/**
 * This class represents a packet of data to be sent to the Server.
 * It contains Phone Hardware information along with a bundle of PluriLockEvents.
 */
public class PluriLockPackage {
    private static final String TAG = "PluriLockPackage";

    private String id;
    private String ip;
    private String countryCode;
    private String deviceModel;
    private String deviceManufacturer;
    private String userID;
    private String domain;
    private String language;
    private String timeZone;
    private String appName;
    private String dateTime;
    private double appVersion;
    private double latitude;
    private double longitude;
    private int screenWidth;
    private int screenHeight;
    private int sdkVersion;
    private int cpuCores;
    private PluriLockEvent events[];

    private PluriLockPackage(PluriLockPackageBuilder b) {
        Log.d(TAG, "PluriLockPackage constructor");
        this.id = b.id;
        this.ip = b.ip;
        this.countryCode = b.countryCode;
        this.cpuCores = b.cpuCores;
        this.deviceModel = b.deviceModel;
        this.deviceManufacturer = b.deviceManufacturer;
        this.userID = b.userID;
        this.domain = b.domain;
        this.language = b.language;
        this.timeZone = b.timeZone;
        this.appVersion = b.appVersion;
        this.latitude = b.latitude;
        this.longitude = b.longitude;
        this.screenWidth = b.screenWidth;
        this.screenHeight = b.screenHeight;
        this.sdkVersion = b.sdkVersion;
        this.appName = b.appName;
        this.dateTime = b.dateTime;
        this.events = b.events;
    }

    public String getId() {
        return id;
    }

    public String getIPAddress() {return ip;}

    public String getCountryCode() {
        return countryCode;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public String getUserID() {
        return userID;
    }

    public String getDomain() {
        return domain;
    }

    public String getDeviceManufacturer() {
        return deviceManufacturer;
    }

    public String getLanguage() {
        return language;
    }

    public String getTimeZone() { return timeZone; }

    public String getAppName() {return appName; }

    public String getDateTime() {return dateTime;}

    public double getAppVersion() {
        return appVersion;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getSdkVersion() {
        return sdkVersion;
    }

    public int getCpuCores() {return cpuCores;}


    public PluriLockEvent[] getEvents() {
        return events;
    }

    /**
     * Builder Pattern for PluriLockPackage.
     */
    public static class PluriLockPackageBuilder {
        private String id;
        private String ip;
        private String countryCode;
        private String deviceModel;
        private String deviceManufacturer;
        private String userID;
        private String domain;
        private String language;
        private String timeZone;
        private String appName;
        private String dateTime;
        private double appVersion;
        private double latitude;
        private double longitude;
        private int screenWidth;
        private int screenHeight;
        private int sdkVersion;
        private int cpuCores;
        private PluriLockEvent events[];

        public PluriLockPackageBuilder() {
            this.id = UUID.randomUUID().toString();
            this.ip = "0.0.0.0";
            this.countryCode = "";
            this.deviceModel = "";
            this.deviceManufacturer = "";
            this.userID = "";
            this.domain = "test";
            this.language = "";
            this.timeZone = "";
            this.appName = "";
            this.dateTime = "";
            this.appVersion = 1.0;
            this.latitude = 0.0;
            this.longitude = 0.0;
            this.screenWidth = 0;
            this.screenHeight = 0;
            this.sdkVersion = 0;
            this.cpuCores = 0;
            this.events = new PluriLockEvent[0];
        }

        public PluriLockPackageBuilder countryCode(String cc) {
            countryCode = cc;
            return this;
        }

        public PluriLockPackageBuilder model(String m) {
            deviceModel = m;
            return this;
        }

        public PluriLockPackageBuilder ip(String ipAddress) {
            ip = ipAddress;
            return this;
        }

        public PluriLockPackageBuilder cpuCores(int n) {
            cpuCores = n;
            return this;
        }

        public PluriLockPackageBuilder manufacturer(String m) {
            deviceManufacturer = m;
            return this;
        }

        public PluriLockPackageBuilder userID(String id) {
            userID = id;
            return this;
        }

        public PluriLockPackageBuilder domain(String d) {
            domain = d;
            return this;
        }

        public PluriLockPackageBuilder language(String lang) {
            language = lang;
            return this;
        }

        public PluriLockPackageBuilder timeZone(String tz) {
            timeZone = tz;
            return this;
        }

        public PluriLockPackageBuilder appName(String an) {
            appName = an;
            return this;
        }

        public PluriLockPackageBuilder dateTime(String dt) {
            dateTime = dt;
            return this;
        }

        public PluriLockPackageBuilder appVersion(double av) {
            appVersion = av;
            return this;
        }

        public PluriLockPackageBuilder latitude(double lat) {
            latitude = lat;
            return this;
        }

        public PluriLockPackageBuilder longitude(double lon) {
            longitude = lon;
            return this;
        }

        public PluriLockPackageBuilder screenWidth(int w) {
            screenWidth = w;
            return this;
        }

        public PluriLockPackageBuilder screenHeight(int h) {
            screenHeight = h;
            return this;
        }

        public PluriLockPackageBuilder sdkVersion(int v) {
            sdkVersion = v;
            return this;
        }

        public PluriLockPackageBuilder setEvents(PluriLockEvent[] events) {
            shuffleArray(events);
            this.events = events;
            return this;
        }

        /**
         * Builds the PluriLockPackage.
         * @return A PluriLockPackage
         */
        public PluriLockPackage buildPackage() {
            return new PluriLockPackage(this);
        }
    }

    public JSONObject getJSON() {
        Log.d(TAG, "getJSON");
        JSONObject jsonObject = new JSONObject();
        try {
            JSONObject data = new JSONObject();

            jsonObject.put("userID", getUserID());
            jsonObject.put("btClientType", "android");
            jsonObject.put("btClientVersion", getAppVersion());
            jsonObject.put("domain", getDomain());
            jsonObject.put("data", data);

            data.put("id", getId());
            data.put("ip", getIPAddress());
            data.put("model", getDeviceModel());
            data.put("manufacturer", getDeviceManufacturer());
            data.put("screenWidth", getScreenWidth());
            data.put("screenHeight", getScreenHeight());
            data.put("latitude", getLatitude());
            data.put("longitude", getLongitude());
            data.put("countryCode", getCountryCode());
            data.put("language", getLanguage());
            data.put("sdkVersion", getSdkVersion());
            data.put("cpuCores", getCpuCores());
            data.put("timeZone", getTimeZone());
            data.put("appName", getAppName());
            JSONArray jsonArray = new JSONArray();
            for (PluriLockEvent pEvent : getEvents()) {
                jsonArray.put(pEvent.getJSON());
            }
            data.put("events", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * Shuffles an input array.
     * @param array shuffled in random order.
     */
    private static void shuffleArray(PluriLockEvent[] array) {
        Log.d(TAG, "ShuffleArray");
        int index;
        PluriLockEvent temp;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
}