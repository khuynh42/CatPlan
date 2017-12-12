package com.example.matri.catplan;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.CheckBox;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;


/**
 * Created by matri on 12/7/2017.
 */

public class Favorites extends AppCompatActivity
{
    public static List<Courses> favorites = new ArrayList<Courses>();
    public static ArrayList<Courses> courseSelected = new ArrayList<Courses>();
    private Activity activity = this;
    private FavoritesAdapter ad;
    SQLiteHandler db;
    public String course_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_favorites);

        db = new SQLiteHandler(this);

        Bundle bundle = getIntent().getExtras();
        ArrayList<Courses> schedule = bundle.getParcelableArrayList("SCHEDULE_SELECTED");
        course_id = getIntent().getStringExtra("courseId");

        db.setCourseId(course_id);
        Log.d("GomenGomen", "courseId " + course_id);

        db.insertFavorite(schedule);
        //CourseNames to get List of Course Numbers from handler
        favorites = db.getFavorite();

        //Adapter to populate the list
        ad = new FavoritesAdapter(this, favorites);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(ad);


        final Button cancelButton = (Button) findViewById(R.id.btnCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { closeSelector(); }
        });

        Button next = (Button) findViewById(R.id.btnNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                returnSelected();

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                CheckBox cb = (CheckBox) view.findViewById(R.id.selectedCheckbox);
                cb.toggle();
                ad.check.set(position, cb.isChecked());
                String courseNum = parent.getItemAtPosition(position).toString();

            }
        });
    }

    @Override
    public void onBackPressed() {
        closeSelector();
    }

    private void closeSelector(){

        Intent data = new Intent(Favorites.this, FavoriteSelection.class);
        startActivity(data);
        finish();
    }

    private void returnSelected() {

        Intent data = new Intent(Favorites.this, MainMenu.class);
        db.clearSchedule();
        startActivity(data);
        finish();
    }
}