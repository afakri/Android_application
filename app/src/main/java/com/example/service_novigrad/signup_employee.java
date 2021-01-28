package com.example.service_novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

//import static com.example.service_novigrad.signup.isUniqueUsername;

public class signup_employee extends AppCompatActivity {
    EditText username,password,email,address,branchname, confirmPassword;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_employee);

        username = (EditText) findViewById(R.id.username_edittext);
        password= (EditText) findViewById(R.id.password_edittext);
        confirmPassword= (EditText) findViewById(R.id.passwordconfirm_edittext);
        email= (EditText) findViewById(R.id.email_edittext);
        address= (EditText) findViewById(R.id.adressfield);
        branchname= (EditText) findViewById(R.id.branchnamefield);
        submit=(Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( (signup.isValidUsernameOrPassword(username.getText().toString())) && (signup.isValidUsernameOrPassword(password.getText().toString())) && (signup.isValidEmail(email.getText().toString()) ) && (isValidAddress(address.getText().toString())) && (isValidBranchname(branchname.getText().toString())) && (signup.isUniqueUsername(username.getText().toString())) && (signup.passwordsMatch(password.getText().toString(),confirmPassword.getText().toString()))){
                    BranchEmployee employee = new BranchEmployee(username.getText().toString(), password.getText().toString(),email.getText().toString(),branchname.getText().toString(),address.getText().toString());
                    ArrayList<Appointment>appointments = new ArrayList<Appointment>();
                    Appointment appointment = new Appointment("empty","empty","empty");
                    appointments.add(appointment);
                    employee.setAppointments(appointments);
                    MainActivity.memory.enterUser(employee);
                    Branch branch = new Branch(employee.getBranchname(),employee.getAdresse());
                    branch.setEmployeename(employee.getUsername());
                    MainActivity.db.child(branch.getName()).setValue(branch);
                    Intent intent = new Intent(getApplicationContext(),login.class);
                    startActivity(intent);

                }
                
                // check to see if username isnt blank
                if (!signup.isValidUsernameOrPassword(username.getText().toString())){
                    username.setError("Invalid Username: Must not be blank or contain spaces");
                }
                // check to see that username is not already taken
                if (!signup.isUniqueUsername(username.getText().toString())){
                    username.setError("Username already taken, enter a unique one");
                }
                // check to see that password is not blank
                if (!signup.isValidUsernameOrPassword(password.getText().toString())){
                    password.setError("Invalid Password: Must not be blank or contain spaces");
                }
                // checks to see that email is valid
                if(!signup.isValidEmail(email.getText().toString())){
                    email.setError("Invalid email: Must contain one '@' followed by a '.'");
                }
                // checks to see that address is valid
                if (!isValidAddress(address.getText().toString())){
                    address.setError("Invalid address: Must not be blank");
                }
                // checks to see that branch name is valid
                if (!isValidBranchname(branchname.getText().toString())){
                    branchname.setError("Invalid branchname: Must enter an input");
                }
                // checks to see that password confirmation matches password
                if(!signup.passwordsMatch(password.getText().toString(),confirmPassword.getText().toString())){
                    confirmPassword.setError("Passwords do not match!");
                }



            }
        });
    }



    // sees if valid address inputted. 
    private static boolean isValidAddress(String input){
        if (input.equals("")){
            return false;
        }
        else{
            return true;
        }

    }
    // checks to see if branchname is valid.
    private static boolean isValidBranchname(String input){
        return !(input.isEmpty() || input == null);
    }
}
