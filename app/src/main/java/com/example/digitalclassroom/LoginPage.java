package com.example.digitalclassroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity implements View.OnClickListener {

    private EditText memail, mpassword;
    private Button login, signup;
    private String email, pass, gname, guname, gemail, gphone;
    DataBaseHelper dataBaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        memail = findViewById(R.id.getemail);
        mpassword = findViewById(R.id.getpass);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);

        dataBaseHelper = new DataBaseHelper(this);
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();

        mpassword.setTransformationMethod(new PasswordTransformationMethod());
        login.setOnClickListener(this);
        signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        email = memail.getText().toString().trim();
        pass = mpassword.getText().toString().trim();

        if(v.getId()==R.id.login){

            if(email.equals("") || pass.equals("")){

                Toast.makeText(getApplicationContext(), "Please Enter all data", Toast.LENGTH_LONG).show();

            }else {

                Boolean result = dataBaseHelper.findTeacher(email, pass);

                if(result==true){

                    Cursor c = dataBaseHelper.DisplayTeacher(email);
                    if(c.getCount()==0){
                        Toast.makeText(getApplicationContext(), "There are no data", Toast.LENGTH_LONG).show();
                    }else {
                        while (c.moveToNext()){
                            gname = c.getString(0);
                            gemail = c.getString(1);
                            gphone = c.getString(2);

                        }

                        //  SaveSharedPreference saveSharedPreference = new SaveSharedPreference();
                       // saveSharedPreference.setUserName(getApplicationContext(), email, pass);
                        Intent home =  new Intent(LoginPage.this, HomeActivity.class);
                        home.putExtra("name", gname);
                        home.putExtra("email", gemail);
                        home.putExtra("phone", gphone);
                        startActivity(home);
                        finish();
                    }

               }else {

                    Toast.makeText(getApplicationContext(), "Email or Password doesn't match", Toast.LENGTH_LONG).show();

                }
            }
        }

        if (v.getId()==R.id.signup){

            startActivity(new Intent(LoginPage.this, SignUpPage.class));
        }
    }
}
