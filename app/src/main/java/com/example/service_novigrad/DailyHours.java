package com.example.service_novigrad;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Calendar;

import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

//Still need to connect working hours to branch and database
public class DailyHours  extends AppCompatActivity {

    Button editStartTime, editEndTime, submitHours;
    TextView startHours, hours, day;
    String[] days = {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};
    TimePickerDialog timePicker;
    TimePickerDialog timePicker2;
    int startTimeHour = 0, startTimeMin = 0, endTimeHour = 0, endTimeMin = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_hours);
        String employeename = (String) getIntent().getExtras().get("username");
        editStartTime = (Button) findViewById(R.id.edit_start_time);
        editEndTime = (Button) findViewById(R.id.edit_end_time);
        submitHours = (Button) findViewById(R.id.button2);
        startHours = (TextView) findViewById(R.id.starthours);
        hours = (TextView) findViewById(R.id.hours);
        day = (TextView) findViewById(R.id.day);
        DatabaseReference db = MainActivity.memory.getDatabaseReference();
        BranchEmployee emp = MainActivity.memory.getEmployee(employeename);
        startHours.setText(emp.getWorkingHours().get(WorkingHoursInWeek.flag).getStartTimeTime());
        hours.setText(emp.getWorkingHours().get(WorkingHoursInWeek.flag).getEndTime());


        //text view of day clicked on
        day.setText(days[WorkingHoursInWeek.flag]);
        // when edit end time is clicked on takes to edit end time dialogue and setter
        editEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        endTimeHour = hourOfDay;
                        endTimeMin = minute;
                        WorkingHours wh = emp.getWorkingHours().get(WorkingHoursInWeek.flag);
                        wh.setEndTime(endTimeHour, endTimeMin);
                        db.child(employeename).child("workingHours").child(Integer.toString(WorkingHoursInWeek.flag)).setValue(wh);
                        if (endTimeMin <= 10) {
                            hours.setText(hourOfDay + ":0" + endTimeMin);
                        } else {
                            hours.setText(hourOfDay + ":" + endTimeMin);
                        }

                    }
                };
                timePicker = new TimePickerDialog(DailyHours.this, R.style.AppTheme, listener, 12, 0, false);
                timePicker.updateTime(endTimeHour, endTimeMin);
                timePicker.show();


            }
        });
        // when edit start time is clicked on takes to edit start time dialogue and setter
        editStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener listener2 = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        startTimeHour = hourOfDay;
                        startTimeMin = minute;
                        WorkingHours wh = emp.getWorkingHours().get(WorkingHoursInWeek.flag);
                        wh.setStartTime(startTimeHour, startTimeMin);
                        db.child(employeename).child("workingHours").child(Integer.toString(WorkingHoursInWeek.flag)).setValue(wh);

                        // startHours.setText(hourOfDay + ":" + startTimeMin);
                        if (startTimeMin <= 10) {
                            startHours.setText(hourOfDay + ":0" + startTimeMin);
                        } else {
                            startHours.setText(hourOfDay + ":" + startTimeMin);
                        }

                    }
                };
                timePicker2 = new TimePickerDialog(DailyHours.this, R.style.AppTheme, listener2, 12, 0, false);
                timePicker2.updateTime(startTimeHour, startTimeMin);
                timePicker2.show();
                WorkingHours temp = new WorkingHours();
                temp.setStartTime(startTimeHour, startTimeMin);

            }
        });
        // when submit hours button is clicked on verifies its a valid input and if it is puts to memory and goes back to working hours in week class
        submitHours.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if ((startTimeHour > endTimeHour) || ((startTimeHour == endTimeHour) && (startTimeMin >= endTimeMin))) {
                    Toast.makeText(DailyHours.this, "Error: Verify that your start time is before your endtime, and endtime is not the same as starttime!", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(),WorkingHoursInWeek.class);
                    String s =getIntent().getExtras().getString("username");
                    intent.putExtra("username",s);
                    startActivity(intent);
                }
            }


        });
    }



}


