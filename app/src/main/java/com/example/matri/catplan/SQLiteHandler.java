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

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL(TABLE_CREATE_COURSE);

//        db.execSQL(POPULATE_DB2);
//        db.execSQL(POPULATE_DB);
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
         // Courses ENGR = new Courses("CSE", 100, "M", "W", 1030, 1145, 2, "W", 1330, 1630);
          //insertName(ENGR);
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


    private String c_name;
    public String setCourse(String course_name)
    {
        this.c_name = course_name;
        return course_name;
    }
    private int c_num;
    public int setCourseNum(int c_num)
    {
        this.c_num = c_num;
        return c_num;
    }
    public ArrayList<Courses> getListNum(){
        ArrayList<Courses> theList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        String numQuery = "SELECT DISTINCT * " +
                            " FROM " + TABLE_COURSE+
                            " WHERE c_name = " + "\"" + c_name + "\" "+
                            "ORDER BY c_num;";

        Log.d("DB GET LIST NUM", "Iterating to " + c_name);

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
        c_num =  data.getInt(data.getColumnIndex(COURSE_NUM));

        data.close();
        db.close();
        return theList;
    }



    public ArrayList<Courses> getListLab(){
        ArrayList<Courses> theList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        String numQuery = "SELECT DISTINCT * " +
                " FROM " + TABLE_COURSE +
                " WHERE c_name = " + "\"" + c_name + "\"  and  " +
                        " c_num = " +"\"" + c_num + "\"  and  " +
                " ORDER BY c_num;";


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
