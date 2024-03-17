package com.example.appdoctruyen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdoctruyen.model.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ManDangKy extends AppCompatActivity {

    EditText edtDKTaiKhoan, edtDKMatKhau, edtDKEmail;
    Button btnDKDangKy, btnDKDangNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_dang_ky);

        AnhXa();

        btnDKDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra kết nối Internet
                if (!isInternetConnected()) {
                    Toast.makeText(ManDangKy.this, "Không có kết nối Internet", Toast.LENGTH_SHORT).show();
                    return;
                }

                String taikhoan = edtDKTaiKhoan.getText().toString();
                String matkhau = edtDKMatKhau.getText().toString();
                String email = edtDKEmail.getText().toString();

                if (taikhoan.isEmpty() || matkhau.isEmpty() || email.isEmpty()) {
                    Toast.makeText(ManDangKy.this, "Bạn chưa nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    Log.e("Thông báo: ", "Bạn chưa nhập đầy đủ thông tin");
                } else {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    CollectionReference taiKhoanRef = db.collection("Users");
                    DocumentReference newTaiKhoanRef = taiKhoanRef.document(); // Generate a unique document ID

                    taiKhoanRef.whereEqualTo("username", taikhoan)
                            .get()
                            .addOnSuccessListener(queryDocumentSnapshots -> {
                                if (queryDocumentSnapshots.isEmpty()) {
                                    User userMoi = CreateTaiKhoan(newTaiKhoanRef.getId()); // Pass the generated ID to the createTaiKhoan method
                                    newTaiKhoanRef.set(userMoi)
                                            .addOnSuccessListener(aVoid -> {
                                                Toast.makeText(ManDangKy.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                                Log.e("Thông báo: ", "Đăng ký thành công");
                                            })
                                            .addOnFailureListener(e -> {
                                                Toast.makeText(ManDangKy.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                                                Log.e("Truy vấn Firestore", "Lỗi: " + e.getMessage());
                                            });
                                } else {
                                    Toast.makeText(ManDangKy.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                                    Log.e("Thông báo: ", "Tài khoản đã tồn tại");
                                }
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(ManDangKy.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                                Log.e("Truy vấn Firestore", "Lỗi: " + e.getMessage());
                            });

                }
            }
        });

        btnDKDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private User CreateTaiKhoan(String id) {
        String taikhoan = edtDKTaiKhoan.getText().toString();
        String matkhau = edtDKMatKhau.getText().toString();
        String email = edtDKEmail.getText().toString();
        int role = 1;

        User user = new User(taikhoan, matkhau, email, role, id);
        return user;
    }

    @SuppressLint("CutPasteId")
    private void AnhXa() {
        edtDKEmail = findViewById(R.id.dkEmail);
        edtDKMatKhau = findViewById(R.id.dkMatKhau);
        edtDKTaiKhoan = findViewById(R.id.dkTaiKhoan);
        btnDKDangKy = findViewById(R.id.dkDangKy);
        btnDKDangNhap = findViewById(R.id.dkDangNhap);
    }

    private boolean isInternetConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}