package com.example.appdoctruyen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.appdoctruyen.data.DatabaseDocTruyen;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class ManDangNhap extends AppCompatActivity {

    EditText edtTaiKhoan, edtMatKhau;
    Button btnDangNhap, btnDangKy;

    DatabaseDocTruyen databaseDocTruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_dang_nhap);
        AnhXa();

        databaseDocTruyen = new DatabaseDocTruyen(this);

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManDangNhap.this,ManDangKy.class);
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
                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    String tentk = documentSnapshot.getString("mTenTaiKhoan");
                                    String email = documentSnapshot.getString("mEmail");
                                    int phanquyen = documentSnapshot.getLong("mPhanQuyen").intValue();
                                    String docId = documentSnapshot.getId();

                                    // Tạo một tài liệu mới trong Firestore
                                    Map<String, Object> taiKhoan = new HashMap<>();
                                    taiKhoan.put("mTenTaiKhoan", tentk);
                                    taiKhoan.put("mMatKhau", matkhau);
                                    taiKhoan.put("mEmail", email);
                                    taiKhoan.put("mPhanQuyen", phanquyen);

                                    // Thêm tài liệu vào Firestore
                                    taiKhoanRef.document(docId)
                                            .set(taiKhoan)
                                            .addOnSuccessListener(aVoid -> {
                                                // Xử lý thành công
                                                // Tiếp tục xử lý hoặc hiển thị thông báo thành công
                                            })
                                            .addOnFailureListener(e -> {
                                                // Xử lý lỗi
                                                Log.e("Thêm vào Firestore", "Lỗi: " + e.getMessage());
                                            });

                                    Intent intent = new Intent(ManDangNhap.this, MainActivity.class);
                                    intent.putExtra("phanq", phanquyen);
                                    intent.putExtra("idd", docId);
                                    intent.putExtra("email", email);
                                    intent.putExtra("tentaikhoan", tentk);

                                    startActivity(intent);
                                    Log.e("Đăng nhập : ", "Thành công");
                                }
                            } else {
                                Log.e("Đăng nhập : ", "Không thành công");
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