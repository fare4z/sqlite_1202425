package com.fare4z.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DatabaseHelper extends SQLiteOpenHelper {
    //Define database name and version
    private static final String DATABASE_NAME = "tutorial.db";
    private static final int DATABASE_VERSION = 2;
    // SQL Create Table
    private static final String TABLE_CREATE_USER = "CREATE TABLE tblUser " +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT , password TEXT , age TEXT)";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Execute sql create table onCreate
        sqLiteDatabase.execSQL(TABLE_CREATE_USER);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Drop and recreate table if db version changes.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tblUser");
        onCreate(sqLiteDatabase);
    }
}
