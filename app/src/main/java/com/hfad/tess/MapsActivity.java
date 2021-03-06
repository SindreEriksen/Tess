package com.hfad.tess;



import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static java.lang.Double.parseDouble;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    GPSLocation gps;
    private GoogleMap mMap;
    private String[][] latLong;
    private Double phoneLat;
    private Double phoneLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        String latlong[][] = getLatLongs();
        int i = 0;
        while (i<5) {
            Double lat = parseDouble(latlong[i][1]);
            Double lng = Double.parseDouble(latlong[i][2]);
            LatLng ll = new LatLng(lat, lng);
            mMap.addMarker(new MarkerOptions().position(ll).title(latlong[i][0]));
            i++;
        }

        gps = new GPSLocation(MapsActivity.this);
        if (gps.CanGetLocation()) {
            phoneLat = gps.getGPSLatitude();
            phoneLong = gps.getGPSLongitude();
            // Adds a marker for phones position
            LatLng phone = new LatLng(phoneLat, phoneLong);
            mMap.addMarker(new MarkerOptions().position(phone).title("Du er her"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(phone));
        }
        else {
            Toast.makeText(getApplicationContext(), "Kan ikke hente telefonenes lokasjon", Toast.LENGTH_SHORT).show();
        }
    }

    public String[][] getLatLongs(){

        SQLiteOpenHelper dbHelper = new DBHelper(this);

        SQLiteDatabase sampleDB = dbHelper.getReadableDatabase();
        Cursor c = sampleDB.query("aktivitet", new String[]{"navn", "latitude", "longitude"},
                null, null, null, null, null);
        String[][] aryDB = new String[10][3];
        int i = 0;
        if (c != null ) {
            if  (c.moveToFirst()) {
                do {
                    String navn = c.getString(c.getColumnIndex("navn"));
                    String latitude = Double.toString(c.getDouble(c.getColumnIndex("latitude")));
                    String longitude = Double.toString(c.getDouble(c.getColumnIndex("longitude")));
                    aryDB[i][0] = navn;
                    aryDB[i][1] = latitude;
                    aryDB[i][2] = longitude;
                    i++;
                }while (c.moveToNext());
            }
        }
        return aryDB;
    }
}
