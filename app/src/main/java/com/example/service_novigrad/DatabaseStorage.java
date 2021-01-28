package com.example.service_novigrad;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatabaseStorage implements UserService{
        //instance variables for firebase
        private static final String TAG = "cancelled";
        private DatabaseReference database;
        private DataSnapshot dataSnapshot;

        public DatabaseStorage(){
                //sets database with reference "users"
                this.database = FirebaseDatabase.getInstance().getReference("users");
                //Creates listener for changes in database and stores locally
                ValueEventListener listener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                                dataSnapshot = snapshot;
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                        }
                };
                database.addValueEventListener(listener);
        }
        //Retrieves user according to username
        public User getUser(String username){
                return dataSnapshot.child(username).getValue(User.class);
        }
        public BranchEmployee getEmployee(String username){
                return dataSnapshot.child(username).getValue(BranchEmployee.class);
        }
        public Customer getCustomer(String username){
                return dataSnapshot.child(username).getValue(Customer.class);
        }
        //add user
        public void enterUser(User user){
                database.child(user.getUsername()).setValue(user);
        }
        //check if user is in database
        public boolean contains(String username){
                return dataSnapshot.hasChild(username);
        }
        //only admin can access method
        //setValue to null
        public void delete(User user){
                database.child(user.getUsername()).removeValue();
        }

        public DatabaseReference getDatabaseReference(){return this.database;}
}