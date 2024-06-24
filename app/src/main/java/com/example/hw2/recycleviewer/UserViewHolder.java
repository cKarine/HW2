package com.example.hw2.recycleviewer;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw2.R;

public class UserViewHolder extends RecyclerView.ViewHolder {
    public ImageView userIcon;
    public TextView firstname;
    public TextView lastname;
    public TextView country;
    public TextView city;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        userIcon = itemView.findViewById(R.id.usericon);
        firstname = itemView.findViewById(R.id.firstname);
        lastname = itemView.findViewById(R.id.lastname);
        country = itemView.findViewById(R.id.country);
        city = itemView.findViewById(R.id.city);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        itemView.setOnLongClickListener(v -> {
            if (listener != null) {
                listener.onItemLongClicked(getAdapterPosition());
            }
            return true;
        });
    }
}
