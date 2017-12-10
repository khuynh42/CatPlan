package com.example.matri.catplan;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends LoginActivity{

    SQLiteHandler helper = new SQLiteHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        Button reg = (Button) findViewById(R.id.register);


        reg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                EditText name = (EditText) findViewById(R.id.Sname);
                EditText email = (EditText) findViewById(R.id.email);
                EditText username = (EditText) findViewById(R.id.username);
                EditText password = (EditText) findViewById(R.id.Spassword);
                EditText conpass = (EditText) findViewById(R.id.conpass);

                String namestr = name.getText().toString();
                String emailstr = email.getText().toString();
                String userstr = username.getText().toString();
                String passstr = password.getText().toString();
                String constr = conpass.getText().toString();
                if(passstr.equals(constr))
                {
                    Toast.makeText(getBaseContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                    User c = new User();
                    c.setName(namestr);
                    c.setEmail(emailstr);
                    c.setUname(userstr);
                    c.setPass(passstr);
                    helper.insertContact(c);
                    Intent i = new Intent(SignUp.this,LoginActivity.class);
                    startActivity(i);

                }
                else
                    Toast.makeText(getBaseContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();


            }
        });

    }
}
