package com.example.appdoctruyen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuActivity extends AppCompatActivity {

    private TextView tvUsername, tvUserId, tvPhanQuyen, tvEmail;
    private Button btnCreatePost;
    private PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuactivity_layout);

        // Khởi tạo PreferenceHelper
        preferenceHelper = new PreferenceHelper(this);

        // Ánh xạ các view
        tvUsername = findViewById(R.id.tvUsername);
        tvUserId = findViewById(R.id.tvUserId);
        tvPhanQuyen = findViewById(R.id.tvPhanQuyen);
        btnCreatePost = findViewById(R.id.btnCreatePost);
        tvEmail = findViewById(R.id.tvEmail);

        // Lấy thông tin từ PreferenceHelper và hiển thị lên view
        String username = preferenceHelper.getUsername();
        String userId = preferenceHelper.getUserId();
        int phanQuyen = preferenceHelper.getPhanQuyen();
        String email = preferenceHelper.getEmail();

        tvUsername.setText("Username: " + username);
        Log.e("Check username", "Username: "+ username );
        tvUserId.setText("User ID: " + userId);
        tvPhanQuyen.setText("Phân quyền: " + phanQuyen);
        tvEmail.setText("Email" + email);



        // Xử lý sự kiện khi nhấn nút tạo bài viết mới
        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuIntent = new Intent(MenuActivity.this, ManAdmin.class);
                startActivity(menuIntent);
            }
        });
        btnCreatePost = findViewById(R.id.btnCreatePost);
        tvEmail = findViewById(R.id.tvEmail);
        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý hành động khi nhấn nút đăng xuất
                preferenceHelper.clear(); // Xóa thông tin xác thực hoặc đặt lại trạng thái ứng dụng
                Intent intent = new Intent(MenuActivity.this, ManDangNhap.class);
                startActivity(intent);
                finish(); // Đóng MenuActivity
            }
        });
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_account:
                    Log.d("app log", "Tài khoản: ");
                    Intent accountIntent = new Intent(MenuActivity.this, UserListActivity.class);
                    startActivity(accountIntent);
                    return true;
                case R.id.menu_read_story:
                    Log.d("app log", "Đọc truyện");
                    Intent readStoryIntent = new Intent(MenuActivity.this, NewHome.class);
                    startActivity(readStoryIntent);
                    return true;
                case R.id.menu_menu:
                    Log.d("app log", "menu");
                    Intent menuIntent = new Intent(MenuActivity.this, MenuActivity.class);
                    startActivity(menuIntent);
                    return true;
                default:
                    return false;
            }
        });
    }
}