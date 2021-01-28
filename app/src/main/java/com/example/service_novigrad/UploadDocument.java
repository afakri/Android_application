package com.example.service_novigrad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class UploadDocument extends AppCompatActivity {


    Button mButtonChooseImage;
    Button mButtonUpload;
    EditText mEditTextFileName ;
    ImageView mImageView ;
    ProgressBar mProgressBar ;
    int position;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    Uri mImageUri;

    private StorageTask mUploadTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_document);

        Intent i = getIntent();
        String s = (String) i.getStringExtra("position");
        position = Integer.parseInt(s);
//        Toast.makeText(this, "the number is the following "+ position, Toast.LENGTH_SHORT).show();
        Customer customer = MainActivity.memory.getCustomer(i.getStringExtra("username"));
        BranchEmployee employee = MainActivity.memory.getEmployee(i.getStringExtra("BranchEmployee"));

        mStorageRef = MainActivity.requests.getStorageRef();
        mDatabaseRef = MainActivity.requests.getDatabaseRef();

         mButtonChooseImage = (Button) findViewById(R.id.button_choose_image);
         mButtonUpload = (Button) findViewById(R.id.button_upload);
         mEditTextFileName = (EditText) findViewById(R.id.edit_text_file_name);
         mImageView = (ImageView) findViewById(R.id.image_view);
         mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);



        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (mUploadTask != null && mUploadTask.isInProgress()) {
                        Toast.makeText(UploadDocument.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                    } else {
                        uploadFile();
                    }
                }
        });
    }


    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null
                && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.with(this).load(mImageUri).into(mImageView);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadFile() {
        if (mImageUri != null) {
            StorageReference fileReference =mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));
            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(UploadDocument.this, "Upload successful", Toast.LENGTH_LONG).show();

                            Upload upload = new Upload(mEditTextFileName.getText().toString().trim(),
                                    taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
//                            String uploadId = mDatabaseRef.child(CompleteDocuments.service.getName()).push().getKey();
//                            mDatabaseRef.child(uploadId).setValue(upload);
                            CompleteDocuments.docs2.put(CompleteDocuments.docs.get(position),
                                    upload);
                            mDatabaseRef.child(CompleteDocuments.serviceRequest.getName()).child("filleddocs").setValue(CompleteDocuments.docs2);


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UploadDocument.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
}
