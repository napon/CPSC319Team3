package cpsc319.team3.com.biosense;

import java.net.URI;

/**
 * This class allows the client to control specific tracking behaviour.
 *
 * See UML Diagram for more implementation details.
 */
public class PluriLockConfig {

    /**
     * Number of PluriLockEvents to include in a data packet to the Server.
     */
    protected int actionsPerUpload = 10;

    protected boolean ignoreLocation = false;

    protected String domain = "testDomain";

    protected int cacheSize = 1000000; //1MB


    protected boolean enableCellData = false;

    /**
     * 
     * @return Current version of Biosense app
     */
    public double getAppVersion() {
        return appVersion;
    }

    /**
     *
     * @param appVersion the current version of the Biosense app
     */
    public void setAppVersion(double appVersion) {
        this.appVersion = appVersion;
    }

    protected double appVersion = 1.0;

    /**
     *
     * @return URL of server
     */
    public URI getUrl() {
        return url;
    }

    /**
     *
     * @param url the URL of server data is being sent to
     */
    public void setUrl(URI url) {
        this.url = url;
    }

    protected URI url;

    /**
     *
     * @return number of events in each Plurilock package
     */
    public int getActionsPerUpload() {
        return actionsPerUpload;
    }

    /**
     *
     * @param actionsPerUpload the number of events in each Plurilock package
     */
    public void setActionsPerUpload(int actionsPerUpload) {
        this.actionsPerUpload = actionsPerUpload;
    }

    /**
     *
     * @return domain component of the URL
     */
    public String getDomain() {
        return domain;
    }

    /**
     *
     * @param domain component of the URL
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     *
     * @param b true = ignore GPS location, use 0 for lat and long; false = use GPS location
     */
    public void setIgnoreLocation(boolean b) {
        ignoreLocation = b;
    }

    /**
     *
     * @return whether or not GPS location is ignored
     */
    public boolean ignoreLocation() { return ignoreLocation; }

    /**
     *
     * @return size of cache used to save data when user is offline
     */
    public int getCacheSize() {
        return cacheSize;
    }

    /**
     *
     * @param cacheSize size of cache used to save data hwn user is offline
     */
    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }


    public boolean isEnableCellData() {
        return enableCellData;
    }

    public void setEnableCellData(boolean enableCellData) {
        this.enableCellData = enableCellData;
    }
}
