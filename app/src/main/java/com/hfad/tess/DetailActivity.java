package com.hfad.tess;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";
    private SQLiteDatabase db;
    private Cursor cursor;

    private String name, description, imageURL, price, type, homepage;
    private Boolean outdoor;
    private TextView txt_name, txt_description, txt_prisnivå, txt_utendørs, txt_type, txt_hjemmeside;
    private ImageView img_header;
    EditText messageView;

    private String id;


    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        id = intent.getExtras().getString("id");

        //Database
        SQLiteOpenHelper dbHelper = new DBHelper(this);

        //Prøver å opprette database og hente ut data. Sender feilmelding hvis det ikke går
        try {
            db = dbHelper.getReadableDatabase();
        } catch (SQLiteException e) {
            Toast dbToast = Toast.makeText(this, "Database Unavailable", Toast.LENGTH_SHORT);
            dbToast.show();
        }

        retrieveInfo(dbHelper);

    } // end onCreate()

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

    private void retrieveInfo(SQLiteOpenHelper dbHelper) {
        Log.d(TAG, "initListItems: called");
        try {
            db = dbHelper.getReadableDatabase();
            String[] args = {id};
            cursor = db.rawQuery("Select navn, beskrivelse, type, hjemmeside, prisnivå, utendørs, bildeURL from aktivitet WHERE navn =?", args);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                name = cursor.getString(0);
                description = cursor.getString(1);
                type = cursor.getString(2);
                homepage = cursor.getString(3);
                price = cursor.getString(4);
                outdoor = Boolean.parseBoolean(cursor.getString(5));
                imageURL = cursor.getString(6);
                cursor.moveToNext();
            }
        } catch (SQLiteException e) {
            Toast dbToast = Toast.makeText(this, "Database Unavailable", Toast.LENGTH_SHORT);
            dbToast.show();
        }

        setValues();
    }

    private void setValues() {

        img_header = findViewById(R.id.img_header);

        Glide.with(this)
                .asBitmap()
                .load(imageURL)
                .into(img_header);

        txt_name = findViewById(R.id.txt_name);
        txt_name.setText(name);
        txt_description = findViewById(R.id.txt_description);
        txt_description.setText(description);
        txt_prisnivå = findViewById(R.id.txt_prisnivå);
        txt_prisnivå.setText(price);
        txt_utendørs = findViewById(R.id.txt_utendørs);
        /*
        if (outdoor == false) {
            txt_utendørs.setText("Innendørs");
        } else {
            txt_utendørs.setText("Utendørs");
        }
        */
        txt_type = findViewById(R.id.txt_type);
        txt_type.setText(type);
        txt_hjemmeside = findViewById(R.id.txt_hjemmeside);
        txt_hjemmeside.setText(homepage);


    }

    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }

    public void onSendMessage(View view){
        EditText messageView =(EditText)findViewById(R.id.message1);
        String messageText = messageView.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, messageText);
        startActivity(intent);

    }


}
