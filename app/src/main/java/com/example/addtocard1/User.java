package com.example.addtocard1;

public class User {
    String uID ;
    String name;
    String email;

    public User(String uID, String name, String email) {
        this.uID = uID;
        this.name = name;
        this.email = email;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
