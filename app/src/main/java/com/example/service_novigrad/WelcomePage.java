package com.example.service_novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;


public class WelcomePage extends AppCompatActivity {
    TextView name,type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        name= (TextView) findViewById(R.id.textView);
        type=(TextView) findViewById(R.id.textView2) ;
        Intent i = getIntent();
        Bundle b = i.getExtras();
        if(b!=null){
            String s = (String) b.get("username");
            User user = MainActivity.memory.getUser(s);
            name.setText(user.getUsername());
            type.setText(user.getFunction());

        }
    }
}