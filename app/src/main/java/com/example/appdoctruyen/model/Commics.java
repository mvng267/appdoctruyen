package com.example.appdoctruyen.model;

public class Commics {
    private String tittle;
    private String content;
    private String img;
    private int id;
    private String userID;

    public Commics() {
        // Constructor mặc định được yêu cầu khi sử dụng với Firebase Firestore
    }

    public Commics(String tittle, String content, String img) {
        this.tittle = tittle;
        this.content = content;
        this.img = img;
    }

    public Commics(String tittle, String content, String img, String userID) {
        this.tittle = tittle;
        this.content = content;
        this.img = img;
        this.userID = userID;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}