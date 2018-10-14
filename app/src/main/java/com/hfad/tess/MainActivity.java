package com.hfad.tess;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView aktivtetsliste = findViewById(R.id.aktivtetsliste);

        //Database
        SQLiteOpenHelper dbHelper = new DBHelper(this);
        //Prøver å opprette database og hente ut data. Sender feilmelding hvis det ikke går

        try {
            db = dbHelper.getReadableDatabase();

            cursor = db.query("aktivitet", new String[] {"_id", "navn", "beskrivelse"}, null, null, null, null, null);
            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, new String[]{"navn", "beskrivelse"}, new int[]{android.R.id.text1, android.R.id.text2 },0);
            aktivtetsliste.setAdapter(listAdapter);
        } catch(SQLiteException e) {
            Toast dbToast = Toast.makeText(this, "Database Unavailable", Toast.LENGTH_SHORT);
            dbToast.show();
        }
    } // end onCreate()

    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}

