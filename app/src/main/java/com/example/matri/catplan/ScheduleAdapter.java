package com.example.matri.catplan;

/**
 * Created by matri on 12/10/2017.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends ArrayAdapter<Courses>{
    public List<Boolean> check;

    ScheduleAdapter(Context context, List<Courses> courses) {
        super(context, R.layout.activity_schedule, courses);
        check = new ArrayList<Boolean>(courses.size());
        for (int i = 0; i < courses.size(); i++) {
            check.add(i, false);
        }

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View row = inflater.inflate(R.layout.activity_schedule, parent, false);


        Courses c = getItem(position);

        TextView schedule = (TextView) row.findViewById(R.id.schedule);
        CheckBox checkbox = (CheckBox) row.findViewById(R.id.selectedCheckbox);

        schedule.setText(c.getCourseName() + " " + c.getCourseNum() + " " + c.getDay1()  + c.getDay2()+ " "+
                c.getStartTime()+ " - "+ c.getEndTime() +" \nLAB  0" + c.getLabNum() + " " + c.getLabDay()
                + "   " + c.getLabStart() + " - " + c.getLabEnd());
        return row;
    }

    public boolean isChecked(int i) {
        return check.get(i);
    }
}
