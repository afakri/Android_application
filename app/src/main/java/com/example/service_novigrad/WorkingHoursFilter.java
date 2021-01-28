package com.example.service_novigrad;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

public class WorkingHoursFilter extends AppCompatActivity {
    Spinner spinner;
    RecyclerView view ;
    ArrayList<Branch> arrayList;
    TextView start,end;
    Button setend, setstart, search;
    TimePickerDialog timePicker;
    DatabaseReference data;
    branchAdapter adapterbranch;
    TimePickerDialog timePicker2;
    private int endTimeHour,endTimeMin,startTimeHour,startTimeMin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working_hours_filter);
        arrayList = new ArrayList<Branch>();
        adapterbranch= new branchAdapter(this,arrayList);
        view = findViewById(R.id.listview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        view.setLayoutManager(layoutManager);
        view.setAdapter(adapterbranch);
        spinner = findViewById(R.id.spinner);
        start = findViewById(R.id.starttime);
        end = findViewById(R.id.endtime);
        search= findViewById(R.id.searchbutton);
        data = getInstance().getReference("branches");
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot data : snapshot.getChildren()){
                    Branch temp = data.getValue(Branch.class);
                    arrayList.add(temp);

                }
                adapterbranch.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(WorkingHoursFilter.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.days));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        setend= findViewById(R.id.end);
        setstart=findViewById(R.id.start);
        setend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        endTimeHour = hourOfDay;
                        endTimeMin = minute;
                        if (endTimeMin <= 10) {
                            end.setText(hourOfDay + ":0" + endTimeMin);
                        } else {
                            end.setText(hourOfDay + ":" + endTimeMin);
                        }

                    }
                };
                timePicker = new TimePickerDialog(WorkingHoursFilter.this, R.style.AppTheme, listener, 12, 0, false);
                timePicker.updateTime(endTimeHour, endTimeMin);
                timePicker.show();


            }
        });
        setstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener listener2 = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        startTimeHour = hourOfDay;
                        startTimeMin = minute;
                        // startHours.setText(hourOfDay + ":" + startTimeMin);
                        if (startTimeMin <= 10) {
                            start.setText(hourOfDay + ":0" + startTimeMin);
                        } else {
                            start.setText(hourOfDay + ":" + startTimeMin);
                        }

                    }
                };
                timePicker2 = new TimePickerDialog(WorkingHoursFilter.this, R.style.AppTheme, listener2, 12, 0, false);
                timePicker2.updateTime(startTimeHour, startTimeMin);
                timePicker2.show();
                WorkingHours temp = new WorkingHours();
                temp.setStartTime(startTimeHour, startTimeMin);

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] starttime = start.getText().toString().split(":");
                String[] endtime = end.getText().toString().split(":");
                if(start.getText().toString().equals("")||end.getText().toString().equals("")){
                    Toast.makeText(WorkingHoursFilter.this, "Set the start and the end first", Toast.LENGTH_LONG).show();
                }
                else if (Integer.parseInt(starttime[0]) >=Integer.parseInt(endtime[0]) || ((Integer.parseInt(starttime[0]) >=Integer.parseInt(endtime[0]))&&Integer.parseInt(starttime[1]) >= Integer.parseInt(endtime[1]))) {
                    Toast.makeText(WorkingHoursFilter.this, "Error: Verify that your start time is before your endtime, and endtime is not the same as starttime!", Toast.LENGTH_LONG).show();
                }
                else{
                    String day = spinner.getSelectedItem().toString();
                    WorkingHours wh =  new WorkingHours();
                    wh.setStartTime(Integer.parseInt(starttime[0]),Integer.parseInt(starttime[1]));
                    wh.setEndTime(Integer.parseInt(endtime[0]),Integer.parseInt(endtime[1]));
                    int count;
                    switch (day){
                        case "Monday":
                            count = 0;
                            searchbranch(count,wh);
                            break;
                        case "Tuesday":
                            count = 1;
                            searchbranch(count,wh);
                            break;
                        case "Wednesday":
                            count = 2;
                            searchbranch(count,wh);
                            break;
                        case "Thursday":
                            count = 3;
                            searchbranch(count,wh);
                            break;
                        case "Friday":
                            count = 4;
                            searchbranch(count,wh);
                            break;
                        case "Saturday":
                            count = 5;
                            searchbranch(count,wh);
                            break;
                        case "Sunday":
                            count = 6;
                            searchbranch(count,wh);
                            break;

                    }
                }
                }
            });
        }
    private void searchbranch(int day, WorkingHours wh){
        ArrayList<Branch> filteredList = new ArrayList<>();
        for(Branch item : arrayList){
            BranchEmployee temp = MainActivity.memory.getEmployee(item.getEmployeename());
            WorkingHours wh1 = temp.getWorkingHours().get(day);
            if(wh.getStarthour()<=wh1.getStarthour() && wh.getStartmin()<=wh1.getStartmin() &&  wh.getEndhour()<=wh1.getEndhour() && wh.getEndmin()<=wh1.getEndmin()){
                filteredList.add(item);

            }

        }
        adapterbranch.filterList(filteredList);

    }

}

