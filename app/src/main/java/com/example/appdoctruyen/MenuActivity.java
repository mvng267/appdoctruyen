package com.example.appdoctruyen;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MenuActivity extends AppCompatActivity {

    private TextView tvUsername, tvUserId, tvPhanQuyen, tvEmail;
    private Button btnCreatePost, btnLogout, btnChangePassword;
    private PreferenceHelper preferenceHelper;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuactivity_layout);

        btnChangePassword = findViewById(R.id.btnChangePassword);

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
        userId = preferenceHelper.getUserId();
        int phanQuyen = preferenceHelper.getPhanQuyen();
        String email = preferenceHelper.getEmail();

        tvUsername.setText("Xin chào :" + username);
        Log.e("Check username", "Username: " + username);
        tvUserId.setText("User ID: " + userId);
        if (phanQuyen == 1) {
            tvPhanQuyen.setText("Phân quyền: Tài khoản thường");
        } else if (phanQuyen == 2) {
            tvPhanQuyen.setText("Phân quyền: Tài khoản VIP");
        }
        tvEmail.setText("Email: " + email);
        Log.e("Check phan quyen", "Gia tri phan quyen =" + phanQuyen + "Gia tri username" + username);

        // Xử lý sự kiện khi nhấn nút tạo bài viết mới
        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phanQuyen == 1) {
                    Toast.makeText(MenuActivity.this, "Bạn làm gì có quyền mà đăng bài, mua VIP liên hệ Zalo 0869256720", Toast.LENGTH_LONG).show();
                } else {
                    Intent menuIntent = new Intent(MenuActivity.this, ManAdmin.class);
                    startActivity(menuIntent);
                }
            }
        });

        // Xử lý sự kiện khi nhấn nút đăng xuất
        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý hành động khi nhấn nút đăng xuất
                preferenceHelper.clear(); // Xóa thông tin xác thực hoặc đặt lại trạng thái ứng dụng
                Intent intent = new Intent(MenuActivity.this, ManDangNhap.class);
                startActivity(intent);
                finish(); // Đóng MenuActivity            }
            }});


            // Xử lý sự kiện khi nhấn nút đổi mật khẩu
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openChangePasswordPopup();
                }
            });
        btnChangePassword.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (phanQuyen == 1) {
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    DocumentReference userRef = db.collection("Users").document(userId);

                    Map<String, Object> updates = new HashMap<>();
                    updates.put("role", 2);

                    userRef.update(updates)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Cập nhật thành công
                                    Log.d("Firestore", "Cập nhật giá trị 'role' thành công");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Xử lý lỗi nếu cập nhật thất bại
                                    Log.e("Firestore", "Lỗi khi cập nhật giá trị 'role'", e);
                                }
                            });
                } else if (phanQuyen == 2) {
                    tvPhanQuyen.setText("Phân quyền: Tài khoản VIP");
                }

                return true;
            }
        });

            // Xử lý sự kiện khi chọn các mục trong BottomNavigationView
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

        private void openChangePasswordPopup() {
            // Tạo dialog
            Dialog changePasswordDialog = new Dialog(MenuActivity.this);
            changePasswordDialog.setContentView(R.layout.popup_change_password);

            // Ánh xạ các view trong dialog
            EditText etNewPassword = changePasswordDialog.findViewById(R.id.etNewPassword);
            EditText etConfirmPassword = changePasswordDialog.findViewById(R.id.etConfirmPassword);
            Button btnChange = changePasswordDialog.findViewById(R.id.btnChangePassword);
            Button btnCancel = changePasswordDialog.findViewById(R.id.btnCancelChangePassword);

            // Xử lý sự kiện khi nhấn nút "Đổi mật khẩu" trong dialog
            btnChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String newPassword = etNewPassword.getText().toString();
                    String confirmPassword = etConfirmPassword.getText().toString();
                    if (newPassword.length() < 8) {
                        Toast.makeText(MenuActivity.this, "Mật khẩu phải có ít nhất 8 ký tự", Toast.LENGTH_SHORT).show();
                        Log.e("Thông báo: ", "Mật khẩu phải có ít nhất 8 ký tự");
                        return;
                    }
                    if (confirmPassword.length() < 8) {
                        Toast.makeText(MenuActivity.this, "Mật khẩu phải có ít nhất 8 ký tự", Toast.LENGTH_SHORT).show();
                        Log.e("Thông báo: ", "Mật khẩu phải có ít nhất 8 ký tự");
                        return;
                    }

                    // Kiểm tra mật khẩu mới và xác nhận mật khẩu phải khớp
                    if (newPassword.equals(confirmPassword)) {
                        // Thực hiện đổi mật khẩu
                        updatePasswordInFirebase(newPassword);

                        Toast.makeText(MenuActivity.this, "Đã đổi mật khẩu", Toast.LENGTH_SHORT).show();

                        // Đóng dialog
                        changePasswordDialog.dismiss();
                        return;
                    }
                    Toast.makeText(MenuActivity.this, "Mật khẩu không khớp nhau, đổi lại đi ", Toast.LENGTH_SHORT).show();

                }
            });

            // Xử lý sự kiện khi nhấn nút "Hủy" trong dialog
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Đóng dialog
                    changePasswordDialog.dismiss();
                }
            });

            // Hiển thị dialog
            changePasswordDialog.show();
        }

        private void updatePasswordInFirebase(String newPassword) {
            // Tham chiếu đến collection "users" trong Firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference userRef = db.collection("Users").document(userId);

            // Tạo một HashMap để lưu trữ dữ liệu cần cập nhật
            Map<String, Object> updates = new HashMap<>();
            updates.put("password", newPassword);

            // Thực hiện cập nhật dữ liệu trong Firestore
            userRef.update(updates)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Cập nhật thành công
                            Log.d("Firestore", "Cập nhật mật khẩu thành công");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Xử lý lỗi nếu cập nhật thất bại
                            Log.e("Firestore", "Lỗi khi cập nhật mật khẩu", e);
                        }
                    });
        }
    }
