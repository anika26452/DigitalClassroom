package com.example.digitalclassroom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class CourseInformation extends AppCompatActivity {

    private TextView title , code, room, tdyas, sdate, edate, stime, etime, days;
    private String tstr, cstr, rstr,tdsrt, sdstr, edstr, ststr, etstr, dstr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_information);

        tstr = getIntent().getStringExtra("Title");
        cstr = getIntent().getStringExtra("Code");
        rstr = getIntent().getStringExtra("room");
        tdsrt = getIntent().getStringExtra("tdays");
        sdstr = getIntent().getStringExtra("startdate");
        edstr = getIntent().getStringExtra("enddate");
        ststr = getIntent().getStringExtra("starttime");
        etstr = getIntent().getStringExtra("endtime");
        dstr = getIntent().getStringExtra("days");

        title = findViewById(R.id.titleview);
        code = findViewById(R.id.codeview);
        room = findViewById(R.id.roomview);
        tdyas = findViewById(R.id.tdaysview);
        sdate = findViewById(R.id.sdateview);
        edate = findViewById(R.id.edateview);
        stime = findViewById(R.id.stimeview);
        etime = findViewById(R.id.etimeview);
        days = findViewById(R.id.daysview);

        title.setText(tstr);
        code.setText(cstr);
        room.setText(rstr);
        sdate.setText(sdstr);
        edate.setText(edstr);
        stime.setText(ststr);
        etime.setText(etstr);
        days.setText(dstr);
        tdyas.setText(tdsrt);
    }
}
