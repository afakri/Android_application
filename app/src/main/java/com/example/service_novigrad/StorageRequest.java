package com.example.service_novigrad;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class StorageRequest {

    private static final String TAG = "cancelled";
    private final DatabaseReference database;
    private DataSnapshot dataSnapshot;
    private final StorageReference storage;
    private DataSnapshot dataSnapshot2;

    public StorageRequest(){
        this.database = FirebaseDatabase.getInstance().getReference("Requests");

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataSnapshot = snapshot;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        database.addValueEventListener(listener);


        this.storage = FirebaseStorage.getInstance().getReference("Requests");

//        ValueEventListener listener1 = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                dataSnapshot2 = snapshot;
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.w(TAG, "loadPost:onCancelled", error.toException());
//            }
//        };
//        this.storage.addValueEventListener(listener1);

    }

    //edit Service by putting in original name and the new instance of service
    //Could change service or name of service
    public void edit(String originalName, @NonNull ServiceRequest serviceChanged){
        database.child(originalName).removeValue();
        database.child(originalName).setValue(serviceChanged);
    }
    //removes service
    public void delete(ServiceRequest service){
        database.child(service.getName()).removeValue();
    }
    public void add(ServiceRequest service){
        database.child(service.getName()).setValue(service);
    }
    //returns service
    public ServiceRequest getServiceRequest(String name){
        return  dataSnapshot.child(name).getValue(ServiceRequest.class);


    }
    //returns true if contains service
    public boolean contains(String name){
        return dataSnapshot.hasChild(name) ;
    }

    public DatabaseReference getDatabaseRef(){
        return this.database;
    }

    public Customer getCustomerAssociated(String name){
        return dataSnapshot.child(name).getValue(Customer.class);
    }
    public StorageReference getStorageRef(){ return this.storage; }

}
