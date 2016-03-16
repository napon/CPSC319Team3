package cpsc319.team3.com.biosense.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

import cpsc319.team3.com.biosense.PluriLockConfig;
import cpsc319.team3.com.biosense.exception.LocationServiceUnavailableException;

/**
 * This class provides access to device's location information.
 */
public class LocationUtil implements LocationListener {

    private static double lat = 0.0;
    private static double lon = 0.0;

    public LocationUtil(Context c, PluriLockConfig config) throws LocationServiceUnavailableException {
        if (config.ignoreLocation()) return;
        try {
            // API >= 19
            String provider1 = Settings.Secure.getString(c.getContentResolver(),
                    Settings.Secure.LOCATION_MODE);
            // API < 19
            String provider2 = Settings.Secure.getString(c.getContentResolver(),
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

            if (provider1 != null && provider1.equals(Settings.Secure.LOCATION_MODE_OFF)) {
                throw new LocationServiceUnavailableException("Location not available");
            }

            if (provider2 != null && provider2.equals("")) {
                throw new LocationServiceUnavailableException("Location not available");
            }

            LocationManager lm = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        } catch (SecurityException e) { e.printStackTrace(); }
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
