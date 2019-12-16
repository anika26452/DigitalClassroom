package com.example.digitalclassroom;


import android.content.Intent;
import android.os.Bundle;

import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {

    String name, email, phone;
    TextView nametext, emailtext, phonetext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        this.setTitle("Profile");

        Intent profile = getIntent();
        name = profile.getStringExtra("name");
        email = profile.getStringExtra("email");
        phone = profile.getStringExtra("phone");

        nametext = findViewById(R.id.name);
        emailtext = findViewById(R.id.emailtextview);
        phonetext = findViewById(R.id.phonetextview);

        nametext.setText(name);
        emailtext.setText(email);
        phonetext.setText("0"+phone);
    }
}
