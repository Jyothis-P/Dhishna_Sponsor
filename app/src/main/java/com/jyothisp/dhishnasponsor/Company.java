package com.jyothisp.dhishnasponsor;

public class Company {
    private String name, phone, locality, status, user, time;

    public Company(){
    }

    public Company(String name, String locality, String phone, String status, String user, String mTime){
        this.name = name;
        this.locality = locality;
        this.phone = phone;
        this.status = status;
        this.user = user;
        this.time = mTime;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
