package com.example.appdoctruyen.model;

public class TaiKhoan {
    private String mId;
    private String mTenTaiKhoan;
    private String mMatKhau;
    private String mEmail;
    private int mPhanQuyen;

    public TaiKhoan(String mTenTaiKhoan, String mMatKhau, String mEmail, int mPhanQuyen, String mId) {
        this.mTenTaiKhoan = mTenTaiKhoan;
        this.mMatKhau = mMatKhau;
        this.mEmail = mEmail;
        this.mPhanQuyen = mPhanQuyen;
        this.mId = mId;
    }

    public TaiKhoan() {
        this.mTenTaiKhoan = mTenTaiKhoan;
        this.mEmail = mEmail;
    }



    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmTenTaiKhoan() {
        return mTenTaiKhoan;
    }

    public void setmTenTaiKhoan(String mTenTaiKhoan) {
        this.mTenTaiKhoan = mTenTaiKhoan;
    }

    public String getmMatKhau() {
        return mMatKhau;
    }

    public void setmMatKhau(String mMatKhau) {
        this.mMatKhau = mMatKhau;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public int getmPhanQuyen() {
        return mPhanQuyen;
    }

    public void setmPhanQuyen(int mPhanQuyen) {
        this.mPhanQuyen = mPhanQuyen;
    }

}
