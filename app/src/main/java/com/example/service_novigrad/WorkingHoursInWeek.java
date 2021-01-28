package com.example.service_novigrad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

// allows for choosing days where working hours will be set
public class WorkingHoursInWeek  extends AppCompatActivity {
    Button monday_Button, tuesday_Button, wednesday_Button, thursday_Button,
            friday_Button, saturday_Button, sunday_Button;
    public static int flag;
    public static ArrayList<WorkingHours> week_hours;
    // using activity_list_of_days xml
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        week_hours = new ArrayList<WorkingHours>();
        String username= (String)getIntent().getExtras().get("username");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_days);
        // buttons for each day
        monday_Button =(Button) findViewById(R.id.monday);
        tuesday_Button =(Button) findViewById(R.id.tuesday);
        wednesday_Button =(Button) findViewById(R.id.wednesday);
        monday_Button =(Button) findViewById(R.id.monday);
        thursday_Button =(Button) findViewById(R.id.thursday);
        friday_Button =(Button) findViewById(R.id.friday);
        saturday_Button =(Button) findViewById(R.id.saturday);
        sunday_Button =(Button) findViewById(R.id.sunday);
        
        // monday button: takes to setting monday hours
        monday_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 0;
                Intent intent = new Intent(getApplicationContext(),DailyHours.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
        // tuesday button: takes to setting tuesday hours
        tuesday_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                Intent intent = new Intent(getApplicationContext(),DailyHours.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
        // wednesday button: takes to setting wednesday hours
        wednesday_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 2;
                Intent intent = new Intent(getApplicationContext(),DailyHours.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
        // thursday button: takes to setting thursday hours
        thursday_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 3;
                Intent intent = new Intent(getApplicationContext(),DailyHours.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
        // friday button: takes to setting friday hours
        friday_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 4;
                Intent intent = new Intent(getApplicationContext(),DailyHours.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
        // saturday button: takes to setting saturday hours
        saturday_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 5;
                Intent intent = new Intent(getApplicationContext(),DailyHours.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });
        // sunday button: takes to setting sunday hours
        sunday_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 6;
                Intent intent = new Intent(getApplicationContext(),DailyHours.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

    }
}
