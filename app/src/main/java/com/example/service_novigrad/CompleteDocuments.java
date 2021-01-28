package com.example.service_novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class CompleteDocuments extends AppCompatActivity {

    public static Service service;
    public static ServiceRequest serviceRequest;
    public static ArrayList<String> docs;
    public static HashMap<String, Upload> docs2;
    ArrayAdapter<String> arrayAdapter;
    ListView lvdocs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_documents);

        String name = getIntent().getStringExtra("Service");
        for(Service temp : Request.services){
            if(temp.getName().equals(name)){
                service = temp;
            }
        }
        Intent i =getIntent();
        Customer customer = MainActivity.memory.getCustomer(i.getStringExtra("username"));
        BranchEmployee employee = MainActivity.memory.getEmployee(i.getStringExtra("branchEmployee"));
        String serviceName =  employee.getUsername()+","+ service.getName()+","+customer.getUsername();
        lvdocs = (ListView) findViewById(R.id.lv_forms);

        // condition
        if(! MainActivity.requests.contains(serviceName) ){
            serviceRequest = new ServiceRequest( employee, service, customer);
            MainActivity.requests.add(serviceRequest);
        } else {
            serviceRequest = MainActivity.requests.getServiceRequest(serviceName);
        }



        docs = service.getDocs();
        docs2 = serviceRequest.getFilleddocs();
        arrayAdapter = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1, docs );
        lvdocs.setAdapter(arrayAdapter);
        lvdocs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), UploadDocument.class);
                //putEtras
                intent.putExtra("position", Integer.toString(position));
                intent.putExtra("username", customer.getUsername());
                intent.putExtra("BranchEmployee", employee.getUsername());
                startActivity(intent);
            }
        });

    }

    public void Done1(View view){
        if(docs.size()+1 == docs2.size()){
            finish();
        } else {
            Toast.makeText(this, "Please upload all the necessary documents", Toast.LENGTH_SHORT).show();
        }
    }


    public void Cancel1(View view){
        finish();
    }


    }
