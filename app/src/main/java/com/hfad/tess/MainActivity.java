package com.hfad.tess;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    RecyclerView recyclerView;

    Button button, btn_lesMer;
    GPSLocation gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment fragment = new AktivitetFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content_frame, fragment);
        ft.commit();

// Metode for å sortere aktivitetene
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            Fragment fragment = null;
            Intent intent = null;
            @Override
            public void onClick(View v) {
                fragment = new ParkFragment();

                if(fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                } else {
                    startActivity(intent);
                }
            }
        });



/* KNAPPEN FOR Å SE TELEFONENS LAT/LONG
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gps = new GPSLocation(MainActivity.this);
                if(gps.CanGetLocation()) {
                    double latitude = gps.getGPSLatitude();
                    double longitude = gps.getGPSLongitude();

                    Toast.makeText(getApplicationContext(), "latitude " + latitude + " longitude " + longitude, Toast.LENGTH_LONG).show();
                }
            }
        });
*/




        if(recyclerView != null) {
            Log.d(TAG, "recyclerView is not empty");
        }
    } // end onCreate()

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item ) {
        switch (item.getItemId()) {
            case R.id.action_se_kart:
                Intent intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onDestroy() {
        super.onDestroy();
    }
}

