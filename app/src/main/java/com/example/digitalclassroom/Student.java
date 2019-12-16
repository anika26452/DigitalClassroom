package com.example.digitalclassroom;

public class Student {
    private long idt;
    private String name, sid, email;
    private float c1, c2, c3,att, pre, ass, mid, fin;

    public Student(long idt, String name, String sid, String email, float c1, float c2, float c3, float att, float pre, float ass, float mid, float fin) {
        this.idt = idt;
        this.name = name;
        this.sid = sid;
        this.email = email;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.att = att;
        this.pre = pre;
        this.ass = ass;
        this.mid = mid;
        this.fin = fin;
    }

    public long getIdt() {
        return idt;
    }

    public void setIdt(long idt) {
        this.idt = idt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getC1() {
        return c1;
    }

    public void setC1(float c1) {
        this.c1 = c1;
    }

    public float getC2() {
        return c2;
    }

    public void setC2(float c2) {
        this.c2 = c2;
    }

    public float getC3() {
        return c3;
    }

    public void setC3(float c3) {
        this.c3 = c3;
    }

    public float getAtt() {
        return att;
    }

    public void setAtt(float att) {
        this.att = att;
    }

    public float getPre() {
        return pre;
    }

    public void setPre(float pre) {
        this.pre = pre;
    }

    public float getAss() {
        return ass;
    }

    public void setAss(float ass) {
        this.ass = ass;
    }

    public float getMid() {
        return mid;
    }

    public void setMid(float mid) {
        this.mid = mid;
    }

    public float getFin() {
        return fin;
    }

    public void setFin(float fin) {
        this.fin = fin;
    }
 }
