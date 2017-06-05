package com.closedevice.fastapp.model.record;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;


@Entity
public class BkRecord {
    @Id(autoincrement = true)
    private Long id;
    
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

    @Generated(hash = 1510464895)
    public BkRecord(Long id, String username, String cover_address,
            String class_name, String course_name, String class_type,
            String class_book, String invite_code, String study_aims,
            String syllabus, String exam_schedule) {
        this.id = id;
        this.username = username;
        this.cover_address = cover_address;
        this.class_name = class_name;
        this.course_name = course_name;
        this.class_type = class_type;
        this.class_book = class_book;
        this.invite_code = invite_code;
        this.study_aims = study_aims;
        this.syllabus = syllabus;
        this.exam_schedule = exam_schedule;
    }
    @Generated(hash = 1886565636)
    public BkRecord() {
    }
   
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getCover_address() {
        return this.cover_address;
    }
    public void setCover_address(String cover_address) {
        this.cover_address = cover_address;
    }
    public String getClass_name() {
        return this.class_name;
    }
    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }
    public String getCourse_name() {
        return this.course_name;
    }
    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }
    public String getClass_type() {
        return this.class_type;
    }
    public void setClass_type(String class_type) {
        this.class_type = class_type;
    }
    public String getClass_book() {
        return this.class_book;
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
        return this.study_aims;
    }
    public void setStudy_aims(String study_aims) {
        this.study_aims = study_aims;
    }
    public String getSyllabus() {
        return this.syllabus;
    }
    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }
    public String getExam_schedule() {
        return this.exam_schedule;
    }
    public void setExam_schedule(String exam_schedule) {
        this.exam_schedule = exam_schedule;
    }

    @Override
    public String toString() {
        return "BkRecord{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", cover_address='" + cover_address + '\'' +
                ", class_name='" + class_name + '\'' +
                ", course_name='" + course_name + '\'' +
                ", class_type='" + class_type + '\'' +
                ", class_book='" + class_book + '\'' +
                ", invite_code='" + invite_code + '\'' +
                ", study_aims='" + study_aims + '\'' +
                ", syllabus='" + syllabus + '\'' +
                ", exam_schedule='" + exam_schedule + '\'' +
                '}';
    }
}
