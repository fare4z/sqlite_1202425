package com.fare4z.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UserDataSource {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    public UserDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }
    private static final String TABLE_NAME = "tblUser";

    public long insertUserData(String name, String password) {
        ContentValues values = new ContentValues();
        values.put("name" , name);
        values.put("password", password);
        return database.insert(TABLE_NAME, null, values);
    }

    public Cursor getAllUserData() {
        String[] allColumn = {"id" , "name" , "password"};
        return database.query(TABLE_NAME,allColumn,
                null,
                null,
                null,
                null,
                null);
    }

    public Cursor getLoginData(String name , String password) {
        String[] allColumn = {"id", "name" , "password"};
        return database.query(TABLE_NAME,allColumn,
                "name = ? and password = ?",
                new String[] {name , password},
                null,
                null,
                null);
    }

    public long updateUserData(long id , String name, String password) {
        ContentValues values = new ContentValues();
        values.put("name" , name);
        values.put("password", password);
        return database.update(TABLE_NAME, values,
                "id = ?",
                new String[]{String.valueOf(id)});
    }

    public void deleteUser(long id) {
        database.delete(TABLE_NAME, "id =" + id,null);
    }
}
