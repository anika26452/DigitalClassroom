package com.example.digitalclassroom;

import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StudentCreateFragment extends DialogFragment {

    private static String fcode, n, e, t, r, sd, ed, st, et, d;
    private static StudentCreateListener studentCreateListener;

    private EditText snameeditext, sidedittext, semailedittext;
    private Button savebutton, cancelbutton;

    private String namestr, idstr, emailstr;

    public StudentCreateFragment() {
    }
    public static StudentCreateFragment newInstance(String code, String name, String email, String title, String room,String sdate, String edate, String stime, String etime, String days, StudentCreateListener listener){
        n = name;
        e = email;
        t = title;
        fcode = code;
        r = room;
        sd = sdate;
        ed = edate;
        st = stime;
        et = etime;
        d = days;
        studentCreateListener = listener;
        StudentCreateFragment studentCreateFragment = new StudentCreateFragment();

        studentCreateFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        return studentCreateFragment;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState){

        View view = inflater.inflate(R.layout.activity_student_create_fragment, container, false);
        getDialog().setTitle("Add Student");

        snameeditext = view.findViewById(R.id.studentname);
        sidedittext = view.findViewById(R.id.studentid);
        semailedittext = view.findViewById(R.id.studentemail);
        savebutton = view.findViewById(R.id.savestudent);
        cancelbutton = view.findViewById(R.id.cancelstudent);

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                namestr = snameeditext.getText().toString();
                idstr = sidedittext.getText().toString();
                emailstr = semailedittext.getText().toString();

                if(namestr.isEmpty()){
                    snameeditext.setError("Please Enter student's name");
                    snameeditext.requestFocus();
                    return;
                }
                if(idstr.isEmpty()){
                    sidedittext.setError("Please Enter student's id");
                    sidedittext.requestFocus();
                    return;
                }
                if(emailstr.isEmpty()){
                    semailedittext.setError("Please Enter student's email");
                    semailedittext.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(emailstr).matches()) {
                    semailedittext.setError("Enter a valid email address");
                    semailedittext.requestFocus();
                    return;
                }

                Student student = new Student(1, namestr, idstr, emailstr, 0, 0, 0, 0, 0, 0, 0, 0);

                DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());

                long id  = dataBaseHelper.insertStudent(student, fcode);

                if(id>0) {

                    student.setIdt(id);
                    studentCreateListener.onStudentCreated(student);

                    Thread sender = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String body = "Dear " + namestr + ", you are added in a course in Digital Classroom by " + n + ".\nTeacher's email: " + e + "\nCourse Details:\nTitle : " + t + "\nCode : " + fcode + "\nRoom no : " + r + "\nDate : " + sd + "-" + ed + "\nDays : " + d + "\nTime :" + st + "-" + et;
                                GMailSender sender = new GMailSender("ayesha.anika1@gmail.com", "ayesha.26452");
                                sender.sendMail("Added to DigitalClassroom!",
                                        body,
                                        "ayesha.anika1@gmail.com",
                                        emailstr);
                            } catch (Exception e) {
                                Log.e("SendMail", e.getMessage(), e);
                            }
                        }
                    });
                    sender.start();

                    getDialog().dismiss();
                }else {

                    Toast.makeText(getContext(), "This student is added already in this course", Toast.LENGTH_LONG).show();
                }
            }
        });
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;
    }
    @Override
    public void onStart(){
        super.onStart();
        Dialog dialog = getDialog();
        if(dialog!=null){
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}
