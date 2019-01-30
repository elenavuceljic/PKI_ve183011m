package com.example.ve183011m.pki_ve183011m.model;

import java.io.Serializable;

public class Job implements Serializable {

    private String name;
    private float price;

    public Job(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

}
