package com.example.ve183011m.pki_ve183011m.model;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    private String username;
    private String password;
    private String fullName;
    private String telephone;
    private String address;
    private Boolean isBuyer;
    private int experience;
    private boolean knowsPlumbing;
    private boolean knowsElectrical;
    private boolean knowsFurniture;
    private boolean knowsAC;
    private boolean knowsPainting;

    public User(String username, String password, String fullName, String telephone, String address,
                Boolean isBuyer) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.telephone = telephone;
        this.address = address;
        this.isBuyer = isBuyer;
    }

    public User(String username, String password, String fullName, String telephone, String address,
                Boolean isBuyer, int experience, boolean knowsPlumbing, boolean knowsElectrical,
                boolean knowsFurniture, boolean knowsAC, boolean knowsPainting) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.telephone = telephone;
        this.address = address;
        this.isBuyer = isBuyer;
        this.experience = experience;
        this.knowsPlumbing = knowsPlumbing;
        this.knowsElectrical = knowsElectrical;
        this.knowsFurniture = knowsFurniture;
        this.knowsAC = knowsAC;
        this.knowsPainting = knowsPainting;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getBuyer() {
        return isBuyer;
    }

    public void setBuyer(Boolean buyer) {
        isBuyer = buyer;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public boolean isKnowsPlumbing() {
        return knowsPlumbing;
    }

    public void setKnowsPlumbing(boolean knowsPlumbing) {
        this.knowsPlumbing = knowsPlumbing;
    }

    public boolean isKnowsElectrical() {
        return knowsElectrical;
    }

    public void setKnowsElectrical(boolean knowsElectrical) {
        this.knowsElectrical = knowsElectrical;
    }

    public boolean isKnowsFurniture() {
        return knowsFurniture;
    }

    public void setKnowsFurniture(boolean knowsFurniture) {
        this.knowsFurniture = knowsFurniture;
    }

    public boolean isKnowsAC() {
        return knowsAC;
    }

    public void setKnowsAC(boolean knowsAC) {
        this.knowsAC = knowsAC;
    }

    public boolean isKnowsPainting() {
        return knowsPainting;
    }

    public void setKnowsPainting(boolean knowsPainting) {
        this.knowsPainting = knowsPainting;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

}
