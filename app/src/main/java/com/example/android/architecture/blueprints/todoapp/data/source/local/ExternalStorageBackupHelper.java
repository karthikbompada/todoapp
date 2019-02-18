package com.example.android.architecture.blueprints.todoapp.data.source.local;

import android.content.Context;
import android.util.Log;


import com.example.android.architecture.blueprints.todoapp.App;
import com.example.android.architecture.blueprints.todoapp.R;
import com.snatik.storage.Storage;

import java.io.File;

import static com.example.android.architecture.blueprints.todoapp.App.TAG;


/**
 * Helps with writing out the database to a flat
 * file on external storage
 */
class ExternalStorageBackupHelper {
    private final Storage storage;
    private final String backupDir;
    private final String backupFileName;

    ExternalStorageBackupHelper(String fileName) {
        Log.i(TAG, "ExternalStorageBackupHelper: called");

        Context context = App.get();
        storage = new Storage(context);
        backupFileName = fileName;

        // get external storage
        if (!Storage.isExternalWritable()) {
            throw new IllegalStateException("Can't backup since SD card is not available");
        }
        String path = storage.getExternalStorageDirectory();
        backupDir = path + File.separator + context.getResources().getString(R.string.app_name);
       // storage.createDirectory(backupDir); // fails with log if already exists
        Log.i(TAG, "Backing up to " + backupDir);
    }

    <T> void writeObjects(Iterable<T> objects) {
        StringBuilder str = new StringBuilder();
        for (T obj: objects) {
            str.append(obj.toString())
                    .append(System.lineSeparator());
        }
        Log.e(TAG, backupDir + File.separator + backupFileName);
        Log.e(TAG, "Content LIST::" + str.toString());
      //  storage.createFile(backupDir + File.separator + backupFileName, str.toString());
    }
}
