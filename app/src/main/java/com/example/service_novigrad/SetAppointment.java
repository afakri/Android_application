package com.example.service_novigrad;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

public class SetAppointment extends AppCompatActivity {
    Button start , end, submit;
    TextView Start,End;
    Spinner days;
    ArrayAdapter<String> arrayAdapter;
    ListView list;
    ArrayList<String> arrayList;
    ArrayList<Appointment> array;
    ArrayList<Appointment> app;
    DatabaseReference db;

    TimePickerDialog timePicker,timePicker2;
    DatabaseStorage fuck;
    int endTimeHour, endTimeMin,startTimeHour,startTimeMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = MainActivity.memory.getDatabaseReference();
        ArrayList<Appointment> array= new ArrayList<Appointment>();
        setContentView(R.layout.activity_set_appointment);
        BranchEmployee temp = MainActivity.memory.getEmployee(EmployeeControlCenter.username);
        app = temp.getAppointments();
        arrayList = new ArrayList<String>();
        list = findViewById(R.id.listapp);
        arrayAdapter =  new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        list.setAdapter(arrayAdapter);
        start = findViewById(R.id.button5);
        end = findViewById(R.id.button7);
        Start= findViewById(R.id.textView17);
        submit= findViewById(R.id.button8);
        End= findViewById(R.id.textView18);
        days = findViewById(R.id.spinnerdays);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(SetAppointment.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.days));
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        days.setAdapter(adapter2);
        for(Appointment app : app){
            if(!app.toString().equals("empty|empty")){
                arrayList.add(days.getSelectedItem().toString()+"|"+app.toString());
            }
        }
        arrayAdapter.notifyDataSetChanged();

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        endTimeHour = hourOfDay;
                        endTimeMin = minute;
                        if (endTimeMin <= 10) {
                            End.setText(hourOfDay + ":0" + endTimeMin);
                        } else {
                            End.setText(hourOfDay + ":" + endTimeMin);
                        }

                    }
                };
                timePicker = new TimePickerDialog(SetAppointment.this, R.style.AppTheme, listener, 12, 0, false);
                timePicker.updateTime(endTimeHour, endTimeMin);
                timePicker.show();


            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener listener2 = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        startTimeHour = hourOfDay;
                        startTimeMin = minute;
                        // startHours.setText(hourOfDay + ":" + startTimeMin);
                        if (startTimeMin <= 10) {
                            Start.setText(hourOfDay + ":0" + startTimeMin);
                        } else {
                            Start.setText(hourOfDay + ":" + startTimeMin);
                        }

                    }
                };
                timePicker2 = new TimePickerDialog(SetAppointment.this, R.style.AppTheme, listener2, 12, 0, false);
                timePicker2.updateTime(startTimeHour, startTimeMin);
                timePicker2.show();
                WorkingHours temp = new WorkingHours();
                temp.setStartTime(startTimeHour, startTimeMin);

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] starttime = Start.getText().toString().split(":");
                String[] endtime = End.getText().toString().split(":");
                if(Start.getText().toString().equals("")||End.getText().toString().equals("")){
                    Toast.makeText(SetAppointment.this, "Set the start and the end first", Toast.LENGTH_LONG).show();
                }
                else if (Integer.parseInt(starttime[0]) >=Integer.parseInt(endtime[0]) || ((Integer.parseInt(starttime[0]) >=Integer.parseInt(endtime[0]))&&Integer.parseInt(starttime[1]) >= Integer.parseInt(endtime[1]))) {
                    Toast.makeText(SetAppointment.this, "Error: Verify that your start time is before your endtime, and endtime is not the same as starttime!", Toast.LENGTH_LONG).show();
                }
                else{
                    Appointment temp = new Appointment(Start.getText().toString(),End.getText().toString(),days.getSelectedItem().toString());
                    arrayList.add(days.getSelectedItem().toString()+"|"+temp.toString());
                    array.add(temp);
                    arrayAdapter.notifyDataSetChanged();
                    db.child(EmployeeControlCenter.username).child("appointments").setValue(array);


                }
            }
        });

    }

}