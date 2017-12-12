package com.example.matri.catplan;

/**
 * Created by matri on 12/11/2017.
 */
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class FavoriteId extends Activity{

    SQLiteHandler db = new SQLiteHandler(this);
    public String course_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_id);
        Button enter = (Button) findViewById(R.id.Enter);
        Bundle bundle = getIntent().getExtras();
        final ArrayList<Courses> schedule = bundle.getParcelableArrayList("SCHEDULE_SELECTED");


       enter.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                EditText name = (EditText) findViewById(R.id.favoriteName);
                String id = name.getText().toString();
                Log.d("FavoriteId", "courseID = " + id);
                course_id = db.setCourseId(id);

                Intent data = new Intent(FavoriteId.this, Favorites.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("SCHEDULE_SELECTED", schedule);
                data.putExtras(bundle);
                data.putExtra("courseId", course_id);

                startActivity(data);
                finish();
            }
        });

    }

}
