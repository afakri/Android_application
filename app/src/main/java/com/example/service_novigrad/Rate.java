package com.example.service_novigrad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

public class Rate extends AppCompatActivity {
    TextView branchname, avrRating;
    RatingBar stars;
    Button submit;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_rate);
            branchname = (TextView) findViewById(R.id.branchName) ;
            avrRating = (TextView) findViewById(R.id.average) ;
            stars = (RatingBar) findViewById(R.id.ratingBar) ;
            submit = (Button) findViewById(R.id.submit) ;
            DatabaseReference db = MainActivity.memory.getDatabaseReference();

            Intent i = getIntent();
            Bundle b = i.getExtras();
            if(b!=null){
                String s = (String) b.get("branchEmployee");
                BranchEmployee employee = MainActivity.memory.getEmployee(s);
                branchname.setText(employee.getBranchname());
                if(employee.getNumRatings() != 0){
                    String rate = "Current branch rating is: " + employee.getRating();
                    avrRating.setText(rate);
                }else{
                    String rate = "not rated";
                    avrRating.setText(rate);
                }

            }

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    float rating = stars.getRating();
                    if(b!=null){
                        String s = (String) b.get("branchEmployee");
                        String u = (String) b.get("username");
                        BranchEmployee employee = MainActivity.memory.getEmployee(s);
                        Customer user = MainActivity.memory.getCustomer(u);
                        if(user.containsRating(employee.getBranchname())){
                            employee.setRating(user.getRating(employee.getBranchname()), rating);
                            user.setRating(employee.getBranchname(), rating);
                            Toast.makeText(getApplicationContext(),"Branch rating updated!",  Toast.LENGTH_LONG).show();

                        }else{
                            user.setRating(employee.getBranchname(), rating);
                            employee.setRating(rating);
                            Toast.makeText(getApplicationContext(),"Branch rated",  Toast.LENGTH_LONG).show();
                        }
                        db.child(user.getUsername()).setValue(user);
                        db.child(employee.getUsername()).setValue(employee);
                        String rate = "Current branch rating is: " + employee.getRating();
                        avrRating.setText(rate);
                    }

                }});
        }
}
