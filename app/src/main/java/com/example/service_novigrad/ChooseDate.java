package com.example.service_novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ChooseDate extends AppCompatActivity {
    ListView view;
    ArrayList<Appointment> app;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_date);
        view = findViewById(R.id.listdates);
        arrayList = new ArrayList<String>();
        arrayAdapter =  new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        view.setAdapter(arrayAdapter);
        String employeename=getIntent().getStringExtra("employee");
        BranchEmployee temp = MainActivity.memory.getEmployee(employeename);
        app = temp.getAppointments();
        for(Appointment ap : app){
            if(!ap.toString().equals("empty|empty")&& !ap.getTaken()){
                arrayList.add(ap.getDay()+"|"+ap.toString());
            }
        }
        arrayAdapter.notifyDataSetChanged();
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
               app.get(position).setTaken(true);
               DatabaseReference k =MainActivity.memory.getDatabaseReference();
               k.child(employeename).child("appointments").setValue(app);
                arrayList.remove(position);
                arrayAdapter.notifyDataSetChanged();
                Toast.makeText(ChooseDate.this, "The appointment was chosen successfully ", Toast.LENGTH_LONG).show();

            }

        });
    }
}