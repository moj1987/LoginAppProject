package com.example.prep_authentication_integration.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UserEntity.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "UsersDatabase.db";
    public static volatile UserDatabase instance;
    public static final Object LOCK = new Object();

    public abstract UserDAO userDAO();

    public static UserDatabase getInstance(Context context) {
        if (instance == null)
            synchronized (LOCK) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            UserDatabase.class,
                            DATABASE_NAME).build();
                }
            }

        return instance;
    }
}
