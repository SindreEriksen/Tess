package com.hfad.tess;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
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
    private SQLiteDatabase db;
    private Cursor cursor;

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mDescriptions = new ArrayList<>();
    private ArrayList<String> mImagesURLs = new ArrayList<>();

    Button button;
    GPSLocation gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Database
        SQLiteOpenHelper dbHelper = new DBHelper(this);

        //Prøver å opprette database og hente ut data. Sender feilmelding hvis det ikke går
        try {
            db = dbHelper.getReadableDatabase();
        } catch(SQLiteException e) {
            Toast dbToast = Toast.makeText(this, "Database Unavailable", Toast.LENGTH_SHORT);
            dbToast.show();
        }

        initListItems(dbHelper);

        button = (Button) findViewById(R.id.button);
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

    private void initListItems(SQLiteOpenHelper dbHelper) {
        Log.d(TAG, "initListItems: called");
        try {
        db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("Select navn, beskrivelse, bildeURL from aktivitet", null);
        cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                mNames.add(cursor.getString(0));
                mDescriptions.add(cursor.getString(1));
                mImagesURLs.add(cursor.getString(2));
                cursor.moveToNext();
            }
        } catch(SQLiteException e) {
            Toast dbToast = Toast.makeText(this, "Database Unavailable", Toast.LENGTH_SHORT);
            dbToast.show();
        }

        initRecyclerView();
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: called");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mImagesURLs, mNames, mDescriptions, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }


}

