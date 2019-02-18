package com.example.android.architecture.blueprints.todoapp.job;

import android.Manifest;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.example.android.architecture.blueprints.todoapp.App;
import com.example.android.architecture.blueprints.todoapp.BuildConfig;
import com.example.android.architecture.blueprints.todoapp.Injection;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository;

import androidx.core.app.ActivityCompat;


public class BackupJobService extends JobService {
    private AsyncTask<JobParameters, Integer, Void> backupTask;
    public static final String TAG = "BackupJobService";

    public BackupJobService() {
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.i(TAG, "onStartJob: called");
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
            Log.i(TAG, "doInBackground: called");
            this.jobParams = jobParameters[0];
            TasksRepository repository = Injection.provideTasksRepository(
                    BackupJobService.this
            );

            boolean result = false;
            try {
                App.setBackingUp(true);
                result = repository.backup(); // slow call
                Log.i(TAG, "doInBackground: repository.backUp called");
            } catch (Exception e) {
                Log.e(TAG, "Unable to backup", e);
                if (BuildConfig.DEBUG) { // fail fast
                    throw e;
                }
            }finally {
                App.setBackingUp(false);
            }

            jobFinished(jobParams, !result);
            Log.i(TAG, "doInBackground: jobFinished Called");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.i(TAG, "onPostExecute: called");
            backupTask = null;
        }
    }
}
