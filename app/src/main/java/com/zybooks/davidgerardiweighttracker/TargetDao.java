package com.zybooks.davidgerardiweighttracker;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.OnConflictStrategy;

@Dao
public interface TargetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void setTarget(TargetWeight target);

    @Query("SELECT * FROM target_weight LIMIT 1")
    TargetWeight getTarget();
}
