package com.example.matri.catplan;

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

public class AddCourses extends Activity implements
        OnItemSelectedListener {
    // Spinner element
    Spinner spinner;
    Button next;
    String course_name;

    private static final String TAG = MainMenu.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_courses);

        // Spinner element
        spinner = (Spinner) findViewById(R.id.spinner);
        next = (Button) findViewById(R.id.btnNext);
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        // Loading spinner data from database
        loadSpinnerData();

        Log.d(TAG," set items WORKS");

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(AddCourses.this, AddCourseNum.class);
                i2.putExtra("course_name", course_name);
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
        List<String> courseName = db.getAllNames();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, courseName);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        Log.d(TAG," load spinner WORKS");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // On selecting a spinner item
        String courseName = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "You selected: " + courseName,
                Toast.LENGTH_LONG).show();
        SQLiteHandler helper = new SQLiteHandler(this);

        helper.setCourseName(courseName);

        course_name = courseName;
        Log.d(TAG," Course being selected from Spinner: " + course_name);
    }



    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
}