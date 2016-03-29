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


    public double getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(double appVersion) {
        this.appVersion = appVersion;
    }

    protected double appVersion = 1.0;

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }

    protected URI url;

    public int getActionsPerUpload() {
        return actionsPerUpload;
    }

    public void setActionsPerUpload(int actionsPerUpload) {
        this.actionsPerUpload = actionsPerUpload;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setIgnoreLocation(boolean b) {
        ignoreLocation = b;
    }

    public boolean ignoreLocation() { return ignoreLocation; }


    public int getCacheSize() {
        return cacheSize;
    }

    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }
}
