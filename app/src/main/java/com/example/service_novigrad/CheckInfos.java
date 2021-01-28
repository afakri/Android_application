package com.example.service_novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CheckInfos extends AppCompatActivity {

    ListView lvForms;
    HashMap<String, String> filledform;
    ArrayList< String> keys;
    ArrayList<String> values;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_infos);

        Intent i = getIntent();
        String s = i.getStringExtra("request");
        ServiceRequest serviceRequest = MainActivity.requests.getServiceRequest(s);

        filledform = serviceRequest.getFilledforms();


        keys = new ArrayList<String>();
        for(String key : filledform.keySet()){
            if( ! key.equals("init")){
                keys.add(key);
            }

        }

        lvForms = (ListView) findViewById(R.id.lv_forms) ;

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, keys);
        lvForms.setAdapter(arrayAdapter);

        lvForms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CheckInfos.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.view_form_check, null);
                dialogBuilder.setView(dialogView);
                final TextView textView = (TextView) dialogView.findViewById(R.id.formfilled);
                final TextView textView1 = (TextView) dialogView.findViewById(R.id.customerRes);
                final Button okBtn = (Button) dialogView.findViewById(R.id.button3);
                final AlertDialog b = dialogBuilder.create();
                b.show();

                textView.setText(keys.get(position));
                textView1.setText(filledform.get(keys.get(position)));

                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        b.dismiss();
                    }
                });
            }
        });


    }


    public void reject(View view){
        finish();
    }
}