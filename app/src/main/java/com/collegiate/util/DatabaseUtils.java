package com.collegiate.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.collegiate.database.PerferenceStoreImpl;
import com.collegiate.database.PreferencesStore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by gauravarora on 18/09/15.
 */
public class DatabaseUtils {

    private static final String DB_NAME = "collegiate.db";
    private static final Integer DB_VERSION = 1;

    public static void addBundledDatabase(Context context, String databaseName) {
        PreferencesStore preferencesStore = new PerferenceStoreImpl(context);
        int version = preferencesStore.getObject("VERSION", DB_VERSION);
        if(version == DB_VERSION){
            return;
        }

        databaseName = databaseName + ".db";
        context.deleteDatabase(databaseName);

        final File dbPath = context.getDatabasePath(databaseName);
        dbPath.getParentFile().mkdirs();

        try {
            InputStream inputStream = context.getAssets().open(databaseName);
            writeDB(dbPath, inputStream);
            preferencesStore.saveObject("VERSION", DB_VERSION);
        } catch (IOException e) {
            Log.e("Collegiate", "Failed to open file");
        }
    }

    private static void writeDB(File dbPath, InputStream existingDB) throws IOException {
        final OutputStream output = new FileOutputStream(dbPath);

        byte[] buffer = new byte[1024];
        int length;

        while ((length = existingDB.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }

        output.flush();
        output.close();
        existingDB.close();
    }

    public static void exportDb(Context context) {
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source = null;
        FileChannel destination = null;
        String currentDBPath = "/data/" + "com.collegiate" + "/databases/" + DB_NAME;
        String backupDBPath = DB_NAME;
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Toast.makeText(context, "DB Exported!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
