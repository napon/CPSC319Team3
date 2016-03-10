package cpsc319.team3.com.plurilockitup.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import cpsc319.team3.com.biosense.PluriLockAPI;
import cpsc319.team3.com.biosense.PluriLockTouchListener;
import cpsc319.team3.com.plurilockitup.R;

public class MapLocationActivity extends FragmentActivity implements OnMapReadyCallback {
    PluriLockAPI plapi;
    GestureDetector gestD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_location);

        //Generate map
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //underline title
        TextView title = (TextView) findViewById(R.id.nearestBranchTitle);
        title.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        plapi = PluriLockAPI.getInstance();
        if (plapi != null){
            PluriLockTouchListener plTouch = plapi.createTouchListener();
            gestD = new GestureDetector(plTouch);
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        final GoogleMap loadedMap = map;
        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                //center camera
                LatLng swBound = new LatLng(49.230538, -123.184893); //sw marine + Dunbar
                LatLng neBound = new LatLng(49.281586, -123.043552); //PNE
                LatLngBounds vanMap = new LatLngBounds(swBound, neBound);
                loadedMap.moveCamera(CameraUpdateFactory.newLatLngBounds(vanMap, 0));

                //bank location 1
                LatLng ubcLocation = new LatLng(49.261283, -123.248711); //UBC ICICS
                loadedMap.addMarker(new MarkerOptions()
                        .position(ubcLocation)
                        .title("UBC"));

                //bank location 2
                LatLng burnabyLocation = new LatLng(49.225469, -123.001106); //Metrotown
                loadedMap.addMarker(new MarkerOptions()
                        .position(burnabyLocation)
                        .title("Metro"));

                //bank location 3
                LatLng vanLocation = new LatLng(49.281902, -123.124479); //Scotia Theater Vancouver
                loadedMap.addMarker(new MarkerOptions()
                        .position(vanLocation)
                        .title("Scotia Theatre Vancouver"));
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        if(plapi!= null || gestD != null)
            return gestD.onTouchEvent(ev);
        return false; //TODO check if default to false
    }
}
