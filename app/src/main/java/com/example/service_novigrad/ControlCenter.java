package com.example.service_novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class ControlCenter extends AppCompatActivity {
    Button EditServices, seeUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_center);
        EditServices=(Button) findViewById(R.id.edit_services);
        // when edit services button is clicked, loads up edit services class
        EditServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),EditServices.class);
                startActivity(intent);
            }
        });
        // when see users button is clicked, loads up list of users class
        seeUsers = (Button) findViewById(R.id.usersbtn);
        seeUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListOfUsers.class);
                startActivity(intent);
            }
        });


    }
}
