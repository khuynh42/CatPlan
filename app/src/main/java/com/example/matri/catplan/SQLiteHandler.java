package com.example.matri.catplan;

/**
 * Created by matri on 12/4/2017.
 */

import android.util.Log;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHandler extends SQLiteOpenHelper {


    SQLiteDatabase db;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "catplan.db";
    private static final String TABLE_NAME = "contacts";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_UNAME = "uname";
    private static final String COLUMN_PASS = "pass";
    private static final String TABLE_CREATE = "create table if not exists contacts " +
                                                 "(id integer primary key not null, " +
                                                 "name text not null, " +
                                                 "email text not null, " +
                                                 "uname text not null, " +
                                                 "pass text name not null);";

    private static final String TABLE_COURSE ="courses";
    private static final String COURSE_NAME = "c_name";
    private static final String COURSE_NUM = "c_num";
    private static final String COURSE_DAY1 = "c_day1";
    private static final String COURSE_DAY2 = "c_day2";
    private static final String COURSE_START = "c_start";
    private static final String COURSE_END = "c_end";
    private static final String COURSE_LAB_NUM = "c_lab_num";
    private static final String COURSE_LAB_DAY = "c_lab_day";
    private static final String COURSE_LAB_START = "c_lab_start";
    private static final String COURSE_LAB_END = "c_lab_end";

    private static final String TABLE_CREATE_COURSE =
            "create table if not exists courses " +
                    "(c_name text not null, "  +
                    "c_num integer, " +
                    "c_day1 text not null, " +
                    "c_day2 text not null, " +
                    "c_start integer, "+
                    "c_end integer, " +
                    "c_lab_num integer, " +
                    "c_lab_day text not null, " +
                    "c_lab_start integer, " +
                    "c_lab_end integer);";


    private static final String TABLE_SCHEDULE ="schedule";
    private static final String TABLE_CREATE_SCHEDULE =
            "create table if not exists schedule " +
                    "(c_name text not null, "  +
                    "c_num integer, " +
                    "c_day1 text not null, " +
                    "c_day2 text not null, " +
                    "c_start integer, "+
                    "c_end integer, " +
                    "c_lab_num integer, " +
                    "c_lab_day text not null, " +
                    "c_lab_start integer, " +
                    "c_lab_end integer);";

    private static final String TABLE_FAVORITE ="favorite";
    private static final String COURSE_ID = "c_id";
    private static final String TABLE_CREATE_FAVORITE =
            "create table if not exists favorite " +
                    "(c_id text not null, "  +
                    "c_name text not null, "  +
                    "c_num integer, " +
                    "c_day1 text not null, " +
                    "c_day2 text not null, " +
                    "c_start integer, "+
                    "c_end integer, " +
                    "c_lab_num integer, " +
                    "c_lab_day text not null, " +
                    "c_lab_start integer, " +
                    "c_lab_end integer);";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL(TABLE_CREATE_COURSE);
        db.execSQL(TABLE_CREATE_SCHEDULE);
        db.execSQL(TABLE_CREATE_FAVORITE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS" + TABLE_NAME;
        String query2 = "DROP TABLE IF EXISTS" + TABLE_SCHEDULE;
        String query3 = "DROP TABLE IF EXISTS" + TABLE_FAVORITE;
        db.execSQL(query);
        db.execSQL(query2);
        db.execSQL(query3);
        this.onCreate(db);
    }


    public void insertContact(User c)
    {
        //followed a guide to insert user and search pass
        //Used the guide to apply to rest of the SQLITE HANDLER
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String query = "select * from contacts";
        Cursor cu = db.rawQuery(query, null);
        int count = cu.getCount();

        values.put(COLUMN_ID, count);
        values.put(COLUMN_NAME, c.getName());
        values.put(COLUMN_EMAIL, c.getEmail());
        values.put(COLUMN_UNAME, c.getUname());
        values.put(COLUMN_PASS, c.getPass());
        db.insert(TABLE_NAME, null, values);
        insertCourseCSE();
        insertCourseENGR();
        insertCourseMATH();
    }

    public String searchPass(String uname)
    {
        //followed a guide to insert user and search pass
        //Used the guide to apply to rest of the SQLITE HANDLER
        db = this.getReadableDatabase();
        String query = "select uname, pass from "+ TABLE_NAME;
        Cursor cu = db.rawQuery(query, null);
        String a,b = null;
        if(cu.moveToFirst())
        {
            do{
                a = cu.getString(0);

                if(a.equals(uname))
                {
                    b = cu.getString(1);
                    break;
                }
            }
            while(cu.moveToNext());

        }
        return b;
    }

    public void insertName(Courses c)
    {
        //followed a guide to insert user and search pass
        //Used the guide to apply to rest of the SQLITE HANDLER
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COURSE_NAME, c.getCourseName());
        values.put(COURSE_NUM, c.getCourseNum());
        values.put(COURSE_DAY1, c.getDay1());
        values.put(COURSE_DAY2, c.getDay2());
        values.put(COURSE_START, c.getStartTime());
        values.put(COURSE_END, c.getEndTime());
        values.put(COURSE_LAB_NUM, c.getLabNum());
        values.put(COURSE_LAB_DAY, c.getLabDay());
        values.put(COURSE_LAB_START, c.getLabStart());
        values.put(COURSE_LAB_END, c.getLabEnd());

        db.insert("courses", null, values);
        db.close();
    }

    public List<String> getAllNames(){
        List<String> names = new ArrayList<String>();
        //Get all names for the courses

        // Select AllQuery
        String selectQuery = "SELECT  DISTINCT c_name " +
                "FROM " + TABLE_COURSE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Log.d("While", "There are " + cursor.getCount());

        // While there isnt anything to move next to
        while (cursor.moveToNext())
        {
            //First String in the row
            Log.d("While", "Iterating to " + cursor.getString(0));
            names.add(cursor.getString(0));
        }

        // closing connection
        cursor.close();

        // returning names
        return names;
    }


    private String courseName;

    public String setCourseName (String courseName)
    {
    this.courseName = courseName;
    return courseName;
    }


    public ArrayList<Courses> getListNum(){
        ArrayList<Courses> theList = new ArrayList<>();

        //Search te database for the course number depending on the course name selected
        SQLiteDatabase db = this.getWritableDatabase();
        String numQuery = "SELECT DISTINCT * " +
                            " FROM " + TABLE_COURSE+
                            " WHERE c_name = " + "\"" + courseName + "\" "+
                            "ORDER BY c_num;";

        Log.d("DB GET LIST NUM", "Iterating to " + courseName);

        //add the courses selcted to an array list and send back to be displayed
        Cursor data = db.rawQuery(numQuery,null);
        while(data.moveToNext()){

            theList.add( new Courses(data.getString(data.getColumnIndex(COURSE_NAME))
                    , data.getInt(data.getColumnIndex(COURSE_NUM))
                    , data.getString(data.getColumnIndex(COURSE_DAY1))
                    , data.getString(data.getColumnIndex(COURSE_DAY2))
                    , data.getInt(data.getColumnIndex(COURSE_START))
                    , data.getInt(data.getColumnIndex(COURSE_END))
                    , data.getInt(data.getColumnIndex(COURSE_LAB_NUM))
                    , data.getString(data.getColumnIndex(COURSE_LAB_DAY))
                    , data.getInt(data.getColumnIndex(COURSE_LAB_START))
                    , data.getInt(data.getColumnIndex(COURSE_LAB_END))));
        }
        data.close();
        return theList;
    }





    public ArrayList<Courses> insertSchedule(ArrayList<Courses> course)
    {
    //Insert to a new table Schedule from array list passed
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.d("INSERT SCHEDULE SIZE", "VALUES: " + course.size());
        for (int i =0; i < course.size(); i++)
        {
            values.put(COURSE_NAME, course.get(i).getCourseName());
            values.put(COURSE_NUM, course.get(i).getCourseNum());
            values.put(COURSE_DAY1, course.get(i).getDay1());
            values.put(COURSE_DAY2, course.get(i).getDay2());
            values.put(COURSE_START, course.get(i).getStartTime());
            values.put(COURSE_END, course.get(i).getEndTime());
            values.put(COURSE_LAB_NUM, course.get(i).getLabNum());
            values.put(COURSE_LAB_DAY, course.get(i).getLabDay());
            values.put(COURSE_LAB_START, course.get(i).getLabStart());
            values.put(COURSE_LAB_END, course.get(i).getLabEnd());
            db.insert("schedule", null, values);
            Log.d("INSERT SCHEDULE", "VALUES: " + course.get(i).getCourseName() + course.get(i).getCourseNum() + course.get(i).getDay1());
        }
        return course;
    }


    public ArrayList<Courses> getSchedule(){
        ArrayList<Courses> theList = new ArrayList<>();
    //obtain the schedule using a query from the table schedule
        SQLiteDatabase db = this.getWritableDatabase();
        String numQuery = "SELECT DISTINCT * " +
                " FROM " + TABLE_SCHEDULE +
                " ORDER BY c_num; ";

        Cursor data = db.rawQuery(numQuery,null);
        while(data.moveToNext()){

            theList.add( new Courses(data.getString(data.getColumnIndex(COURSE_NAME))
                    , data.getInt(data.getColumnIndex(COURSE_NUM))
                    , data.getString(data.getColumnIndex(COURSE_DAY1))
                    , data.getString(data.getColumnIndex(COURSE_DAY2))
                    , data.getInt(data.getColumnIndex(COURSE_START))
                    , data.getInt(data.getColumnIndex(COURSE_END))
                    , data.getInt(data.getColumnIndex(COURSE_LAB_NUM))
                    , data.getString(data.getColumnIndex(COURSE_LAB_DAY))
                    , data.getInt(data.getColumnIndex(COURSE_LAB_START))
                    , data.getInt(data.getColumnIndex(COURSE_LAB_END))));
        }
        data.close();

        for(int i = 0; i < theList.size(); i++)
        {
            Log.d("INSERT TheList", "VALUES: " + theList.get(i));
        }

        return theList;
    }

    public ArrayList<Courses> getAllCourse(){
        ArrayList<Courses> theList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        String numQuery = "SELECT DISTINCT * " +
                " FROM " + TABLE_COURSE +
                " ORDER BY c_name, c_num;";


        Cursor data = db.rawQuery(numQuery,null);
        while(data.moveToNext()){

            theList.add( new Courses(data.getString(data.getColumnIndex(COURSE_NAME))
                    , data.getInt(data.getColumnIndex(COURSE_NUM))
                    , data.getString(data.getColumnIndex(COURSE_DAY1))
                    , data.getString(data.getColumnIndex(COURSE_DAY2))
                    , data.getInt(data.getColumnIndex(COURSE_START))
                    , data.getInt(data.getColumnIndex(COURSE_END))
                    , data.getInt(data.getColumnIndex(COURSE_LAB_NUM))
                    , data.getString(data.getColumnIndex(COURSE_LAB_DAY))
                    , data.getInt(data.getColumnIndex(COURSE_LAB_START))
                    , data.getInt(data.getColumnIndex(COURSE_LAB_END))));
        }
        data.close();
        return theList;
    }

    public void insertCourseCSE()
    {
    //Add courses to db so every CSE, ENGR, MATH
        Courses CSE5_2 = new Courses("CSE", 5, "M", "W", 1430, 1545, 2, "R", 800, 1100);
        insertName(CSE5_2);

        Courses CSE5_3 = new Courses("CSE", 5, "M", "W", 1430, 1545, 3, "F", 730, 1020);
        insertName(CSE5_3);

        Courses CSE5_11 = new Courses("CSE", 5, "M", "W", 930, 1020, 11, "T", 730, 1020);
        insertName(CSE5_11);

        Courses CSE5_12 = new Courses("CSE", 5, "M", "W", 930, 1020, 12, "W", 1330, 1620);
        insertName(CSE5_12);


        Courses CSE15_2 = new Courses("CSE", 15, "M", "W", 1500, 1615, 2, "M", 730, 1020);
        insertName(CSE15_2);

        Courses CSE15_3 = new Courses("CSE", 15, "M", "W", 1500, 1615, 3, "M", 1030, 1320);
        insertName(CSE15_3);

        Courses CSE15_4 = new Courses("CSE", 15, "M", "W", 1500, 1615, 4, "W", 1630, 1920);
        insertName(CSE15_4);

        Courses CSE15_5 = new Courses("CSE", 15, "M", "W", 1500, 1615, 5, "F", 1930, 2220);
        insertName(CSE15_5);


        Courses CSE20_2 = new Courses("CSE", 20, "M", "W", 1530, 1620, 2, "F", 1030, 1320);
        insertName(CSE20_2);

        Courses CSE20_3 = new Courses("CSE", 20, "M", "W", 1530, 1620, 3, "W", 1030, 1320);
        insertName(CSE20_3);

        Courses CSE20_4 = new Courses("CSE", 20, "M", "W", 1530, 1620, 4, "T", 1030, 1320);
        insertName(CSE20_4);

        Courses CSE20_5 = new Courses("CSE", 20, "M", "W", 1530, 1620, 5, "M", 1630, 1920);
        insertName(CSE20_5);


        Courses CSE21_2 = new Courses("CSE", 21, "M", "W", 1530, 1620, 2, "T", 1330, 1620);
        insertName(CSE21_2);

        Courses CSE21_3 = new Courses("CSE", 21, "M", "W", 1530, 1620, 3, "R", 1330, 1620);
        insertName(CSE21_3);

        Courses CSE21_4 = new Courses("CSE", 21, "M", "W", 1530, 1620, 4, "T", 1630, 1920);
        insertName(CSE21_4);

        Courses CSE21_5 = new Courses("CSE", 21, "M", "W", 1530, 1620, 5, "R", 1630, 1920);
        insertName(CSE21_5);

        Courses CSE21_6 = new Courses("CSE", 21, "M", "W", 1530, 1620, 6, "T", 1930, 2220);
        insertName(CSE21_6);

        Courses CSE21_7 = new Courses("CSE", 21, "M", "W", 1530, 1620, 7, "R", 1930, 2220);
        insertName(CSE21_7);

        Courses CSE21_8 = new Courses("CSE", 21, "M", "W", 1530, 1620, 8, "T", 730, 1020);
        insertName(CSE21_8);

        Courses CSE21_9 = new Courses("CSE", 21, "M", "W", 1530, 1620, 9, "W", 730, 1020);
        insertName(CSE21_9);

        Courses CSE21_10 = new Courses("CSE", 21, "M", "W", 1530, 1620, 10, "M", 730, 1020);
        insertName(CSE21_10);

        Courses CSE21_11 = new Courses("CSE", 21, "M", "W", 1530, 1620, 11, "w", 1630, 1920);
        insertName(CSE21_11);


        Courses CSE30_2 = new Courses("CSE", 30, "M", "W", 1330, 1445, 2, "F", 730, 1020);
        insertName(CSE30_2);

        Courses CSE30_3 = new Courses("CSE", 30, "M", "W", 1330, 1445, 3, "R", 1030, 1320);
        insertName(CSE30_3);


        Courses CSE31_2 = new Courses("CSE", 31, "M", "W", 1330, 1445, 2, "R", 730, 1020);
        insertName(CSE31_2);

        Courses CSE31_3 = new Courses("CSE", 31, "M", "W", 1330, 1445, 3, "R", 1030, 1320);
        insertName(CSE31_3);

        Courses CSE31_4 = new Courses("CSE", 31, "M", "W", 1330, 1445, 4, "R", 1330, 1620);
        insertName(CSE31_4);

        Courses CSE31_5 = new Courses("CSE", 31, "M", "W", 1330, 1445, 5, "F", 1030, 1320);
        insertName(CSE31_5);


        Courses CSE100_2 = new Courses("CSE", 100, "T", "R", 1630, 1745, 2, "M", 1330, 1620);
        insertName(CSE100_2);

        Courses CSE100_3 = new Courses("CSE", 100, "T", "R", 1630, 1745, 3, "M", 1930, 2220);
        insertName(CSE100_3);

        Courses CSE100_4 = new Courses("CSE", 100, "T", "R", 1630, 1745, 4, "W", 1030, 1320);
        insertName(CSE100_4);


        Courses CSE120_2 = new Courses("CSE", 120, "W", "F", 1200, 1315, 2, "M", 730, 1020);
        insertName(CSE120_2);

        Courses CSE120_3 = new Courses("CSE", 120, "W", "F", 1200, 1315, 3, "M", 1030, 1320);
        insertName(CSE120_3);


        Courses CSE140_2 = new Courses("CSE", 140, "M", "W", 1030, 1145, 2, "W", 1330, 1620);
        insertName(CSE140_2);

        Courses CSE140_3 = new Courses("CSE", 140, "M", "W", 1030, 1145, 3, "M", 1930, 2220);
        insertName(CSE140_3);

        Courses CSE140_4 = new Courses("CSE", 140, "M", "W", 1030, 1145, 4, "M", 1330, 1620);
        insertName(CSE140_4);


        Courses CSE150_2 = new Courses("CSE", 150, "M", "W", 700, 815, 2, "W", 1330, 1620);
        insertName(CSE150_2);

        Courses CSE150_3 = new Courses("CSE", 150, "M", "W", 700, 815, 3, "F", 1630, 1920);
        insertName(CSE150_3);

        Courses CSE150_4 = new Courses("CSE", 150, "M", "W", 700, 815, 4, "T", 1930, 2220);
        insertName(CSE150_4);


        Courses CSE165_2 = new Courses("CSE", 165, "T", "R", 900, 1015, 2, "F", 730, 1020);
        insertName(CSE165_2);

        Courses CSE165_3 = new Courses("CSE", 165, "T", "R", 900, 1015, 3, "F", 1330, 1620);
        insertName(CSE165_3);

        Courses CSE165_4 = new Courses("CSE", 165, "T", "R", 900, 1015, 4, "F", 1630, 1920);
        insertName(CSE165_4);

        Courses CSE165_5 = new Courses("CSE", 165, "T", "R", 900, 1015, 5, "W", 1930, 2220);
        insertName(CSE165_5);


        Courses CSE170_2 = new Courses("CSE", 170, "M", "W", 900, 1015, 2, "F", 730, 1020);
        insertName(CSE170_2);

        Courses CSE170_3 = new Courses("CSE", 170, "M", "W", 900, 1015, 3, "T", 1330, 1620);
        insertName(CSE170_3);


        Courses CSE173_2 = new Courses("CSE", 173, "T", "R", 1500, 1615, 2, "R", 1630, 1915);
        insertName(CSE173_2);

        Courses CSE173_3 = new Courses("CSE", 173, "T", "R", 1500, 1615, 3, "R", 1930, 2215);
        insertName(CSE173_3);


        Courses CSE180_2 = new Courses("CSE", 180, "T", "R", 1500, 1615, 2, "R", 1930, 2220);
        insertName(CSE180_2);

        Courses CSE180_3 = new Courses("CSE", 180, "T", "R", 1500, 1615, 3, "F", 1330, 1620);
        insertName(CSE180_3);

        Courses CSE180_4 = new Courses("CSE", 180, "T", "R", 1500, 1615, 4, "W", 1930, 2220);
        insertName(CSE180_4);

    }

    public void insertCourseENGR()
    {

        Courses ENGR45_2 = new Courses("ENGR", 45, "T", "R", 1030, 1145, 2, "M", 1330, 1620);
        insertName(ENGR45_2);

        Courses ENGR45_3 = new Courses("ENGR", 45, "T", "R", 1030, 1145, 3, "T", 1730, 2220);
        insertName(ENGR45_3);

        Courses ENGR45_4 = new Courses("ENGR", 45, "T", "R", 1030, 1145, 4, "W", 1330, 1620);
        insertName(ENGR45_4);

        Courses ENGR45_5 = new Courses("ENGR", 45, "T", "R", 1030, 1145, 5, "R", 1230, 1520);
        insertName(ENGR45_5);

        Courses ENGR45_6 = new Courses("ENGR", 45, "T", "R", 1030, 1145, 6, "F", 1330, 1620);
        insertName(ENGR45_6);

        Courses ENGR45_7 = new Courses("ENGR", 45, "T", "R", 1030, 1145, 7, "F", 1730, 2220);
        insertName(ENGR45_7);


        Courses ENGR57_2 = new Courses("ENGR", 57, "T", "R", 930, 1015, 2, "W", 1330, 1420);
        insertName(ENGR57_2);

        Courses ENGR57_3 = new Courses("ENGR", 57, "T", "R", 930, 1015, 3, "W", 1430, 1520);
        insertName(ENGR57_3);

        Courses ENGR57_4 = new Courses("ENGR", 57, "T", "R", 930, 1015, 4, "W", 1530, 1620);
        insertName(ENGR57_4);

        Courses ENGR57_5 = new Courses("ENGR", 57, "T", "R", 930, 1015, 5, "W", 1130, 1220);
        insertName(ENGR57_5);

        Courses ENGR57_6 = new Courses("ENGR", 57, "T", "R", 930, 1015, 6, "W", 1230, 1320);
        insertName(ENGR57_6);


        Courses ENGR65_2 = new Courses("ENGR", 65, "M", "W", 1330, 1445, 2, "T", 900, 1150);
        insertName(ENGR65_2);

        Courses ENGR65_3 = new Courses("ENGR", 65, "M", "W", 1330, 1445, 3, "R", 900, 1150);
        insertName(ENGR65_3);

        Courses ENGR65_4 = new Courses("ENGR", 65, "M", "W", 1330, 1445, 4, "T", 1200, 1450);
        insertName(ENGR65_4);

        Courses ENGR65_5 = new Courses("ENGR", 65, "M", "W", 1330, 1445, 5, "R", 1200, 1450);
        insertName(ENGR65_5);

        Courses ENGR65_6 = new Courses("ENGR", 65, "M", "W", 1330, 1445, 6, "T", 1500, 1750);
        insertName(ENGR65_6);

        Courses ENGR65_7 = new Courses("ENGR", 65, "M", "W", 1330, 1445, 7, "R", 1500, 1750);
        insertName(ENGR65_7);

        Courses ENGR65_8 = new Courses("ENGR", 65, "M", "W", 1330, 1445, 8, "W", 1500, 1750);
        insertName(ENGR65_8);

        Courses ENGR65_9 = new Courses("ENGR", 65, "M", "W", 1330, 1445, 9, "F", 1500, 1750);
        insertName(ENGR65_9);


        Courses ENGR97_2 = new Courses("ENGR", 97, "M", "F", 830, 920, 2, "R", 1400, 1550);
        insertName(ENGR97_2);

        Courses ENGR97_3 = new Courses("ENGR", 97, "M", "F", 830, 920, 3, "W", 1400, 1550);
        insertName(ENGR97_3);

        Courses ENGR97_4 = new Courses("ENGR", 97, "M", "F", 830, 920, 4, "T", 1500, 1750);
        insertName(ENGR97_4);

        Courses ENGR97_5 = new Courses("ENGR", 97, "M", "F", 830, 920, 5, "T", 900, 1050);
        insertName(ENGR97_5);

        Courses ENGR97_6 = new Courses("ENGR", 97, "M", "F", 830, 920, 6, "W", 1200, 1350);
        insertName(ENGR97_6);

        Courses ENGR97_7 = new Courses("ENGR", 97, "M", "F", 830, 920, 7, "W", 1000, 1150);
        insertName(ENGR97_7);

        Courses ENGR97_8 = new Courses("ENGR", 97, "M", "F", 830, 920, 8, "T", 1300, 1450);
        insertName(ENGR97_8);

        Courses ENGR97_9 = new Courses("ENGR", 97, "M", "F", 830, 920, 9, "R", 1600, 1750);
        insertName(ENGR97_9);

        Courses ENGR97_10 = new Courses("ENGR", 97, "M", "F", 830, 920, 10, "M", 1300, 1450);
        insertName(ENGR97_10);

        Courses ENGR97_11 = new Courses("ENGR", 97, "M", "F", 830, 920, 11, "R", 1200, 1350);
        insertName(ENGR97_11);


        Courses ENGR120_2 = new Courses("ENGR", 120, "M", "F", 1430, 1545, 2, "R", 1030, 1320);
        insertName(ENGR120_2);

        Courses ENGR120_3 = new Courses("ENGR", 120, "M", "F", 1430, 1545, 3, "R", 1330, 1620);
        insertName(ENGR120_3);

        Courses ENGR120_4 = new Courses("ENGR", 120, "M", "F", 1430, 1545, 4, "W", 830, 1020);
        insertName(ENGR120_4);

        Courses ENGR120_5 = new Courses("ENGR", 120, "M", "F", 1430, 1545, 5, "F", 1330, 1620);
        insertName(ENGR120_5);

        Courses ENGR120_6 = new Courses("ENGR", 120, "M", "F", 1430, 1545, 6, "W", 1130, 1320);
        insertName(ENGR120_6);

        Courses ENGR120_7 = new Courses("ENGR", 120, "M", "F", 1430, 1545, 7, "F", 1030, 1320);
        insertName(ENGR120_7);


        Courses ENGR130_1 = new Courses("ENGR", 130, "T", "R", 1430, 1545, 1, "M", 1830, 2120);
        insertName(ENGR130_1);


        Courses ENGR135_2 = new Courses("ENGR", 135, "T", "R", 900, 1015, 2, "T", 1330, 1620);
        insertName(ENGR135_2);

        Courses ENGR135_3 = new Courses("ENGR", 135, "T", "R", 900, 1015, 3, "W", 1330, 1620);
        insertName(ENGR135_3);

        Courses ENGR135_4 = new Courses("ENGR", 135, "T", "R", 900, 1015, 4, "F", 830, 1120);
        insertName(ENGR135_4);


        Courses ENGR140_2 = new Courses("ENGR", 140, "T", "R", 900, 1015, 2, "F", 730, 1020);
        insertName(ENGR140_2);

        Courses ENGR140_3 = new Courses("ENGR", 140, "T", "R", 900, 1015, 3, "F", 1330, 1620);
        insertName(ENGR140_3);

        Courses ENGR140_4 = new Courses("ENGR", 140, "T", "R", 900, 1015, 4, "F", 1630, 1920);
        insertName(ENGR140_4);

        Courses ENGR140_5 = new Courses("ENGR", 140, "T", "R", 900, 1015, 5, "W", 730, 1020);
        insertName(ENGR140_5);


        Courses ENGR151_2 = new Courses("ENGR", 151, "M", "W", 1200, 1315, 2, "M", 800, 1050);
        insertName(ENGR151_2);

        Courses ENGR151_3 = new Courses("ENGR", 151, "M", "W", 1200, 1315, 3, "W", 800, 1050);
        insertName(ENGR151_3);

        Courses ENGR151_4 = new Courses("ENGR", 151, "M", "W", 1200, 1315, 4, "F", 800, 1050);
        insertName(ENGR151_4);

        Courses ENGR151_5 = new Courses("ENGR", 151, "M", "W", 1200, 1315, 5, "T", 1430, 1720);
        insertName(ENGR151_5);


        Courses ENGR155_2 = new Courses("ENGR", 155, "T", "R", 1030, 1145, 2, "F", 1500, 1720);
        insertName(ENGR155_2);


        Courses ENGR175_2 = new Courses("ENGR", 175, "M", "W", 900, 1015, 2, "T", 1130, 1420);
        insertName(ENGR175_2);


        Courses ENGR190_2 = new Courses("ENGR", 190, "W", "F", 1630, 1720, 2, "M", 1730, 1920);
        insertName(ENGR190_2);

        Courses ENGR190_3 = new Courses("ENGR", 190, "W", "F", 1630, 1720, 3, "M", 1730, 1920);
        insertName(ENGR190_3);

        Courses ENGR190_4 = new Courses("ENGR", 190, "W", "F", 1630, 1720, 4, "W", 1730, 1920);
        insertName(ENGR190_4);

        Courses ENGR190_5 = new Courses("ENGR", 190, "W", "F", 1630, 1720, 5, "W", 1730, 1920);
        insertName(ENGR190_5);

        Courses ENGR190_6 = new Courses("ENGR", 190, "W", "F", 1630, 1720, 6, "W", 1730, 1920);
        insertName(ENGR190_6);

        Courses ENGR190_7 = new Courses("ENGR", 190, "W", "F", 1630, 1720, 7, "M", 1730, 1920);
        insertName(ENGR190_7);


        Courses ENGR191_2 = new Courses("ENGR", 191, "M", "T", 1630, 1720, 2, "M", 1730, 1920);
        insertName(ENGR191_2);


        Courses ENGR192_2 = new Courses("ENGR", 192, "W", "F", 1000, 1220, 2, "T", 1500, 1720);
        insertName(ENGR192_2);


        Courses ENGR197_2 = new Courses("ENGR", 197, "M", "T", 830, 920, 2, "R", 1400, 1550);
        insertName(ENGR197_2);

        Courses ENGR197_3 = new Courses("ENGR", 197, "M", "T", 830, 920, 3, "W", 1400, 1550);
        insertName(ENGR197_3);

        Courses ENGR197_4 = new Courses("ENGR", 197, "M", "T", 830, 920, 4, "T", 1500, 1650);
        insertName(ENGR197_4);

        Courses ENGR197_5 = new Courses("ENGR", 197, "M", "T", 830, 920, 5, "T", 900, 1050);
        insertName(ENGR197_5);

        Courses ENGR197_6 = new Courses("ENGR", 197, "M", "T", 830, 920, 6, "W", 1200, 1350);
        insertName(ENGR197_6);

        Courses ENGR197_7 = new Courses("ENGR", 197, "M", "T", 830, 920, 7, "W", 1000, 1150);
        insertName(ENGR197_7);

        Courses ENGR197_8 = new Courses("ENGR", 197, "M", "T", 830, 920, 8, "T", 1300, 1450);
        insertName(ENGR197_8);

        Courses ENGR197_9 = new Courses("ENGR", 197, "M", "T", 830, 920, 9, "W", 1600, 1750);
        insertName(ENGR197_9);

        Courses ENGR197_10 = new Courses("ENGR", 197, "M", "T", 830, 920, 10, "M", 1600, 1750);
        insertName(ENGR197_10);

        Courses ENGR197_11 = new Courses("ENGR", 197, "M", "T", 830, 920, 11, "R", 1200, 1350);
        insertName(ENGR197_11);


        Courses ENGR292_2 = new Courses("ENGR", 292, "M", "T", 1000, 1250, 2, "T", 1500, 1650);
        insertName(ENGR292_2);

    }

    public void insertCourseMATH()
    {

        Courses MATH5_3 = new Courses("MATH", 5, "M", "W", 1030, 1145, 3, "M", 930, 1120);
        insertName(MATH5_3);

        Courses MATH5_4 = new Courses("MATH", 5, "M", "W", 1030, 1145, 4, "F", 1330, 1520);
        insertName(MATH5_4);

        Courses MATH5_5 = new Courses("MATH", 5, "M", "W", 1030, 1145, 5, "M", 930, 1120);
        insertName(MATH5_5);

        Courses MATH5_6 = new Courses("MATH", 5, "M", "W", 1030, 1145, 6, "W", 1530, 1720);
        insertName(MATH5_6);

        Courses MATH5_7 = new Courses("MATH", 5, "M", "W", 1030, 1145, 7, "R", 1530, 1720);
        insertName(MATH5_7);

        Courses MATH5_8 = new Courses("MATH", 5, "M", "W", 1030, 1145, 8, "R", 1730, 1920);
        insertName(MATH5_8);

        Courses MATH5_9 = new Courses("MATH", 5, "M", "W", 1030, 1145, 9, "R", 1730, 1920);
        insertName(MATH5_9);


        Courses MATH11_2 = new Courses("MATH", 11, "M", "W", 1630, 1720, 2, "T", 1730, 1920);
        insertName(MATH11_2);

        Courses MATH11_3 = new Courses("MATH", 11, "M", "W", 1630, 1820, 3, "W", 1930, 2120);
        insertName(MATH11_3);

        Courses MATH11_4 = new Courses("MATH", 11, "M", "W", 1630, 1820, 4, "W", 1130, 1320);
        insertName(MATH11_4);

        Courses MATH11_5 = new Courses("MATH", 11, "M", "W", 1630, 1820, 5, "T", 1930, 2120);
        insertName(MATH11_5);

        Courses MATH11_6 = new Courses("MATH", 11, "M", "W", 1630, 1820, 6, "W", 1330, 1520);
        insertName(MATH11_6);


        Courses MATH12_2 = new Courses("MATH", 12, "M", "W", 1630, 1720, 2, "F", 1130, 1320);
        insertName(MATH12_2);

        Courses MATH12_3 = new Courses("MATH", 12, "M", "W", 1630, 1720, 3, "M", 930, 1120);
        insertName(MATH12_3);

        Courses MATH12_4 = new Courses("MATH", 12, "M", "W", 1630, 1720, 4, "R", 1730, 1920);
        insertName(MATH12_4);

        Courses MATH12_21 = new Courses("MATH", 12, "M", "W", 1130, 1220, 21, "F", 730, 920);
        insertName(MATH12_21);

        Courses MATH12_22 = new Courses("MATH", 12, "M", "W", 1130, 1220, 22, "F", 930, 1120);
        insertName(MATH12_22);


        Courses MATH15_2 = new Courses("MATH", 15, "M", "W", 1330, 1420, 2, "M", 1530, 1720);
        insertName(MATH15_2);

        Courses MATH15_3 = new Courses("MATH", 15, "M", "W", 1330, 1420, 3, "T", 1130, 1320);
        insertName(MATH15_3);

        Courses MATH15_4 = new Courses("MATH", 15, "M", "W", 1330, 1420, 4, "M", 1930, 2120);
        insertName(MATH15_4);


        Courses MATH21_2 = new Courses("MATH", 21, "M", "W", 1130, 1220, 2, "M", 1730, 1920);
        insertName(MATH21_2);

        Courses MATH21_3 = new Courses("MATH", 21, "M", "W", 1130, 1220, 3, "M", 1930, 2120);
        insertName(MATH21_3);

        Courses MATH21_4 = new Courses("MATH", 21, "M", "W", 1130, 1220, 4, "M", 1330, 1520);
        insertName(MATH21_4);

        Courses MATH21_5 = new Courses("MATH", 21, "M", "W", 1130, 1220, 5, "R", 1530, 1720);
        insertName(MATH21_5);

        Courses MATH21_6 = new Courses("MATH", 21, "M", "W", 1130, 1220, 6, "W", 1530, 1720);
        insertName(MATH21_6);

        Courses MATH21_7 = new Courses("MATH", 21, "M", "W", 1130, 1220, 7, "F", 1530, 1720);
        insertName(MATH21_7);


        Courses MATH22_2 = new Courses("MATH", 22, "M", "W", 1230, 1320, 2, "M", 730, 920);
        insertName(MATH22_2);

        Courses MATH22_3 = new Courses("MATH", 22, "M", "W", 1230, 1320, 3, "M", 930, 1120);
        insertName(MATH22_3);

        Courses MATH22_4 = new Courses("MATH", 22, "M", "W", 1230, 1320, 4, "M", 1930, 2120);
        insertName(MATH22_4);

        Courses MATH22_5 = new Courses("MATH", 22, "M", "W", 1230, 1320, 5, "M", 1330, 1520);
        insertName(MATH22_5);

        Courses MATH22_6 = new Courses("MATH", 22, "M", "W", 1230, 1320, 6, "M", 1630, 1820);
        insertName(MATH22_6);

        Courses MATH22_7 = new Courses("MATH", 22, "M", "W", 1230, 1320, 7, "T", 1830, 2020);
        insertName(MATH22_7);


        Courses MATH23_2 = new Courses("MATH", 23, "M", "W", 1030, 1120, 2, "F", 1130, 1320);
        insertName(MATH23_2);

        Courses MATH23_3 = new Courses("MATH", 23, "M", "W", 1030, 1120, 3, "F", 1330, 1520);
        insertName(MATH23_3);

        Courses MATH23_4 = new Courses("MATH", 23, "M", "W", 1030, 1120, 4, "T", 1730, 1920);
        insertName(MATH23_4);

        Courses MATH23_5 = new Courses("MATH", 23, "M", "W", 1030, 1120, 5, "M", 730, 920);
        insertName(MATH23_5);


        Courses MATH24_2 = new Courses("MATH", 24, "T", "R", 1330, 1445, 2, "M", 730, 920);
        insertName(MATH24_2);

        Courses MATH24_3 = new Courses("MATH", 24, "T", "R", 1330, 1445, 3, "W", 730, 920);
        insertName(MATH24_3);

        Courses MATH24_4 = new Courses("MATH", 24, "T", "R", 1330, 1445, 4, "R", 1530, 1720);
        insertName(MATH24_4);


        Courses MATH32_2 = new Courses("MATH", 32, "M", "W", 1330, 1420, 2, "M", 1430, 1620);
        insertName(MATH32_2);

        Courses MATH32_3 = new Courses("MATH", 32, "M", "W", 1330, 1420, 3, "W", 1530, 1720);
        insertName(MATH32_3);

        Courses MATH32_4 = new Courses("MATH", 32, "M", "W", 1330, 1420, 4, "R", 1530, 1720);
        insertName(MATH32_4);

        Courses MATH32_5 = new Courses("MATH", 32, "M", "W", 1330, 1420, 5, "F", 1430, 1620);
        insertName(MATH32_5);

        Courses MATH32_6 = new Courses("MATH", 32, "M", "W", 1330, 1420, 6, "W", 1930, 2120);
        insertName(MATH32_6);

        Courses MATH32_7 = new Courses("MATH", 32, "M", "W", 1330, 1420, 7, "R", 1930, 2120);
        insertName(MATH32_7);

        Courses MATH32_8 = new Courses("MATH", 32, "M", "W", 1330, 1420, 8, "F", 930, 1120);
        insertName(MATH32_8);

        Courses MATH32_9 = new Courses("MATH", 32, "M", "W", 1330, 1420, 9, "W", 1730, 1920);
        insertName(MATH32_9);


        Courses MATH125_2 = new Courses("MATH", 125, "M", "W", 1000, 1115, 2, "F", 1230, 1320);
        insertName(MATH125_2);

        Courses MATH125_3 = new Courses("MATH", 125, "M", "W", 1000, 1115, 3, "F", 1430, 1520);
        insertName(MATH125_3);


        Courses MATH126_2 = new Courses("MATH", 126, "T", "R", 1030, 1145, 2, "F", 1030, 1120);
        insertName(MATH126_2);

        Courses MATH126_3 = new Courses("MATH", 126, "T", "R", 1030, 1145, 3, "F", 1130, 1220);
        insertName(MATH126_3);


        Courses MATH131_2 = new Courses("MATH", 131, "T", "R", 1330, 1445, 2, "T", 1630, 1720);
        insertName(MATH131_2);

        Courses MATH131_3 = new Courses("MATH", 131, "T", "R", 1330, 1445, 3, "T", 1730, 1820);
        insertName(MATH131_3);

        Courses MATH131_4 = new Courses("MATH", 131, "T", "R", 1330, 1445, 4, "T", 1830, 1920);
        insertName(MATH131_4);

        Courses MATH131_5 = new Courses("MATH", 131, "T", "R", 1330, 1445, 5, "T", 1530, 1620);
        insertName(MATH131_5);

        Courses MATH131_6 = new Courses("MATH", 131, "T", "R", 1330, 1445, 6, "T", 1930, 2020);
        insertName(MATH131_6);


        Courses MATH132_2 = new Courses("MATH", 132, "T", "R", 1200, 1315, 2, "R", 1830, 1920);
        insertName(MATH132_2);


        Courses MATH140_2 = new Courses("MATH", 140, "T", "R", 1630, 1745, 2, "M", 1430, 1520);
        insertName(MATH140_2);


        Courses MATH160_2 = new Courses("MATH", 160, "M", "W", 1630, 1745, 2, "F", 1530, 1800);
        insertName(MATH160_2);


        Courses MATH222_2 = new Courses("MATH", 222, "T", "R", 1200, 1315, 2, "F", 1030, 1120);
        insertName(MATH222_2);


        Courses MATH224_2 = new Courses("MATH", 224, "T", "R", 1330, 1445, 2, "W", 1130, 1220);
        insertName(MATH224_2);


        Courses MATH232_2 = new Courses("MATH", 232, "T", "R", 1030, 1145, 2, "F", 1230, 1320);
        insertName(MATH232_2);


        Courses MATH291_2 = new Courses("MATH", 291, "R", "T", 1500, 1620, 2, "R", 1130, 1420);
        insertName(MATH291_2);

    }

    private String courseId;

    public String setCourseId (String courseId)
    {
        this.courseId = courseId;
        return courseId;
    }


    public List<String> getFavoriteId(){
        List<String> names = new ArrayList<String>();


        // Select AllQuery
        String selectQuery = "SELECT  DISTINCT c_id " +
                " FROM " + TABLE_FAVORITE ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        // While there isnt anything to move next to
        while (cursor.moveToNext())
        {
            names.add(cursor.getString(0));
        }
        // closing connection
        cursor.close();
        // returning names
        return names;
    }

    public ArrayList<Courses> insertFavorite(ArrayList<Courses> course)
    {

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //insert looping through the size of arraylist passed through
        for (int i =0; i < course.size(); i++)
        {
            values.put(COURSE_ID, courseId);
            values.put(COURSE_NAME, course.get(i).getCourseName());
            values.put(COURSE_NUM, course.get(i).getCourseNum());
            values.put(COURSE_DAY1, course.get(i).getDay1());
            values.put(COURSE_DAY2, course.get(i).getDay2());
            values.put(COURSE_START, course.get(i).getStartTime());
            values.put(COURSE_END, course.get(i).getEndTime());
            values.put(COURSE_LAB_NUM, course.get(i).getLabNum());
            values.put(COURSE_LAB_DAY, course.get(i).getLabDay());
            values.put(COURSE_LAB_START, course.get(i).getLabStart());
            values.put(COURSE_LAB_END, course.get(i).getLabEnd());
            db.insert("favorite", null, values);
            Log.d("INSERT Favorite", "VALUES: " + courseId + course.get(i).getCourseName() + course.get(i).getCourseNum() + course.get(i).getDay1());
        }
        return course;
    }

    public ArrayList<Courses> getFavorite(){
        ArrayList<Courses> theList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        String numQuery = "SELECT DISTINCT * " +
                " FROM " + TABLE_FAVORITE  +
                " WHERE c_id = " + "\"" + courseId + "\" "+
                " ORDER BY c_num;";

        //add the data from arraylist to theList to be displayed
        Cursor data = db.rawQuery(numQuery,null);
        while(data.moveToNext()){

            theList.add( new Courses(data.getString(data.getColumnIndex(COURSE_ID))
                    , data.getString(data.getColumnIndex(COURSE_NAME))
                    , data.getInt(data.getColumnIndex(COURSE_NUM))
                    , data.getString(data.getColumnIndex(COURSE_DAY1))
                    , data.getString(data.getColumnIndex(COURSE_DAY2))
                    , data.getInt(data.getColumnIndex(COURSE_START))
                    , data.getInt(data.getColumnIndex(COURSE_END))
                    , data.getInt(data.getColumnIndex(COURSE_LAB_NUM))
                    , data.getString(data.getColumnIndex(COURSE_LAB_DAY))
                    , data.getInt(data.getColumnIndex(COURSE_LAB_START))
                    , data.getInt(data.getColumnIndex(COURSE_LAB_END))));
        }
        data.close();
        return theList;
    }

    //Clear the schedule when done with scheduling
    public void clearSchedule() {

            db.delete(TABLE_SCHEDULE,null,null);
        Log.d("CLEAR DATA", "ITS BEEN DONE");
    }
}


