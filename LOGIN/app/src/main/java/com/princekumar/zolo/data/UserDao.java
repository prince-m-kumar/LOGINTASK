package com.princekumar.zolo.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.princekumar.zolo.data.entity.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM User")
    List<User> getAll();

    @Query("SELECT * FROM User where name LIKE  :userName")
    User findByName(String userName);

    @Query("SELECT * FROM User where phone LIKE  :userPhone")
    User findByPhone(String userPhone);

    @Query("SELECT * FROM User where emailid LIKE  :emailID")
    User findByEmailID(String emailID);

    @Query("SELECT * FROM User where uid LIKE  :uid")
    User findByID(int uid);

    @Query("SELECT COUNT(*) from User")
    int countUser();

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User users);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateUser(User user);
}
