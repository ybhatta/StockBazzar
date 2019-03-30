package com.roshankc.myclasses;

import java.io.Serializable;

public class User implements Serializable {

    private int ID;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;

    public User(String firstName, String lastName, String emailAddress, String password,int ID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.ID=ID;
    }

    public User(){

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public int getID(){
        return ID;
    }
}
