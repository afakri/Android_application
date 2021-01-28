package com.example.service_novigrad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class RequestStatus extends AppCompatActivity {
    ArrayList<String> arrayList = new ArrayList<String>();
    ListView requestList;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        requestList = (ListView) findViewById(R.id.requestList);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        requestList.setAdapter(arrayAdapter);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        String u = (String) b.get("username");
        MainActivity.requests.getDatabaseRef().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value = snapshot.getValue(ServiceRequest.class).getName();
                ServiceRequest requestS = snapshot.getValue(ServiceRequest.class);
                //String value = snapshot.getValue(ServiceRequest.class).getServicefilled().getName();
                if(requestS.getCustomer().getUsername().equals(u)){
                    arrayList.add(value);
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        requestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String request = arrayList.get(position);
                Intent control2 =  new Intent(getApplicationContext(),Status.class);
                control2.putExtra("request", request);
                startActivity(control2);
            }
        });
    }
}
