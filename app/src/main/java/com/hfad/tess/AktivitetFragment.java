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

    private static final String EXTRA_ARG_CODE = null;

    private SQLiteDatabase db;
    private Cursor cursor;

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mTypes = new ArrayList<>();
    private ArrayList<String> mImagesURLs = new ArrayList<>();

    public AktivitetFragment() {
        // Required empty public constructor
    }

    public static final AktivitetFragment newInstance(String argument) {
        AktivitetFragment fragment = new AktivitetFragment();

        final Bundle args = new Bundle(1);
        args.putString(EXTRA_ARG_CODE, argument);
        fragment.setArguments(args);

        return fragment;
    }

    private String argument;
    String[] queryArgument;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        argument = getArguments().getString(EXTRA_ARG_CODE);
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

            switch (argument) {
                case "Fornøyelsespark":
                    String[] args = {"Fornøyelsespark"};
                    cursor = db.rawQuery("Select navn, type, bildeURL from aktivitet WHERE type =?", args);
                    break;
                case "Badeland":
                    String[] args2 = {"Badeland"};
                    cursor = db.rawQuery("Select navn, type, bildeURL from aktivitet WHERE type =?", args2);
                    break;
                case "Fjelltur":
                    String[] args21 = {"Fjelltur"};
                    cursor = db.rawQuery("Select navn, type, bildeURL from aktivitet WHERE type =?", args21);
                    break;
                case "Utendørs":
                    String[] args3 = {"1"};
                    cursor = db.rawQuery("Select navn, type, bildeURL from aktivitet WHERE utendørs =?", args3);
                    break;
                case "Innendørs":
                    String[] args4 = {"0"};
                    cursor = db.rawQuery("Select navn, type, bildeURL from aktivitet WHERE utendørs =?", args4);
                    break;
                case "Gratis":
                    String[] args5 = {"Gratis"};
                    cursor = db.rawQuery("Select navn, type, bildeURL from aktivitet WHERE prisnivå =?", args5);
                    break;
                case "Lav":
                    String[] args6 = {"Lav"};
                    cursor = db.rawQuery("Select navn, type, bildeURL from aktivitet WHERE prisnivå =?", args6);
                    break;
                case "Middels":
                    String[] args7 = {"Middels"};
                    cursor = db.rawQuery("Select navn, type, bildeURL from aktivitet WHERE prisnivå =?", args7);
                    break;
                case "Høy":
                    String[] args8 = {"Høy"};
                    cursor = db.rawQuery("Select navn, type, bildeURL from aktivitet WHERE prisnivå =?", args8);
                    break;
                default:
                    cursor = db.rawQuery("Select navn, type, bildeURL from aktivitet", null);
            }

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
