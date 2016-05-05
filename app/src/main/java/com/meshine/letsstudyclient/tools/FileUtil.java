package com.meshine.letsstudyclient.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Ming on 2016/5/4.
 */
public class FileUtil {
    private static String APP_DIR = "/LetsStudy";
    private static String IMAGE_DIR = "/images";
    private static String IMAGE_TYPE = ".jpg";
    public static String getSDCardPath(Context context){
        String path = Environment.getExternalStorageDirectory()+"";
        return path;
    }

    public static String getNewPictureName(Context context){
        File dir = new File(getSDCardPath(context)+APP_DIR+IMAGE_DIR);
        if (!dir.exists()){
            dir.mkdirs();
        }
        return dir+"/"+UUID.randomUUID()+IMAGE_TYPE;
    }

    public static File saveBitmapToFile(Context context, Bitmap bitmap){
        File f = new File(getNewPictureName(context));
        FileOutputStream fOut = null;
        try {
            f.createNewFile();
            fOut = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        }catch (Exception e){

        }finally {
            try {
                fOut.flush();
                fOut.close();
            }catch (IOException e1){

            }

        }
        return f;

    }

}
