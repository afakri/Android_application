package com.example.service_novigrad;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;

// list of all branches, when a branch is clicked on takes to next page where available time and day for meeting is chosen
public class MeetingChooseBranch extends AppCompatActivity {
    DatabaseReference dataReference = MainActivity.memory.getDatabaseReference();
    ArrayList<String> arrayList = new ArrayList<String>();
    ListView branchList;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_choosebranch);
        branchList = (ListView) findViewById(R.id.branchlist);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        HashMap<String, String> hashMap = new HashMap<String, String>();
        branchList.setAdapter(arrayAdapter);
        dataReference.addChildEventListener(new ChildEventListener() {
            // lists branches
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
        // when a branch is clicked, takes to date choosing page. MeetingChooseDate.class
        branchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(),ChooseDate.class);
                    String s = hashMap.get(arrayList.get(position));
                    intent.putExtra("employee", s);
                    startActivity(intent);
            }


        });
    }
}
