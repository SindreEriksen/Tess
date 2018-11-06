package com.hfad.tess;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import java.text.DecimalFormat;

public class GPSLocation extends Service implements LocationListener {
    private final Context context;

    boolean isGPSEnabled = false;
    boolean isNETWORKEnabled = false;
    boolean canGetLocation = false;
    Location location;
    double latitude;
    double longitude;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

    protected LocationManager locationManager;

    public GPSLocation(Context context){
        this.context = context;
        getLocation();
    }

    @SuppressLint("MissingPermission")
    public Location getLocation() {
        try {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNETWORKEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNETWORKEnabled) {

            }
            else {
                this.canGetLocation = true;

                if(isNETWORKEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);


                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }

                if (isGPSEnabled) {
                    if(location == null){
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES,this);


                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                            if (location != null){
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }



        } catch(Exception e) {
            e.printStackTrace();
        }
        return  location;
    }


    public void stopUsingGPS(){
        if(locationManager != null) {
            locationManager.removeUpdates(GPSLocation.this);
        }
    }

    public double getGPSLatitude(){
        if (location != null) {
            latitude = location.getLatitude();
        }
        return  latitude;
    }

    public double getGPSLongitude(){
        if (location != null) {
            longitude = location.getLongitude();
        }
        return  longitude;
    }

    public boolean CanGetLocation() {
        return canGetLocation;
    }

    public void showSettingsAlert(){
        AlertDialog.Builder alertdialg = new AlertDialog.Builder(context);
        alertdialg.setTitle("GPS settings");
        alertdialg.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        alertdialg.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
           Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
           context.startActivity(intent);
            }
        });
        alertdialg.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertdialg.show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
