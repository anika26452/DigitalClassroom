package com.example.digitalclassroom;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class Pdf extends AppCompatActivity {

    private Button pdfcourse, pdfclass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        this.setTitle("Course Curriculum");

        pdfcourse = findViewById(R.id.coursecurriculum);
        pdfclass = findViewById(R.id.classschedule);

        pdfcourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pdfcourse = new Intent(Pdf.this, pdfcoursesurriculum.class);
                startActivity(pdfcourse);
            }
        });

        pdfclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pdfclass = new Intent(Pdf.this, PdfclassSchedule.class);
                startActivity(pdfclass);
            }
        });
    }
}
