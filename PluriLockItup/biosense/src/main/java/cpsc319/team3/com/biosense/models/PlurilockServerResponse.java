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

    public static PlurilockServerResponse fromJsonString(String response) throws JSONException {
        JSONObject json = new JSONObject(new JSONTokener(response));
        return new PlurilockServerResponse(json.getDouble("confidenceLevel"));
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
}
