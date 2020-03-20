package com.example.prep_authentication_integration.database;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUser(UserEntity userEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(List<UserEntity> sampleUsers);

    @Query("SELECT * FROM usersTable WHERE userName= :userName")
    public UserEntity getUserByUserName(String userName);

}
