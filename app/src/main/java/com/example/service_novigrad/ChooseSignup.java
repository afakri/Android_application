package com.example.service_novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseSignup extends AppCompatActivity {
    // button for employee signup and customer signup
    Button signup_emp;
    Button signup_cust;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_signup);
        signup_emp = (Button) findViewById(R.id.signupemp);
        signup_cust = (Button) findViewById(R.id.signupcust);
        // when customer button is clicked takes to customer signup page
        signup_cust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),signup.class);
                startActivity(intent);
            }
        });
        // when employee button is clicked takes to employee signup page
        signup_emp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),signup_employee.class);
                startActivity(intent);
            }
        });
    }
}
