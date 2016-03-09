package cpsc319.team3.com.biosense.models;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by Sunny on 2016-03-07.
 */
public class PlurilockServerResponseTest {

    @Test
    public void parseTest() throws Exception {
        String msg = "{confidenceLevel: 0.1234}";
        PlurilockServerResponse response = PlurilockServerResponse.fromJsonString(msg);
        Assert.assertEquals(0.1234, response.getConfidenceLevel());
    }
}
