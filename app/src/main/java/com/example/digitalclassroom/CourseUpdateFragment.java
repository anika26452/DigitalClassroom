package com.example.digitalclassroom;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Calendar;

public class CourseUpdateFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    private static String fcoursecode;
    private static CourseUpdateListener courseUpdateListener;
    private static int courseItemposition;
    private DatePickerDialog.OnDateSetListener date1, date2;
    private CourseInfo courseInfo;

    private EditText titleupdatetext, codeupdatetext, roomupdatetext, updatesdate, updateedate, stime, etime, daysupdate, totaldaysedit;
    private Button update, cancel;
    private ImageView sdatepdate, edateupdate, stimeupdate, etimeupdate, dropdownupdate;
    String tstring, cstring, rstring, sdatestring, edatestring, stimestring, etimestring, daysstring, tdays;
    String[] listitems;
    boolean[] checkeditems;
    ArrayList<Integer> item = new ArrayList<>();
    int flag;
    private DataBaseHelper dataBaseHelper;

    public CourseUpdateFragment() {
    }
    public static CourseUpdateFragment newInstance(String coursecode, int position, CourseUpdateListener listener){
        fcoursecode = coursecode;
        courseItemposition = position;
        courseUpdateListener = listener;
        CourseUpdateFragment courseUpdateFragment = new CourseUpdateFragment();
        Bundle args = new Bundle();
        args.putString("title", "Update course information");
        courseUpdateFragment.setArguments(args);

        courseUpdateFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return courseUpdateFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.course_update, container, false);

        dataBaseHelper = new DataBaseHelper(getContext());

        titleupdatetext = view.findViewById(R.id.title);
        codeupdatetext  =view.findViewById(R.id.code);
        roomupdatetext = view.findViewById(R.id.room);
        totaldaysedit = view.findViewById(R.id.tdaysupdate);
        updatesdate = view.findViewById(R.id.sdateupdateedittext);
        updateedate = view.findViewById(R.id.edateupdateedittext);
        stime = view.findViewById(R.id.stimeupdateedittext);
        etime = view.findViewById(R.id.etimeupdateedittext);
        daysupdate  =view.findViewById(R.id.daysupdate);
        sdatepdate = view.findViewById(R.id.sdateupdatebutton);
        edateupdate = view.findViewById(R.id.edateupdatebutton);
        stimeupdate = view.findViewById(R.id.stimeupdatebutton);
        etimeupdate  = view.findViewById(R.id.etimeupdatebutton);
        dropdownupdate = view.findViewById(R.id.dropdownupdate);
        update = view.findViewById(R.id.update);
        cancel = view.findViewById(R.id.cancel);

        getDialog().setTitle("Update Course Details");
        listitems = getResources().getStringArray(R.array.Weekdays);
        checkeditems = new boolean[listitems.length];

        courseInfo = dataBaseHelper.getselectedcourse(fcoursecode);

        if(courseInfo!=null) {
            titleupdatetext.setText(courseInfo.getTitle());
            codeupdatetext.setText(courseInfo.getCode());
            roomupdatetext.setText(courseInfo.getRoom());
            totaldaysedit.setText(String.valueOf(courseInfo.getTdays()));
            updatesdate.setText(courseInfo.getSdate());
            updateedate.setText(courseInfo.getEdate());
            stime.setText(courseInfo.getStime());
            etime.setText(courseInfo.getEtime());
            daysupdate.setText(courseInfo.getDays());

            stimeupdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DialogFragment timepicker = new TimePickerFragment();
                    ((TimePickerFragment) timepicker).setListenerupdate(CourseUpdateFragment.this);
                    timepicker.show(getFragmentManager(), "time picker");
                    flag = 1;

                }
            });
            etimeupdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment timepicker = new TimePickerFragment();
                    ((TimePickerFragment) timepicker).setListenerupdate(CourseUpdateFragment.this);
                    timepicker.show(getFragmentManager(), "time picker");
                    flag = 2;
                }
            });
            sdatepdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            getContext(),
                            android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth,
                            date1,
                            year, month, day);
                    datePickerDialog.show();
                }
            });

            date1 = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    month = month+1;
                    String sdate = dayOfMonth+"/"+month+"/"+year;
                    updatesdate.setText(sdate);
                }
            };

            edateupdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            getContext(),
                            android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth,
                            date2,
                            year, month, day);
                    datePickerDialog.show();
                }
            });

            date2 = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    month = month+1;
                    String edate = dayOfMonth+"/"+month+"/"+year;
                    updateedate.setText(edate);
                }
            };

            dropdownupdate.setOnClickListener(new View.OnClickListener() {
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
                            daysupdate.setText(it);
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
                                daysupdate.setText("");
                            }
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tstring  = titleupdatetext.getText().toString();
                    cstring = codeupdatetext.getText().toString();
                    rstring  = roomupdatetext.getText().toString();
                    tdays = totaldaysedit.getText().toString();
                    sdatestring = updatesdate.getText().toString();
                    edatestring = updateedate.getText().toString();
                    stimestring = stime.getText().toString();
                    etimestring  =etime.getText().toString();
                    daysstring = daysupdate.getText().toString();

                    courseInfo.setTitle(tstring);
                    courseInfo.setCode(cstring);
                    courseInfo.setRoom(rstring);
                    courseInfo.setTdays(tdays);
                    courseInfo.setSdate(sdatestring);
                    courseInfo.setEdate(edatestring);
                    courseInfo.setStime(stimestring);
                    courseInfo.setEtime(etimestring);
                    courseInfo.setDays(daysstring);

                    long id = dataBaseHelper.updateCourse(courseInfo);

                    if(id>0){
                        courseUpdateListener.onCourseUpdate(courseInfo, courseItemposition);
                        getDialog().dismiss();
                    }
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDialog().dismiss();
                }
            });
        }
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
                stime.setText(String.valueOf(h-12)+":"+(String.valueOf(m)+" pm"));
            }else if (h==12){
                stime.setText("12:"+(String.valueOf(m)+" pm"));
            }else if (h<12){
                if(h!=0){
                    stime.setText(String.valueOf(h)+":"+(String.valueOf(m)+" am"));
                }
            }
        }
        if(flag == 2){
            if(h>12){
                etime.setText(String.valueOf(h-12)+":"+(String.valueOf(m)+" pm"));
            }else if (h==12){
                etime.setText("12:"+(String.valueOf(m)+" pm"));
            }else if (h<12){
                if(h!=0){
                    etime.setText(String.valueOf(h)+":"+(String.valueOf(m)+" am"));
                }
            }
        }
    }

  /*  @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if(dialog!=null){
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }*/
}
