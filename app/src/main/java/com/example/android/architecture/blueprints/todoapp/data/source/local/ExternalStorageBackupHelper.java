package com.example.android.architecture.blueprints.todoapp.data.source.local;

import android.content.Context;
import android.os.Environment;
import android.util.Log;


import com.example.android.architecture.blueprints.todoapp.App;
import com.example.android.architecture.blueprints.todoapp.R;

import java.io.File;

import static com.example.android.architecture.blueprints.todoapp.App.TAG;


/**
 * Helps with writing out the database to a flat
 * file on external storage
 */
class ExternalStorageBackupHelper {
  //  private final String backupDir;
    private final String backupFileName;

    ExternalStorageBackupHelper(String fileName) {

        Context context = App.get();
        backupFileName = fileName;

        // get external storage
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            throw new IllegalStateException("Can't backup since SD card is not available");
        }
        /*String path = storage.getExternalStorageDirectory();
        backupDir = path + File.separator + context.getResources().getString(R.string.app_name);
        storage.createDirectory(backupDir); // fails with log if already exists*/
        Log.i(TAG, "Backing up to "/* + backupDir*/);
    }

    <T> void writeObjects(Iterable<T> objects) {
        StringBuilder str = new StringBuilder();
        for (T obj: objects) {
            str.append(obj.toString())
               .append(System.lineSeparator());
        }
      //  storage.createFile(backupDir + File.separator + backupFileName, str.toString());
    }


    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }
}
