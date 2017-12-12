package com.example.matri.catplan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.CheckBox;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by matri on 12/10/2017.
 */

public class Scheduler extends AppCompatActivity {


    public static ArrayList<Courses> display = new ArrayList<Courses>();
    public static ArrayList<Courses> finalschedule = new ArrayList<Courses>();
    public static ArrayList<Courses> courseSelected = new ArrayList<Courses>();
    private Activity activity = this;
    private ScheduleAdapter ad;
    SQLiteHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_schedule);

        db = new SQLiteHandler(this);


        Bundle bundle = getIntent().getExtras();
        ArrayList<Courses> schedule = bundle.getParcelableArrayList("COURSE_SELECTED");
        ListView listView = (ListView) findViewById(R.id.listView);

        for(int i = 0; i < schedule.size(); i++)
        {
            for(int j = schedule.size()- 1; j >=0 ; j--) {
                //check if its not the same course num
                if (schedule.get(i).getCourseNum() != schedule.get(j).getCourseNum())
                {
                    // Check if Course1 and course 2 have the same day
                    if(schedule.get(i).getDay1().equals(schedule.get(j).getDay1()))
                    {
                        if(schedule.get(i).getStartTime() != schedule.get(j).getStartTime())
                        {
                            //Check if the course1 is before course 2
                            if (schedule.get(i).getEndTime() < schedule.get(j).getStartTime()) {
                                display.add(schedule.get(i));
                                display.add(schedule.get(j));
                                Log.d("Display", " Check: " + display.get(i));
                            }
                            //Check if course 1 is after course 2
                            else if (schedule.get(i).getStartTime() > schedule.get(j).getEndTime()) {
                                display.add(schedule.get(i));
                                display.add(schedule.get(j));
                                Log.d("Display", " Check2: " + display.get(i));
                            }
                        }

                    }
                    // Check if course 1 and Course 2 day 2 have the same day
                    else if(schedule.get(i).getDay1().equals(schedule.get(j).getDay2()))
                    {
                        if(schedule.get(i).getStartTime() != schedule.get(j).getStartTime())
                        {
                            //Check if the course1 is before course 2
                            if (schedule.get(i).getEndTime() < schedule.get(j).getStartTime()) {
                                display.add(schedule.get(i));
                                display.add(schedule.get(j));
                                Log.d("Display", " Check3: " + display.get(i));
                            }
                            //Check if course 1 is after course 2
                            else if (schedule.get(i).getStartTime() > schedule.get(j).getEndTime()) {
                                display.add(schedule.get(i));
                                display.add(schedule.get(j));
                                Log.d("Display", " Check4: " + display.get(i));
                            }
                        }
                    }
                    //Check if course1 day 2 and course 2 day 2 have the same day
                    else if(schedule.get(i).getDay2().equals(schedule.get(j).getDay2()))
                    {
                        if(schedule.get(i).getStartTime() != schedule.get(j).getStartTime())
                        {
                            //Check if the course1 is before course 2
                            if (schedule.get(i).getEndTime() < schedule.get(j).getStartTime()) {
                                display.add(schedule.get(i));
                                display.add(schedule.get(j));
                                Log.d("Display", " Check5: " + display.get(i));
                            }
                            //Check if course 1 is after course 2
                            else if (schedule.get(i).getStartTime() > schedule.get(j).getEndTime()) {
                                display.add(schedule.get(i));
                                display.add(schedule.get(j));
                                Log.d("Display", " Check6: " + display.get(i));
                            }
                        }
                    }
                    // Check if course1 and course 2 Lab have the same day
                    else if ( schedule.get(i).getDay1().equals(schedule.get(j).getLabDay()))
                    {
                        if(schedule.get(i).getStartTime() != schedule.get(j).getLabStart())
                        {
                            //Check if the course1 is before course 2 Lab
                            if (schedule.get(i).getEndTime() < schedule.get(j).getLabStart()) {
                                display.add(schedule.get(i));
                                display.add(schedule.get(j));
                                Log.d("Display", " Check7: " + display.get(i));
                            }
                            //Check if course 1 is after course 2 Lab
                            else if (schedule.get(i).getStartTime() > schedule.get(j).getLabEnd()) {
                                display.add(schedule.get(i));
                                display.add(schedule.get(j));
                                Log.d("Display", " Check8: " + display.get(i));
                            }
                        }
                    }
                    //Check if course1 Day 2 and course2 Lab have the same day
                    else if(schedule.get(i).getDay2().equals(schedule.get(j).getLabDay()))
                    {
                        if (schedule.get(i).getStartTime() != schedule.get(j).getLabStart())
                        {
                            //Check if the course1 is before course 2 Lab
                            if (schedule.get(i).getEndTime() < schedule.get(j).getLabStart()) {
                                display.add(schedule.get(i));
                                display.add(schedule.get(j));
                                Log.d("Display", " Check9: " + display.get(i));
                            }
                            //Check if course 1 is after course 2 Lab
                            else if (schedule.get(i).getStartTime() > schedule.get(j).getLabEnd()) {
                                display.add(schedule.get(i));
                                display.add(schedule.get(j));
                                Log.d("Display", " Check10: " + display.get(i));
                            }
                        }
                    }
                    else
                    {
                        display.add(schedule.get(i));
                        display.add(schedule.get(j));
                    }
                }

                }
            }

            Log.d("Display", " Size: " + display.size());

        for(int i = 0; i < display.size(); i++)
        {
            Log.d("Display LOOP", " Check " + i + " : " + display.get(i));
        }
        db.insertSchedule(display);
        finalschedule = db.getSchedule();
        ad = new ScheduleAdapter(this, finalschedule);
        listView.setAdapter(ad);


        Button favorites= (Button) findViewById(R.id.btnCancel);
        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { closeSelector(); }
        });

        Button finish = (Button) findViewById(R.id.btnNext2);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnSelected(); }
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
        for (int i=0; i<finalschedule.size(); i++) {
            if (ad.check.get(i))
                courseSelected.add(finalschedule.get(i));
        }

        Intent data = new Intent(Scheduler.this, FavoriteId.class);

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("SCHEDULE_SELECTED", courseSelected);
        data.putExtras(bundle);
        startActivity(data);


        finish();
    }

    private void returnSelected() {

        Intent data = new Intent(Scheduler.this, MainMenu.class);
        db.clearSchedule();
        startActivity(data);
        finish();
    }

}





















