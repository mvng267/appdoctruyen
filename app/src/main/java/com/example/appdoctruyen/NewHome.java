package com.example.appdoctruyen;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdoctruyen.adapter.NewAdapterComics;
import com.example.appdoctruyen.model.Comics;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class NewHome extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Comics> comicsArrayList;
    private NewAdapterComics NewAdapterComics;
    private FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_home);

        listView = findViewById(R.id.listviewAdminnew);
        firestore = FirebaseFirestore.getInstance();

        initList();
    }

    public void initList() {
        comicsArrayList = new ArrayList<>();

        CollectionReference comicsRef = firestore.collection("Comics");
        comicsRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                String id = documentSnapshot.getId();
                String content = documentSnapshot.getString("content");
                String img = documentSnapshot.getString("img");
                String title = documentSnapshot.getString("title");
                String userID = documentSnapshot.getString("userID");
                Log.d("Firestore", "ID: " + id + ", Title: " + title + ", Content: " + content);

                comicsArrayList.add(new Comics(id, content, img, title, userID));

            }
            for (Comics comics : comicsArrayList) {
                Log.d("Firestore Data", "ID: " + comics.getId() + ", Title: " + comics.getTitle() + ", Content: " + comics.getContent());
            }
            NewAdapterComics = new NewAdapterComics(NewHome.this, comicsArrayList);
            listView.setAdapter(NewAdapterComics);
            NewAdapterComics.updateData(comicsArrayList); //cập nhật dữ liệu trong adapter

            Toast.makeText(NewHome.this, "Tải dữ liệu thành công", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(NewHome.this, "Lỗi khi tải dữ liệu từ Firestore", Toast.LENGTH_SHORT).show();
        });
    }
}