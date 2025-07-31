package com.zybooks.davidgerardiweighttracker;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import java.util.List;

@Dao
public interface WeightDao {
    @Insert
    void insertWeight(WeightEntry entry);

    @Query("SELECT * FROM weight_entries ORDER BY date ASC")
    List<WeightEntry> getAllWeights();

    @Query("SELECT * FROM weight_entries WHERE date = :date LIMIT 1")
    WeightEntry getWeightByDate(String date);

    @Update
    void updateWeight(WeightEntry entry);

    @Delete
    void deleteWeight(WeightEntry entry);
}
