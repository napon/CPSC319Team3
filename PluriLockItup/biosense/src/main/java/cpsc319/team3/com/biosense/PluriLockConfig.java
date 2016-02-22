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
}
