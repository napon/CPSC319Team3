package cpsc319.team3.com.biosense.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by Sunny on 2016-03-01.
 */
public class PlurilockServerResponse implements Parcelable{

    private final double confidenceLevel;

    public static PlurilockServerResponse parse(String response) throws JSONException {
        PlurilockServerResponse result;
        // PluriLock Server.
        if (response.matches("Worker:\\d*\\$\\w*")) {
            if (response.toLowerCase().contains("ack")) {
                // result is ack
                result = new PlurilockServerResponse(1.0);
            } else {
                // result is lock
                result = new PlurilockServerResponse(0.0);
            }
        } else {
            // Mock Server.
            JSONObject json = new JSONObject(new JSONTokener(response));
            result = new PlurilockServerResponse(json.getDouble("confidenceLevel"));
        }
        return result;
    }

    public PlurilockServerResponse(double confidenceLevel) {
        this.confidenceLevel = confidenceLevel;
    }

    public double getConfidenceLevel() {
        return confidenceLevel;
    }


    protected PlurilockServerResponse(Parcel in) {
        confidenceLevel = in.readDouble();
    }

    public static final Creator<PlurilockServerResponse> CREATOR = new Creator<PlurilockServerResponse>() {
        @Override
        public PlurilockServerResponse createFromParcel(Parcel in) {
            return new PlurilockServerResponse(in);
        }

        @Override
        public PlurilockServerResponse[] newArray(int size) {
            return new PlurilockServerResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(confidenceLevel);
    }

    @Override
    public String toString() {
        return "PlurilockServerResponse{" +
                "confidenceLevel=" + confidenceLevel +
                '}';
    }
}
