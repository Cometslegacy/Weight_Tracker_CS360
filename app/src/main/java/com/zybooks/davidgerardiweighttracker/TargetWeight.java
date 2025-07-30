package com.zybooks.davidgerardiweighttracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "target_weight")
public class TargetWeight {
    @PrimaryKey
    public int id = 1; // singleton

    public float target;
}
