package com.example.appdoctruyen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdoctruyen.adapter.UserListAdapter;
import com.example.appdoctruyen.model.Users;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity {
    private ArrayList<Users> usersArrayList;
    private ListView listView;
    private FirebaseFirestore firestore;
    private UserListAdapter newUserListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        listView = findViewById(R.id.listview_users);
        firestore = FirebaseFirestore.getInstance();

        initList();

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_account:
                    Log.d("app log", "Tài khoản: ");
                    Intent accountIntent = new Intent(UserListActivity.this, UserListActivity.class);
                    startActivity(accountIntent);
                    return true;
                case R.id.menu_read_story:
                    Log.d("app log", "Đọc truyện");
                    Intent readStoryIntent = new Intent(UserListActivity.this, NewHome.class);
                    startActivity(readStoryIntent);
                    return true;
                case R.id.menu_menu:
                    Log.d("app log", "menu");
                    Intent menuIntent = new Intent(UserListActivity.this, MenuActivity.class);
                    startActivity(menuIntent);
                    return true;
                default:
                    return false;
            }
        });
    }

    public void initList() {
        usersArrayList = new ArrayList<>();

        CollectionReference usersRef = firestore.collection("Users");
        usersRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                String id = documentSnapshot.getId();
                String email = documentSnapshot.getString("email");
                String password = documentSnapshot.getString("password");
                Object roleObject = documentSnapshot.get("role");
                int role = 0;
                if (roleObject instanceof Integer) {
                    role = (int) roleObject;
                } else if (roleObject instanceof Long) {
                    role = ((Long) roleObject).intValue();
                }
                String username = documentSnapshot.getString("username");
                Log.d("debug Firestore", "ID: " + id + ", Email: " + email + ", Username: " + role);

                usersArrayList.add(new Users(username, password, email, role, id));
            }

            newUserListAdapter = new UserListAdapter(UserListActivity.this, usersArrayList);
            listView.setAdapter(newUserListAdapter);

            Toast.makeText(UserListActivity.this, "Tải dữ liệu thành công", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(UserListActivity.this, "Lỗi khi tải dữ liệu từ Firestore", Toast.LENGTH_SHORT).show();
        });
    }
}