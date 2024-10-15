package com.fare4z.sqlite;

public class UserData {
    private long id;
    private String name, password;

    public UserData() {
    }
    public UserData(long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }
}
