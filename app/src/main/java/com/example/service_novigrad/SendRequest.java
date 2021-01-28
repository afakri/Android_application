package com.example.service_novigrad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;

public class SendRequest extends AppCompatActivity {

    DatabaseReference dataReference = MainActivity.memory.getDatabaseReference();
    ArrayList<String> arrayList = new ArrayList<String>();
    ListView branchList;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_list_rate);

        branchList = (ListView) findViewById(R.id.branchlist);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        HashMap<String, String> hashMap = new HashMap<String, String>();
        branchList.setAdapter(arrayAdapter);
        dataReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.getValue(BranchEmployee.class) != null){
                    BranchEmployee value = snapshot.getValue(BranchEmployee.class);
                    assert value != null;
                    if(value.hasBranchname()) {
                        hashMap.put(value.getBranchname(), value.getUsername());
                        arrayList.add(value.getBranchname());
                        arrayAdapter.notifyDataSetChanged();
                    }
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

        branchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BranchEmployee employee = MainActivity.memory.getEmployee(hashMap.get(arrayList.get(position)));
                Intent control2 =  new Intent(getApplicationContext(), Request.class);
                control2.putExtra("branchEmployee", employee.getUsername());
                Intent i = getIntent();
                Bundle b = i.getExtras();
                if(b!=null){
                    String s = (String) b.get("username");
                    Customer customer = MainActivity.memory.getCustomer(s);
                    control2.putExtra("username", customer.getUsername());
                }
                startActivity(control2);
            }
        });
    }
}