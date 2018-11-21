package com.hfad.tess;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Button;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "MainActivity";

    RecyclerView recyclerView;

    private NavigationView navigationView;
    private DrawerLayout drawer;

    Fragment fragment;

    Button button, btn_lesMer;
    GPSLocation gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragment = AktivitetFragment.newInstance("");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content_frame, fragment);
        ft.commit();



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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        fragment = null;
        Intent intent = null;

        switch (id) {
            case R.id.nav_utendørs:
                fragment = AktivitetFragment.newInstance("Utendørs");
                break;
            case R.id.nav_innendørs:
                fragment = AktivitetFragment.newInstance("Innendørs");
                break;
            case R.id.nav_fornøyelsesparker:
                fragment = AktivitetFragment.newInstance("Fornøyelsespark");
                break;
            case R.id.nav_badeland:
                fragment = AktivitetFragment.newInstance("Badeland");
                break;
            case R.id.nav_fjelltur:
                fragment = AktivitetFragment.newInstance("Fjelltur");
                break;
            case R.id.nav_gratis:
                fragment = AktivitetFragment.newInstance("Gratis");
                break;
            case R.id.nav_lav:
                fragment = AktivitetFragment.newInstance("Lav");
                break;
            case R.id.nav_middels:
                fragment = AktivitetFragment.newInstance("Middels");
                break;
            case R.id.nav_høy:
                fragment = AktivitetFragment.newInstance("Høy");
                break;
            default:
                fragment = new AktivitetFragment();
        }

        if(fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        } else {
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    } // end onNavigationItemSelected

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
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

