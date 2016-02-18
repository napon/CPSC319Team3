package cpsc319.team3.com.biosense.models;

import java.util.UUID;

/**
 * This class represents a packet of data to be sent to the Server.
 * It contains Phone Hardware information along with a bundle of PluriLockEvents.
 */
public class PluriLockPackage {

    private String id;
    private String countryCode;
    private String deviceModel;
    private String deviceManufacturer;
    private String userID;
    private String language;
    private String timeZone;
    private double latitude;
    private double longitude;
    private int screenWidth;
    private int screenHeight;
    private int sdkVersion;
    private PluriLockEvent events[];

    private PluriLockPackage(PluriLockPackageBuilder b) {
        this.id = b.id;
        this.countryCode = b.countryCode;
        this.deviceModel = b.deviceModel;
        this.deviceManufacturer = b.deviceManufacturer;
        this.userID = b.userID;
        this.language = b.language;
        this.timeZone = b.timeZone;
        this.latitude = b.latitude;
        this.longitude = b.longitude;
        this.screenWidth = b.screenWidth;
        this.screenHeight = b.screenHeight;
        this.sdkVersion = b.sdkVersion;
        this.events = b.events;
    }

    public String getId() {
        return id;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public String getUserID() {
        return userID;
    }

    public String getDeviceManufacturer() {
        return deviceManufacturer;
    }

    public String getLanguage() {
        return language;
    }

    public String getTimeZone() { return timeZone; }

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

    public PluriLockEvent[] getEvents() {
        return events;
    }

    /**
     * Builder Pattern for PluriLockPackage.
     */
    public static class PluriLockPackageBuilder {
        private String id;
        private String countryCode;
        private String deviceModel;
        private String deviceManufacturer;
        private String userID;
        private String language;
        private String timeZone;
        private double latitude;
        private double longitude;
        private int screenWidth;
        private int screenHeight;
        private int sdkVersion;
        private PluriLockEvent events[];

        public PluriLockPackageBuilder() { this.id = UUID.randomUUID().toString(); }

        public PluriLockPackageBuilder countryCode(String cc) {
            countryCode = cc;
            return this;
        }

        public PluriLockPackageBuilder model(String m) {
            deviceModel = m;
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

        public PluriLockPackageBuilder language(String lang) {
            language = lang;
            return this;
        }

        public PluriLockPackageBuilder timeZone(String tz) {
            timeZone = tz;
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
}