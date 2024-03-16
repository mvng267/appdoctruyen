package com.example.appdoctruyen.model;

public class Users {
    private String ID;
    private String username;
    private String pass;
    private String email;
    private int roles;

    public Users(String username, String pass, String email, int roles, String ID) {
        this.username = username;
        this.pass = pass;
        this.email = email;
        this.roles = roles;
        this.ID = ID;
    }

    public Users() {
        this.username = username;
        this.email = email;
    }



    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoles() {
        return roles;
    }

    public void setRoles(int roles) {
        this.roles = roles;
    }

}
