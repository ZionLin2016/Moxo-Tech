package com.closedevice.fastapp.ui.groupclass.model;


import com.closedevice.fastapp.model.base.Entity;

/**
 * Created by LSD on 2017/5/11.
 */

public class UserInfo {

    private String account;
    private String name;
    private int gender;
    private String photo;
    private String telephone;
    private String label;
    private String native_place;
    private String email;
    private String password;
    private String birthday;
    private int type;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "UserInfo [account=" + account + ", name=" + name + ", gender=" + gender + ", photo=" + photo
                + ", telephone=" + telephone + ", label=" + label + ", native_place=" + native_place + ", email="
                + email + ", password=" + password + ", birthday=" + birthday + ", type=" + type + "]";
    }

}
