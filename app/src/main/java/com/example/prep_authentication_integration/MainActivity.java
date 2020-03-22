package com.example.prep_authentication_integration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prep_authentication_integration.database.UserDatabase;
import com.example.prep_authentication_integration.database.UserEntity;
import com.example.prep_authentication_integration.utilities.SampleUsers;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity {

    ArrayList<UserEntity> mUsers = new ArrayList<>();
    EditText usernameInput;
    EditText passwordInput;
    TextView textView;
    private UserDatabase mDb;
    private Executor executor = Executors.newSingleThreadExecutor();
    final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameInput = findViewById(R.id.user_name_text);
        passwordInput = findViewById(R.id.password_Text);
        textView = findViewById(R.id.textView);

        Button logInButton = findViewById(R.id.log_in);
        logInButton.setOnClickListener(v -> loginButtonClicked());

        Button createAccountButton = findViewById(R.id.creat_account);
        createAccountButton.setOnClickListener(v -> createAccountButtonClicked());

        addSampleUsers();
//        showUsers();
    }

    private void createAccountButtonClicked() {
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
        UserEntity user = new UserEntity(username,password);
        Log.i(TAG, "checking information for username: " + username + ", and password: " + password);

        executor.execute((() -> {
            mDb.userDAO().insertUser(user);
            runOnUiThread(() -> {
                Toast.makeText(this, "New user created", Toast.LENGTH_LONG).show();
                usernameInput.setText("");
                passwordInput.setText("");
            });

        }));

    }

    /**
     * Add a few sample users for testing purposes.
     */
    private void addSampleUsers() {
        executor.execute(() -> {
            mDb = UserDatabase.getInstance(getApplicationContext());
            mDb.userDAO().insertAll(SampleUsers.getUsers());
        });
    }

    private void showUsers() {
        executor.execute(() -> {
            ArrayList<UserEntity> users = (ArrayList<UserEntity>) mDb.userDAO().getAllUsers();
            runOnUiThread(() -> textView.setText(users.toString()));
        });
    }

    public void loginButtonClicked() {

        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
        Log.i(TAG, "checking information for username: " + username + ", and password: " + password);

        executor.execute((() -> {
            UserEntity user = mDb.userDAO().getUserByUserName(username);
            runOnUiThread(() -> validateUser(user));
        }));
    }

    public void validateUser(UserEntity user) {

        if (user == null) {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.i(TAG, "user found, password should be: " + user.getPassword());
        String password = passwordInput.getText().toString();
        if (!password.equals(user.getPassword())) {
            Toast.makeText(MainActivity.this, "Password is incorrect", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    private boolean checkPasswordValidity() {

        if (usernameInput.getText().toString().equals("a")) {
            return true;
        }
        return false;
    }

}