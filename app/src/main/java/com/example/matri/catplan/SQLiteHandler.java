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

    private static final String POPULATE_DB =
            "INSERT INTO " + TABLE_COURSE +
                    "(c_name, c_num, c_day1, c_day2, c_start, c_end, c_lab_num, c_lab_day, c_lab_start, c_lab_end)" +
                    " VALUES (" + "\"CSE\"," + "\" 140\","+ "\"M\"," + "\"W\"," + " 1030, 1145, 02," + "\"W\"," + " 1330, 1630);";
    private static final String POPULATE_DB2 =
            "INSERT INTO " + TABLE_COURSE +
                    "(c_name, c_num, c_day1, c_day2, c_start, c_end, c_lab_num, c_lab_day, c_lab_start, c_lab_end)" +
                    " VALUES (" + "\"ENGR\"," + "\" 165\","+ "\"M\"," + "\"W\"," + " 1030, 1145, 02," + "\"W\"," + " 1330, 1630);";

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

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL(TABLE_CREATE_COURSE);
        db.execSQL(TABLE_CREATE_SCHEDULE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS" + TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }

    public void insertContact(User c)
    {
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
        db.close();
    }

    public String searchPass(String uname)
    {
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
/*
        Courses ENGR = new Courses("CSE", 100, "M", "W", 1030, 1145, 2, "W", 1330, 1630);
        Courses CSE005 = new Courses("CSE", 5, "M", "W", 1430, 1545, 2, "R", 800, 1100);
        insertName(CSE005);
        Courses CSE015 = new Courses("CSE", 5, "M", "W", 1500, 1615, 2, "M", 730, 1020);
        insertName(CSE015);
        Courses CSE152 = new Courses("CSE", 5, "M", "W", 1500, 1615, 3, "M", 1030, 1320);
        insertName(CSE152);
        Courses CSE020 = new Courses("CSE", 20, "M", "M", 1530, 1620, 2, "F", 1030, 1320);
        insertName(CSE020);
        Courses CSE021 = new Courses("CSE", 21, "M", "M", 1530, 1620, 2, "T", 1330, 1620);
        insertName(CSE021);
        Courses CSE030 = new Courses("CSE", 30, "M", "W", 1330, 1445, 2, "F", 730, 1020);
        insertName(CSE030);
        Courses CSE031 = new Courses("CSE", 31, "M", "W", 1330, 1445, 2, "R", 730, 1020);
        insertName(CSE031);
        Courses CSE100 = new Courses("CSE", 100, "T", "R", 1630, 1745, 2, "M", 1330, 1620);
        insertName(CSE100);
        Courses CSE120 = new Courses("CSE", 120, "W", "F", 1200, 1315, 2, "M", 730, 1020);
        insertName(CSE120);
        Courses CSE140 = new Courses("CSE", 140, "M", "W", 1030, 1145, 2, "W", 1330, 1620);
        insertName(CSE140);
        Courses CSE150 = new Courses("CSE", 150, "M", "W", 700, 815, 2, "W", 1330, 1620);
        insertName(CSE150);
        Courses CSE165 = new Courses("CSE", 165, "T", "R", 900, 1015, 2, "F", 730, 1020);
        insertName(CSE165);
        Courses CSE1652 = new Courses("CSE", 165, "T", "R", 900, 1015, 3, "F", 1330, 1620);
        insertName(CSE1652);
        Courses CSE170 = new Courses("CSE", 170, "M", "W", 900, 1015, 2, "F", 730, 1020);
        insertName(CSE170);
        Courses CSE173 = new Courses("CSE", 173, "T", "R", 1500, 1615, 2, "R", 1630, 1915);
        insertName(CSE173);
        Courses CSE180 = new Courses("CSE", 180, "T", "R", 1500, 1615, 2, "R", 1930, 2220);
        insertName(CSE180);
*/
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
        db.close();

        // returning names
        return names;
    }


    private String courseName;
    private int courseNum;
    private int courseLab;



public String setCourseName (String courseName)
{
    this.courseName = courseName;
    return courseName;
}


    public ArrayList<Courses> getListNum(){
        ArrayList<Courses> theList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        String numQuery = "SELECT DISTINCT * " +
                            " FROM " + TABLE_COURSE+
                            " WHERE c_name = " + "\"" + courseName + "\" "+
                            "ORDER BY c_num;";

        Log.d("DB GET LIST NUM", "Iterating to " + courseName);

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
        db.close();
        return theList;
    }




    public ArrayList<Courses> insertSchedule(ArrayList<Courses> course)
    {


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


        db.close();
        return course;
    }

    public ArrayList<Courses> getSchedule(){
        ArrayList<Courses> theList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        String numQuery = "SELECT DISTINCT * " +
                " FROM " + TABLE_SCHEDULE ;

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
        db.close();

        return theList;
    }

}
