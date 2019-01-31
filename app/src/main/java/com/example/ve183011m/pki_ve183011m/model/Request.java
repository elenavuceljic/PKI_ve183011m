package com.example.ve183011m.pki_ve183011m.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Request implements Serializable {

    private Handyman handyman;
    private User buyer;
    private int urgency;
    private Date startDate, endDate;
    private String address;
    private Status status;
    private boolean isPayableByCash;
    private Job job;

    public enum Status {
        SENT,
        ACCEPTED,
        DENIED,
        DONE,
        FAILED;
    }

    public Request(Handyman handyman, User buyer, int urgency, Date startDate, Date endDate, String address, Status status, boolean isPayableByCash, Job job) {
        this.handyman = handyman;
        this.buyer = buyer;
        this.urgency = urgency;
        this.startDate = startDate;
        this.endDate = endDate;
        this.address = address;
        this.status = status;
        this.isPayableByCash = isPayableByCash;
        this.job = job;
    }

    public User getHandyman() {
        return handyman;
    }

    public void setHandyman(Handyman handyman) {
        this.handyman = handyman;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public int getUrgency() {
        return urgency;
    }

    public void setUrgency(int urgency) {
        this.urgency = urgency;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isPayableByCash() {
        return isPayableByCash;
    }

    public void setPayableByCash(boolean payableByCash) {
        isPayableByCash = payableByCash;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return urgency == request.urgency &&
                isPayableByCash == request.isPayableByCash &&
                Objects.equals(handyman, request.handyman) &&
                Objects.equals(buyer, request.buyer) &&
                Objects.equals(startDate, request.startDate) &&
                Objects.equals(endDate, request.endDate) &&
                Objects.equals(address, request.address) &&
                status == request.status &&
                Objects.equals(job, request.job);
    }

    @Override
    public int hashCode() {
        return Objects.hash(handyman, buyer, urgency, startDate, endDate, address, status, isPayableByCash, job);
    }

}
