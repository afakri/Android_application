package com.example.service_novigrad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListOfUsers extends AppCompatActivity {


    DatabaseReference dataReference = MainActivity.memory.getDatabaseReference();
    ArrayList<String> arrayList = new ArrayList<String>();
    ListView usersList;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_users);

        usersList = (ListView) findViewById(R.id.usersList);
        arrayAdapter =  new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        usersList.setAdapter(arrayAdapter);
        dataReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value = snapshot.getValue(User.class).getUsername();
                arrayList.add(value);
                arrayAdapter.notifyDataSetChanged();
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

        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = MainActivity.memory.getUser(arrayList.get(position));
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ListOfUsers.this);
                alertDialog.setTitle(user.getUsername());

                if(user.getUsername().equals(MainActivity.memory.getUser("admin").getUsername())){
                    alertDialog.setMessage("This is the admin !");
                } else{
                    alertDialog.setMessage("This "+ user.getFunction()+" is using the following usernmame : "+ user.getUsername()
                        /*+" /n email :"+ user.getEmail()*/);

                }

                alertDialog.setCancelable(false);
                alertDialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!user.getUsername().equals(MainActivity.memory.getUser("admin").getUsername())){
                            if(user.getFunction().equals("Employee")){
                                BranchEmployee emp = MainActivity.memory.getEmployee(user.getUsername());
                                MainActivity.db.child(emp.getBranchname()).removeValue();
                            }
                            MainActivity.memory.delete(user);
                            Toast.makeText(ListOfUsers.this, "This user "+ user.getUsername()+" has been deleted", Toast.LENGTH_SHORT).show();
                            arrayAdapter.remove(user.getUsername());
                            arrayAdapter.notifyDataSetChanged();
                        }
                        else {
                            Toast.makeText(ListOfUsers.this, "The admin cannot be deleted", Toast.LENGTH_SHORT).show();
                        }



                    }
                });
                alertDialog.show();

            }
        });




    }
}