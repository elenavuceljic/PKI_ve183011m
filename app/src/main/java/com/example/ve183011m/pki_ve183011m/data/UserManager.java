package com.example.ve183011m.pki_ve183011m.data;

import com.example.ve183011m.pki_ve183011m.model.Handyman;
import com.example.ve183011m.pki_ve183011m.model.Job;
import com.example.ve183011m.pki_ve183011m.model.Request;
import com.example.ve183011m.pki_ve183011m.model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserManager implements Serializable {

    private static UserManager instance = new UserManager();

    private List<User> users = new ArrayList<User>() {{
        add(new User("buyer", "buyer", "John Doe", "064555333",
                "Sunset Boulevard 13"));
        add(new Handyman("handy", "handy", "Jane Doe", "064555444",
                "Moonlight Boulevard 13", 12, new ArrayList<Job>() {{
            add(new Job("Plumbing", 200.0F));
            add(new Job("Air conditioning", 300.0F));
            add(new Job("Electrical installations", 150.0F));
        }}));
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
        int index = users.indexOf(user);
        if (index == -1)
            users.add(user);
        else {
            users.remove(index);
            users.add(user);
        }
    }

    public List<User> getHandymen() {
        List<User> handymen = new ArrayList<>();

        for (User u : users) {
            if (u instanceof Handyman) {
                handymen.add(u);
            }
        }

        return handymen;
    }
}
