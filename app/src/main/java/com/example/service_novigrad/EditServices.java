package com.example.service_novigrad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.widget.AdapterView;
import java.util.ArrayList;
import java.util.List;

public class EditServices extends AppCompatActivity {
    public static StorageService memoryService;
    public static ArrayList<Service> services;
    ArrayList<String> arrayList = new ArrayList<String>();
    ListView listofservices;
    DatabaseReference databaseservices;
    ArrayAdapter<String> arrayAdapter;
    Button addservices;
    Button removeservice;
    EditText editTextName;
    // activity_edit_services xml
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_services);
        StorageService memoryService = new StorageService();

        this.memoryService = memoryService;
        this.databaseservices = memoryService.getDatabaseRef();
        services = new ArrayList<>();
        listofservices = (ListView) findViewById(R.id.list_services);
        arrayAdapter =  new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        listofservices.setAdapter(arrayAdapter);
        addservices = (Button)findViewById(R.id.add_services);
        removeservice=(Button)findViewById(R.id.delete_service) ;
        // when remove service button is clicked removesservice
        removeservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDeleteService();
            }
        });
        // when add service button is clicked adds service
        addservices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowAddService();
            }
        });

        databaseservices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                services.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Service value = snapshot.getValue(Service.class);
                    arrayList.add(value.getName());
                    services.add(value);
                }
                arrayAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError){

            }

        });
        // when list of services is clicked shows list of services
        listofservices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Service service = services.get(position);
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(EditServices.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.view_doc_info, null);
                dialogBuilder.setView(dialogView);
                final Button viewdoc = (Button) dialogView.findViewById(R.id.view_doc);
                final Button viewinfo = (Button) dialogView.findViewById(R.id.view_info);
                final AlertDialog b = dialogBuilder.create();
                b.show();
                viewdoc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        b.dismiss();
                        Intent docs = new Intent(getApplicationContext(),Documents.class);
                        docs.putExtra("Service",service.getName());
                        startActivity(docs);
                    }
                });
                viewinfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        b.dismiss();
                        Intent info = new Intent(getApplicationContext(),Infos.class);
                        info.putExtra("Service",service.getName());
                        startActivity(info);
                    }
                });

            }
        });





    };
    private void ShowDeleteService() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.remove_dialog, null);
        dialogBuilder.setView(dialogView);
        final EditText nameremove = (EditText) dialogView.findViewById(R.id.nameremove);
        final Button buttonremove = (Button) dialogView.findViewById(R.id.removeservice);
        dialogBuilder.setTitle(nameremove.getText().toString());
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameremove.getText().toString().trim();
                if(memoryService.contains(name)){
                    deleteService(name,b);
                }
                else{
                    Toast.makeText(EditServices.this,"Please enter a valid service name! ", Toast.LENGTH_LONG).show();
                }


            }
        });
    }
    private void ShowAddService() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final Button buttonadd = (Button) dialogView.findViewById(R.id.add_service);
        dialogBuilder.setTitle(editTextName.getText().toString());
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                addService(name,b);

            }
        });
    }
    // deletes service
    private void deleteService(String name,AlertDialog b) {

        if(!TextUtils.isEmpty(name)){
            Service service = new Service(name);
            this.memoryService.delete(service);
            Toast.makeText(this,"Service deleted", Toast.LENGTH_LONG).show();
            b.dismiss();

        }
        else{
            Toast.makeText(this,"Please enter a valid Service name", Toast.LENGTH_LONG).show();
        }
    }
    // adds service
    // data validation: must not be empty or have the same name as an existing
    // service.
    private void addService(String name,AlertDialog b) {
        if(!TextUtils.isEmpty(name) && !(memoryService.contains(name))){
            Service service = new Service(name);
            this.memoryService.add(service);
            Toast.makeText(this,"Service added", Toast.LENGTH_LONG).show();
            b.dismiss();

        }
        else if (memoryService.contains(name)){
            Toast.makeText(this,"Service already exists", Toast.LENGTH_LONG).show();
        }

        else{
            Toast.makeText(this,"Please enter a name", Toast.LENGTH_LONG).show();
        }

    }

}
