package com.themallbd.workspaceit.utility;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Mausum on 4/7/2016.
 */
public class SaveImageToLocal {

    public void createDirectoryAndSaveFile(SessionManager sessionManager, Bitmap imageToSave, String fileName) {


        File direct = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES + "/MallBD");

        Log.v("taiful", direct.toString());

        if (!direct.exists()) {
            File wallpaperDirectory = new File(direct.toString());
            wallpaperDirectory.mkdirs();
        }

        File file = new File(new File(direct.toString()), fileName + ".jpg");
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        sessionManager.saveProfileImageUri(file.getAbsolutePath());






    }
}
