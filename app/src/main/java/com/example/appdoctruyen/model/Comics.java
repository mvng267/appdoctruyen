package com.example.appdoctruyen.model;

public class Comics {
    private String id;
    private String content;
    private String img;
    private String title;
    private String userID;

    public Comics() {
        // Empty constructor required for Firestore
    }

    public Comics(String id, String content, String img, String title, String userID) {
        this.id = id;
        this.content = content;
        this.img = img;
        this.title = title;
        this.userID = userID;
    }

    public Comics(String title, String content, String img) {
    }

    public String getImage() {
        return img;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getImg() {
        return img;
    }

    public String getTitle() {
        return title;
    }

    public String getUserID() {
        return userID;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}