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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class CheckDocuments extends AppCompatActivity {

    ListView lvForms;
    HashMap<String, Upload> filleddoc;
    ArrayList< String> keys;
    ArrayAdapter<String> arrayAdapter;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_documents);


        Intent i = getIntent();
        String username = i.getStringExtra("request");
        ServiceRequest serviceRequest = MainActivity.requests.getServiceRequest(username);

        filleddoc = serviceRequest.getFilleddocs();


        keys = new ArrayList<String>();
        for(String key : filleddoc.keySet()){
            if( ! key.equals("init")){
                keys.add(key);
            }

        }


        lvForms = (ListView) findViewById(R.id.lv_docs) ;

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, keys);
        lvForms.setAdapter(arrayAdapter);

        mStorageRef = MainActivity.requests.getStorageRef();
        mDatabaseRef = MainActivity.requests.getDatabaseRef();

        lvForms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CheckDocuments.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.view_doc_check, null);
                dialogBuilder.setView(dialogView);
                final TextView textView = (TextView) dialogView.findViewById(R.id.tv1);
                final ImageView imageView = (ImageView) dialogView.findViewById(R.id.image_view1);
                final TextView tv = (TextView) dialogView.findViewById(R.id.textView22);
                final Button okBtn = (Button) dialogView.findViewById(R.id.button9);
                final AlertDialog b = dialogBuilder.create();
                b.show();

//                StorageReference reference = filleddoc.get(keys.get(position)).getImageUrl() ;

                textView.setText(keys.get(position));
                tv.setText("ImageUrl: "+ filleddoc.get(keys.get(position)).getImageUrl());
//                Picasso.with(CheckDocuments.this)
//                        .load(filleddoc.get(keys.get(position)).getImageUrl())
//                        .fit()
//                        .centerCrop()
//                        .into(imageView);



                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        b.dismiss();
                    }
                });
            }
        });

    }

    public void alright(View view){
        finish();
    }
}