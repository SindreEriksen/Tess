package com.hfad.tess;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AktivitetFragment extends Fragment {

    private SQLiteDatabase db;
    private Cursor cursor;

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mTypes = new ArrayList<>();
    private ArrayList<String> mImagesURLs = new ArrayList<>();

    public AktivitetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RecyclerView aktivitetRecycler = (RecyclerView)inflater.inflate(R.layout.fragment_aktivitet, container, false);

        //Database
        SQLiteOpenHelper dbHelper = new DBHelper(getActivity());

        //Prøver å opprette database og hente ut data. Sender feilmelding hvis det ikke går
        try {
            db = dbHelper.getReadableDatabase();
        } catch(SQLiteException e) {
            Toast dbToast = Toast.makeText(getActivity(), "Database Unavailable", Toast.LENGTH_SHORT);
            dbToast.show();
        }

        initListItems(dbHelper);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mImagesURLs, mNames, mTypes, getActivity());
        aktivitetRecycler.setAdapter(adapter);
        aktivitetRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        return aktivitetRecycler;
    }

    private void initListItems(SQLiteOpenHelper dbHelper) {
        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery("Select navn, type, bildeURL from aktivitet", null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                mNames.add(cursor.getString(0));
                mTypes.add(cursor.getString(1));
                mImagesURLs.add(cursor.getString(2));
                cursor.moveToNext();
            }
        } catch(SQLiteException e) {
            Toast dbToast = Toast.makeText(getActivity(), "Database Unavailable", Toast.LENGTH_SHORT);
            dbToast.show();
        }
    }

}
