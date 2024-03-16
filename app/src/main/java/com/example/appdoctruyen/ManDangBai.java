package com.example.appdoctruyen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdoctruyen.model.TruyenFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ManDangBai extends AppCompatActivity {

    EditText edtTieuDe, edtNoiDung, edtAnh;
    Button btnDangBai;
    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_dang_bai);

        edtTieuDe = findViewById(R.id.dbtieude);
        edtNoiDung = findViewById(R.id.dbnoidung);
        btnDangBai = findViewById(R.id.dbdangbai);
        edtAnh = findViewById(R.id.dbimg);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        btnDangBai.setOnClickListener(v -> {
            String tentruyen = edtTieuDe.getText().toString();
            String noidung = edtNoiDung.getText().toString();
            String img = edtAnh.getText().toString();

            if (tentruyen.isEmpty() || noidung.isEmpty() || img.isEmpty()) {
                Toast.makeText(ManDangBai.this, "Yêu cầu nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                TruyenFirebase truyen = createTruyen(tentruyen, noidung, img);

                FirebaseUser currentUser = auth.getCurrentUser();
                if (currentUser != null) {
                    String userId = currentUser.getUid();
                    truyen.setTaiKhoanId(userId);

                    CollectionReference truyenRef = db.collection("Truyen");
                    truyenRef.add(truyen)
                            .addOnSuccessListener(documentReference -> {
                                Toast.makeText(ManDangBai.this, "Thêm truyện thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ManDangBai.this, ManAdmin.class);
                                startActivity(intent);
                                finish();
                            })
                            .addOnFailureListener(e -> Toast.makeText(ManDangBai.this, "Thêm truyện thất bại", Toast.LENGTH_SHORT).show());
                } else {
                    Toast.makeText(ManDangBai.this, "Lỗi: Không thể lấy ID của tài khoản", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private TruyenFirebase createTruyen(String tentruyen, String noidung, String img) {
        Intent intent = getIntent();
        int id = intent.getIntExtra("Id", 0);

        return new TruyenFirebase(tentruyen, noidung, img, id);
    }
}