package com.example.service_novigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

public class signup extends AppCompatActivity {
    //TextView usernameText, emailText, passwordText;
    EditText username, email, password, confirmPassword;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        username=(EditText) findViewById(R.id.username_edittext);
        email=(EditText) findViewById(R.id.email_edittext);
        password=(EditText) findViewById(R.id.password_edittext);
        confirmPassword=(EditText) findViewById(R.id.passwordconfirm_edittext);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if all fields are a go, then create a customer
                if( (isValidUsernameOrPassword(username.getText().toString())) && (isValidUsernameOrPassword(password.getText().toString())) && (isValidEmail(email.getText().toString())) && (isUniqueUsername(username.getText().toString())) && (passwordsMatch(password.getText().toString(),confirmPassword.getText().toString()))){
                    Customer customer = new Customer(username.getText().toString(), password.getText().toString(),email.getText().toString());
                    MainActivity.memory.enterUser(customer);
                    Intent intent = new Intent(getApplicationContext(),login.class);
                    startActivity(intent);
                }
                // if all fields together isnt valid, set errors for the things that are not working.
                if (!isValidUsernameOrPassword(username.getText().toString())){
                    username.setError("Invalid Username: Must not be blank or contain spaces");
                }

                if (!isUniqueUsername(username.getText().toString())){
                    username.setError("Username already taken, enter a unique one");
                }

                if (!isValidUsernameOrPassword(password.getText().toString())){
                    password.setError("Invalid Password: Must not be blank or contain spaces");
                }
                if(!isValidEmail(email.getText().toString())){
                    email.setError("Invalid email: Must contain one '@' followed by a '.'");
                }

                if(!passwordsMatch(password.getText().toString(),confirmPassword.getText().toString())){
                    confirmPassword.setError("Passwords do not match!");
                }



            }
        });


    }
    // method to ensure user entered the correct password
    public static boolean passwordsMatch(String password1 , String password2){
        return (password1.equals(password2));
    }

    // making sure the username is unique and not already in database.
    public static boolean isUniqueUsername(String username){
        return !(MainActivity.memory.contains(username));

    }
    // ensures username/password is valid (not empty, nor contains a space)
    public static boolean isValidUsernameOrPassword(String input) {
        if (input.isEmpty()) {
            return false;
        }

        else {
            // SPACES ARE NOT ALLOWED IN THE USERNAME/PASSWORD.
            return !(input.contains(" "));
        }
    }
    // checks if email is valid
    public static boolean isValidEmail(String input) {
        if (input.isEmpty()) {
            return false;
        } else {
            // SPACES ARE NOT ALLOWED IN THE EMAIL.
            if (input.contains(" ")) {
                return false;
            }
            // All emails contain 1 @, followed by a period.
            // example (blahblah@blah.com) is valid
            // bl@hbl@h@bl@h.com is not valid
            // hotmail.com@username is not valid
            int atCounter = 0;
            boolean periodValid = false;

            for (int i = 0; i < input.length(); i++) {
                if (input.charAt(i) == '@') {
                    atCounter++;
                }
                // starts looking for a period after the @
                if (atCounter > 0) {
                    if (input.charAt(i) == '.') {
                        periodValid = true;
                    }
                }
            }
            // true if period is after the @ and only 1 @ occurs
            return (periodValid && (atCounter == 1));
        }
    }

}
