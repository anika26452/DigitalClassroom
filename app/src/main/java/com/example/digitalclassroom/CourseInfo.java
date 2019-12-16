package com.example.digitalclassroom;

public class CourseInfo {

    private long id;
    private String title, code, room, tdays, sdate, edate, stime, etime, days;

    public CourseInfo(long id, String title, String code, String room, String tdays, String sdate, String edate, String stime, String etime, String days) {
        this.id = id;
        this.title = title;
        this.code = code;
        this.room = room;
        this.tdays = tdays;
        this.sdate = sdate;
        this.edate = edate;
        this.stime = stime;
        this.etime = etime;
        this.days = days;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTdays() {
        return tdays;
    }

    public void setTdays(String tdays) {
        this.tdays = tdays;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
