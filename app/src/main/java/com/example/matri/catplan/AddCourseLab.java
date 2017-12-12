package com.example.matri.catplan;

/**
 * Created by matri on 12/8/2017.
 */
import android.util.Log;
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
    public static ArrayList<Courses> courseSelected = new ArrayList<Courses>();
    public static ArrayList<Courses> temp = new ArrayList<Courses>();
    private Activity activity = this;
    private AddLabAdapter ad;
    SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_lab);

        db = new SQLiteHandler(this);
        //courseLab.clear();
        Bundle bundle = getIntent().getExtras();
        ArrayList<Courses> courseNum = bundle.getParcelableArrayList("COURSE_SELECTED");
        ListView listView = (ListView) findViewById(R.id.listView);


        Log.d("COURSENUM", "SIZE: " + courseNum.size());
        //increment through number of choices
       for(int i = 0; i < courseNum.size(); i++)
        {
            //Hold the array list with the values query provies
           courseLab.add(courseNum.get(i));

        }



        ad = new AddLabAdapter(this, courseLab);
        listView.setAdapter(ad);

        Button cancelButton = (Button) findViewById(R.id.btnCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { closeSelector(); }
        });

        Button next = (Button) findViewById(R.id.btnNext2);
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
        //courseSelected.clear();
        for (int i=0; i<courseLab.size(); i++) {
            if (ad.check.get(i))
                courseSelected.add(courseLab.get(i));
        }
        Intent data = new Intent(AddCourseLab.this, ScheduleChoice.class);
        Log.d("INTENT", "WORKS TO SCHEDULER CHOICE " );

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("COURSE_SELECTED", courseSelected);
        data.putExtras(bundle);
        courseLab.clear();
        setResult(RESULT_OK, data);
        startActivity(data);
        finish();
    }
}