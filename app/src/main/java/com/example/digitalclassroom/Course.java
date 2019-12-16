package com.example.digitalclassroom;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class Course extends AppCompatActivity implements CreateClassListener{

    DataBaseHelper dataBaseHelper;

    private List<CourseInfo> courseInfoList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CourseAdapter courseAdapter;
    private FloatingActionButton floatbutton;
    private String temail, tname;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        this.setTitle("Course List");

        tname = getIntent().getStringExtra("name");
        temail = getIntent().getStringExtra("email");

        dataBaseHelper = new DataBaseHelper(this);
        recyclerView = findViewById(R.id.recycler);
        floatbutton = findViewById(R.id.fab);

        courseInfoList.addAll(dataBaseHelper.getAllCourse());

        courseAdapter = new CourseAdapter(this, courseInfoList, tname, temail);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(courseAdapter);

        floatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createclassDialog();
            }
        });
    }

    private void createclassDialog() {
        CreateClass createClass = CreateClass.newInstance("Create Course",this);
        createClass.show(getSupportFragmentManager(), "Create Course");
    }

    @Override
    public void onCourseCreate(CourseInfo courseInfo) {
        courseInfoList.add(courseInfo);
        courseAdapter.notifyDataSetChanged();
    }
}
