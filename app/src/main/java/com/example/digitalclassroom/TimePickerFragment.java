package com.example.digitalclassroom;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment {

    public static TimePickerFragment newInstance() {
        return new TimePickerFragment();
    }

    private TimePickerDialog.OnTimeSetListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), listener, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }
    public void setListener(CreateClass listener) {
        this.listener = listener;
    }
    public void setListenerupdate(CourseUpdateFragment listener) {
        this.listener = listener;
    }


}
