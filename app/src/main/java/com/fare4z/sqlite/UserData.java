package com.fare4z.sqlite;

public class UserData {
    private long id;
    private String name, password;

    public UserData() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserData(long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }
}
