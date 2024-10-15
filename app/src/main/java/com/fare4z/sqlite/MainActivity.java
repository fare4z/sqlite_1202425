package com.fare4z.sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText etUsername , etPassword ,etID;
    Button btnRegister , btnView , btnLogin , btnUpdate , btnDelete;
    TextView tvOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnView = findViewById(R.id.btnView);
        tvOutput = findViewById(R.id.tvOutput);
        btnLogin = findViewById(R.id.btnLogin);
        etID = findViewById(R.id.etID);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDataSource userDataSource = new UserDataSource(getApplicationContext());
                userDataSource.open();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                long newUser = userDataSource.insertUserData(username , password);

                etUsername.setText(null);
                etPassword.setText(null);
                userDataSource.close();
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();

            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDataSource userDataSource = new UserDataSource(getApplicationContext());
                userDataSource.open();
                Cursor cursor = userDataSource.getAllUserData();
                if (cursor.moveToFirst()) {
                    do {
                        String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                        String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                        StringBuilder sb = new StringBuilder();
                        sb.append("ID : ").append(id).append("\n")
                                .append("Name : ").append(name).append("\n")
                                .append("Password : ").append(password);

                        tvOutput.setText(sb.toString());
                        etID.setText(id);
                        etUsername.setText(name);
                        etPassword.setText(password);

                    } while (cursor.moveToNext());
                } else {
                    tvOutput.setText("Data not found!");
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameInput = etUsername.getText().toString();
                String passwordInput = etPassword.getText().toString();
                UserDataSource userDataSource = new UserDataSource(getApplicationContext());
                userDataSource.open();
                Cursor cursor = userDataSource.getLoginData(usernameInput , passwordInput);
                if (cursor.moveToFirst()) {
                    do {
                        long id = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
                        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                        String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                        StringBuilder sb = new StringBuilder();
                        sb.append("Welcome : ").append(name).append("\n");

                        tvOutput.setText(sb.toString());
                    } while (cursor.moveToNext());
                } else {
                    tvOutput.setText("Data not found");
                }

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameInput = etUsername.getText().toString();
                String passwordInput = etPassword.getText().toString();
                String idInput = etID.getText().toString();

                UserDataSource userDataSource = new UserDataSource(getApplicationContext());
                userDataSource.open();
                userDataSource.updateUserData(
                        Long.parseLong(idInput),
                        usernameInput ,
                        passwordInput);
                userDataSource.close();
                Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idInput = etID.getText().toString();
                UserDataSource userDataSource = new UserDataSource(getApplicationContext());
                userDataSource.open();
                userDataSource.deleteUser(Long.parseLong(idInput));
                userDataSource.close();

                Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();

            }
        });
    }
}