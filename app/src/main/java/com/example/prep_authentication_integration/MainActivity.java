package com.example.prep_authentication_integration;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prep_authentication_integration.database.UserDatabase;
import com.example.prep_authentication_integration.database.UserEntity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * MainActivity for the sample app.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * EditText for the username field.
     */
    EditText usernameInput;

    /**
     * EditText for the password field.
     */
    EditText passwordInput;

    /**
     * Instance of database for reading and writing.
     */
    private UserDatabase mDb;

    /**
     * An executor to run the database operations on a separate thread.
     */
    private Executor executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameInput = findViewById(R.id.user_name_text);
        passwordInput = findViewById(R.id.password_Text);

        configureListeners();
    }

    /**
     * All the listeners are configured here.
     */
    private void configureListeners() {
        // Configure login button listener
        Button logInButton = findViewById(R.id.log_in);
        logInButton.setOnClickListener(v -> attemptLogin());

        // Configure create account button listener
        Button createAccountButton = findViewById(R.id.creat_account);
        createAccountButton.setOnClickListener(v -> createAccount());
    }

    /**
     * Create an account for the given username and password entered. For simplicity no check is performed when creating
     * an account. After creating the account the fields are cleared to be ready for other actions.
     */
    private void createAccount() {
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
        UserEntity user = new UserEntity(username, password);

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
     * Attempt to login with the given username and password.
     */
    public void attemptLogin() {
        String username = usernameInput.getText().toString();

        executor.execute((() -> {
            UserEntity user = mDb.userDAO().getUserByUserName(username);
            runOnUiThread(() -> validateUser(user));
        }));
    }

    /**
     * Check if a user was retrieved from the database and if the entered password matched the user's password from
     * the database. For simplicity, this functions show a toast message for different scenarios.
     *
     * @param user The user to check.
     */
    public void validateUser(UserEntity user) {
        if (user == null) {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            return;
        }

        String password = passwordInput.getText().toString();
        if (!password.equals(user.getPassword())) {
            Toast.makeText(MainActivity.this, "Password is incorrect", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

}