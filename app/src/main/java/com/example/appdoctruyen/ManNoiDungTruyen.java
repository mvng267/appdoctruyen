package com.example.appdoctruyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;;
import com.google.firebase.database.ValueEventListener;

public class ManNoiDungTruyen extends AppCompatActivity {

    TextView txtTenTruyen, txtNoidung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_noi_dung_truyen);

        txtNoidung = findViewById(R.id.NoiDung);
        txtTenTruyen = findViewById(R.id.TenTruyen);

        String comicsId = getIntent().getStringExtra("comicsId");

        DatabaseReference comicsRef = FirebaseDatabase.getInstance().getReference().child("comics");

        Query query = comicsRef.orderByChild("id").equalTo(comicsId);
        Log.e("commic ID", "onClick: in ra ID bài viết " + comicsId );

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String tenTruyen = snapshot.child("title").getValue(String.class);
                        String noiDung = snapshot.child("content").getValue(String.class);
                        Log.e("ten truyen", "Ten truyen:" + tenTruyen + "noi dung: " + noiDung);
                        txtTenTruyen.setText(tenTruyen);
                        txtNoidung.setText(noiDung);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ten truyen", "loi");
            }
        });

        txtNoidung.setMovementMethod(new ScrollingMovementMethod());
    }
}