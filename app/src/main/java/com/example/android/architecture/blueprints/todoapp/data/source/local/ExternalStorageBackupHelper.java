package com.example.android.architecture.blueprints.todoapp.data.source.local;

import android.content.Context;
import android.os.Environment;
import android.util.Log;


import com.example.android.architecture.blueprints.todoapp.App;
import com.example.android.architecture.blueprints.todoapp.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.crypto.Cipher;

import static com.example.android.architecture.blueprints.todoapp.App.TAG;


/**
 * Helps with writing out the database to a flat
 * file on external storage
 */
class ExternalStorageBackupHelper {
    private final String backupDir;
    private final String backupFileName;
    private EncryptConfiguration mConfiguration;

    public void setEncryptConfiguration(EncryptConfiguration configuration) {
        mConfiguration = configuration;
    }

    ExternalStorageBackupHelper(String fileName) {

        Context context = App.get();
        backupFileName = fileName;

        // get external storage
        if (!isExternalWritable()) {
            throw new IllegalStateException("Can't backup since SD card is not available");
        }
        String path = getExternalStorageDirectory();
        backupDir = path + File.separator + context.getResources().getString(R.string.app_name);
        createDirectory(backupDir); // fails with log if already exists
        Log.i(TAG, "Backing up to " + backupDir);
    }

    <T> void writeObjects(Iterable<T> objects) {
        StringBuilder str = new StringBuilder();
        for (T obj: objects) {
            str.append(obj.toString())
               .append(System.lineSeparator());
        }
        createFile(backupDir + File.separator + backupFileName, str.toString());
    }


    public static boolean isExternalWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public String getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public boolean createDirectory(String path) {
        File directory = new File(path);
        if (directory.exists()) {
            Log.w(TAG, "Directory '" + path + "' already exists");
            return false;
        }
        return directory.mkdirs();
    }

    public boolean createFile(String path, String content) {
        return createFile(path, content.getBytes());
    }

    public boolean createFile(String path, byte[] content) {
        try {
            OutputStream stream = new FileOutputStream(new File(path));

            // encrypt if needed
            /*if (mConfiguration != null && mConfiguration.isEncrypted()) {
                content = encrypt(content, Cipher.ENCRYPT_MODE);
            }*/

            stream.write(content);
            stream.flush();
            stream.close();
        } catch (IOException e) {
            Log.e(TAG, "Failed create file", e);
            return false;
        }
        return true;
    }

    private synchronized byte[] encrypt(byte[] content, int encryptionMode) {
        final byte[] secretKey = mConfiguration.getSecretKey();
        final byte[] ivx = mConfiguration.getIvParameter();
        return SecurityUtil.encrypt(content, encryptionMode, secretKey, ivx);
    }
}
