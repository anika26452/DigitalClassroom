package com.example.digitalclassroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.text.DecimalFormat;


public class StudentProfile extends AppCompatActivity {

    private String sid, sname, semail, code, r, grade = "", point = "", title;
    int position, tday;
    private TextView id, name, email, attendanceday,  ctone, cttwo, ctthree, ctavr,percentage, attendancemark, assignmark, presentmark, midmark, finalmark, resultview;
    private Button result;
    long a, autoid;
    float c1, c2, c3, presen, assign, mid, fin, cavr, total, att;
    long percen, attmark;
    private ImageView attendmail, ctmail, presentmail, assignmail, midmail, resultmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        this.setTitle("Student Profile");


        autoid = getIntent().getLongExtra("autoid", 0);
        tday = getIntent().getIntExtra("tdays", 0);
        sname = getIntent().getStringExtra("studentname");
        semail  =getIntent().getStringExtra("studentemail");
        sid = getIntent().getStringExtra("studentid");
        code = getIntent().getStringExtra("code");
        title = getIntent().getStringExtra("title");
        position = getIntent().getIntExtra("itemposition", 0);
        c1 = getIntent().getFloatExtra("ctone", 0);
        c2 = getIntent().getFloatExtra("cttwo", 0);
        c3 = getIntent().getFloatExtra("ctthree", 0);
        att = getIntent().getFloatExtra("attendance", 0);
        presen = getIntent().getFloatExtra("present", 0);
        assign = getIntent().getFloatExtra("assign", 0);
        mid = getIntent().getFloatExtra("mid", 0);
        fin = getIntent().getFloatExtra("final", 0);
        a = getIntent().getLongExtra("a", 0);

        id = findViewById(R.id.id);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        attendanceday = findViewById(R.id.attendance);
        ctone = findViewById(R.id.ct1);
        cttwo = findViewById(R.id.ct2);
        ctthree = findViewById(R.id.ct3);
        attendancemark = findViewById(R.id.markattendance);
        assignmark = findViewById(R.id.markassignment);
        presentmark = findViewById(R.id.markpresentation);
        midmark = findViewById(R.id.markmid);
        finalmark = findViewById(R.id.markfinal);
        result = findViewById(R.id.result);
        resultview = findViewById(R.id.resultview);
        attendmail = findViewById(R.id.attendancemail);
        ctmail = findViewById(R.id.ctmail);
        assignmail = findViewById(R.id.assignmentmail);
        presentmail = findViewById(R.id.presentationmail);
        midmail = findViewById(R.id.midmail);
        resultmail = findViewById(R.id.resultmail);
        ctavr = findViewById(R.id.ctavr);

        attendanceday.setText(String.valueOf(a));

       // percen = ((a*100)/tday);
        name.setText(sname);
        id.setText(sid);
        email.setText(semail);

        ctone.setText(String.valueOf(c1));
        cttwo.setText(String.valueOf(c2));
        ctthree.setText(String.valueOf(c3));
        presentmark.setText(String.valueOf(presen));
        assignmark.setText(String.valueOf(assign));
        midmark.setText(String.valueOf(mid));
        finalmark.setText(String.valueOf(fin));
        attendancemark.setText(String.valueOf(att));

        attendmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread sender = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String body = "Dear "+sname+", your attendance of "+title+" has sent.\nYour Attendance : "+a+" days.\nThank you.";
                            GMailSender sender = new GMailSender("ayesha.anika1@gmail.com", "ayesha.26452");
                            sender.sendMail("Attendance!",
                                    body,
                                    "ayesha.anika1@gmail.com",
                                    semail);
                        } catch (Exception e) {
                            Log.e("SendMail", e.getMessage(), e);
                        }
                    }
                });
                sender.start();
                Toast.makeText(getApplicationContext(), "Mail sent", Toast.LENGTH_LONG).show();
            }
        });
        cavr = (c1+c2+c3)/3;
        ctavr.setText(new DecimalFormat("##.##").format(cavr));
        ctmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread sender = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String body = "Dear "+sname+", your Class Test marks of "+title+" has sent.\n1st Class Test : "+c1+"\n2nd Class Test : "+c2+"\n3rd Class Test : "+c3+"\nAverage : "+new DecimalFormat("##.##").format(cavr)+"\nThank you.";
                            GMailSender sender = new GMailSender("ayesha.anika1@gmail.com", "ayesha.26452");
                            sender.sendMail("Class Test Marks!",
                                    body,
                                    "ayesha.anika1@gmail.com",
                                    semail);
                        } catch (Exception e) {
                            Log.e("SendMail", e.getMessage(), e);
                        }
                    }
                });
                sender.start();
                Toast.makeText(getApplicationContext(), "Mail sent", Toast.LENGTH_LONG).show();
            }
        });

        presentmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread sender = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String body = "Dear "+sname+", your presentation mark of "+title+" is "+presen+".\nThank you.";
                            GMailSender sender = new GMailSender("ayesha.anika1@gmail.com", "ayesha.26452");
                            sender.sendMail("Presentation Marks!",
                                    body,
                                    "ayesha.anika1@gmail.com",
                                    semail);
                        } catch (Exception e) {
                            Log.e("SendMail", e.getMessage(), e);
                        }
                    }
                });
                sender.start();
                Toast.makeText(getApplicationContext(), "Mail sent", Toast.LENGTH_LONG).show();
            }
        });

        assignmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread sender = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String body = "Dear "+sname+", your assignment mark of "+title+" has sent.You have got "+assign+"\nThank you.";
                            GMailSender sender = new GMailSender("ayesha.anika1@gmail.com", "ayesha.26452");
                            sender.sendMail("Assignment Mark!",
                                    body,
                                    "ayesha.anika1@gmail.com",
                                    semail);
                        } catch (Exception e) {
                            Log.e("SendMail", e.getMessage(), e);
                        }
                    }
                });
                sender.start();
                Toast.makeText(getApplicationContext(), "Mail sent", Toast.LENGTH_LONG).show();
            }
        });

        midmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread sender = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String body = "Dear "+sname+", your mid-term mark of "+title+" has sent.You have got "+mid+"\nThank you.";
                            GMailSender sender = new GMailSender("ayesha.anika1@gmail.com", "ayesha.26452");
                            sender.sendMail("Mid-term Mark!",
                                    body,
                                    "ayesha.anika1@gmail.com",
                                    semail);
                        } catch (Exception e) {
                            Log.e("SendMail", e.getMessage(), e);
                        }
                    }
                });
                sender.start();
                Toast.makeText(getApplicationContext(), "Mail sent", Toast.LENGTH_LONG).show();
            }
        });
        total =att+cavr+attmark+presen+assign+mid+fin;
        r = new DecimalFormat("##.##").format(total);

        if(total>=80.00){
            grade = "A+";
            point = "4.00";
        }else if(total<80.00 && total>=75.00){
            grade = "A";
            point = "3.75";
        }else if(total>=70.00 && total<75.00) {
            grade = "A-";
            point = "3.50";
        }else if(total>=65.00 && total<70.00 ){
            grade = "B+";
            point = "3.25";
        }else if(total>=60.00 && total<65.00){
            grade = "B";
            point = "3.00";
        }else if (total>=55.00 && total<60.00){
            grade = "B-";
            point = "2.75";
        }else if (total>=50.00 && total<55.00){
            grade = "C+";
            point = "2.50";
        }else if(total>=45.00 && total<50.00){
            grade = "C";
            point = "2.25";
        }else if (total>=40.00 && total<45.00){
            grade = "D";
            point = "2.00";
        }
        else {
            grade = "F";
            point = "0.00";
        }


        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultview.setText("Total marks = "+r+"\nLetter Grade = "+grade+"\nGrade point = "+point+"");
                resultmail.setVisibility(View.VISIBLE);
                resultmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Thread sender = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String body = "Dear "+sname+", your result of "+title+" has been sent.\nTotal marks = "+r+"\nLetter Grade = "+grade+"\nGrade point = "+point+"\nThank you.";
                                    GMailSender sender = new GMailSender("ayesha.anika1@gmail.com", "ayesha.26452");
                                    sender.sendMail("Result!",
                                            body,
                                            "ayesha.anika1@gmail.com",
                                            semail);
                                } catch (Exception e) {
                                    Log.e("SendMail", e.getMessage(), e);
                                }
                            }
                        });
                        sender.start();
                        Toast.makeText(getApplicationContext(), "Mail sent", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }
}
