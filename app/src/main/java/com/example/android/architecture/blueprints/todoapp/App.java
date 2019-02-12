package com.example.android.architecture.blueprints.todoapp;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.android.architecture.blueprints.todoapp.job.BackupHelper;


/**
 * Application object for our app
 */
public class App extends Application {
    public static final String TAG = "TodoWithBackup";
    private static Context appContext;
    private static boolean backingUp = false;

    public static void setBackingUp(boolean backingUp) {
        App.backingUp = backingUp;
    }

    public static boolean isBackingUp() {
        return backingUp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        boolean result = BackupHelper.setupTaskBackup(this);
        Log.i(TAG, "Scheduling backup job returned " + result);
    }

    public static Context get() {
        return appContext;
    }
}
