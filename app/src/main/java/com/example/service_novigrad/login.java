package com.example.service_novigrad;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.EditText;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {
    EditText username,password;
    Button submit;

    TextView t;
    String a=null;
    public static User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=(EditText) findViewById(R.id.username_edittext);
        password=(EditText) findViewById(R.id.password_edittext);
        submit = (Button) findViewById(R.id.submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(username.getText().toString(),password.getText().toString());
                validate(user);
            }

        });
    }


    private void validate(User user){

        User usertest = MainActivity.memory.getUser(user.getUsername());

        if (usertest != null) {
            if(user.getUsername().equals("admin")&&user.getPassword().equals("123admin456")){
                Intent control =  new Intent(getApplicationContext(),ControlCenter.class);
                startActivity(control);
            }
            else if(user.getPassword().equals(usertest.getPassword())&&user.getUsername().equals(usertest.getUsername())){
                String func = usertest.getFunction();
                if(func.equals("Employee")){
                    BranchEmployee temp = MainActivity.memory.getEmployee(user.getUsername());
                    Intent control2 =  new Intent(getApplicationContext(),EmployeeControlCenter.class);
                    control2.putExtra("username",usertest.getUsername());
                    startActivity(control2);

                }else {
                    Intent control3 = new Intent(getApplicationContext(), CustomerControlCentre.class);
                    control3.putExtra("username", usertest.getUsername());
                    startActivity(control3);
                }
            }
            // Incorrect password
            else if (!(user.getPassword().equals(usertest.getPassword())) ){
                password.setError("Wrong password.");

            }

            else{
                // user does not exist
                username.setError("Account does not exist");
                password.setError("Account does not exist");

            }
        }
        // null account
        else{
            username.setError("Account does not exist");
            password.setError("Account does not exist");
        }
    }
}