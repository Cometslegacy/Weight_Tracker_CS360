package com.zybooks.davidgerardiweighttracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "weight_entries")
public class WeightEntry {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String date; // Year-Month-Day format for easiest sorting. Example: 2025-07-30

    public float weight;
}
