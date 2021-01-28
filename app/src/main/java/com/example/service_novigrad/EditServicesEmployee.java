package com.example.service_novigrad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class EditServicesEmployee extends AppCompatActivity {

    ArrayList<String> arrayList = new ArrayList<String>();
    ListView lvsevices;
    public static ArrayAdapter<String> arrayAdapter;
    public static ArrayList<Service> temp;
    BranchEmployee branchEmployee;


    // activity_edit_services_employee xml
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_services_employee);



        String s = getIntent().getStringExtra("username");
        branchEmployee = MainActivity.memory.getEmployee(s);

//        ArrayList<Service> temp = branchEmployee.getServices();

        temp = branchEmployee.getServices();
        // if the service name isnt empty list it
        for(Service service: temp){
            if(!service.getName().equals("empty")){
                arrayList.add(service.getName());
            }

        }
        lvsevices = (ListView) findViewById(R.id.lvservices);
        arrayAdapter =  new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        lvsevices.setAdapter(arrayAdapter);
        DatabaseReference db = MainActivity.memory.getDatabaseReference();
        // when a service is clicked adds dialogue
        lvsevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Service pos = temp.get(position+1);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditServicesEmployee.this);
                alertDialog.setTitle(pos.getName());
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
                        Toast.makeText(EditServicesEmployee.this, "This service : "+
                                pos.getName()+ " has been deleted successfully", Toast.LENGTH_SHORT).show();
                        ArrayList<Service> temp = branchEmployee.getServices() ;
                        temp.remove(pos);
                        branchEmployee.setServices(temp);
                        db.child(branchEmployee.getUsername()).child("services").setValue(temp);
                        arrayAdapter.remove(pos.getName());
                        arrayAdapter.notifyDataSetChanged();
                    }
                });
                alertDialog.show();
            }
        });





    }

    public void AddService(View view){


        Intent intent = new Intent(getApplicationContext(), ListOfServicesAvailable.class);
        intent.putExtra("username", branchEmployee.getUsername());
        startActivity(intent);
    }
}
