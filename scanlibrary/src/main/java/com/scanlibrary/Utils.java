package com.scanlibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jhansi on 05/04/15.
 */
public class Utils {

    private Utils() {

    }

    public static Uri getUri(Context context, Bitmap bitmap) {

        String timeStamp;
        timeStamp = new SimpleDateFormat("dd-MM-YYYY HH.mm.ss").format(new
                Date());

        // check existence of folder, if not creates it
        String folderName = "clearscanner";
        File folder = new File(Environment.getExternalStorageDirectory().toString()
                , "/" + folderName);
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                return null;
            }
        }
        String fileName = "clearscanner" + timeStamp + ".jpg";

        // save bitmap to file
        // snippet took from https://stackoverflow.com/questions/45410883/how-to-save-images-in-an-specific-folder-android
        File file = new File(new File(Environment.getExternalStorageDirectory() + "/" + folderName + "/"), fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // add created file to gallery roll
        String path = null;
        try {
            path = MediaStore.Images.Media.insertImage(context.getContentResolver(), folder + "/" + fileName, "ClearScanner " + timeStamp, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return Uri.parse(path);
    }

    public static Bitmap getBitmap(Context context, Uri uri) throws IOException {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
        return bitmap;
    }
}