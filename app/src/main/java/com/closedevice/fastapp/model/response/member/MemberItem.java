package com.closedevice.fastapp.model.response.member;

import com.closedevice.fastapp.model.base.Entity;



public class MemberItem extends Entity {

    private String account;
    private String name;
    private int gender;
    private String photo;
    private String telephone;
    private String label;
    private String native_place;
    private String birthday;
    private String email;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getNative_place() {
        return native_place;
    }

    public void setNative_place(String native_place) {
        this.native_place = native_place;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "MemberItem{" +
                "account='" + account + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", photo='" + photo + '\'' +
                ", telephone='" + telephone + '\'' +
                ", label='" + label + '\'' +
                ", native_place='" + native_place + '\'' +
                ", birthday='" + birthday + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
