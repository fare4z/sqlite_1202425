package com.fare4z.sqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {
    RecyclerView rvUser;
    UserAdapter userAdapter;
    UserDataSource userDataSource;
    ArrayList<UserData> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        rvUser = findViewById(R.id.rvUser);
        rvUser.setLayoutManager(new LinearLayoutManager(this));
        userDataSource = new UserDataSource(getApplicationContext());
        userDataSource.open();

        loadUsers();
        userAdapter = new UserAdapter(this, userList);
        rvUser.setAdapter(userAdapter);
    }

    private void loadUsers() {
        Cursor cursor = userDataSource.getAllUserData();
        userList.clear();

        if (cursor.moveToFirst()) {
            do {
                userList.add(new UserData(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}