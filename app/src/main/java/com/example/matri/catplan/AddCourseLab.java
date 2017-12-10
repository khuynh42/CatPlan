package com.example.matri.catplan;

/**
 * Created by matri on 12/8/2017.
 */
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddCourseLab extends AppCompatActivity {

    public static List<Courses> courseLab = new ArrayList<Courses>();
    public static List<Courses> courseSelected = new ArrayList<Courses>();
    private Activity activity = this;
    private AddLabAdapter ad;
    SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_lab);

        db = new SQLiteHandler(this);

      //  ArrayList<Courses> course = new ArrayList<>();
              //course= (ArrayList<Courses> )getIntent().getSerializableExtra("COURSE_SELECTED");

        //Coursename to call SQLITEHANDLER function to get list of Labs
        courseLab = db.getListLab();

        ad = new AddLabAdapter(this, courseLab);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(ad);


        Button cancelButton = (Button) findViewById(R.id.btnCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { closeSelector(); }
        });

        Button next = (Button) findViewById(R.id.btnNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { returnSelected(); }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                CheckBox cb = (CheckBox) view.findViewById(R.id.selectedCheckbox);
                cb.toggle();
                ad.check.set(position, cb.isChecked());
            }
        });

    }

    @Override
    public void onBackPressed() {
        closeSelector();
    }

    private void closeSelector(){
        finish();
    }

    private void returnSelected() {
        courseSelected.clear();
        for (int i=0; i<courseLab.size(); i++) {
            if (ad.check.get(i))
                courseSelected.add(courseLab.get(i));
        }
        Intent data = new Intent();
        data.putExtra("USERS_SELECTED", courseSelected.size());
        setResult(RESULT_OK, data);
        finish();
    }
}