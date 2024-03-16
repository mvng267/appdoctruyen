package com.example.appdoctruyen.model;

public class Comic {
    private String title;
    private String content;
    private String img;
    private int id;
    private String userID;

    public Comic(String title, String content, String img, int id, String userID) {
        this.title = title;
        this.content = content;
        this.img = img;
        this.id = id;
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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