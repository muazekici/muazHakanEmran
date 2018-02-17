package com.muazhakanemran.myapplication.events;

import com.muazhakanemran.myapplication.models.JobList;

/**
 * Created by muazekici on 17.02.2018.
 */

public class GetUserJobListResponseEvent {

    JobList jobList;

    public JobList getJobList() {
        return jobList;
    }

    public void setJobList(JobList jobList) {
        this.jobList = jobList;
    }
}
