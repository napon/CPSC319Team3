package cpsc319.team3.com.biosense.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import cpsc319.team3.com.biosense.exception.LocationServiceUnavailableException;

/**
 * This class provides access to device's location information.
 */
public class LocationUtil implements LocationListener {

    private static double lat = 0.0;
    private static double lon = 0.0;

    public LocationUtil(Context c) throws LocationServiceUnavailableException {
        if (ActivityCompat.checkSelfPermission(c,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(c,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            throw new LocationServiceUnavailableException("Location Access Permission Required.");
        } else {
            LocationManager lm = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }

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
