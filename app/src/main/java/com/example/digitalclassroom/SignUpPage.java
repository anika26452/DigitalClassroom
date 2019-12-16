package com.example.digitalclassroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpPage extends AppCompatActivity {

    private EditText setname, setemail, setpass, setcpass, setphone;
    private Button save;
    private String name, email, pass, cpass, phone;
    DataBaseHelper dataBaseHelper;
    Teacher teacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        this.setTitle("Sign Up Form");

        setname = findViewById(R.id.setname);
        setemail = findViewById(R.id.setemail);
        setpass = findViewById(R.id.setpass);
        setcpass = findViewById(R.id.confirmpasss);
        setphone = findViewById(R.id.setphone);
        save = findViewById(R.id.savebutton);

        setpass.setTransformationMethod(new PasswordTransformationMethod());
        setcpass.setTransformationMethod(new PasswordTransformationMethod());

        dataBaseHelper = new DataBaseHelper(this);
        SQLiteDatabase sqLiteDatabase  =dataBaseHelper.getWritableDatabase();

        teacher = new Teacher();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = setname.getText().toString().trim();
                email = setemail.getText().toString().trim();
                pass = setpass.getText().toString().trim();
                cpass = setcpass.getText().toString().trim();
                phone = setphone.getText().toString().trim();

                if (name.isEmpty()) {
                    setname.setError("Please Enter your name");
                    setname.requestFocus();
                    return;
                }

                if (email.isEmpty()) {
                    setemail.setError("Please Enter your email address");
                    setemail.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    setemail.setError("Enter a valid email address");
                    setemail.requestFocus();
                    return;
                }
                if (pass.isEmpty()) {
                    setpass.setError("Please Enter Password");
                    setpass.requestFocus();
                    return;
                }
                if (cpass.isEmpty()) {
                    setcpass.setError("Please re-enter password");
                    setcpass.requestFocus();
                    return;
                }
                if (pass.length() < 6) {
                    setpass.setError("Password should have six character");
                    setpass.requestFocus();
                }
                if (!cpass.matches(pass)) {
                    setcpass.setError("Password doesn't match");
                    setcpass.requestFocus();
                    return;
                }
                if (phone.isEmpty()) {
                    setphone.setError("Please Enter your number");
                    setphone.requestFocus();
                    return;
                }
                if (phone.length()!=10) {
                    setphone.setError("Phone number should be 10 digit number");
                    setphone.requestFocus();
                    return;
                }

                teacher.setN(name);
                teacher.setE(email);
                teacher.setPass(pass);
                teacher.setPhone(phone);

                long rowId = dataBaseHelper.insertTeacher(teacher);

                if(rowId>0){

                    Toast.makeText(getApplicationContext(), "Signed Up successfully", Toast.LENGTH_LONG);
                    Toast.makeText(getApplicationContext(), "mail sent", Toast.LENGTH_LONG).show();
                    Thread sender = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String body = "Dear "+name+", you have successfully signed up in Digital Classroom. Thank you!";
                                GMailSender sender = new GMailSender("ayesha.anika1@gmail.com", "ayesha.26452");
                                sender.sendMail("Welcome to DigitalClassroom!",
                                        body,
                                        "ayesha.anika1@gmail.com",
                                        email);
                            } catch (Exception e) {
                                Log.e("SendMail", e.getMessage(), e);
                            }
                        }
                    });
                    sender.start();

                    setname.setText("");
                    setemail.setText("");
                    setpass.setText("");
                    setcpass.setText("");
                    setphone.setText("");

                    startActivity(new Intent(SignUpPage.this, LoginPage.class));
                    finish();

                }else {

                    Toast.makeText(getApplicationContext(), "Signing up is unsuccessfull! This email id is registered already!", Toast.LENGTH_LONG).show();
                    setname.setText("");
                    setemail.setText("");
                    setpass.setText("");
                    setcpass.setText("");
                    setphone.setText("");
                }

            }
        });

    }
}
