package com.example.ve183011m.pki_ve183011m.data;

import com.example.ve183011m.pki_ve183011m.model.User;

import java.util.ArrayList;

public class UserManager {

    private static UserManager instance = new UserManager();

    private ArrayList<User> users = new ArrayList<User>() {{
        add(new User("buyer", "buyer"));
        add(new User("handy", "handy"));
    }};

    private UserManager() {
    }

    public static UserManager getInstance() {
        return instance;
    }

    public boolean containsUser(User user) {
        return users.contains(user);
    }

    public void addUser(User user) {
        users.add(user);
    }
}
