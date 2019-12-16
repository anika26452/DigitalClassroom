package com.example.digitalclassroom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class pdfcoursesurriculum extends AppCompatActivity {

    private PDFView pdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        pdf=(PDFView)findViewById(R.id.pdfcoursecurriculum);

        pdf.fromAsset("CSE SYLLABUS 28-04-19_160cr.pdf").load();
    }
}
