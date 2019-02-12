package com.example.android.architecture.blueprints.todoapp.job;


import android.app.job.JobParameters;
import android.app.job.JobService;

public class BackupFJDJobService extends JobService {
    public BackupFJDJobService() {
    }

    @Override
    public boolean onStartJob(JobParameters job) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}
