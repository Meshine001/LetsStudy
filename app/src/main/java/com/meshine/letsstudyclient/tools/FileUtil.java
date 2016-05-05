package com.meshine.letsstudyclient.tools;

import android.content.Context;
import android.os.Environment;

import java.io.File;
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

}
