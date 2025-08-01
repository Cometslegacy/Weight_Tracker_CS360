package com.zybooks.davidgerardiweighttracker;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "login_accounts")
public class LoginAccount {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String login_email;

    @NonNull
    public String login_password;
}





