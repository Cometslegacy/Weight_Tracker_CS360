package com.zybooks.davidgerardiweighttracker;

import android.content.Context;
import android.view.ViewGroup;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {WeightEntry.class, TargetWeight.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract WeightDao weightDao();
    public abstract TargetDao targetDao();

    private static AppDatabase INSTANCE;

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "weight_tracker_db")
                    .allowMainThreadQueries() //TODO Check if this is useful in production. I think it should be removed at a later date
                    .build();
        }
        return INSTANCE;
    }
}
