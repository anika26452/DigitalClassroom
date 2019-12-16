package com.example.digitalclassroom;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public Context context;
    public Boolean result = false;
    public static final String DATABASE_NAME = "TCS7.db";
    public static final int DATABASE_VERSION = 1;

    //Techer Information
    public static final String TABLE_TEACHER = "Teacher";
    public static final String ID = "_id";
    public static final String NAME = "_name";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String PHONE = "phone";
    public static final String CREATE_TABLE_TEACHER = "CREATE TABLE "+TABLE_TEACHER+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" TEXT NOT NULL, "+EMAIL+" TEXT NOT NULL UNIQUE, "+PASSWORD+" TEXT NOT NULL, "+PHONE+" TEXT NOT NULL);";

    //Course Information
    public static final String TABLE_COURSE = "Course";
    public static final String CID = "_id";
    public static final String TITLE = "title";
    public static final String CODE = "code";
    public static final String ROOM = "room";
    public static final String TDAYS = "tdays";
    public static final String STARTDATE = "s_date";
    public static final String ENDDATE = "e_date";
    public static final String STARTTIME = "s_time";
    public static final String ENDTIME = "e_time";
    public static final String DAYS = "days";
    public static final String CREATE_TABLE_COURSE = "CREATE TABLE "+TABLE_COURSE+"("+CID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+TITLE+" TEXT NOT NULL UNIQUE, "+CODE+" TEXT NOT NULL UNIQUE, "+ROOM+" VARCHAR(3) NOT NULL, "+TDAYS+" TEXT NOT NULL,"+STARTDATE+" TEXT NOT NULL, "+ENDDATE+" TEXT NOT NULL, "+STARTTIME+" TEXT NOT NULL, "+ENDTIME+" TEXT NOT NULL, "+DAYS+" TEXT NOT NULL);";

    // Student Information
    public static final String TABLE_STUDENT = "Student";
    public static final String C_CODE = "c_code";
    public static final String IDT = "_id";
    public static final String SNAME = "name";
    public static final String SID = "s_id";
    public static final String SEMAIL = "s_email";
    public static final String CTONE = "ct1";
    public static final String CTTWO = "ct2";
    public static final String CTTHREE = "ct3";
    public static final String ATTENDANCE = "attendance";
    public static final String PRESENTATION = "presentation";
    public static final String ASSIGNMENT = "assignment";
    public static final String MID = "mid";
    public static final String FINAL = "final";
    public static final String CONS = "cons";
    public static final String CREATE_TABLE_STUDENT = "CREATE TABLE "+TABLE_STUDENT+"("+IDT+" INTEGER PRIMARY KEY AUTOINCREMENT, "+C_CODE+" TEXT NOT NULL, "+SNAME+" TEXT NOT NULL, "+SID+" TEXT NOT NULL , "+SEMAIL+" TEXT NOT NULL, "+CTONE+" FLOAT, "+CTTWO+" FLOAT, "+CTTHREE+" FLOAT, "+ATTENDANCE+" FLOAT,"+PRESENTATION+" FLOAT, "+ASSIGNMENT+" FLOAT, "+MID+" FLOAT, "+FINAL+" FLOAT,  FOREIGN KEY ("+C_CODE+") REFERENCES "+TABLE_COURSE+"("+CODE+") ON UPDATE CASCADE ON DELETE CASCADE, CONSTRAINT "+CONS+" UNIQUE ("+C_CODE+", "+SID+"));";

    //Attendance
    public static final String TABLE_ATTENDACE = "Date";
    public static final String AID = "_id";
    public static final String CD = "_code";
    public static final String PRESENTID = "presentid";
    public static final String DATE = "_date";
    public static final String CREATE_TABALE_ATTENDANCE = "CREATE TABLE "+TABLE_ATTENDACE+"("+AID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+CD+" TEXT NOT NULL,"+PRESENTID+" TEXT NOT NULL, "+DATE+" TEXT NOT NULL, FOREIGN KEY("+PRESENTID+") REFERENCES "+TABLE_STUDENT+"("+IDT+"), UNIQUE("+CD+","+PRESENTID+","+DATE+"));";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_TEACHER);
            db.execSQL(CREATE_TABLE_COURSE);
            db.execSQL(CREATE_TABLE_STUDENT);
            db.execSQL(CREATE_TABALE_ATTENDANCE);
            Toast.makeText(context, "onCreate method is called", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(context, "Exception "+e, Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    public long insertTeacher(Teacher teacher){

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, teacher.getN());
        contentValues.put(EMAIL, teacher.getE());
        contentValues.put(PASSWORD, teacher.getPass());
        contentValues.put(PHONE, teacher.getPhone());

        long rowId = db.insert(TABLE_TEACHER, null, contentValues);

        return rowId;
    }

    public Boolean findTeacher(String e, String p){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_TEACHER, null);

        if(cursor.getCount()==0){

            Toast.makeText(context, "No user is registered yet!", Toast.LENGTH_LONG).show();

        }else{
            while (cursor.moveToNext()){

                String em = cursor.getString(2);
                String pw = cursor.getString(3);

                if(em.equals(e) && pw.equals(p)){

                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    public Cursor DisplayTeacher( String email){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+NAME+", "+EMAIL+", "+PHONE+" FROM "+TABLE_TEACHER+" WHERE "+EMAIL+" = '"+email+"' ", null);
        return cursor;
    }

    public long insertCourse(CourseInfo courseInfo){
           SQLiteDatabase db = this.getWritableDatabase();

           ContentValues contentValues = new ContentValues();
           contentValues.put(TITLE, courseInfo.getTitle());
           contentValues.put(CODE, courseInfo.getCode());
           contentValues.put(ROOM, courseInfo.getRoom());
           contentValues.put(TDAYS, courseInfo.getTdays());
           contentValues.put(STARTDATE, courseInfo.getSdate());
           contentValues.put(ENDDATE, courseInfo.getEdate());
           contentValues.put(STARTTIME, courseInfo.getStime());
           contentValues.put(ENDTIME, courseInfo.getEtime());
           contentValues.put(DAYS, courseInfo.getDays());

           long course = db.insert(TABLE_COURSE, null, contentValues);

           return course;
    }

    public List<CourseInfo> getAllCourse(){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_COURSE, new String[]{CID, TITLE, CODE, ROOM, TDAYS, STARTDATE, ENDDATE, STARTTIME, ENDTIME, DAYS}, null, null, null, null, null);

        List<CourseInfo> coursesList = new ArrayList<>();
        if (cursor!=null){
            if(cursor.moveToFirst()){
                do{
                    int id = cursor.getInt(cursor.getColumnIndex(CID));
                    String title = cursor.getString(cursor.getColumnIndex(TITLE));
                    String code = cursor.getString(cursor.getColumnIndex(CODE));
                    String room = cursor.getString(cursor.getColumnIndex(ROOM));
                    String td = cursor.getString(cursor.getColumnIndex(TDAYS));
                    String sdate = cursor.getString(cursor.getColumnIndex(STARTDATE));
                    String edate= cursor.getString(cursor.getColumnIndex(ENDDATE));
                    String stime= cursor.getString(cursor.getColumnIndex(STARTTIME));
                    String etime= cursor.getString(cursor.getColumnIndex(ENDTIME));
                    String days= cursor.getString(cursor.getColumnIndex(DAYS));

                    coursesList.add(new CourseInfo(id, title, code, room,td, sdate, edate, stime, etime, days));
                } while (cursor.moveToNext());
            }
        }return coursesList;
    }

    public long deleteselectedCourse(String code){
           long deleteRowCount = -1;
           SQLiteDatabase db = this.getWritableDatabase();

           deleteRowCount = db.delete(TABLE_COURSE, CODE +" = ?", new String[]{String.valueOf(code)});

           return deleteRowCount;
    }

    public CourseInfo getselectedcourse(String fcode){

        SQLiteDatabase db = this.getReadableDatabase();
        CourseInfo courseInfo = null;
        Cursor cursor = db.query(TABLE_COURSE, null, CODE + " = ? ", new String[]{String.valueOf(fcode)}, null, null, null);
        if (cursor!=null){
            if(cursor.moveToFirst()){
                do{
                    int id = cursor.getInt(cursor.getColumnIndex(ID));
                    String title = cursor.getString(cursor.getColumnIndex(TITLE));
                    String code = cursor.getString(cursor.getColumnIndex(CODE));
                    String room = cursor.getString(cursor.getColumnIndex(ROOM));
                    String td = cursor.getString(cursor.getColumnIndex(TDAYS));
                    String sdate = cursor.getString(cursor.getColumnIndex(STARTDATE));
                    String edate = cursor.getString(cursor.getColumnIndex(ENDDATE));
                    String stime= cursor.getString(cursor.getColumnIndex(STARTTIME));
                    String etime = cursor.getString(cursor.getColumnIndex(ENDTIME));
                    String days = cursor.getString(cursor.getColumnIndex(DAYS));

                    courseInfo  = new CourseInfo(id, title, code, room, td, sdate, edate, stime, etime, days);
                }while (cursor.moveToNext());
            }
        }
        return courseInfo;
    }

    public long updateCourse(CourseInfo courseInfo){
           long rowcount = 0;
           SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

           ContentValues contentValues = new ContentValues();
           contentValues.put(TITLE, courseInfo.getTitle());
           contentValues.put(CODE, courseInfo.getCode());
           contentValues.put(ROOM, courseInfo.getRoom());
           contentValues.put(TDAYS, courseInfo.getTdays());
           contentValues.put(STARTDATE, courseInfo.getSdate());
           contentValues.put(ENDDATE, courseInfo.getEdate());
           contentValues.put(STARTTIME, courseInfo.getStime());
           contentValues.put(ENDTIME, courseInfo.getEtime());
           contentValues.put(DAYS, courseInfo.getDays());


           rowcount = sqLiteDatabase.update(TABLE_COURSE, contentValues, ID + " = ? ", new String[]{String.valueOf(courseInfo.getId())});
           return rowcount;
    }

    public List<Student> getAllStudent(String fcode){
        SQLiteDatabase db = this.getReadableDatabase();

        List<Student> studentList = new ArrayList<>();
        Cursor cursor= null;

        cursor = db.query(TABLE_STUDENT, new String[]{IDT, SNAME, SID, SEMAIL, CTONE, CTTWO, CTTHREE,ATTENDANCE, PRESENTATION, ASSIGNMENT, MID, FINAL}, C_CODE+" = ?", new String[]{String.valueOf(fcode)}, null, null, null);
        if(cursor!=null && cursor.moveToFirst()){
            studentList = new ArrayList<>();
            do{
                int id = cursor.getInt(cursor.getColumnIndex(IDT));
                String sname = cursor.getString(cursor.getColumnIndex(SNAME));
                String sid = cursor.getString(cursor.getColumnIndex(SID));
                String email = cursor.getString(cursor.getColumnIndex(SEMAIL));
                float ctone = cursor.getFloat(cursor.getColumnIndex(CTONE));
                float cttwo = cursor.getFloat(cursor.getColumnIndex(CTTWO));
                float ctthree = cursor.getFloat(cursor.getColumnIndex(CTTHREE));
                float attend = cursor.getFloat(cursor.getColumnIndex(ATTENDANCE));
                float presen = cursor.getFloat(cursor.getColumnIndex(PRESENTATION));
                float assign = cursor.getFloat(cursor.getColumnIndex(ASSIGNMENT));
                float mid = cursor.getFloat(cursor.getColumnIndex(MID));
                float fin = cursor.getFloat(cursor.getColumnIndex(FINAL));

                studentList.add(new Student(id, sname, sid, email, ctone, cttwo, ctthree, attend, presen, assign, mid, fin));
            }while(cursor.moveToNext());
        }
        return studentList;
    }

    public long insertStudent(Student student, String c){
        long rowid = -1;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put(C_CODE, c);
            contentValues.put(SNAME, student.getName());
            contentValues.put(SID, student.getSid());
            contentValues.put(SEMAIL, student.getEmail());

            rowid = sqLiteDatabase.insert(TABLE_STUDENT, null, contentValues);
        }catch (Exception e){
            Toast.makeText(context, e+"", Toast.LENGTH_LONG).show();
        }
        return rowid;
    }

    public long insertAttendance(String c, long i, String date){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long r = -1;
        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put(CD, c);
            contentValues.put(PRESENTID, i);
            contentValues.put(DATE, date);
            r = sqLiteDatabase.insert(TABLE_ATTENDACE, null, contentValues);
        }catch (Exception e){
            Toast.makeText(context, e+" ", Toast.LENGTH_LONG).show();
        }
        return r;
    }


    public long getAttendance(String c, long i) {
        long count = -1;
        Cursor cursor = null;
        try{
            String countQuery = "SELECT * FROM " + TABLE_ATTENDACE + " WHERE " + CD + " = '" + c + "' AND " + PRESENTID + " = '" + i + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            cursor = db.rawQuery(countQuery, null);

            count = cursor.getCount();
        }catch (Exception e){
            Toast.makeText(context, e+"", Toast.LENGTH_LONG).show();
        }

        return count;
    }
    public long deleteAttendance(String c, long i, String d){
        long deleteRowCount = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        deleteRowCount = db.delete(TABLE_ATTENDACE, CD+" = ? AND "+PRESENTID+" = ? AND "+DATE+" = ?", new String[]{c, String.valueOf(i), d});

        return deleteRowCount;
    }

    public long deleteselectedStudent(String id){
        long deleteRowCount = -1;
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            deleteRowCount = db.delete(TABLE_STUDENT, SNAME +" = ?", new String[]{String.valueOf(id)});
        }catch (Exception e){
            Toast.makeText(context, "Student can't be deleted because its attendance has counted", Toast.LENGTH_LONG).show();
        }


        return deleteRowCount;
    }

    public Student getselectedStudent(String fid){

        SQLiteDatabase db = this.getReadableDatabase();
        Student student = null;
        Cursor cursor = db.query(TABLE_STUDENT, null, SID + " = ? ", new String[]{String.valueOf(fid)}, null, null, null);
        if (cursor!=null){
            if(cursor.moveToFirst()){
                do{
                    int id = cursor.getInt(cursor.getColumnIndex(IDT));
                    String name = cursor.getString(cursor.getColumnIndex(SNAME));
                    String sid = cursor.getString(cursor.getColumnIndex(SID));
                    String email = cursor.getString(cursor.getColumnIndex(SEMAIL));
                    float c1 = cursor.getFloat(cursor.getColumnIndex(CTONE));
                    float c2 = cursor.getFloat(cursor.getColumnIndex(CTTWO));
                    float c3 = cursor.getFloat(cursor.getColumnIndex(CTTHREE));
                    float att = cursor.getFloat(cursor.getColumnIndex(ATTENDANCE));
                    float pr = cursor.getFloat(cursor.getColumnIndex(PRESENTATION));
                    float as = cursor.getFloat(cursor.getColumnIndex(ASSIGNMENT));
                    float mid = cursor.getFloat(cursor.getColumnIndex(MID));
                    float fin = cursor.getFloat(cursor.getColumnIndex(FINAL));

                    student  = new Student(id, name, sid, email, c1, c2, c3, att, pr, as, mid, fin);

                }while (cursor.moveToNext());
            }
        }
        return student;
    }

    public long updateStudent(Student student){
        long rowcount = 0;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(SNAME, student.getName());
        contentValues.put(SID, student.getSid());
        contentValues.put(SEMAIL, student.getEmail());
        contentValues.put(CTONE, student.getC1());
        contentValues.put(CTTWO, student.getC2());
        contentValues.put(CTTHREE, student.getC3());
        contentValues.put(ATTENDANCE, student.getAtt());
        contentValues.put(PRESENTATION, student.getPre());
        contentValues.put(ASSIGNMENT, student.getAss());
        contentValues.put(MID, student.getMid());
        contentValues.put(FINAL, student.getFin());

        rowcount = sqLiteDatabase.update(TABLE_STUDENT, contentValues, IDT + " = ? ", new String[]{String.valueOf(student.getIdt())});
        return rowcount;
    }
    public long getAbsent(String c){
        long count = -1;
        Cursor cursor = null;
        try{
            String countQuery = "SELECT * FROM " + TABLE_ATTENDACE + " WHERE " + CD + " = '" + c + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            cursor = db.rawQuery(countQuery, null);

            count = cursor.getCount();
        }catch (Exception e){
            Toast.makeText(context, e+"", Toast.LENGTH_LONG).show();
        }

        return count;
    }
}
