package com.example.appdoctruyen;

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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ManDangNhap extends AppCompatActivity {

    private EditText edtTaiKhoan, edtMatKhau;
    private Button btnDangNhap, btnDangKy;
    private PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_dang_nhap);
        AnhXa();

        preferenceHelper = new PreferenceHelper(this); // Khởi tạo PreferenceHelper

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManDangNhap.this, ManDangKy.class);
                startActivity(intent);
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra kết nối Internet
                if (!isInternetConnected()) {
                    Toast.makeText(ManDangNhap.this, "Không có kết nối Internet", Toast.LENGTH_SHORT).show();
                    return;
                }

                String tentaikhoan = edtTaiKhoan.getText().toString();
                String matkhau = edtMatKhau.getText().toString();

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference taiKhoanRef = db.collection("Users");

                taiKhoanRef.whereEqualTo("username", tentaikhoan)
                        .whereEqualTo("password", matkhau)
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);

                                String tentk = documentSnapshot.getString("username");
                                String email = documentSnapshot.getString("email");
                                int phanquyen = documentSnapshot.getLong("role").intValue();
                                String docId = documentSnapshot.getId();

                                // Lưu dữ liệu vào PreferenceHelper
                                preferenceHelper.setUserId(docId);
                                preferenceHelper.setEmail(email);
                                preferenceHelper.setUsername(tentk);

                                if (docId != null) {
                                    Intent intent = new Intent(ManDangNhap.this, NewHome.class);
                                    intent.putExtra("phanq", phanquyen);
                                    intent.putExtra("idd", docId);
                                    intent.putExtra("email", email);
                                    intent.putExtra("tentaikhoan", tentk);
                                    startActivity(intent);
                                    Log.e("Đăng nhập: ", "Thành công");
                                    Toast.makeText(ManDangNhap.this, "Đăng nhập thành công id" + docId, Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.e("Đăng nhập: ", "Không thành công");
                                    Toast.makeText(ManDangNhap.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                                    btnDangNhap.setEnabled(false); // Vô hiệu hóa nút đăng nhập
                                }
                            } else {
                                Log.e("Đăng nhập: ", "Không thành công");
                                Toast.makeText(ManDangNhap.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(e -> {
                            // Xử lý lỗi
                            Log.e("Truy vấn Firestore", "Lỗi: " + e.getMessage());
                            Toast.makeText(ManDangNhap.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (preferenceHelper.getUserId() == null) {
            // Không cho phép quay lại nếu docId là null
            Toast.makeText(this, "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }

    private void AnhXa() {
        edtTaiKhoan = findViewById(R.id.taikhoan);
        edtMatKhau = findViewById(R.id.matkhau);
        btnDangNhap = findViewById(R.id.dangnhap);
        btnDangKy = findViewById(R.id.dangky);
    }

    private boolean isInternetConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo!= null && networkInfo.isConnected();
    }
}