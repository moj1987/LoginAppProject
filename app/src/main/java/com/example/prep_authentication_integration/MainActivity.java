package com.example.prep_authentication_integration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prep_authentication_integration.database.UserDAO;
import com.example.prep_authentication_integration.database.UserEntity;
import com.example.prep_authentication_integration.utilities.SampleUsers;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ArrayList<UserEntity> mUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button logInButton = findViewById(R.id.log_in);
        logInButton.setOnClickListener(v -> loginButtonClicked());

        addUsers();
        showUsers();

    }

    private void addUsers() {
        mUsers.addAll(SampleUsers.getUsers());
        UserDAO mUsersData;
    }

    private void showUsers() {
        TextView textView = findViewById(R.id.textView);

        textView.setText(mUsers.toString());
    }

    public void loginButtonClicked() {

        if (checkPasswordValidity()) {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "Wrong password!",
                    Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkPasswordValidity() {

//        اینو کجا باید تعریف کنم؟ اگر تو آنکریت تعریف کنم، چطوری اینجا استفاده کنم؟
        EditText usernameInput = findViewById(R.id.user_name_text);

        if (usernameInput.getText().toString().equals("a")) {
            return true;
        }
        return false;
    }

}