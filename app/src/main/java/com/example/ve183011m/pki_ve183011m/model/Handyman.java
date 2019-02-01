package com.example.ve183011m.pki_ve183011m.model;

import java.io.Serializable;
import java.util.List;

public class Handyman extends User implements Serializable {

    private int experience;
    private List<Job> skills;
    private List<Float> rating;
    private List<Review> reviews;

    public Handyman(String username, String password, String fullName, String telephone, String address, int experience, List<Job> skills, List<Float> rating, List<Review> reviews) {
        super(username, password, fullName, telephone, address);
        this.experience = experience;
        this.skills = skills;
        this.rating = rating;
        this.reviews = reviews;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public List<Job> getSkills() {
        return skills;
    }

    public float getRating() {
        if (rating.size() == 0)
            return 0;

        float sum = 0;

        for (float f: rating) {
            sum += f;
        }

        return sum/rating.size();
    }

    public void setRating(float rating) {
        this.rating.add(rating);
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    public static class Review {

        private String review;
        private User reviewer;

        public Review(String review, User reviewer) {
            this.review = review;
            this.reviewer = reviewer;
        }

        public String getReview() {
            return review;
        }

        public void setReview(String review) {
            this.review = review;
        }

        public User getReviewer() {
            return reviewer;
        }

        public void setReviewer(User reviewer) {
            this.reviewer = reviewer;
        }
    }
}
