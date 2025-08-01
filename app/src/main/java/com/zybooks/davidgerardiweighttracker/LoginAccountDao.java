package com.zybooks.davidgerardiweighttracker;

import android.util.Log;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LoginAccountDao {

    @Insert
    void insert(LoginAccount account);

    @Query("SELECT * FROM login_accounts WHERE login_email = :email AND login_password = :password")
    LoginAccount login(String email, String password);

    @Query("SELECT * FROM login_accounts")
    List<LoginAccount> getAllAccounts();

    // We don't need the ability to delete accounts right now, but its here just in case.
    @Delete
    void delete(LoginAccount account);
}
