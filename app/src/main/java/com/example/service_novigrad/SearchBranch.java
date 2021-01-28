package com.example.service_novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchBranch extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference mbase;
    EditText search;
    private branchAdapter adapter;
    private ArrayList<Branch> arrayList;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_branch);
        List<Branch> filteredlist = new ArrayList<>();
        arrayList = new ArrayList<>();
        spinner = findViewById(R.id.spinner2);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==3){
                    Intent intent = new Intent(getApplicationContext(), WorkingHoursFilter.class);
                    startActivity(intent);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(SearchBranch.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.filters));
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter2);
        search = (EditText) findViewById(R.id.mSearchName);
        mbase = MainActivity.db;
        recyclerView= findViewById(R.id.recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter= new branchAdapter(this,arrayList);
        recyclerView.setAdapter(adapter);
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                filter(s.toString());
            }
        });
        mbase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot data : snapshot.getChildren()){
                    Branch temp = data.getValue(Branch.class);
                    arrayList.add(temp);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private void filter(String text) {
        ArrayList<Branch> filteredList = new ArrayList<>();
        for (Branch item : arrayList) {
            if(spinner.getSelectedItem().toString().equals("Name")){
                if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }
            else if(spinner.getSelectedItem().toString().equals("Address")){
                if (item.getAddress().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }
            else if(spinner.getSelectedItem().toString().equals("Service")){
                BranchEmployee temp = MainActivity.memory.getEmployee(item.getEmployeename());
                for(Service service : temp.getServices()){
                    if (service.getName().toLowerCase().contains(text.toLowerCase())){
                        filteredList.add(item);
                        break;
                    }
                }
            }


        }
        adapter.filterList(filteredList);
    }




}