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
import com.roshankc.myclasses.User;


public class MainActivity extends AppCompatActivity {

    private EditText et_emailAddress;
    private EditText et_password;
    private Button btn_submit;
    private TextView tvbtn_create;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb= new DatabaseHelper(this);
        setUpViews();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailAddress= et_emailAddress.getText().toString().trim();
                String password= et_password.getText().toString().trim();
                if (emailAddress.isEmpty()||password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    User user=null;
                    user=myDb.validateUser(emailAddress,password);
                    if(user==null){
                        Toast.makeText(MainActivity.this, "Invalid Email Address or Password", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent= new Intent(MainActivity.this, LoggedInPage.class);
                        intent.putExtra("user",user);
                        et_password.setText("");
                        startActivity(intent);

                    }
                }
            }

        });


        tvbtn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });
    }



    private void setUpViews(){
        et_emailAddress=findViewById(R.id.et_emailAddress);
        et_password=findViewById(R.id.et_password);
        btn_submit=findViewById(R.id.btnSubmit);
        tvbtn_create=findViewById(R.id.tvbtn_create);
    }
}
