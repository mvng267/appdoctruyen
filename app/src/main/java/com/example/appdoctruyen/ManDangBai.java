package com.example.appdoctruyen;

import static android.widget.Toast.makeText;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdoctruyen.model.Commics;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ManDangBai extends AppCompatActivity {

    EditText edtTieuDe, edtNoiDung, edtAnh;
    Button btnDangBai;
    FirebaseFirestore db;
    PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_dang_bai);

        edtTieuDe = findViewById(R.id.dbtieude);
        edtNoiDung = findViewById(R.id.dbnoidung);
        btnDangBai = findViewById(R.id.dbdangbai);
        edtAnh = findViewById(R.id.dbimg);

        db = FirebaseFirestore.getInstance();
        preferenceHelper = new PreferenceHelper(this);

        btnDangBai.setOnClickListener(v -> {
            String tentruyen = edtTieuDe.getText().toString();
            String noidung = edtNoiDung.getText().toString();
            String img = edtAnh.getText().toString();

            if (tentruyen.isEmpty() || noidung.isEmpty() || img.isEmpty()) {
                makeText(ManDangBai.this, "Yêu cầu nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                Commics truyen = createCommics(tentruyen, noidung, img);

                String userId = preferenceHelper.getUserId();
                truyen.setUserID(userId);

                CollectionReference truyenRef = db.collection("Commics");
                truyenRef.add(truyen)
                        .addOnSuccessListener(documentReference -> {
                            // Lấy ID của bài viết từ Firebase
                            String truyenId = documentReference.getId();

                            // Cập nhật dữ liệu lên Firebase
                            truyenRef.document(truyenId)
                                    .update("tittle", tentruyen,
                                            "content", noidung,
                                            "img", img,
                                            "userID", userId, "id",truyenId)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(ManDangBai.this, "đăng bài thành công id: "+ truyenId , Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ManDangBai.this, ManAdmin.class);
                                        startActivity(intent);
                                        finish();
                                    })
                                    .addOnFailureListener(e -> makeText(ManDangBai.this, "Cập nhật dữ liệu thất bại", Toast.LENGTH_SHORT).show());
                        })
                        .addOnFailureListener(e -> makeText(ManDangBai.this, "Thêm truyện thất bại", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private Commics createCommics(String tittle, String content, String img) {
        return new Commics(tittle, content, img);
    }
}