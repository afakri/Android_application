package com.example.service_novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {


    public static DatabaseStorage memory;
    public static StorageRequest requests;
    public static DatabaseReference db;
    Button signup_Button,login_Button;
    public static Admin admin;
    
    // main screen, brings up login and signup. 
    // uses activity_main xml
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseDatabase.getInstance().getReference("branches");
        signup_Button =(Button) findViewById(R.id.signup_button);
        memory = new DatabaseStorage();;
        requests = new StorageRequest();
        login_Button=(Button) findViewById(R.id.login_button);
        // when login button is pressed takes to login page
        login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),login.class);
                startActivity(intent);
            }
        });
        // when signup button is pressed takes to signup
        signup_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),ChooseSignup.class);
                startActivity(intent1);
            }
        });

    }
}
