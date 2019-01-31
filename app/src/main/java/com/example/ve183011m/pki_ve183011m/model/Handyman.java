package com.example.ve183011m.pki_ve183011m.model;

import java.io.Serializable;
import java.util.List;

public class Handyman extends User implements Serializable {

    private int experience;
    private List<Job> skills;

    public Handyman(String username, String password, String fullName, String telephone, String address, int experience, List<Job> skills) {
        super(username, password, fullName, telephone, address);
        this.experience = experience;
        this.skills = skills;
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

}
