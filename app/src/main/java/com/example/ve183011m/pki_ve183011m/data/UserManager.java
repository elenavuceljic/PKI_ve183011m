package com.example.ve183011m.pki_ve183011m.data;

import com.example.ve183011m.pki_ve183011m.model.Handyman;
import com.example.ve183011m.pki_ve183011m.model.Job;
import com.example.ve183011m.pki_ve183011m.model.Request;
import com.example.ve183011m.pki_ve183011m.model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserManager implements Serializable {

    private static UserManager instance = new UserManager();

    private static float ratingFilter = 0.0f;
    private static Date startDate, endDate;
    private static String jobFilter = "";
    private static float priceMoreThanFilter = 0.0f, priceLessThanFilter = 0.0f;
    private static int experienceFilter = 0;

    private List<User> users = new ArrayList<User>() {{
        add(new User("buyer", "buyer", "John Doe", "064555333",
                "Sunset Boulevard 13"));
        add(new Handyman("handy", "handy", "Jane Doe", "064555444",
                "Moonlight Boulevard 13", 12, new ArrayList<Job>() {{
            add(new Job("Plumbing", 200.0F));
            add(new Job("Air conditioning", 300.0F));
            add(new Job("Electrical installations", 150.0F));
        }}, new ArrayList<Float>() {{
            add(3.5f);
        }}, new ArrayList<Handyman.Review>()));
    }};

    private UserManager() {
    }

    public static UserManager getInstance() {
        ((Handyman) instance.getUserWithCredentials("handy", "handy")).addReview(new Handyman.Review("Good job", instance.getUserWithCredentials("buyer", "buyer")));
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

    public List<Handyman> getHandymen() {
        List<Handyman> handymen = new ArrayList<>();

        for (User u : users) {
            if (u instanceof Handyman && isInHandymanResults((Handyman) u)) {
                handymen.add((Handyman) u);
            }
        }

        return handymen;
    }

    public static float getRatingFilter() {
        return ratingFilter;
    }

    public static void setRatingFilter(float ratingFilter) {
        UserManager.ratingFilter = ratingFilter;
    }

    public static Date getStartDate() {
        return startDate;
    }

    public static void setStartDate(Date startDate) {
        UserManager.startDate = startDate;
    }

    public static Date getEndDate() {
        return endDate;
    }

    public static void setEndDate(Date endDate) {
        UserManager.endDate = endDate;
    }

    public static float getPriceMoreThanFilter() {
        return priceMoreThanFilter;
    }

    public static void setPriceMoreThanFilter(float priceMoreThanFilter) {
        UserManager.priceMoreThanFilter = priceMoreThanFilter;
    }

    public static float getPriceLessThanFilter() {
        return priceLessThanFilter;
    }

    public static void setPriceLessThanFilter(float priceLessThanFilter) {
        UserManager.priceLessThanFilter = priceLessThanFilter;
    }

    public static int getExperienceFilter() {
        return experienceFilter;
    }

    public static void setExperienceFilter(int experienceFilter) {
        UserManager.experienceFilter = experienceFilter;
    }

    public static String getJobFilter() {
        return jobFilter;
    }

    public static void setJobFilter(String jobFilter) {
        UserManager.jobFilter = jobFilter;
    }

    private boolean isInHandymanResults(Handyman handyman) {
        if (jobFilter.length() > 0) {
            boolean hasSkill = false;

            for (Job job : handyman.getSkills()) {
                if (job.getName().equals(jobFilter)) {
                    if (priceLessThanFilter != 0 && job.getPrice() > priceLessThanFilter)
                        continue;
                    if (priceMoreThanFilter != 0 && job.getPrice() < priceMoreThanFilter)
                        continue;

                    hasSkill = true;
                }
            }

            if (!hasSkill)
                return false;
        }

        if (handyman.getExperience() < experienceFilter) {
            return false;
        }

        if (handyman.getRating() < ratingFilter)
            return false;

        List<Request> requests = RequestManager.getInstance().getAllActiveRequestsForHandyman(handyman);
        for (Request request : requests) {
            if (startDate == null || endDate == null || endDate.before(startDate))
                continue;

            if ((startDate.after(request.getStartDate()) && startDate.before(request.getEndDate()))
                    || (endDate.after(request.getStartDate()) && endDate.before(request.getEndDate())
                    || (startDate.before(request.getStartDate()) && endDate.after(request.getEndDate()))))
                return false;
        }

        return true;
    }
}
