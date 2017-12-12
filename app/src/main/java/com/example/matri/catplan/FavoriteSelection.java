package com.example.matri.catplan;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class FavoriteSelection extends Activity implements
        OnItemSelectedListener {
    // Spinner element
    Spinner spinner;
    Button next;
    String course_id;
    SQLiteHandler db ;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_selection);
        // Spinner element
        spinner = (Spinner) findViewById(R.id.spinner);
        next = (Button) findViewById(R.id.btnNext);
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        // Loading spinner data from database
        loadSpinnerData();



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i2 = new Intent(FavoriteSelection.this, Favorites.class);
                i2.putExtra("courseId", course_id);
                startActivity(i2);
            }
        });
    }

    /**
     * Function to load the spinner data from SQLite database
     * */
    private void loadSpinnerData() {
        // database handler
        SQLiteHandler db = new SQLiteHandler(getApplicationContext());

        // Spinner Drop down elements
        List<String> courseId = db.getFavoriteId();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, courseId);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // On selecting a spinner item
        String courseId = parent.getItemAtPosition(position).toString();

        Toast.makeText(parent.getContext(), "You selected: " + courseId,
                Toast.LENGTH_LONG).show();
        SQLiteHandler helper = new SQLiteHandler(this);

        helper.setCourseId(course_id);

        course_id = courseId;

    }



    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
}