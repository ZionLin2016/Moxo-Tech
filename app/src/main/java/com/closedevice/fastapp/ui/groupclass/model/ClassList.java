package com.closedevice.fastapp.ui.groupclass.model;

/**
 * Created by LSD on 2017/4/22.
 */

public class ClassList {
    private String username;
    private String cover_address;
    private String class_name;
    private String course_name;
    private String class_type;
    private String class_book;
    private String invite_code;

    // class detail
    private String study_aims;
    private String syllabus;
    private String exam_schedule;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCover_address() {
        return cover_address;
    }

    public void setCover_address(String cover_address) {
        this.cover_address = cover_address;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getClass_type() {
        return class_type;
    }

    public void setClass_type(String class_type) {
        this.class_type = class_type;
    }

    public String getClass_book() {
        return class_book;
    }

    public void setClass_book(String class_book) {
        this.class_book = class_book;
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public String getStudy_aims() {
        return study_aims;
    }

    public void setStudy_aims(String study_aims) {
        this.study_aims = study_aims;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

    public String getExam_schedule() {
        return exam_schedule;
    }

    public void setExam_schedule(String exam_schedule) {
        this.exam_schedule = exam_schedule;
    }

    @Override
    public String toString() {
        return "ClassJson [username=" + username + ", cover_address=" + cover_address + ", class_name=" + class_name
                + ", course_name=" + course_name + ", class_type=" + class_type + ", class_book=" + class_book
                + ", invite_code=" + invite_code + ", study_aims=" + study_aims + ", syllabus=" + syllabus
                + ", exam_schedule=" + exam_schedule + "]";
    }
}
