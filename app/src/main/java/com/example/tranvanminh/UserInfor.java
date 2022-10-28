package com.example.tranvanminh;

import java.io.Serializable;

public class UserInfor implements Serializable  {
    public String address;
    public String email;
    public String firstname;
    public String lastname;
    public double latitude;
    public double longitude;
    public String mobile;
    public String userID;

    public UserInfor(String userID, String firstname, String lastname, String address, String email) {
        this.userID = userID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.email = email;
    }

    public UserInfor(String firstname, String lastname, String address, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.email = email;
    }

    public String getUserID() {
        return this.userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    @Override
    public String toString() {
        return "User{" +
                "address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", mobile=" + mobile +
                '}';
    }

    public UserInfor() {
    }
}

