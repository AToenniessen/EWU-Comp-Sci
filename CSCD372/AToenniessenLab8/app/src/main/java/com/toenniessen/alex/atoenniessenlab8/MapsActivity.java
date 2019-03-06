package com.toenniessen.alex.atoenniessenlab8;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int PERMISSION_REQUEST = 0;
    private ArrayList<MarkerOptions> mMarkers = new ArrayList<>();
    private ArrayList<Marker> mLastMark = new ArrayList<>();
    private float mZoom = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(savedInstanceState != null){
            mMarkers = (ArrayList<MarkerOptions>) savedInstanceState.getSerializable("Markers");
            mZoom = (float) savedInstanceState.getSerializable("Zoom");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("Markers", mMarkers);
        outState.putSerializable("Zoom", mMap.getCameraPosition().zoom);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// This adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here
        int id = item.getItemId();
        if (id == R.id.action_about) {
            Toast.makeText(this, getResources().getString(R.string.about_data),
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        initMap();
    }

    private void initMap() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // we don’t yet have permission, so request it and return
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST);
            return;
        }
        if(!mMarkers.isEmpty()){
            for(MarkerOptions m : mMarkers){
                mLastMark.add(mMap.addMarker(m));
            }
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Location location = getLocation() ;
                if (location!=null)
                    moveToLocation(location) ;
                else
                    Toast.makeText(getApplicationContext(),
                            "No Location, try the zoom to button", Toast.LENGTH_SHORT).show();
            }
        }, 200);

        findViewById(R.id.change_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mMap.getMapType()){
                    case GoogleMap.MAP_TYPE_NORMAL:
                        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        break;
                    case GoogleMap.MAP_TYPE_SATELLITE:
                        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        break;
                    case GoogleMap.MAP_TYPE_TERRAIN:
                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        break;
                }
            }
        });
        findViewById(R.id.mark).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location temp = getLocation();
                LatLng location = new LatLng(temp.getLatitude(), temp.getLongitude());
                MarkerOptions marker = new MarkerOptions();
                marker.position(location);
                marker.title("Mark " + (mMarkers.size() + 1));
                mMarkers.add(marker);
                mLastMark.add(mMap.addMarker(marker));
            }
        });
        findViewById(R.id.mark).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(!mLastMark.isEmpty()){
                    mLastMark.remove(mLastMark.size() - 1).remove();
                    mMarkers.remove(mMarkers.size() - 1);
                }
                return true;
            }
        });

    }
    private void moveToLocation(Location location){
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), mZoom));
    }
    private Location getLocation() {
        LocationManager locMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
// if we have a map (class Map), try getting it from there (this is still there,
// but deprecated, no longer documented, and appears to no longer work)
        Location location = mMap.getMyLocation();
        String provider;
// if that fails, try using a FINE Criteria
        if (location == null) {
            provider = getProvider(locMgr, Criteria.ACCURACY_FINE, locMgr.GPS_PROVIDER);
            try {
                location = locMgr.getLastKnownLocation(provider);
            } catch (SecurityException e) {
                Log.e("ERROR", "Security Exception: " + e.getMessage());
            }
        }
// if FINE failed, try COARSE
        if (location == null) {
            provider = getProvider(locMgr, Criteria.ACCURACY_COARSE, locMgr.NETWORK_PROVIDER);
            try {
                location = locMgr.getLastKnownLocation(provider);
            } catch (SecurityException e) {
                Log.e("ERROR", "Security Exception: " + e.getMessage());
            }
        }
        return location;
    }
    private String getProvider(LocationManager locMgr, int locAccuracy, String
            defProvider) {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(locAccuracy);
// get best provider regardless of whether it is enabled
        String providerName = locMgr.getBestProvider(criteria, false);
        if (providerName == null)
            providerName = defProvider;
// if neither that nor the default are enabled, prompt user to change settings
        if (!locMgr.isProviderEnabled(providerName)) {
            View parent = findViewById(R.id.rootLayout);
            Snackbar snack = Snackbar.make(parent,
                    "Location Provider Not Enabled: Goto Settings?", Snackbar.LENGTH_LONG);
            snack.setAction("Confirm", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            });
            snack.show();
        }
        return providerName;
    }
    @Override
    public void onRequestPermissionsResult(int rqst, String perms[], int[] res){
        if (rqst == PERMISSION_REQUEST) {
// if the request is cancelled, the result arrays are empty.
            if (res.length>0 && res[0] == PackageManager.PERMISSION_GRANTED) {
// permission was granted! We can now init the map
                initMap() ;
            } else {
                Toast.makeText(this, "This app is useless without loc permissions",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
