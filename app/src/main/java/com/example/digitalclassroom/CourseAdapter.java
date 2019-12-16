package com.example.digitalclassroom;

import androidx.appcompat.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private Context context;
    private List<CourseInfo> courseInfoList;
    private DataBaseHelper dataBaseHelper;
    private String name, email;

    public CourseAdapter(Context context, List<CourseInfo> courseList, String name, String email) {
        this.context = context;
        this.courseInfoList = courseList;
        this.name = name;
        this.email = email;
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.course_item, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CourseViewHolder holder, int position) {
        final int itemPosition = position;
        final CourseInfo courseInfo = courseInfoList.get(position);

        holder.titletext.setText(courseInfo.getTitle());
        holder.codetext.setText(courseInfo.getCode());

        holder.infocourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent info = new Intent(context, CourseInformation.class);
                info.putExtra("Code", courseInfo.getCode());
                info.putExtra("Title", courseInfo.getTitle());
                info.putExtra("room", courseInfo.getRoom());
                info.putExtra("tdays", courseInfo.getTdays());
                info.putExtra("startdate", courseInfo.getSdate());
                info.putExtra("enddate", courseInfo.getEdate());
                info.putExtra("starttime", courseInfo.getStime());
                info.putExtra("endtime", courseInfo.getEtime());
                info.putExtra("days", courseInfo.getDays());
                context.startActivity(info);
            }
        });

        holder.deletecourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure, you want to delete this course?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int arg1) {
                                deleteCourse(itemPosition);
                            }
                        });
                alertDialogBuilder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        holder.editcourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseUpdateFragment courseUpdateFragment = CourseUpdateFragment.newInstance(courseInfo.getCode(), itemPosition, new CourseUpdateListener() {
                    @Override
                    public void onCourseUpdate(CourseInfo courseInfo, int position) {
                        courseInfoList.set(position, courseInfo);
                        notifyDataSetChanged();
                    }
                });
                courseUpdateFragment.show(((Course) context).getSupportFragmentManager(), "Update Course");
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, StudentListActivity.class);
                in.putExtra("name", name);
                in.putExtra("email", email);
                in.putExtra("Code", courseInfo.getCode());
                in.putExtra("Title", courseInfo.getTitle());
                in.putExtra("room", courseInfo.getRoom());
                in.putExtra("tdays", courseInfo.getTdays());
                in.putExtra("startdate", courseInfo.getSdate());
                in.putExtra("enddate", courseInfo.getEdate());
                in.putExtra("starttime", courseInfo.getStime());
                in.putExtra("endtime", courseInfo.getEtime());
                in.putExtra("days", courseInfo.getDays());
                context.startActivity(in);
            }
        });
    }


    private void deleteCourse(int itemPosition) {
        CourseInfo courseInfo = courseInfoList.get(itemPosition);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        long count = dataBaseHelper.deleteselectedCourse(courseInfo.getCode());

        if(count>0){
            courseInfoList.remove(itemPosition);
            notifyDataSetChanged();
            Toast.makeText(context, "Course is deleted successfully",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context, "Course is not deleted. Something wrong!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public int getItemCount() {
        return courseInfoList.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {

        TextView titletext, codetext;
        ImageView infocourse, editcourse, deletecourse;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);

            titletext = itemView.findViewById(R.id.titletextview);
            codetext = itemView.findViewById(R.id.codeview);
            infocourse = itemView.findViewById(R.id.infoimageview);
            editcourse = itemView.findViewById(R.id.editimageview);
            deletecourse = itemView.findViewById(R.id.deleteimageview);
        }
    }
}
