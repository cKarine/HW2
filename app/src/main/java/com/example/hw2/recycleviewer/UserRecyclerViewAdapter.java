package com.example.hw2.recycleviewer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hw2.R;
import com.example.hw2.usersDB.User;
import com.example.hw2.usersDB.UserDao;
import com.example.hw2.usersDB.UserDatabase;

import java.util.List;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserViewHolder> {
    private final List<User> dataSet;
    public UserRecyclerViewAdapter(List<User> dataSet) {
        this.dataSet = dataSet;
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user, parent, false);
        return new UserViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        View view = holder.itemView;
        User user = dataSet.get(position);
        holder.firstname.setText(user.firstName);
        holder.lastname.setText(user.lastName);
        holder.city.setText(user.city);
        holder.country.setText(user.country);

        // Set the user icon
        Glide.with(view)
                .load(user.image)
                .into(holder.userIcon);

        holder.setOnItemLongClickListener(position1 -> {

            try{
                // Delete the user from the database
                UserDatabase userDatabase = UserDatabase.getInstance(view.getContext());
                UserDao userDao = userDatabase.userDao();
                userDao.deleteUser(user);
                dataSet.remove(position1);
                notifyItemRemoved(position1);
            } catch (Exception e) {
                Toast.makeText(view.getContext(), "Error deleting user", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override public int getItemCount() {
        return dataSet.size();
    }
}

