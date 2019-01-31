package com.example.ve183011m.pki_ve183011m.data;

import com.example.ve183011m.pki_ve183011m.model.Request;
import com.example.ve183011m.pki_ve183011m.model.User;

import java.util.ArrayList;
import java.util.List;

public class RequestManager {

    private static RequestManager instance = new RequestManager();

    private ArrayList<Request> requests = new ArrayList<>();

    private RequestManager() {
    }

    public static RequestManager getInstance() {
        return instance;
    }

    public List<Request> getRequestsForUser(User user) {
        return null;
    }
}
