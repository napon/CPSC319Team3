package cpsc319.team3.com.plurilockitup.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import cpsc319.team3.com.plurilockitup.R;

public class MapLocationActivity extends FragmentActivity implements OnMapReadyCallback {

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
        title.setPaintFlags(title.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public void onMapReady(GoogleMap map) {

        if(map != null) {
            //center camera
            LatLng swBound = new LatLng(49.230538, -123.184893); //sw marine + Dunbar
            LatLng neBound = new LatLng(49.281586, -123.043552); //PNE
            LatLngBounds vanMap = new LatLngBounds(swBound, neBound);
            map.moveCamera(CameraUpdateFactory.newLatLngBounds(vanMap, 0));

            //bank location 1
            LatLng ubcLocation = new LatLng(49.261283, -123.248711); //UBC ICICS
            map.addMarker(new MarkerOptions()
                    .position(ubcLocation)
                    .title("UBC"));

            //bank location 2
            LatLng burnabyLocation = new LatLng(49.225469, -123.001106); //Metrotown
            map.addMarker(new MarkerOptions()
                    .position(burnabyLocation)
                    .title("Metro"));
        }
    }
}
