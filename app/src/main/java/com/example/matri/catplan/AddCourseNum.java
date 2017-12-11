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

public class AddCourseNum extends AppCompatActivity
{
    public static List<Courses> courseNames = new ArrayList<Courses>();
    public static ArrayList<Courses> courseSelected = new ArrayList<Courses>();
    private Activity activity = this;
    private AddAdapter ad;
    SQLiteHandler db;
    public String course_name;
    public String course_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_coursenum);

        db = new SQLiteHandler(this);

        course_name  = getIntent().getStringExtra("course_name");
        db.setCourseName(course_name);

        //CourseNames to get List of Course Numbers from handler
        courseNames = db.getListNum();

        //Addapter to populate the list
        ad = new AddAdapter(this, courseNames);
        ListView listView = (ListView) findViewById(R.id.listScrollView);
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
        finish();
    }

    private void returnSelected() {
        courseSelected.clear();
        for (int i=0; i<courseNames.size(); i++) {
            if (ad.check.get(i))
                courseSelected.add(courseNames.get(i));
          Log.d("SELECTION CHECKBOX", "You Selected: " + courseNames.get(i) );
        }
        Intent data = new Intent(AddCourseNum.this, AddCourseLab.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("COURSE_SELECTED", courseSelected);
        data.putExtras(bundle);

        setResult(RESULT_OK, data);
        startActivity(data);
        finish();
    }
}