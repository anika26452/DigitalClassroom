package com.example.digitalclassroom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Test extends AppCompatActivity {


    TextView textView;
    long a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        textView = findViewById(R.id.text);
        a = getIntent().getLongExtra("a", 0);
        textView.setText(String.valueOf(a));
    }
}
