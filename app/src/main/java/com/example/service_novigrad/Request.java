package com.example.service_novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Request extends AppCompatActivity {


    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    ListView listOfServices;
    public static ArrayList<Service> services;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        Intent i = getIntent();
        String s = i.getStringExtra("branchEmployee");
        BranchEmployee employee = MainActivity.memory.getEmployee(s);
//        Toast.makeText(this, employee.getBranchname() +" "+ employee.getUsername(), Toast.LENGTH_SHORT).show();
        String s2 = i.getStringExtra("username");
        Customer customer = MainActivity.memory.getCustomer(s2);
        services = employee.getServices();
        for(Service service : services) {
            if (!service.getName().equals("empty")) {
                arrayList.add(service.getName());
            }
        }

        listOfServices = (ListView) findViewById(R.id.servicesToChoose);
        arrayAdapter =  new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        listOfServices.setAdapter(arrayAdapter);

        listOfServices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Service service = services.get(position + 1);
//                Toast.makeText(Request.this, temp.getName() , Toast.LENGTH_SHORT).show();
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Request.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.view_complete_doc_infos, null);
                dialogBuilder.setView(dialogView);
                final Button viewdoc = (Button) dialogView.findViewById(R.id.view_doc2);
                final Button viewinfo = (Button) dialogView.findViewById(R.id.view_info2);
                final AlertDialog b = dialogBuilder.create();
                b.show();
                viewdoc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent docs = new Intent(getApplicationContext(), CompleteDocuments.class);
                        docs.putExtra("Service", service.getName());
                        docs.putExtra("username", customer.getUsername());
                        docs.putExtra("branchEmployee", employee.getUsername());
                        startActivity(docs);
                    }
                });
                viewinfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent info = new Intent(getApplicationContext(), CompleteInfos.class);
                        info.putExtra("Service", service.getName());
                        info.putExtra("username", customer.getUsername());
                        info.putExtra("branchEmployee", employee.getUsername());
                        startActivity(info);
                    }
                });


            }
        });
    }
}