package com.example.appdoctruyen.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appdoctruyen.ManNoiDungTruyen;
import com.example.appdoctruyen.R;
import com.example.appdoctruyen.model.Comics;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewAdapterComics extends ArrayAdapter<Comics> {
    private final Context mContext;
    private ArrayList<Comics> mComicsList;
    private ArrayList<Comics> mComicsListFiltered;

    public NewAdapterComics(Context context, ArrayList<Comics> comicsList) {
        super(context, 0, comicsList);
        mContext = context;
        mComicsList = comicsList;
        mComicsListFiltered = comicsList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_comics, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewTitle = convertView.findViewById(R.id.textViewTitle);
            viewHolder.textViewContent = convertView.findViewById(R.id.textViewContent);
            viewHolder.imageView = convertView.findViewById(R.id.imageView);
            viewHolder.textViewUserID = convertView.findViewById(R.id.textViewUserID);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Comics comics = mComicsListFiltered.get(position);

        viewHolder.textViewTitle.setText(comics.getTitle());
        String content = comics.getContent();
        viewHolder.textViewContent.setText(content);
        viewHolder.textViewUserID.setText(comics.getUserID());

        // Sử dụng Picasso để tải và hiển thị ảnh từ URL
        Picasso.get().load(comics.getImg()).placeholder(R.drawable.ic_baseline_cloud_download_24).error(R.drawable.ic_baseline_image_not_supported_24).into(viewHolder.imageView);

        // Lấy username từ Users dựa trên userID
        FirebaseFirestore.getInstance().collection("Users").document(comics.getUserID())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String username = documentSnapshot.getString("username");
                        viewHolder.textViewUserID.setText(username);
                    } else {
                        viewHolder.textViewUserID.setText("Unknown User");
                    }
                })
                .addOnFailureListener(e -> {
                    // Xử lý khi có lỗi truy vấn
                    viewHolder.textViewUserID.setText("Unknown User");
                    Log.e("Truy vấn Firestore", "Lỗi: " + e.getMessage());
                });

        return convertView;
    }

    static class ViewHolder {
        TextView textViewTitle;
        TextView textViewContent;
        ImageView imageView;
        TextView textViewUserID;
    }

    public void updateData(ArrayList<Comics> comicsList) {
        mComicsList.clear();
        mComicsList.addAll(comicsList);
        mComicsListFiltered = comicsList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mComicsListFiltered.size();
    }

    @Override
    public Comics getItem(int position) {
        return mComicsListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}