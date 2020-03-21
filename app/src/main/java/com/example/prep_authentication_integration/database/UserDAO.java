package com.example.prep_authentication_integration.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(UserEntity userEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<UserEntity> sampleUsers);

    @Query("SELECT * FROM usersTable WHERE userName= :userName")
    UserEntity getUserByUserName(String userName);

    @Query("SELECT * FROM usersTable ORDER BY userName DESC")
    List<UserEntity> getAllUsers();

}
