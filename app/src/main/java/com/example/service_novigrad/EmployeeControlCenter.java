package com.example.service_novigrad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

public class EmployeeControlCenter extends AppCompatActivity {
    Button WorkingHours, EditServices, Rating, setAppointment, requestsCustomers;
    static String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_control);
        username =getIntent().getExtras().getString("username");
        WorkingHours =(Button) findViewById(R.id.workinghours);
        WorkingHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),WorkingHoursInWeek.class);
                String s =getIntent().getExtras().getString("username");
                intent.putExtra("username",s);
                startActivity(intent);
            }
        });
        setAppointment = findViewById(R.id.appointment);
        setAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SetAppointment.class);
                String s =getIntent().getExtras().getString("username");
                intent.putExtra("username",s);
                startActivity(intent);
            }
        });

        EditServices= (Button) findViewById(R.id.edit_services);
        EditServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditServicesEmployee.class);
                String s = getIntent().getStringExtra("username");
                intent.putExtra("username", s);
                startActivity(intent);
            }
        });

        Rating = (Button) findViewById(R.id.rating);
        Rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EmployeeViewRating.class);
                String s = getIntent().getExtras().getString("username");
                intent.putExtra("username", s);
                startActivity(intent);
            }
        });

        requestsCustomers = (Button) findViewById(R.id.requests);
        requestsCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RequestListforBranch.class);
                String s = getIntent().getExtras().getString("username");
                intent.putExtra("username", s);
                startActivity(intent);
            }
        });


    }
}
