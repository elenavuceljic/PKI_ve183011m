package com.example.ve183011m.pki_ve183011m.data;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.example.ve183011m.pki_ve183011m.model.Handyman;
import com.example.ve183011m.pki_ve183011m.model.Job;
import com.example.ve183011m.pki_ve183011m.model.Request;
import com.example.ve183011m.pki_ve183011m.model.User;
import com.example.ve183011m.pki_ve183011m.util.HandyApplication;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RequestManager {

    private static RequestManager instance = new RequestManager();

    private static List<Request.Urgency> urgencyFilter = new ArrayList<Request.Urgency>() {{
        add(Request.Urgency.HIGH);
        add(Request.Urgency.MEDIUM);
        add(Request.Urgency.LOW);
    }};
    private static float distanceFilter = 50;

    private ArrayList<Request> requests = new ArrayList<Request>() {{
        UserManager userManager = UserManager.getInstance();
        User buyer = userManager.getUserWithCredentials("buyer", "buyer");
        Handyman handyman = (Handyman) userManager.getUserWithCredentials("handy", "handy");
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        try {
            add(new Request(handyman, buyer, Request.Urgency.HIGH, ft.parse("2019-03-12"), ft.parse("2019-03-14"), "Bulevar kralja Aleksandra 34", Request.Status.SENT, true, new Job("Air conditioning", 200.0F)));
            add(new Request(handyman, buyer, Request.Urgency.LOW, ft.parse("2019-02-12"), ft.parse("2019-02-14"), "Bulevar kralja Aleksandra 234", Request.Status.ACCEPTED, false, new Job("Plumbing", 200.0F)));
            add(new Request(handyman, buyer, Request.Urgency.MEDIUM, ft.parse("2019-03-03"), ft.parse("2019-03-04"), "Kneza Milosa 14", Request.Status.FAILED, false, new Job("Furniture assembly", 200.0F)));
            add(new Request(handyman, buyer, Request.Urgency.LOW, ft.parse("2019-03-22"), ft.parse("2019-03-24"), "Ruzveltova 30", Request.Status.DENIED, true, new Job("Plumbing", 200.0F)));
            add(new Request(handyman, buyer, Request.Urgency.HIGH, ft.parse("2019-04-01"), ft.parse("2019-04-02"), "Kneginje Ljubice 2", Request.Status.DONE, false, new Job("Electrical installations", 200.0F)));
            add(new Request(handyman, buyer, Request.Urgency.HIGH, ft.parse("2019-03-15"), ft.parse("2019-03-16"), "Dimitrija Tucovica 15", Request.Status.SENT, true, new Job("Plumbing", 200.0F)));
            add(new Request(handyman, buyer, Request.Urgency.LOW, ft.parse("2019-02-02"), ft.parse("2019-02-04"), "Juzni bulevra 99", Request.Status.SENT, true, new Job("Interior painting", 200.0F)));
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

        for (Request request : requests) {
            if (request.getStatus().ordinal() < 2 && request.getBuyer().equals(user)) {
                list.add(request);
            }
        }

        return list;
    }

    public List<Request> getClosedRequestsForBuyer(User user) {
        List<Request> list = new ArrayList<>();

        for (Request request : requests) {
            if (request.getStatus().ordinal() > 1 && request.getBuyer().equals(user)) {
                list.add(request);
            }
        }

        return list;
    }

    public List<Request> getActiveRequestsForHandyman(User user) {
        List<Request> list = new ArrayList<>();

        for (Request request : requests) {
            if (request.getStatus().ordinal() < 2 && request.getHandyman().equals(user) && isInHandymanResults(request)) {
                list.add(request);
            }
        }

        return list;
    }

    public List<Request> getClosedRequestsForHandyman(User user) {
        List<Request> list = new ArrayList<>();

        for (Request request : requests) {
            if (request.getStatus().ordinal() > 1 && request.getHandyman().equals(user) && isInHandymanResults(request)) {
                list.add(request);
            }
        }

        return list;
    }


    public List<Request> getRequestsForHandyman(User user) {
        List<Request> list = new ArrayList<>();

        for (Request request : requests) {
            if (request.getHandyman().equals(user) && isInHandymanResults(request)) {
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

    public static List<Request.Urgency> getUrgencyFilter() {
        return urgencyFilter;
    }

    public static void setUrgencyFilter(List<Request.Urgency> urgencyFilter) {
        RequestManager.urgencyFilter = urgencyFilter;
    }

    public static float getDistanceFilter() {
        return distanceFilter;
    }

    public static void setDistanceFilter(float distanceFilter) {
        RequestManager.distanceFilter = distanceFilter;
    }

    private boolean isInHandymanResults(Request request) {
        if (!urgencyFilter.contains(request.getUrgency())) {
            return false;
        }

        Geocoder geocoder = new Geocoder(HandyApplication.getAppContext());

        Address requestAddress = null, handymanAddress = null;
        try {
            requestAddress = geocoder.getFromLocationName(request.getAddress(), 1).get(0);
            handymanAddress = geocoder.getFromLocationName(request.getHandyman().getAddress(), 1).get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (requestAddress == null || handymanAddress == null)
            return false;

        double requestAddressLatitude = requestAddress.getLatitude();
        double requestAddressLongitude = requestAddress.getLongitude();

        double handymanAddressLatitude = requestAddress.getLatitude();
        double handymanAddressLongitude = requestAddress.getLongitude();

        float[] results = new float[1];
        Location.distanceBetween(handymanAddressLatitude, handymanAddressLongitude,
                requestAddressLatitude, requestAddressLongitude, results);

        return !(results[0] / 1000 > distanceFilter);
    }
}
