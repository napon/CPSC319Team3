package cpsc319.team3.com.biosense.models;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by Sunny on 2016-03-07.
 */
public class PlurilockServerResponseTest {

    @Test
    public void parseTestMockServer() throws Exception {
        String msg = "{confidenceLevel: 0.1234}";
        PlurilockServerResponse response = PlurilockServerResponse.parse(msg);
        Assert.assertEquals(0.1234, response.getConfidenceLevel());
    }

    @Test
    public void parseTestPluriLockReceiveAck() throws Exception {
        String msg = "Worker:27493$Ack";
        PlurilockServerResponse response = PlurilockServerResponse.parse(msg);
        Assert.assertEquals(1.0, response.getConfidenceLevel());
    }

    @Test
    public void parseTestPluriLockReceiveLock() throws Exception {
        String msg = "Worker:27493$lock";
        PlurilockServerResponse response = PlurilockServerResponse.parse(msg);
        Assert.assertEquals(0.0, response.getConfidenceLevel());
    }
}
