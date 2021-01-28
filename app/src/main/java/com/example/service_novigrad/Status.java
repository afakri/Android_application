package com.example.service_novigrad;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Status extends AppCompatActivity {
    TextView status;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_customer);
        status = (TextView)findViewById(R.id.status);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        String s = (String) b.get("request");
        ServiceRequest request = MainActivity.requests.getServiceRequest(s);
        status.setText(request.getStatus());

    }
}
