package com.example.service_novigrad;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class StorageService {
    //only User of type admin can use these methods
    private static final String TAG = "cancelled";
    private final DatabaseReference database;
    private DataSnapshot dataSnapshot;

    public StorageService(){
        this.database = FirebaseDatabase.getInstance().getReference("services");

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
    }
    //edit Service by putting in original name and the new instance of service
    //Could change service or name of service
     public void edit(String originalName, @NonNull Service serviceChanged){
        database.child(originalName).removeValue();
        database.child(serviceChanged.getName()).setValue(serviceChanged);
    }
    //removes service
    public void delete(@NonNull Service service){
        database.child(service.getName()).removeValue();
    }
    public void add(@NonNull Service service){
        database.child(service.getName()).setValue(service);
    }
    //returns Service
    public Service getService(String username){
        return dataSnapshot.child(username).getValue(Service.class);
    }
    //returns true if contains service
    public boolean contains(String serviceName){
        return dataSnapshot.hasChild(serviceName);
    }

    public DatabaseReference getDatabaseRef(){
        return this.database;
    }
}

