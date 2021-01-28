package com.example.service_novigrad;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class RequestListforBranch extends AppCompatActivity {

    ArrayList<String> arrayList = new ArrayList<String>();
    ListView requestList;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);


        requestList = (ListView) findViewById(R.id.listrequests);
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
                if(requestS.getEmployee().getUsername().equals(u) && requestS.getStatus().equals("in progress")){
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

//                String request = arrayList.get(position);
//                Intent control2 =  new Intent(getApplicationContext(),CheckInfos.class);
//                control2.putExtra("request", request);
//                startActivity(control2);

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(RequestListforBranch.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.view_validate_request, null);
                dialogBuilder.setView(dialogView);
                final Button viewdoc = (Button) dialogView.findViewById(R.id.button4);
                final Button viewinfo = (Button) dialogView.findViewById(R.id.button6);
                final Button reject = (Button) dialogView.findViewById(R.id.button10);
                final Button validate = (Button) dialogView.findViewById(R.id.button11);
                final AlertDialog b = dialogBuilder.create();
                b.show();


                String request = arrayList.get(position);
                ServiceRequest serviceRequest = MainActivity.requests.getServiceRequest(request);


                viewdoc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String request = arrayList.get(position);
                        Intent docs = new Intent(getApplicationContext(), CheckDocuments.class);
                        docs.putExtra("request", request);
                        startActivity(docs);
                    }
                });
                viewinfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        String request = arrayList.get(position);
                        Intent info = new Intent(getApplicationContext(), CheckInfos.class);
                        info.putExtra("request", request);
                        startActivity(info);
                    }
                });

                validate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        serviceRequest.setStatus("accepted");
//                        MainActivity.requests.edit(request, serviceRequest);
                        MainActivity.requests.getDatabaseRef().child(request).child("status").setValue("accepted");
                        Toast.makeText(RequestListforBranch.this, "Request accepted", Toast.LENGTH_SHORT).show();
                    }
                });

                reject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.requests.getDatabaseRef().child(request).child("status").setValue("rejected");
                        Toast.makeText(RequestListforBranch.this, "Request rejected", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
    }
}
