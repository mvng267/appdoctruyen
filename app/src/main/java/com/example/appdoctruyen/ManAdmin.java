package com.example.appdoctruyen;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdoctruyen.adapter.NewAdapterComics;
import com.example.appdoctruyen.model.Comics;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ManAdmin extends AppCompatActivity {

    ListView listView;
    Button buttonThem;

    private ArrayList<Comics> comicsArrayList;
    private NewAdapterComics newAdapterComics;
    private FirebaseFirestore firestore;
    private PreferenceHelper preferenceHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_admin);
        preferenceHelper = new PreferenceHelper(this);
        listView = findViewById(R.id.listviewAdmin);
        buttonThem = findViewById(R.id.buttonAddTruyen);

        firestore = FirebaseFirestore.getInstance();

        initList();
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            return false;
        });

        buttonThem.setOnClickListener(v -> {
            Intent intent = getIntent();
            String id = intent.getStringExtra("Id");
            Intent intent1 = new Intent(ManAdmin.this, ManDangBai.class);
            intent1.putExtra("Id", id);
            startActivity(intent1);
        });
    }

    // Gán dữ liệu vào ListView
    public void initList() {
        comicsArrayList = new ArrayList<>();

        String userID = preferenceHelper.getUserId();
        CollectionReference comicsRef = firestore.collection("Comics");
        comicsRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                String id = documentSnapshot.getId();
                String content = documentSnapshot.getString("content");
                String img = documentSnapshot.getString("img");
                String title = documentSnapshot.getString("title");
                String documentUserID = documentSnapshot.getString("userID"); // Lấy giá trị userID từ Firestore

                if (userID.equals(documentUserID)) { // Kiểm tra trùng lặp giữa userID và documentUserID
                    Log.d("debug Firestore", "ID: " + id + ", Title: " + title + ", Content: " + img);

                    comicsArrayList.add(new Comics(id, content, img, title, userID));
                }
            }

            newAdapterComics = new NewAdapterComics(ManAdmin.this, comicsArrayList);
            listView.setAdapter(newAdapterComics);

            Toast.makeText(ManAdmin.this, "Tải dữ liệu thành công", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(ManAdmin.this, "Lỗi khi tải dữ liệu từ Firestore", Toast.LENGTH_SHORT).show();
        });
    }
}