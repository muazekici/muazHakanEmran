package com.muazhakanemran.myapplication.models;

import com.muazhakanemran.myapplication.models.Job;

/**
 * Created by muazekici on 17.02.2018.
 */

public class PostJobResponse {

    String message;
    Job job;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
