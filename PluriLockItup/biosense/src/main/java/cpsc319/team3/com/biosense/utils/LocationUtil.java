package cpsc319.team3.com.biosense.utils;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import cpsc319.team3.com.biosense.PluriLockConfig;

/**
 * This class provides access to device's location information.
 */
public class LocationUtil implements LocationListener {

    private Context context;
    private boolean enabled = false;

    private double lat = 0.0;
    private double lon = 0.0;

    public LocationUtil(Context c, PluriLockConfig config) {
        context = c;

        if (config.ignoreLocation()) {
            return;
        }

        // API >= 19
        String provider1 = Settings.Secure.getString(c.getContentResolver(),
                Settings.Secure.LOCATION_MODE);
        // API < 19
        String provider2 = Settings.Secure.getString(c.getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if ((provider1 != null && provider1.equals(Settings.Secure.LOCATION_MODE_OFF)) ||
                (provider2 != null && provider2.equals(""))) {
            Log.d("LocationUtil", "Location service is unavailable!");
            setDisabled();
            return;
        }

        LocationManager lm = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
        try {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            setEnabled();
        } catch (SecurityException e) {
            Log.d("LocationUtil", e.getMessage());
            setDisabled();
        }

    }

    public double getLatitude() {
        return enabled ? lat : 0.0;
    }

    public double getLongitude() {
        return enabled ? lon : 0.0;
    }


    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lon = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            switch (status) {
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("LocationUtil", "GPS Provider Status: Out of Service");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("LocationUtil", "GPS Provider Status: Unavailable");
                    break;
                case LocationProvider.AVAILABLE:
                    Log.d("LocationUtil", "GPS Provider Status: Available");
                    break;
            }
        }
    }

    @Override
    public void onProviderEnabled(String provider) {
        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            Log.d("LocationUtil", "GPS Provider Enabled!");
            setEnabled();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            Log.d("LocationUtil", "GPS Provider Disabled!");
            setDisabled();
        }
    }

    private void setEnabled() {
        enabled = true;
    }

    private void setDisabled() {
        enabled = false;
        broadcastLocationDisabled();
    }

    private void broadcastLocationDisabled() {
        Intent intent = new Intent("location-disabled");
        intent.putExtra("msg", "Please enable location services");
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
