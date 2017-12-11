package com.example.matri.catplan;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by matri on 12/6/2017.
 */

public class Courses implements Parcelable{

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Courses createFromParcel(Parcel in) {
            return new Courses(in);
        }

        public Courses[] newArray(int size) {
            return new Courses[size];
        }
    };


    private int id;
    private String courseName;
    private int courseNum;
    private String day1;
    private String day2;
    private int startTime;
    private int endTime;
    private int labNum;
    private String labDay;
    private int labStart;
    private int labEnd;




    public Courses(String course_name, int course_num, String day1, String day2, int startTime,
                   int endTime, int lab_num, String lab_day, int lab_start, int lab_end) {
        this.courseName = course_name;
        this.courseNum = course_num;
        this.day1 = day1;
        this.day2 = day2;
        this.startTime = startTime;
        this.endTime = endTime;
        this.labNum = lab_num;
        this.labDay = lab_day;
        this.labStart = lab_start;
        this.labEnd = lab_end;
    }





    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(int courseNum) {
        this.courseNum = courseNum;
    }

    public String getDay1() {
        return day1;
    }

    public void setDay1(String day1) {
        this.day1 = day1;
    }

    public String getDay2() {
        return day2;
    }

    public void setDay2(String day2) {
        this.day2 = day2;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getLabNum() {
        return labNum;
    }

    public void setLabNum(int labNum) {
        this.labNum = labNum;
    }

    public String getLabDay() {
        return labDay;
    }

    public void setLabDay(String labDay) {
        this.labDay = labDay;
    }

    public int getLabStart() {
        return labStart;
    }

    public void setLabStart(int labStart) {
        this.labStart = labStart;
    }

    public int getLabEnd() {
        return labEnd;
    }

    public void setLabEnd(int labEnd) {
        this.labEnd = labEnd;
    }

    @Override
    public String toString() {
        return "courses{"+
                "c_name =" + courseName +
                ", c_num ='" + courseNum + '\'' +
                ", c_day1 ='" + day1 + '\'' +
                ", c_day2 ='" + day2 + '\'' +
                ", c_start ='" + startTime + '\'' +
                ", c_end ='" + endTime + '\'' +
                ", c_lab_num ='" + labNum + '\'' +
                ", c_lab_day ='" + labDay + '\'' +
                ", c_lab_start ='" + labStart + '\'' +
                ", c_lab_end ='" + labEnd +
                '}';
    }

    public Courses(Parcel in){

        this.courseName = in.readString();
        this.courseNum = in.readInt();
        this.day1 = in.readString();
        this.day2 = in.readString();
        this.startTime = in.readInt();
        this.endTime = in.readInt();
        this.labNum = in.readInt();
        this.labDay = in.readString();
        this.labStart = in.readInt();
        this.labEnd = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.courseName);
        dest.writeInt(this.courseNum);
        dest.writeString(this.day1);
        dest.writeString(this.day2);
        dest.writeInt(this.startTime);
        dest.writeInt(this.endTime);
        dest.writeInt(this.labNum);
        dest.writeString(this.labDay);
        dest.writeInt(this.labStart);
        dest.writeInt(this.labEnd);
    }


}
