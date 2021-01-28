package com.example.service_novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.se.omapi.SEService;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ListOfServicesAvailable extends AppCompatActivity {

    ArrayList<String> arrayList = new ArrayList<String>();
    ListView lvsevices;
    ArrayAdapter<String> arrayAdapter;
    BranchEmployee branchEmployee;
    ArrayList<Service> listServices;
    DatabaseReference databaseReference;
    // activity_list_of_services_available xml
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_services_available);

        databaseReference = FirebaseDatabase.getInstance().getReference("services");
        listServices = new ArrayList<>();
        lvsevices = (ListView) findViewById(R.id.servicesAvailable);
        arrayAdapter =  new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        lvsevices.setAdapter(arrayAdapter);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                listServices.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Service value = snapshot.getValue(Service.class);
                    arrayList.add(value.getName());
                    listServices.add(value);
                }
                arrayAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NotNull DatabaseError databaseError){

            }

        });


        String username = getIntent().getStringExtra("username");
        branchEmployee = MainActivity.memory.getEmployee(username);
        DatabaseReference db = MainActivity.memory.getDatabaseReference();
        // when services button clicked shows list of services available
        lvsevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Service pos = listServices.get(position);

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListOfServicesAvailable.this);
                alertDialog.setTitle(pos.getName());
                alertDialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(branchEmployee.containsService(pos)){
                            Toast.makeText(ListOfServicesAvailable.this, "This Service does already" +
                                    " exists in you BranchEmployee", Toast.LENGTH_SHORT).show();
//                        if(db.child(branchEmployee.getUsername()).child("services"))
                        } else {
                            ArrayList<Service> temp = branchEmployee.getServices();
                            temp.add(pos);
                            branchEmployee.setServices(temp);
                            db.child(branchEmployee.getUsername()).child("services").setValue(temp);
                            EditServicesEmployee.temp.add(pos);
                            EditServicesEmployee.arrayAdapter.add(pos.getName());
                            EditServicesEmployee.arrayAdapter.notifyDataSetChanged();
                            Toast.makeText(ListOfServicesAvailable.this, "The Service "+
                                    pos.getName()+" has been added to you Branch successfully", Toast.LENGTH_SHORT).show();

                        }

                    }
                });
                alertDialog.show();
            }
        });
    }

    public void Done(View view){
        Intent intent = new Intent(getApplicationContext(),EditServicesEmployee.class);
        String s = getIntent().getStringExtra("username");
        intent.putExtra("username",s);
        startActivity(intent);
    }
}
