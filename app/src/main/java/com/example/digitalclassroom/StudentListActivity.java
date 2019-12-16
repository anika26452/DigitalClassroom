package com.example.digitalclassroom;


import android.os.Bundle;
import android.view.View;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StudentListActivity extends AppCompatActivity implements StudentCreateListener {

    private String code, title, name, email, room, sdate, edate, stime, etime, days;
    private int tday;
    DataBaseHelper dataBaseHelper;
    private FloatingActionButton fabbutton;
    private List<Student> studentList = new ArrayList<>();
    private RecyclerView recycler;
    private StudentAdapter studentAdapter;
    private TextView date;
    String d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_list);

        name = getIntent().getStringExtra("name");
        email= getIntent().getStringExtra("email");
        title = getIntent().getStringExtra("Title");
        code = getIntent().getStringExtra("Code");
        room = getIntent().getStringExtra("room");
        tday = getIntent().getIntExtra("tdays", 0);
        sdate = getIntent().getStringExtra("startdate");
        edate = getIntent().getStringExtra("enddate");
        stime = getIntent().getStringExtra("starttime");
        etime = getIntent().getStringExtra("endtime");
        days = getIntent().getStringExtra("days");

        this.setTitle(title);

        dataBaseHelper = new DataBaseHelper(this);

        recycler = findViewById(R.id.recycler);
        fabbutton = findViewById(R.id.fabb);
        date = findViewById(R.id.date);

        d = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        //d = "123";
        date.setText(d);

        studentList.addAll(dataBaseHelper.getAllStudent(code));
        studentList.add(new Student(0,"","","", 0, 0, 0, 0, 0, 0, 0, 0));
        studentAdapter = new StudentAdapter(this, studentList, d, code, title,tday);
        recycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recycler.setAdapter(studentAdapter);


        fabbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStudentCreateDialog();
            }
        });

    }

    private void openStudentCreateDialog() {
        StudentCreateFragment studentCreateFragment = StudentCreateFragment.newInstance(code, name, email, title,  room, sdate, edate, stime, etime, days, this);
        studentCreateFragment.show(getSupportFragmentManager(), "Add students");
    }

    @Override
    public void onStudentCreated(Student student) {
        studentList.add(student);
    }
}
