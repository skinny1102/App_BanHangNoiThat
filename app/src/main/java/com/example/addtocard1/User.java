package com.example.addtocard1;

public class User {

    String imgResource;
    String fullName;
    String phoneNumber;
    String email;

    public User(String imgResource, String fullName, String phoneNumber, String email) {
        this.imgResource = imgResource;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
    public User(){

    }

    public String getImgResource() {
        return imgResource;
    }

    public void setImgResource(String imgResource) {
        this.imgResource = imgResource;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
