package cpsc319.team3.com.biosense.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

import cpsc319.team3.com.biosense.exception.LocationServiceUnavailableException;

/**
 * This class provides access to device's location information.
 */
public class LocationUtil implements LocationListener {

    private static double lat = 0.0;
    private static double lon = 0.0;

    public LocationUtil(Context c) throws LocationServiceUnavailableException {
        if (!locationServicesEnabled(c)) {
            throw new LocationServiceUnavailableException("Device Location is disabled.");
        } else {
            LocationManager lm = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
            try {
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            } catch (SecurityException e) {}
        }
    }

    private boolean locationServicesEnabled(Context context) {
        int locationMode = 0;
        try {
            locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return locationMode != Settings.Secure.LOCATION_MODE_OFF;
    }

    public static double getLatitude() { return lat; }
    public static double getLongitude() { return lon; }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lon = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}
}
