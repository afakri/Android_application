package com.example.service_novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class CompleteInfos extends AppCompatActivity {

    Service service;
    ServiceRequest serviceRequest;
    ArrayList<String> forms;
    HashMap<String, String> forms2;
    ArrayAdapter<String> arrayAdapter;
    ListView lvforms;
    BranchEmployee employee;
    Customer customer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_infos);

        String name = getIntent().getStringExtra("Service");
        for(Service temp : Request.services){
            if(temp.getName().equals(name)){
                service = temp;
            }
        }
        Intent i =getIntent();
         customer = MainActivity.memory.getCustomer(i.getStringExtra("username"));

         employee = MainActivity.memory.getEmployee(i.getStringExtra("branchEmployee"));
        String serviceName =  employee.getUsername()+","+ service.getName()+","+customer.getUsername();
        lvforms = (ListView) findViewById(R.id.lv_forms);

        if(! MainActivity.requests.contains(serviceName) ){
            serviceRequest = new ServiceRequest( employee, service, customer);
            MainActivity.requests.add(serviceRequest);


        } else {
            serviceRequest = MainActivity.requests.getServiceRequest(serviceName);


        }



        forms = service.getForms();

        forms2 = serviceRequest.getFilledforms();


//        Toast.makeText(this, forms2.get("init"), Toast.LENGTH_SHORT).show();

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, forms);
        lvforms.setAdapter(arrayAdapter);

        lvforms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CompleteInfos.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.view_file_form, null);
                dialogBuilder.setView(dialogView);
                final TextView textView = (TextView) dialogView.findViewById(R.id.formToFile);
                final EditText editText = (EditText) dialogView.findViewById(R.id.textToFile);
                final Button submit = (Button) dialogView.findViewById(R.id.submiting);
                final AlertDialog b = dialogBuilder.create();
                b.show();

                textView.setText(forms.get(position));
                if(forms2.containsKey(forms.get(position))){
                    editText.setText((String)forms2.get(forms.get(position)));
                }

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // if no info is put in, don't accept it
                        if (!editText.getText().toString().equals("")) {
                            forms2.put(forms.get(position), editText.getText().toString());
                            serviceRequest.setFilledforms(forms2);
                            Toast.makeText(CompleteInfos.this, "info added successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(CompleteInfos.this, "Error: Empty fields, data not set"+editText.getText().toString(), Toast.LENGTH_SHORT).show();
                        }
                        b.dismiss();

                    }
                });

//

            }
        });

    }


    public void Done2(View view){
        if( forms.size()+1 == forms2.size()){

                MainActivity.requests.edit(employee.getUsername()+","+service.getName()
                +","+ customer.getUsername(),serviceRequest);


            finish();
        }
        else {
            Toast.makeText(this, "Complete all the form", Toast.LENGTH_SHORT).show();
        }

    }

    public void Cancel2(View view){
        finish();
    }
}
