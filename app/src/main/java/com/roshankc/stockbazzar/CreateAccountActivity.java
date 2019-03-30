package com.roshankc.stockbazzar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.roshankc.myclasses.DatabaseHelper;


public class CreateAccountActivity extends AppCompatActivity {

    EditText etFirstName, etLastName,etEmailAddress,etPassword,etPasswordAgain;
    Button btnCreate;
    TextView tvBtnLogin;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        setUpViews();
        myDb= new DatabaseHelper(this);
        setUpViews();


        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean flag=false;
                String firstName=etFirstName.getText().toString().trim();
                String lastName=etLastName.getText().toString().trim();
                String emailAddress=etEmailAddress.getText().toString().trim();
                String password=etPassword.getText().toString().trim();
                String passwordAgain=etPasswordAgain.getText().toString().trim();

                if (firstName.isEmpty()||lastName.isEmpty()||emailAddress.isEmpty()||password.isEmpty()||passwordAgain.isEmpty()) {
                    Toast.makeText(CreateAccountActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }else if(!password.equals(passwordAgain)){
                    Toast.makeText(CreateAccountActivity.this, "Passwords Do not match", Toast.LENGTH_SHORT).show();
                }else if(myDb.emailAdreadyExists(emailAddress)){
                    Toast.makeText(CreateAccountActivity.this, "Email Already Exists", Toast.LENGTH_SHORT).show();
                }
                else {
                     flag=myDb.insertData(firstName, lastName, emailAddress, password);
                    if(flag=true){
                        Toast.makeText(CreateAccountActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(CreateAccountActivity.this, MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(CreateAccountActivity.this, "Couldnot create Account", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tvBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateAccountActivity.this, MainActivity.class));
            }
        });
    }



    private void setUpViews(){
        etFirstName=findViewById(R.id.etFirstName);
        etLastName=findViewById(R.id.etLastName);
        etEmailAddress=findViewById(R.id.etEmailAddress);
        etPassword=findViewById(R.id.etPassword);
        etPasswordAgain=findViewById(R.id.etPasswordAgain);
        btnCreate= findViewById(R.id.btnCreate);
        tvBtnLogin=findViewById(R.id.tvBtnLogin);

    }
}
