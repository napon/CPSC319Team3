package cpsc319.team3.com.biosense.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by Sunny on 2016-03-01.
 */
public class PlurilockServerResponse {

    public final double confidenceLevel;

    public static PlurilockServerResponse fromJsonString(String response) throws JSONException {
        JSONObject json = new JSONObject(new JSONTokener(response));
        return new PlurilockServerResponse(json.getDouble("confidenceLevel"));
    }

    public PlurilockServerResponse(double confidenceLevel) {
        this.confidenceLevel = confidenceLevel;
    }


}
