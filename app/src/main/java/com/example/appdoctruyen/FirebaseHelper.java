package com.example.appdoctruyen;

import com.example.appdoctruyen.model.TaiKhoan;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHelper {
    private DatabaseReference rootRef;

    public FirebaseHelper() {
        // Khởi tạo tham chiếu đến nút gốc trong Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        rootRef = database.getReference();
    }

    public void addTaiKhoan(TaiKhoan taiKhoan) {
        // Tạo một tham chiếu đến nút "taikhoan" trong Firebase Realtime Database
        DatabaseReference taiKhoanRef = rootRef.child("taikhoan");

        // Tạo một ID mới cho tài khoản
        String taiKhoanId = taiKhoanRef.push().getKey();

        // Đặt giá trị của tài khoản vào Firebase Realtime Database
        assert taiKhoanId != null;
        taiKhoanRef.child(taiKhoanId).setValue(taiKhoan);
    }
}