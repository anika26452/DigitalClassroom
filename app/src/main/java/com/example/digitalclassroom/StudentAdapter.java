package com.example.digitalclassroom;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AlertDialog;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private Context context;
    private List<Student> studentList;
    private String currentdate;
    private String code, title;
    private int tday;

    public StudentAdapter(Context context, List<Student> studentList, String currentdate, String code, String title, int tday){
        this.context = context;
        this.studentList = studentList;
        this.currentdate = currentdate;
        this.code = code;
        this.title = title;
        this.tday = tday;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.student_item, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StudentViewHolder holder, final int position) {
        final int listposition = position;
        final Student student = studentList.get(position);

        holder.idtext.setText(student.getSid());
        if (student.getSid() == "") {
            holder.p.setVisibility(View.GONE);
            holder.a.setVisibility(View.INVISIBLE);
            holder.itemView.setBackgroundResource(R.color.BackgroundColor);
            holder.itemView.setEnabled(false);
        }
        holder.p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
                long r = dataBaseHelper.insertAttendance(code, student.getIdt(), currentdate);
                if (r > 0) {
                    Toast.makeText(context, "Attendance counted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Attendance is already counted!", Toast.LENGTH_LONG).show();
                }
            }
        });
        holder.a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
                long i = dataBaseHelper.deleteAttendance(code, student.getIdt(), currentdate);
                if (i > 0) {
                    Toast.makeText(context, "Attendance removed!", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(context, "Absence!", Toast.LENGTH_LONG).show();
                }

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
                long a = dataBaseHelper.getAttendance(code, student.getIdt());

                Intent profile = new Intent(context, StudentProfile.class);
                profile.putExtra("code", code);
                profile.putExtra("title", title);
                profile.putExtra("tday", tday);
                profile.putExtra("autoid", student.getIdt());
                profile.putExtra("studentname", student.getName());
                profile.putExtra("studentid", student.getSid());
                profile.putExtra("studentemail", student.getEmail());
                profile.putExtra("a", a);
                profile.putExtra("ctone", student.getC1());
                profile.putExtra("cttwo", student.getC2());
                profile.putExtra("ctthree", student.getC3());
                profile.putExtra("attendance", student.getAtt());
                profile.putExtra("present", student.getPre());
                profile.putExtra("assign", student.getAss());
                profile.putExtra("mid", student.getMid());
                profile.putExtra("final", student.getFin());
                profile.putExtra("itemposition", listposition);
                context.startActivity(profile);
            }
        });

       holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, holder.itemView);
                popupMenu.inflate(R.menu.student_edit_update);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.editstudent:{
                                StudentUpdateFragment studentUpdateFragment  = StudentUpdateFragment.newInstance(student.getSid(), listposition, new StudentUpdateListener() {
                                    @Override
                                    public void onStudentUpdate(Student student, int pos) {
                                        studentList.set(position, student);
                                        notifyDataSetChanged();
                                    }
                                });
                                studentUpdateFragment.show(((StudentListActivity) context).getSupportFragmentManager(), "Update Student");
                                break;
                            }
                            case R.id.mailstudent:{
                                Intent sendmail = new Intent(context, Sendingmail.class);
                                sendmail.putExtra("email", student.getEmail());
                                context.startActivity(sendmail);
                                break;
                            }
                            case R.id.deletestudent:{
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                                alertDialogBuilder.setMessage("Are you sure, you want to delete this student and all information?");
                                alertDialogBuilder.setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int arg1) {
                                                deleteStudent(listposition);
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
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return false;
            }
        });
    }
    private void deleteStudent(int listposition) {

        Student student = studentList.get(listposition);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        long count = dataBaseHelper.deleteselectedStudent(student.getName());

        if(count>0){
            studentList.remove(listposition);
            notifyDataSetChanged();
            Toast.makeText(context, "Student is deleted successfully",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context, "Student is not deleted. Because its attendance has counted", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {

        TextView idtext;
        RadioButton p,a;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            idtext = itemView.findViewById(R.id.idtextview);
            p = itemView.findViewById(R.id.present);
            a = itemView.findViewById(R.id.absent);


        }
    }
}
