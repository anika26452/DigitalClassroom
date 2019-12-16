package com.example.digitalclassroom;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class StudentUpdateFragment extends DialogFragment {

    private static String studentid, c1str, c2str, c3str, attendstr, prestr, assstr, midstr, finstr;
    private static StudentUpdateListener studentUpdateListener;
    private static int studentItemposition;
    private Student student;

    private EditText nameedit, idedit, emailedit, c1, c2, c3, attend, pres, asse, mide, fine;
    private String namestr, idstr, emailstr;
    private Button saveupdate, cancelupdate;
    private DataBaseHelper dataBaseHelper;
    private float ctm1, ctm2, ctm3, att, prem, assm, midm, finm;

    public StudentUpdateFragment() {
    }

    public static StudentUpdateFragment newInstance(String id, int pos, StudentUpdateListener listener){
        studentid =id;
        studentItemposition = pos;
        studentUpdateListener = listener;
        StudentUpdateFragment studentUpdateFragment = new StudentUpdateFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Title", "Update Student Information");
        studentUpdateFragment.setArguments(bundle);
        studentUpdateFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        return studentUpdateFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.student_update_fragment, container, false);

        dataBaseHelper = new DataBaseHelper(getContext());

        nameedit = view.findViewById(R.id.nameupdate);
        idedit = view.findViewById(R.id.idupdate);
        emailedit = view.findViewById(R.id.emailupdate);
        c1 = view.findViewById(R.id.ct1update);
        c2 = view.findViewById(R.id.ct2update);
        c3 = view.findViewById(R.id.ct3update);
        attend = view.findViewById(R.id.attendanceupdate);
        pres = view.findViewById(R.id.presentupdate);
        asse = view.findViewById(R.id.assignmentupdate);
        mide = view.findViewById(R.id.midupdate);
        fine = view.findViewById(R.id.finalupdate);
        saveupdate = view.findViewById(R.id.saveupdate);
        cancelupdate = view.findViewById(R.id.cancelupdate);

        getDialog().setTitle("Update Student Information");

        student = dataBaseHelper.getselectedStudent(studentid);

        if(student!=null) {
            nameedit.setText(student.getName());
            idedit.setText(student.getSid());
            emailedit.setText(student.getEmail());
            c1.setText(String.valueOf(student.getC1()));
            c2.setText(String.valueOf(student.getC2()));
            c3.setText(String.valueOf(student.getC3()));
            attend.setText(String.valueOf(student.getAtt()));
            pres.setText(String.valueOf(student.getPre()));
            asse.setText(String.valueOf(student.getAss()));
            mide.setText(String.valueOf(student.getMid()));
            fine.setText(String.valueOf(student.getFin()));

            saveupdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    namestr  = nameedit.getText().toString();
                    idstr = idedit.getText().toString();
                    emailstr  = emailedit.getText().toString();
                    c1str = c1.getText().toString();
                    c2str = c2.getText().toString();
                    c3str = c3.getText().toString();
                    attendstr = attend.getText().toString();
                    prestr = pres.getText().toString();
                    assstr = asse.getText().toString();
                    midstr = mide.getText().toString();
                    finstr = fine.getText().toString();

                    ctm1 = Float.parseFloat(c1str);
                    ctm2 = Float.parseFloat(c2str);
                    ctm3 = Float.parseFloat(c3str);
                    att = Float.parseFloat(attendstr);
                    prem = Float.parseFloat(prestr);
                    assm = Float.parseFloat(assstr);
                    midm = Float.parseFloat(midstr);
                    finm = Float.parseFloat(finstr);

                    student.setName(namestr);
                    student.setSid(idstr);
                    student.setEmail(emailstr);
                    student.setC1(ctm1);
                    student.setC2(ctm2);
                    student.setC3(ctm3);
                    student.setAtt(att);
                    student.setPre(prem);
                    student.setAss(assm);
                    student.setMid(midm);
                    student.setFin(finm);

                    long id = dataBaseHelper.updateStudent(student);

                    if(id>0){
                        studentUpdateListener.onStudentUpdate(student, studentItemposition);
                        getDialog().dismiss();
                    }
                }
            });

            cancelupdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDialog().dismiss();
                }
            });
        }
        return view;
    }
}
