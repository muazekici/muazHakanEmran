package com.muazhakanemran.myapplication.events;

import com.muazhakanemran.myapplication.models.Job;

/**
 * Created by muazekici on 17.02.2018.
 */

public class PostNewJobEvent {

    Job job;

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
