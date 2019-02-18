package com.example.android.architecture.blueprints.todoapp.job;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.Log;

import com.example.android.architecture.blueprints.todoapp.App;
import com.example.android.architecture.blueprints.todoapp.BuildConfig;
import com.example.android.architecture.blueprints.todoapp.Injection;
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository;
import com.example.android.architecture.blueprints.todoapp.util.AppExecutors;


/**
 * Schedules a Job Scheduler job to setup daily backup for our tasks
 */
public final class BackupHelper {
    private static final String TAG = "BackupHelper";
    public static final int BACKUP_JOB_ID = 6601098;

    /**
     * Checks if we have a job scheduler job already created, and if not, creates
     * one.
     *
     * @param context
     */
    public static boolean setupTaskBackup(Context context) {
        Log.i(TAG, "inside BackUpHelper setupTaskBackup: ");
        JobScheduler js = (JobScheduler)
                context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        for (JobInfo job : js.getAllPendingJobs()) {
            if (job.getId() == BACKUP_JOB_ID) {
                Log.i(TAG, "BackupHelper: job already created and pending");
                return true;
            }
        }

        // Didn't find the BACKUP job, so let's make one
        return scheduleBackupJob(context, js);
        /*return new Handler().post(new Runnable() {
            @Override
            public void run() {
                new Thread() {
                    @Override
                    public void run() {
                       backupNow();
                    }
                }.start();
            }
        });*/
    }

    static boolean backupNow() {
        if (App.isBackingUp()) {
            Log.e(TAG, "Already backing up, wait for operation to complete");
            return false;
        }

        TasksRepository repository = Injection.provideTasksRepository(App.get());

        boolean result = false;
        try {
            App.setBackingUp(true);
            result = repository.backup();
        } catch (Exception any) {
            Log.e(TAG, "Unable to backup due to unknown failure", any);
        } finally {
            App.setBackingUp(false);
        }
        return result;
    }

    public static boolean scheduleBackupJob(
            Context context,
            JobScheduler jobScheduler) {

        Log.i(TAG, "inside scheduleBackupJob: ");
        /*JobInfo.Builder builder = new JobInfo.Builder(BACKUP_JOB_ID,
                new ComponentName(context, BackupJobService.class))
                .setPeriodic(DateUtils.DAY_IN_MILLIS)
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setBackoffCriteria(DateUtils.HOUR_IN_MILLIS / 2,
                        JobInfo.BACKOFF_POLICY_EXPONENTIAL)
                .setPersisted(true);*/
        JobInfo.Builder builder = new JobInfo.Builder(BACKUP_JOB_ID,
                new ComponentName(context, BackupJobService.class))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);

        JobInfo jobInfo;
        try {
            jobInfo = builder.build();
        } catch(Exception e) {
            Log.e(TAG, "Job construction failed", e);
            if (BuildConfig.DEBUG) { // fail fast in debug
                throw e;
            } else {
                return false;
            }
        }

        int result = jobScheduler.schedule(jobInfo);
        Log.i(TAG, "scheduleBackupJob: result" + result);
        if (result != JobScheduler.RESULT_SUCCESS) {
            Log.e(TAG, "Fatal error scheduling job");
            return false;
        }
        return true;
    }
}
