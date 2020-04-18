package com.yoonbae.planting.planner.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.yoonbae.planting.planner.dao.PlantDao;
import com.yoonbae.planting.planner.entity.Plant;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Plant.class}, version = 1, exportSchema = false)
public abstract class PlantDatabase extends RoomDatabase {
    public abstract PlantDao plantDao();

    private static volatile PlantDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static PlantDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PlantDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PlantDatabase.class, "plant_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}