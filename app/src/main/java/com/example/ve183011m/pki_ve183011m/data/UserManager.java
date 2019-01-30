package com.example.ve183011m.pki_ve183011m.data;

import com.example.ve183011m.pki_ve183011m.model.User;

import java.util.ArrayList;

public class UserManager {

    private static UserManager instance = new UserManager();

    private ArrayList<User> users = new ArrayList<User>() {{
        add(new User("buyer", "buyer", "John Doe", "064555333",
                "Sunset Boulevard 13", true));
        add(new User("handy", "handy", "Jane Doe", "064555444",
                "Moonlight Boulevard 13", false, 12, true,
                true, false, false, true));
    }};

    private UserManager() {
    }

    public static UserManager getInstance() {
        return instance;
    }

    public User getUserWithCredentials(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }

        return null;
    }

    public void addUser(User user) {
        users.add(user);
    }
}
