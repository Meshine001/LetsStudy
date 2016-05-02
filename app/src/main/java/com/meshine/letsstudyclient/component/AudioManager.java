package com.meshine.letsstudyclient.component;


import android.media.AudioFormat;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Ming on 2016/5/2.
 */
public class AudioManager {
    private static final String AUDIO_DIR = "/audios";

    private MediaRecorder mMediaRecorder;
    private String mDir;
    private String mCurrentFilePath;

    private static AudioManager mInstance;

    private boolean isPrepared;
    private static final String TAG = "AudioManager";

    private AudioManager(String rootDir){
        File dir = new File(rootDir);
        if (!dir.exists()){
            dir.mkdirs();
        }
        mDir = rootDir+AUDIO_DIR;
    }

    public String getCurrentFilePath() {
        return mCurrentFilePath;
    }

    /**
     * 回调准备完毕
     */
    public interface AudioStateListener{
        void wellPrepared();
    }

    public AudioStateListener mListener;

    public void setOnAudioStateLstener(AudioStateListener listener){
        mListener = listener;
    }
    public static AudioManager getInstance(String rootDir){
        if (mInstance == null){
            synchronized (AudioManager.class){
                if(mInstance == null){
                    mInstance = new AudioManager(rootDir);
                }
            }
        }

        return mInstance;
    }

    public void prepareAudio(){
        try {
            isPrepared = false;

            File dir = new File(mDir);
            if (!dir.exists()){
                dir.mkdirs();
            }
            String fileName = generateFileName();
            File file = new File(dir,fileName);

            mCurrentFilePath = file.getAbsolutePath();

            if (mMediaRecorder!=null){
                Log.e(TAG,"MediaRecorder is not null");
                release();
            }

            mMediaRecorder = new MediaRecorder();

            mMediaRecorder.setOnInfoListener(new MediaRecorder.OnInfoListener() {
                @Override
                public void onInfo(MediaRecorder mr, int what, int extra) {
                    Log.e(TAG,"Info: what:"+what+" ========== "+"extra: "+extra);
                }
            });

            mMediaRecorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {
                @Override
                public void onError(MediaRecorder mr, int what, int extra) {
                    Log.e(TAG,"Error: what:"+what+" ========== "+"extra: "+extra);
                }
            });


            //设置音频源为麦克风
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

            //设置音频格式
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
            //设置音频编码
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            //设置输出文件
            mMediaRecorder.setOutputFile(file.getAbsolutePath());



            mMediaRecorder.prepare();
            mMediaRecorder.start();

            isPrepared = true;

            if (mListener!=null){
                mListener.wellPrepared();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateFileName() {
        return UUID.randomUUID().toString()+".amr";
    }

    public int getVoiceLevel(int maxLevel){
        if (isPrepared){
            try {
                //mMediaRecorder.getMaxAmplitude() 1~32767
                return maxLevel*mMediaRecorder.getMaxAmplitude()/32768+1;//取值1~7
            } catch (Exception e) {
                //e.printStackTrace();
                return 1;
            }
        }
        return 1;
    }
    public void release(){
        if (mMediaRecorder!=null){
            mMediaRecorder.stop();
            mMediaRecorder.release();
            mMediaRecorder = null;
        }

    }

    public void cancel(){
        release();
        if(mCurrentFilePath != null){
            File file = new File(mCurrentFilePath);
            file.delete();
            mCurrentFilePath = null;
        }


    }

}
