package com.example.appdoctruyen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appdoctruyen.R;
import com.example.appdoctruyen.model.Comics;

import java.util.ArrayList;

public class NewAdapterComics extends ArrayAdapter<Comics> {
    private final Context mContext;
    private ArrayList<Comics> mComicsList;

    public NewAdapterComics(Context context, ArrayList<Comics> comicsList) {
        super(context, 0, comicsList);
        mContext = context;
        mComicsList = comicsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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

        Comics comics = mComicsList.get(position);

        viewHolder.textViewTitle.setText(comics.getTitle());
        viewHolder.textViewContent.setText(comics.getContent());
        viewHolder.textViewUserID.setText(comics.getUserID());
        Glide.with(mContext).load(comics.getImg()).into(viewHolder.imageView);

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
        notifyDataSetChanged();
    }
}