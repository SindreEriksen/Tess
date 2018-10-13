package com.hfad.tess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_name = "TessDatabase";
    private static final int DB_version = 1;

    private String createAktivitetTypeQuery = "CREATE TABLE aktivitet_type (_id INTEGER PRIMARY KEY AUTOINCREMENT, type TEXT)";
    private String createPrisnivåQuery = "CREATE TABLE prisnivå (_id INTEGER PRIMARY KEY AUTOINCREMENT, prisnivå TEXT)";

    private String createAktivitetQuery = "CREATE TABLE aktivitet (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                                            + "navn TEXT,"
                                            + "beskrivelse TEXT,"
                                            + "type INTEGER,"
                                            + "hjemmeside TEXT,"
                                            + "prisnivå INTEGER,"
                                            + "utendørs,"
                                            + "FOREIGN KEY (type) REFERENCES aktivitet_type(_id),"
                                            + "FOREIGN KEY (prisnivå) REFERENCES prisnivå(_id)"
                                            + ")";

    public DBHelper(@Nullable Context context) {
        super(context, DB_name, null, DB_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(createAktivitetTypeQuery);
        db.execSQL(createPrisnivåQuery);
        db.execSQL(createAktivitetQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
