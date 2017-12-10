package com.example.matri.catplan;

/**
 * Created by matri on 11/16/2017.
 */
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

public class MainMenu extends AppCompatActivity{
    Button addCourse;
    Button catalog;
    Button favorites;
    Button logout;

    private static final String TAG = MainMenu.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        addCourse = (Button) findViewById(R.id.button3);
        catalog = (Button)   findViewById(R.id.button5);
        favorites = (Button) findViewById(R.id.button9);
        logout = (Button)   findViewById(R.id.button10);

        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainMenu.this, AddCourses.class);
                startActivity(i);
                Log.d(TAG," ADD COURSE WORKS");
            }
        });

        catalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(MainMenu.this, Catalog.class);
                startActivity(i2);
                Log.d(TAG," CATALOG WORKS");
            }
        });

        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i3 = new Intent(MainMenu.this, Favourites.class);
                startActivity(i3);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent log = new Intent(MainMenu.this, LoginActivity.class);
                startActivity(log);
                Log.d(TAG," LOGOUT WORKS");
            }
        });
    }



}

