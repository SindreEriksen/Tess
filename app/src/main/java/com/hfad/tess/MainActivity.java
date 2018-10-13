package com.hfad.tess;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private String elise = "Sindre";
    private String elise = "Sarah";



    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Database
        SQLiteOpenHelper DBHelper = new DBHelper(this);
        db = DBHelper.getReadableDatabase();

    }
}

