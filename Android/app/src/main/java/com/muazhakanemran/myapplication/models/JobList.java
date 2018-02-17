package com.muazhakanemran.myapplication.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by muazekici on 17.02.2018.
 */

public class JobList implements Serializable {

    String _id;
    String android_id;
    int credits;
    List<Job> openJobs;
    List<Job> closedJobs;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAndroid_id() {
        return android_id;
    }

    public void setAndroid_id(String android_id) {
        this.android_id = android_id;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public List<Job> getOpenJobs() {
        return openJobs;
    }

    public void setOpenJobs(List<Job> openJobs) {
        this.openJobs = openJobs;
    }

    public List<Job> getClosedJobs() {
        return closedJobs;
    }

    public void setClosedJobs(List<Job> closedJobs) {
        this.closedJobs = closedJobs;
    }
}
