package com.example.service_novigrad;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
public class EmployeeViewRating extends AppCompatActivity {
    TextView branchname, avrRating;
    // opens up activity_view_rating xml
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rating);
        branchname = (TextView) findViewById(R.id.branchName) ;
        avrRating = (TextView) findViewById(R.id.rate) ;

        String username= (String)getIntent().getExtras().get("username");
        BranchEmployee employee = MainActivity.memory.getEmployee(username);
        branchname.setText(employee.getBranchname());
        // if ratings exist get average rating
        if(employee.getNumRatings() != 0){
            String rate = String.valueOf(employee.getRating());
            avrRating.setText(rate);
        }else{
            // else its not rated
            String rate = "not rated";
            avrRating.setText(rate);
        }
    }
}
