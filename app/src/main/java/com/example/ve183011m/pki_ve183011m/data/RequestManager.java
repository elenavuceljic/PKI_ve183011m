package com.example.ve183011m.pki_ve183011m.data;

import com.example.ve183011m.pki_ve183011m.R;
import com.example.ve183011m.pki_ve183011m.model.Handyman;
import com.example.ve183011m.pki_ve183011m.model.Job;
import com.example.ve183011m.pki_ve183011m.model.Request;
import com.example.ve183011m.pki_ve183011m.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static java.text.DateFormat.getDateInstance;

public class RequestManager {

    private static RequestManager instance = new RequestManager();

    private ArrayList<Request> requests = new ArrayList<Request>(){{
        UserManager userManager = UserManager.getInstance();
        User buyer = userManager.getUserWithCredentials("buyer", "buyer");
        Handyman handyman = (Handyman) userManager.getUserWithCredentials("handy", "handy");
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        try {
            add(new Request(handyman, buyer, Request.Urgency.HIGH, ft.parse("2019-03-12"), ft.parse("2019-03-14"), "Bulevar kralja Aleksandra 234", Request.Status.SENT, true, new Job("Air conditioning", 200.0F)));
            add(new Request(handyman, buyer, Request.Urgency.LOW, ft.parse("2019-02-12"), ft.parse("2019-02-14"), "Bulevar kralja Aleksandra 234", Request.Status.ACCEPTED, false, new Job("Plumbing", 200.0F)));
            add(new Request(handyman, buyer, Request.Urgency.MEDIUM, ft.parse("2019-03-03"), ft.parse("2019-03-04"), "Bulevar kralja Aleksandra 234", Request.Status.FAILED, false, new Job("Furniture assembly", 200.0F)));
            add(new Request(handyman, buyer, Request.Urgency.LOW, ft.parse("2019-03-22"), ft.parse("2019-03-24"), "Bulevar kralja Aleksandra 234", Request.Status.DENIED, true, new Job("Plumbing", 200.0F)));
            add(new Request(handyman, buyer, Request.Urgency.HIGH, ft.parse("2019-04-01"), ft.parse("2019-04-02"), "Bulevar kralja Aleksandra 234", Request.Status.DONE, false, new Job("Electrical installations", 200.0F)));
            add(new Request(handyman, buyer, Request.Urgency.HIGH, ft.parse("2019-03-15"), ft.parse("2019-03-16"), "Bulevar kralja Aleksandra 234", Request.Status.SENT, true, new Job("Plumbing", 200.0F)));
            add(new Request(handyman, buyer, Request.Urgency.LOW, ft.parse("2019-02-02"), ft.parse("2019-02-04"), "Bulevar kralja Aleksandra 234", Request.Status.SENT, true, new Job("Interior painting", 200.0F)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }};

    private RequestManager() {
    }

    public static RequestManager getInstance() {
        return instance;
    }

    public List<Request> getActiveRequestsForBuyer(User user) {
        List<Request> list = new ArrayList<>();

        for (Request request: requests) {
            if (request.getStatus().ordinal() < 2 && request.getBuyer().equals(user)) {
                list.add(request);
            }
        }

        return list;
    }

    public List<Request> getClosedRequestsForBuyer(User user) {
        List<Request> list = new ArrayList<>();

        for (Request request: requests) {
            if (request.getStatus().ordinal() > 1 && request.getBuyer().equals(user)) {
                list.add(request);
            }
        }

        return list;
    }

    public List<Request> getActiveRequestsForHandyman(User user) {
        List<Request> list = new ArrayList<>();

        for (Request request: requests) {
            if (request.getStatus().ordinal() < 2 && request.getHandyman().equals(user)) {
                list.add(request);
            }
        }

        return list;
    }

    public List<Request> getClosedRequestsForHandyman(User user) {
        List<Request> list = new ArrayList<>();

        for (Request request: requests) {
            if (request.getStatus().ordinal() > 1 && request.getHandyman().equals(user)) {
                list.add(request);
            }
        }

        return list;
    }

    public void deleteRequest(Request request) {
        requests.remove(request);
    }

    public void addRequest(Request request) {
        requests.add(request);
    }
}
