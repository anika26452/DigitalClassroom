package com.example.digitalclassroom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class PdfclassSchedule extends AppCompatActivity {

    private PDFView pdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfclass_schedule);

        pdf = findViewById(R.id.pdfclassschedule);

        pdf.fromAsset("Class Schedule for Fall 2019.pdf").load();
    }
}
