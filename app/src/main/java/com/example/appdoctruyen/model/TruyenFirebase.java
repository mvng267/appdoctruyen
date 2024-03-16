package com.example.appdoctruyen.model;

public class TruyenFirebase {
    private String tentruyen;
    private String noidung;
    private String img;
    private int id;
    private String taiKhoanId;

    public TruyenFirebase(String tentruyen, String noidung, String img, int id) {
        this.tentruyen = tentruyen;
        this.noidung = noidung;
        this.img = img;
        this.id = id;
        this.taiKhoanId = taiKhoanId;
    }

    public String getTentruyen() {
        return tentruyen;
    }

    public void setTentruyen(String tentruyen) {
        this.tentruyen = tentruyen;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
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

    public String getTaiKhoanId() {
        return taiKhoanId;
    }

    public void setTaiKhoanId(String taiKhoanId) {
        this.taiKhoanId = taiKhoanId;
    }
}