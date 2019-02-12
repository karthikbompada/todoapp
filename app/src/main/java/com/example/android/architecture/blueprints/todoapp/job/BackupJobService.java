package com.example.android.architecture.blueprints.todoapp.job;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.architecture.blueprints.todoapp.BuildConfig;
import com.example.android.architecture.blueprints.todoapp.Injection;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository;


public class BackupJobService extends JobService {
    private AsyncTask<JobParameters, Integer, Void> backupTask;
    public static final String TAG = "BackupJobService";

    public BackupJobService() {
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        backupTask = new BackupTask().execute(jobParameters);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        if (backupTask != null) {
            backupTask.cancel(true);
            return true;
        }
        return false;
    }

    class BackupTask extends AsyncTask<JobParameters, Integer, Void> {

        private JobParameters jobParams;

        @Override
        protected Void doInBackground(JobParameters... jobParameters) {
            this.jobParams = jobParameters[0];
            TasksRepository repository = Injection.provideTasksRepository(
                    BackupJobService.this
            );

            boolean result = false;
            try {
                result = repository.backup(); // slow call
            } catch (Exception e) {
                Log.e(TAG, "Unable to backup", e);
                if (BuildConfig.DEBUG) { // fail fast
                    throw e;
                }
            }

            jobFinished(jobParams, !result);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            backupTask = null;
        }
    }
}
