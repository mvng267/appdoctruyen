package com.example.appdoctruyen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdoctruyen.adapter.NewAdapterComics;
import com.example.appdoctruyen.model.Comics;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class NewHome extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Comics> comicsArrayList;
    private NewAdapterComics newAdapterComics;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_home);

        listView = findViewById(R.id.listviewAdminnew);
        firestore = FirebaseFirestore.getInstance();

        initList();

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_account:
                    Log.d("app log", "Tài khoản: ");
                    Intent accountIntent = new Intent(NewHome.this, UserListActivity.class);
                    startActivity(accountIntent);
                    return true;
                case R.id.menu_read_story:
                    Log.d("app log", "Đọc truyện");
                    Intent readStoryIntent = new Intent(NewHome.this, NewHome.class);
                    startActivity(readStoryIntent);
                    return true;
                case R.id.menu_menu:
                    Log.d("app log", "menu");
                    Intent menuIntent = new Intent(NewHome.this, MenuActivity.class);
                    startActivity(menuIntent);
                    return true;
                default:
                    return false;
            }
        });
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
                Log.d(" debug Firestore", "ID: " + id + ", Title: " + title + ", Content: " + img);

                comicsArrayList.add(new Comics(id, content, img, title, userID));
            }

            newAdapterComics = new NewAdapterComics(NewHome.this, comicsArrayList);
            listView.setAdapter(newAdapterComics);

            Toast.makeText(NewHome.this, "Tải dữ liệu thành công", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(NewHome.this, "Lỗi khi tải dữ liệu từ Firestore", Toast.LENGTH_SHORT).show();
        });
    }
}