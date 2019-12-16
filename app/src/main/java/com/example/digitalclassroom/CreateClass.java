package com.example.digitalclassroom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;

import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class CreateClass extends DialogFragment implements TimePickerDialog.OnTimeSetListener  {

    private static CreateClassListener createClassListener;
    private EditText titleedit, codeedit, roomedit, sdateedit, edateedit, stimeedit, etimeedit, daysedit, totaldays;
    private Button save,  cancel;
    private ImageView startdate, enddate, starttime, endtime, dropdown;
    private DatePickerDialog.OnDateSetListener dateSetListener1, dateSetListener2;
    DataBaseHelper dataBaseHelper;
    int flag;
    String title, code, room, sdate, edate, stime, etime, temail, days, tdays, codepattern = "[a-za-za-z]+.[1-9]+";
    String[] listitems;
    boolean[] checkeditems;
    ArrayList<Integer> item = new ArrayList<>();

    public CreateClass(){

    }
    public static CreateClass newInstance(String title, CreateClassListener listener){
        createClassListener = listener;
        CreateClass createClass = new CreateClass();
        Bundle args = new Bundle();
        args.putString("title", title);
        createClass.setArguments(args);

        createClass.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        return createClass;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view  = inflater.inflate(R.layout.activity_create_class, container, false);

        Bundle bundle = this.getArguments();
        temail = bundle.getString("email");
        titleedit = view.findViewById(R.id.titleedittext);
        codeedit = view.findViewById(R.id.codeedittext);
        roomedit = view.findViewById(R.id.roomedittext);
        sdateedit = view.findViewById(R.id.startdateedittext);
        edateedit = view.findViewById(R.id.enddateedittext);
        stimeedit = view.findViewById(R.id.starttimeedittext);
        etimeedit = view.findViewById(R.id.endtimeedittext);
        totaldays = view.findViewById(R.id.totaldays);
        save = view.findViewById(R.id.savebutton);
        startdate = view.findViewById(R.id.startdatebutton);
        enddate = view.findViewById(R.id.enddatebutton);
        starttime = view.findViewById(R.id.starttimebutton);
        endtime = view.findViewById(R.id.endtimebutton);
        daysedit = view.findViewById(R.id.days);
        dropdown = view.findViewById(R.id.dropdown);
        cancel = view.findViewById(R.id.cancelbutton);

        listitems = getResources().getStringArray(R.array.Weekdays);
        checkeditems = new boolean[listitems.length];

        dataBaseHelper = new DataBaseHelper(getContext());

        getDialog().setTitle("Create Course");


        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment timepicker = new TimePickerFragment();
                ((TimePickerFragment) timepicker).setListener(CreateClass.this);
                timepicker.show(getFragmentManager(), "time picker");
                flag = 1;

            }
        });
        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timepicker = new TimePickerFragment();
                ((TimePickerFragment) timepicker).setListener(CreateClass.this);
                timepicker.show(getFragmentManager(), "time picker");
                flag = 2;
            }
        });
        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth,
                        dateSetListener1,
                        year, month, day);
                datePickerDialog.show();
            }
        });

        dateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String sdate = dayOfMonth+"/"+month+"/"+year;
                sdateedit.setText(sdate);
            }
        };

        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth,
                        dateSetListener2,
                        year, month, day);
                datePickerDialog.show();
            }
        });

        dateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String edate = dayOfMonth+"/"+month+"/"+year;
                edateedit.setText(edate);
            }
        };

        dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Choose days");
                builder.setMultiChoiceItems(listitems, checkeditems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked){
                            item.add(position);
                        }else {
                            item.remove((Integer.valueOf(position)));
                        }
                    }
                });
                builder.setCancelable(false);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String it = "";
                        for(int i=0; i<item.size(); i++){
                            it = it+listitems[item.get(i)];
                            if(i!=item.size()-1){
                                it = it + ",";
                            }
                        }
                        daysedit.setText(it);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int j=0; j<checkeditems.length; j++){
                            checkeditems[j] = false;
                            item.clear();
                            daysedit.setText("");
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = titleedit.getText().toString().trim();
                code = codeedit.getText().toString().trim();
                room = roomedit.getText().toString().trim();
                sdate = sdateedit.getText().toString().trim();
                edate = edateedit.getText().toString().trim();
                stime = stimeedit.getText().toString().trim();
                etime = etimeedit.getText().toString().trim();
                days = daysedit.getText().toString().trim();
                tdays = totaldays.getText().toString().trim();

                if(title.isEmpty()){
                    titleedit.setError("Please enter course title");
                    titleedit.requestFocus();
                    return;
                }
                if(code.isEmpty()){
                    codeedit.setError("Please enter course code");
                    codeedit.requestFocus();
                    return;
                }
        /*        if(code.matches(codepattern)){
                    codeedit.setError("Please valid course code");
                    codeedit.requestFocus();
                    return;
                }  */
                if(room.isEmpty()){
                    roomedit.setError("Please enter room num");
                    roomedit.requestFocus();
                }
                if(sdate.isEmpty()){
                    sdateedit.setError("Please enter start date");
                    sdateedit.requestFocus();
                }
                if(edate.isEmpty()){
                    edateedit.setError("Please enter end date");
                    edateedit.requestFocus();
                }
                if(stime.isEmpty()){
                    stimeedit.setError("Please enter start time");
                    stimeedit.requestFocus();
                }
                if(etime.isEmpty()){
                    etimeedit.setError("Please enter end time");
                    etimeedit.requestFocus();
                }
                if(tdays.isEmpty()){
                    totaldays.setError("Please amount of class days");
                    totaldays.requestFocus();
                }
                if(days.isEmpty()){
                    daysedit.setError("Please choose days");
                    daysedit.requestFocus();
                }
                CourseInfo courseInfo = new CourseInfo(-1, title, code, room, tdays, sdate, edate, stime, etime, days);

                long rowcourse = dataBaseHelper.insertCourse(courseInfo);

                if(rowcourse>0){
                    courseInfo.setId(rowcourse);
                    createClassListener.onCourseCreate(courseInfo);
                    getDialog().dismiss();
                }else{
                    Toast.makeText(getContext(), "Sorry! There is another course with this title and code", Toast.LENGTH_LONG).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        int h = hourOfDay;
        int m = minute;
        Displaytime(h, m);
    }

    public void Displaytime(int h, int m) {
        if(flag == 1){
            if(h>12){
                stimeedit.setText(String.valueOf(h-12)+":"+(String.valueOf(m)+" pm"));
            }else if (h==12){
                stimeedit.setText("12:"+(String.valueOf(m)+" pm"));
            }else if (h<12){
                if(h!=0){
                    stimeedit.setText(String.valueOf(h)+":"+(String.valueOf(m)+" am"));
                }
            }
        }
        if(flag == 2){
            if(h>12){
                etimeedit.setText(String.valueOf(h-12)+":"+(String.valueOf(m)+" pm"));
            }else if (h==12){
                etimeedit.setText("12:"+(String.valueOf(m)+" pm"));
            }else if (h<12){
                if(h!=0){
                    etimeedit.setText(String.valueOf(h)+":"+(String.valueOf(m)+" am"));
                }
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if(dialog!=null){
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int hieght = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, hieght);
        }
    }
}
