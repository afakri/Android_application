package com.example.service_novigrad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

public class CustomerControlCentre extends AppCompatActivity {
    Button ChooseBranch, Rate, Status, sendRequest, Search, SetMeeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_control);
        ChooseBranch =(Button) findViewById(R.id.search_branch);
        // When choose branch button is clicked, rates to choose branch class (wip)
        ChooseBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplicationContext(), .class;
                //String s =getIntent().getExtras().getString("username");
                //intent.putExtra("username", s);
                //startActivity(intent);
            }
        });

        Rate = (Button) findViewById(R.id.rate);
        // when Rate Branch button is clicked takes to rate branch 
        Rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChooseBranchtoRate.class);
                String s = getIntent().getStringExtra("username");
                intent.putExtra("username", s);
                startActivity(intent);
            }
        });
        Status = (Button) findViewById(R.id.status);
        // when status button is clicked takes to status class (wip)
        Status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RequestStatus.class);
                String s = getIntent().getStringExtra("username");
                intent.putExtra("username", s);
                startActivity(intent);
            }
        });
        // when search button is clicked takes to branch search class
        Search = (Button) findViewById(R.id.search_branch);
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SearchBranch.class);
                startActivity(intent);
            }
        });
        // when set meeting button is clicked takes to set meeting class
        SetMeeting = (Button) findViewById(R.id.set_meeting);
        SetMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MeetingChooseBranch.class);
                String s = getIntent().getStringExtra("username");
                intent.putExtra("username", s);
                startActivity(intent); }

        });
        // when send request button is clicked takes to send request class
        sendRequest = (Button) findViewById(R.id.sendRequest);
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SendRequest.class);
                String s = getIntent().getStringExtra("username");
                intent.putExtra("username", s);
                startActivity(intent);
            }
        });



    }
}
