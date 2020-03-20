package com.example.prep_authentication_integration.utilities;

import com.example.prep_authentication_integration.database.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class SampleUsers {
    private static final String username1 = "Mojtaba";
    private static final String username2 = "Sina";
    private static final String username3 = "Bagher";

    private static final String password1 = "mojtaba";
    private static final String password2 = "1";
    private static final String password3 = "2";

    public static List<UserEntity> getUsers(){
        ArrayList<UserEntity> users = new ArrayList<>();
        users.add(new UserEntity(username1,password1));
        users.add(new UserEntity(username2,password2));
        users.add(new UserEntity(username3,password3));
        return users;
    }


}
