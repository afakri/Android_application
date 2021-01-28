package com.example.service_novigrad;

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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class Infos extends AppCompatActivity {
    ArrayList<String> info ;
    ArrayAdapter<String> dapter;
    ListView listinfo;
    DatabaseReference databaseReference;
    Button add_info;
    // activity_infos xml
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos);
        listinfo=(ListView)findViewById(R.id.list_info);
        databaseReference=FirebaseDatabase.getInstance().getReference("services");
        Intent i = getIntent();
        Bundle b = i.getExtras();
        add_info=(Button)findViewById(R.id.add_info2);


        if(b!=null){
            String s = b.getString("Service");
            info=EditServices.memoryService.getService(s).getForms();
            dapter =  new ArrayAdapter<String>(Infos.this, android.R.layout.simple_list_item_1,info);
            listinfo.setAdapter(dapter);
            listinfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Infos.this);
                    LayoutInflater inflater = getLayoutInflater();
                    final View dialogView = inflater.inflate(R.layout.update_delete, null);
                    dialogBuilder.setView(dialogView);
                    final Button update = (Button) dialogView.findViewById(R.id.update);
                    final Button delete = (Button) dialogView.findViewById(R.id.delete);
                    final EditText name = (EditText) dialogView.findViewById(R.id.updatename);
                    final AlertDialog b = dialogBuilder.create();
                    b.show();
                    // when update button is clicked makes user update info
                    update.setOnClickListener(new View.OnClickListener() {
                        @Override
                        // update info form
                        public void onClick(View v) {
                            if (name.getText().toString().equals("")) {
                                Toast.makeText(getApplicationContext(), "Please enter non blank text", Toast.LENGTH_LONG).show();
                            }
                            else {
                                databaseReference.child(s).child("forms").child(String.valueOf(position)).setValue(name.getText().toString());
                                info.set(position, name.getText().toString());
                                dapter.notifyDataSetChanged();
                                b.dismiss();
                            }
                        }
                    });
                    // when delete button is clicked deletes an info form, but cant have 0 info form
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(info.size()>1){
                                databaseReference.child(s).child("forms").child(String.valueOf(position)).setValue(null);
                                info.remove(position);
                                dapter.notifyDataSetChanged();
                                b.dismiss();
                            }
                            else{
                                Toast.makeText(Infos.this," You can't have 0 info!", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }
            });
            // Add info / form
            add_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Infos.this);
                    LayoutInflater inflater = getLayoutInflater();
                    final View dialogView = inflater.inflate(R.layout.add_info_dialog, null);
                    dialogBuilder.setView(dialogView);
                    final Button add = (Button) dialogView.findViewById(R.id.add_info);
                    final EditText name = (EditText) dialogView.findViewById(R.id.info_name);
                    final AlertDialog b = dialogBuilder.create();
                    b.show();
                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Data validation; set if validated
                            if(!name.getText().toString().equals("")){
                                databaseReference.child(s).child("forms").child(String.valueOf(info.size())).setValue(name.getText().toString());
                                info.add(name.getText().toString());
                                dapter.notifyDataSetChanged();
                                Toast.makeText(Infos.this,"Information added", Toast.LENGTH_LONG).show();
                                b.dismiss();
                            }
                            else{
                                Toast.makeText(Infos.this,"Please enter service information", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            });
        }

    }
}
