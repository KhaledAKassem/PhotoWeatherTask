package com.github.khaledakassem.photo_weather.common.utils;

import android.content.Context;
import android.net.Uri;
import androidx.core.content.FileProvider;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageManger {

    private static final String CAPTURE_IMAGE_FILE_PROVIDER = "com.github.khaledakassem.photo_weather.provider";
    private static String IMAGE_NAME = "";

    public static Uri save_image_in_provider(Context context) {
        File imagePath = new File(context.getFilesDir(), "images");
        if (!imagePath.exists()) imagePath.mkdirs();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File image = new File(imagePath, timeStamp + ".jpg");
        IMAGE_NAME = timeStamp + ".jpg";
        Uri imageUri = FileProvider.getUriForFile(context, CAPTURE_IMAGE_FILE_PROVIDER, image);
        return imageUri;
    }

    public static File get_saved_image(Context context) {
        File path = new File(context.getFilesDir(), "images");
        if (!path.exists()) path.mkdirs();
        File imageFile = new File(path, IMAGE_NAME);
        return imageFile;
    }

}
