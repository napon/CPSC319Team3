package cpsc319.team3.com.biosense.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * This class provides access to device's location information.
 */
public class LocationUtil implements LocationListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private double lat = 0.0;
    private double lon = 0.0;

    private static LocationUtil locationUtil;

    private static LocationManager lm;

    private Activity currentActivity;

    private LocationUtil(Activity c) {
        currentActivity = c;
    }

    public static synchronized LocationUtil getInstance() {
        if (locationUtil == null) {
            throw new RuntimeException("locationUtil not initialized. Have you called startListening?");
        }
        return locationUtil;
    }

    public static synchronized void startListening(Activity c) {
        if (locationUtil == null) {
            locationUtil = new LocationUtil(c);
        }
        hasLocationPermission(c);
    }

    public static synchronized void stopListening() {
        if (lm != null) {
            try {
                lm.removeUpdates(locationUtil);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    public double getLatitude() { return lat; }
    public double getLongitude() { return lon; }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lon = location.getLongitude();
        Log.d("LocationUtil", "Found location.");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}

    private static void hasLocationPermission(Activity c) {
        int permissionFine = ContextCompat.checkSelfPermission(c,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionCoarse = ContextCompat.checkSelfPermission(c,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        if (Build.VERSION.SDK_INT >= 23) {
            if (permissionFine != PackageManager.PERMISSION_GRANTED ||
                    permissionCoarse != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(c, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            } else {
                requestUpdates(c);
            }
        } else {
            if (permissionFine == PackageManager.PERMISSION_GRANTED ||
                    permissionCoarse == PackageManager.PERMISSION_GRANTED) {
                requestUpdates(c);
            } else {
                Log.e("LocationUtil", "Location permission denied.");
            }
        }
    }

    private static void requestUpdates(Activity c) {
        lm = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
        try {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationUtil);
            Log.d("LocationUtil", "Requested updates.");
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length == 2) {
            lm = (LocationManager) this.currentActivity.getSystemService(Context.LOCATION_SERVICE);
            try {
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                Log.d("LocationUtil", "Requested updates.");
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("LocationUtil", "Location permission denied.");
        }
    }
}
