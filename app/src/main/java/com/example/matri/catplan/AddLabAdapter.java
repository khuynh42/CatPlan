package com.example.matri.catplan;

/**
 * Created by matri on 12/9/2017.
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
public class AddLabAdapter extends ArrayAdapter<Courses>{
    public List<Boolean> check;

    AddLabAdapter(Context context, List<Courses> courses) {
        super(context, R.layout.activity_add_lab, courses);

        check = new ArrayList<Boolean>(courses.size());
        for (int i = 0; i < courses.size(); i++) {
            check.add(i, false);
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View row = inflater.inflate(R.layout.activity_add_lab, parent, false);


        Courses c = getItem(position);

        TextView courseLab = (TextView) row.findViewById(R.id.courseLab);
        CheckBox checkbox = (CheckBox) row.findViewById(R.id.selectedCheckbox);

        checkbox.setChecked(check.get(position));
        courseLab.setText(c.getCourseName() + " " + c.getCourseNum() + " LAB " + c.getLabNum() + " " + c.getLabDay()
                + " " + c.getLabStart() + " - " + c.getLabEnd());
        return row;
    }

    public boolean isChecked(int i) {
        return check.get(i);
    }
}
