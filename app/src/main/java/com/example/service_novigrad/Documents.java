package com.example.service_novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Documents extends AppCompatActivity {
    ArrayList<String> documents ;
    ArrayAdapter<String> dapter;
    ListView listdocs;
    DatabaseReference databaseReference;
    Button add_doc;
    // loads up list_docs xml
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);
        listdocs=(ListView)findViewById(R.id.list_docs);
        databaseReference=FirebaseDatabase.getInstance().getReference("services");
        Intent i = getIntent();
        Bundle b = i.getExtras();
        add_doc=(Button)findViewById(R.id.add_doc);

        if(b!=null){
            String s = b.getString("Service");
            documents=EditServices.memoryService.getService(s).getDocs();
            dapter =  new ArrayAdapter<String>(Documents.this, android.R.layout.simple_list_item_1,documents);
            listdocs.setAdapter(dapter);
            listdocs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                // on click produces screen and dialogue
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Documents.this);
                    LayoutInflater inflater = getLayoutInflater();
                    final View dialogView = inflater.inflate(R.layout.update_delete, null);
                    dialogBuilder.setView(dialogView);
                    final Button update = (Button) dialogView.findViewById(R.id.update);
                    final Button delete = (Button) dialogView.findViewById(R.id.delete);
                    final EditText name = (EditText) dialogView.findViewById(R.id.updatename);
                    final AlertDialog b = dialogBuilder.create();
                    b.show();
                    update.setOnClickListener(new View.OnClickListener() {
                        @Override
                        // data validation; if validated update doc name
                        public void onClick(View v) {
                            if (name.getText().toString().equals("")) {
                                Toast.makeText(getApplicationContext(), "Please enter a name", Toast.LENGTH_LONG).show();
                            }
                            else {
                                databaseReference.child(s).child("docs").child(String.valueOf(position)).setValue(name.getText().toString());
                                documents.set(position, name.getText().toString());
                                dapter.notifyDataSetChanged();
                                b.dismiss();
                            }
                        }
                    });
                    // when delete button is clicked deletes doc
                    delete.setOnClickListener(new View.OnClickListener() {
                        // delete doc
                        @Override
                        public void onClick(View v) {
                            if(documents.size()>1){
                                databaseReference.child(s).child("docs").child(String.valueOf(position)).setValue(null);
                                documents.remove(position);
                                dapter.notifyDataSetChanged();
                                b.dismiss();
                            }
                            else{
                                Toast.makeText(Documents.this," You can't have 0 documents!", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }
            });
            // when add_doc button is clicked adds doc
            add_doc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Documents.this);
                    LayoutInflater inflater = getLayoutInflater();
                    final View dialogView = inflater.inflate(R.layout.add_document_dialog, null);
                    dialogBuilder.setView(dialogView);
                    final Button add = (Button) dialogView.findViewById(R.id.add_document);
                    final EditText name = (EditText) dialogView.findViewById(R.id.doc_name);
                    final AlertDialog b = dialogBuilder.create();
                    b.show();
                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        // create new document
                        public void onClick(View v) {
                            if(!name.getText().toString().equals("")){
                                databaseReference.child(s).child("docs").child(String.valueOf(documents.size())).setValue(name.getText().toString());
                                documents.add(name.getText().toString());
                                dapter.notifyDataSetChanged();
                                Toast.makeText(Documents.this,"Document added", Toast.LENGTH_LONG).show();
                                b.dismiss();
                            }
                            else{
                                Toast.makeText(Documents.this,"Please enter a document name", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            });
        }




    }
}
