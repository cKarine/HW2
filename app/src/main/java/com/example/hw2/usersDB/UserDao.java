package com.example.hw2.usersDB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    List<User> getAll();
    @Query("SELECT * FROM users WHERE id = :id")
    User getUserById(int id);
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertUser(User user);

    @Delete
    void deleteUser(User user);
}

