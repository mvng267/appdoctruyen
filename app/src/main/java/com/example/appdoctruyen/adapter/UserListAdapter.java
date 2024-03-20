package com.example.appdoctruyen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appdoctruyen.R;
import com.example.appdoctruyen.model.Users;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserListAdapter extends ArrayAdapter<Users> {
    private final Context mContext;
    private ArrayList<Users> mUsersList;

    public UserListAdapter(Context context, ArrayList<Users> usersList) {
        super(context, 0, usersList);
        mContext = context;
        mUsersList = usersList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_user, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewId = convertView.findViewById(R.id.textview_id);
            viewHolder.textViewEmail = convertView.findViewById(R.id.textview_Email);
            viewHolder.textViewRole = convertView.findViewById(R.id.textview_role);
            viewHolder.textViewUsername = convertView.findViewById(R.id.textview_username);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Users user = mUsersList.get(position);

        viewHolder.textViewId.setText(user.getID());
        viewHolder.textViewUsername.setText(user.getUsername());
        viewHolder.textViewEmail.setText(user.getEmail());

        int role = user.getRoles();
        if (role == 1) {
            viewHolder.textViewRole.setText("Tài khoản thường");
        } else if (role == 2) {
            viewHolder.textViewRole.setText("Tài khoản VIP");
        }

        return convertView;
    }

    static class ViewHolder {
        TextView textViewEmail;
        TextView textViewId;
        TextView textViewRole;
        TextView textViewUsername;
    }

    public void updateData(ArrayList<Users> usersList) {
        mUsersList.clear();
        mUsersList.addAll(usersList);
        notifyDataSetChanged();
    }
}