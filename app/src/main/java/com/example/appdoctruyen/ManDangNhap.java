package com.example.appdoctruyen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ManDangNhap extends AppCompatActivity {

    private EditText edtTaiKhoan, edtMatKhau;
    private Button btnDangNhap, btnDangKy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_dang_nhap);
        AnhXa();

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
                String tentaikhoan = edtTaiKhoan.getText().toString();
                String matkhau = edtMatKhau.getText().toString();

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference taiKhoanRef = db.collection("TaiKhoan");

                taiKhoanRef.whereEqualTo("mTenTaiKhoan", tentaikhoan)
                        .whereEqualTo("mMatKhau", matkhau)
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);

                                String tentk = documentSnapshot.getString("mTenTaiKhoan");
                                String email = documentSnapshot.getString("mEmail");
                                int phanquyen = documentSnapshot.getLong("mPhanQuyen").intValue();
                                String docId = documentSnapshot.getId();

                                Intent intent = new Intent(ManDangNhap.this, MainActivity.class);
                                intent.putExtra("phanq", phanquyen);
                                intent.putExtra("idd", docId);
                                intent.putExtra("email", email);
                                intent.putExtra("tentaikhoan", tentk);

                                startActivity(intent);
                                Log.e("Đăng nhập: ", "Thành công");
                                Toast.makeText(ManDangNhap.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e("Đăng nhập: ", "Không thành công");
                                Toast.makeText(ManDangNhap.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(e -> {
                            // Xử lý lỗi
                            Log.e("Truy vấn Firestore", "Lỗi: " + e.getMessage());
                        });
            }
        });
    }

    private void AnhXa() {
        edtTaiKhoan = findViewById(R.id.taikhoan);
        edtMatKhau = findViewById(R.id.matkhau);
        btnDangNhap = findViewById(R.id.dangnhap);
        btnDangKy = findViewById(R.id.dangky);
    }
}