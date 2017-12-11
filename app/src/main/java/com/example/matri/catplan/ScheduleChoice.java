package com.example.matri.catplan;

import android.util.Log;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by matri on 12/10/2017.
 */

public class ScheduleChoice extends AppCompatActivity{
    Button addCourse;
    Button Scheduler;
    public static ArrayList<Courses> courseSelected = new ArrayList<Courses>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_choice);

        addCourse = (Button) findViewById(R.id.btnAddCourse);
        Scheduler = (Button)   findViewById(R.id.btnSchedule);

        Bundle bundle = getIntent().getExtras();
        final ArrayList<Courses> courseNum = bundle.getParcelableArrayList("COURSE_SELECTED");


        Scheduler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ScheduleChoice.this, Scheduler.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("COURSE_SELECTED", courseNum);
                i.putExtras(bundle);
                setResult(RESULT_OK, i);
                startActivity(i);
            }
        });

        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(ScheduleChoice.this, AddCourses.class);
                startActivity(i2);

            }
        });


    }


}
