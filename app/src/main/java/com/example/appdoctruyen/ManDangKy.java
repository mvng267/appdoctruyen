package com.example.appdoctruyen;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdoctruyen.data.DatabaseDocTruyen;
import com.example.appdoctruyen.model.TaiKhoan;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ManDangKy extends AppCompatActivity {


    EditText edtDKTaiKhoan,edtDKMatKhau,edtDKEmail;
    Button btnDKDangKy,btnDKDangNhap;

    DatabaseDocTruyen databaseDocTruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_dang_ky);

        AnhXa();

        databaseDocTruyen = new DatabaseDocTruyen(this);

        btnDKDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taikhoan = edtDKTaiKhoan.getText().toString();
                String matkhau = edtDKMatKhau.getText().toString();
                String email = edtDKEmail.getText().toString();

                if (taikhoan.isEmpty() || matkhau.isEmpty() || email.isEmpty()) {
                    Toast.makeText(ManDangKy.this, "Bạn chưa nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    Log.e("Thông báo: ", "Bạn chưa nhập đầy đủ thông tin");
                } else {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    CollectionReference taiKhoanRef = db.collection("TaiKhoan");

                    taiKhoanRef.whereEqualTo("mTenTaiKhoan", taikhoan)
                            .get()
                            .addOnSuccessListener(queryDocumentSnapshots -> {
                                if (queryDocumentSnapshots.isEmpty()) {
                                    TaiKhoan taiKhoanMoi = CreateTaiKhoan();
                                    taiKhoanRef.add(taiKhoanMoi)
                                            .addOnSuccessListener(documentReference -> {
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
    private TaiKhoan CreateTaiKhoan() {
        String taikhoan = edtDKTaiKhoan.getText().toString();
        String matkhau = edtDKMatKhau.getText().toString();
        String email = edtDKEmail.getText().toString();
        int phanquyen = 1;

        TaiKhoan tk = new TaiKhoan(taikhoan, matkhau, email, phanquyen);
        return tk;
    }
    @SuppressLint("CutPasteId")
    private void AnhXa() {
        edtDKEmail = findViewById(R.id.dkMatKhau);
        edtDKMatKhau = findViewById(R.id.dkMatKhau);
        edtDKTaiKhoan = findViewById(R.id.dkTaiKhoan);
        btnDKDangKy = findViewById(R.id.dkDangKy);
        btnDKDangNhap = findViewById(R.id.dkDangNhap);

    }
}