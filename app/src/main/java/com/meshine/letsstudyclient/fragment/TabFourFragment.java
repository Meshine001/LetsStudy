package com.meshine.letsstudyclient.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meshine.letsstudyclient.LoginActivity_;
import com.meshine.letsstudyclient.MyInfoActivity_;
import com.meshine.letsstudyclient.R;
import com.meshine.letsstudyclient.SettingsActivity;
import com.meshine.letsstudyclient.SettingsActivity_;
import com.meshine.letsstudyclient.application.MyApplication;
import com.meshine.letsstudyclient.tools.AppManager;
import com.meshine.letsstudyclient.tools.FileUtil;
import com.meshine.letsstudyclient.tools.HandleResponseCode;
import com.meshine.letsstudyclient.tools.MyPrefs_;
import com.meshine.letsstudyclient.tools.UrIUtil;
import com.meshine.letsstudyclient.widget.CircleImageView;
import com.meshine.letsstudyclient.widget.PickAvatarDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by Ming on 2016/4/24.
 */
@EFragment(R.layout.fragment_tab_four)
public class TabFourFragment extends Fragment {
    private static final String TAG = "TabMe";

    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_GALLERY_KITKAT = 3;// 从相册中选择,sdk >= 4.4
    private static final int PHOTO_REQUEST_CUT = 4;// 结果
    private static final int REQUEST_IMAGE = 0x1000;

    @ViewById(R.id.id_me_avatar)
    CircleImageView ivAvatar;
    @ViewById(R.id.id_me_nickname)
    TextView tvNick;

    PickAvatarDialog pickAvatarDialog;
    File captureFile = new File(FileUtil.getNewPictureName(getContext()));
    File uploadAvatar;


    @Pref
    MyPrefs_ myPrefs;

    UserInfo userInfo;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }

    @AfterViews
    void init() {
        initUserInfo();
        initDialogs();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApplication.isLogin){
            initUserInfo();
        }

    }

    @Click({R.id.id_me_avatar, R.id.id_me_info_line, R.id.id_me_settings_line, R.id.id_me_exit_line})
    void onClick(View view) {
        if (!checkSignIn())return;

        switch (view.getId()) {
            case R.id.id_me_avatar:
//                pickAvatarDialog.show();
                openImageSelector();
                break;
            case R.id.id_me_info_line:
                Intent myInfo = new Intent(getContext(), MyInfoActivity_.class);
                startActivity(myInfo);
                break;
            case R.id.id_me_settings_line:
                Intent settings = new Intent(getContext(), SettingsActivity_.class);
                startActivity(settings);
                break;
            case R.id.id_me_exit_line:
                JMessageClient.logout();
                AppManager.getAppManager().AppExit(getContext());
                break;

        }
    }

    void openImageSelector() {
        Intent intent = new Intent(getContext(), MultiImageSelectorActivity.class);
        // whether show camera
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // max select image amount
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
        // select mode (MultiImageSelectorActivity.MODE_SINGLE OR MultiImageSelectorActivity.MODE_MULTI)
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
        // default select images (support array list)
        //intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, defaultDataArray);
        startActivityForResult(intent, REQUEST_IMAGE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_IMAGE:
                if(resultCode == Activity.RESULT_OK){
                    // Get the result list of select image paths
                    List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    // do your logic ....
                    Log.i(TAG,path.get(0));
                    startPhotoZoom(Uri.fromFile(new File(path.get(0))));
                }
                break;

            case PHOTO_REQUEST_TAKEPHOTO:// 当选择拍照时调用
                startPhotoZoom(Uri.fromFile(captureFile));
                break;
            case PHOTO_REQUEST_GALLERY:// 当选择从本地获取图片时
                // 做非空判断，当我们觉得不满意想重新剪裁的时候便不会报异常，下同
                if (data != null) {
                    Log.i(TAG, "获得图片:" + data.toString());
                    String path = UrIUtil.selectImage(getContext(), data);
                    startPhotoZoom(Uri.fromFile(new File(path)));
                } else {
                    Log.i(TAG, "获取数据为null");
                }
                break;
            case PHOTO_REQUEST_GALLERY_KITKAT:// 当选择从本地获取图片时
                // 做非空判断，当我们觉得不满意想重新剪裁的时候便不会报异常，下同
                if (data != null) {
                    String path = UrIUtil.getPath(getContext(), data.getData());
                    startPhotoZoom(Uri.fromFile(new File(path)));
                } else {
                    Log.i(TAG, "获取数据为null");
                }
                break;
            case PHOTO_REQUEST_CUT:// 返回的结果
                if (data != null)
                    // setPicToView(data);
                    modifyAvatar(data);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent();
        intent.setAction("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");// mUri是已经选择的图片Uri
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);// 输出图片大小
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_REQUEST_CUT);

    }


    void modifyAvatar(Intent data) {
        Log.i(TAG, "裁剪完成 ");
        // 拿到剪切数据
        Bitmap bmap = data.getParcelableExtra("data");

        uploadAvatar = FileUtil.saveBitmapToFile(getContext(), bmap);

        if (uploadAvatar != null) {
            uploadAvatar();
        }

    }

    void uploadAvatar() {
        JMessageClient.updateUserAvatar(uploadAvatar, new BasicCallback() {
            @Override
            public void gotResult(int status, String s) {
                if (status == 0) {
                    ivAvatar.setImageURI(Uri.fromFile(uploadAvatar));
                } else {
                    HandleResponseCode.onHandle(getContext(), status, true);
                }
            }
        });
    }


    void initDialogs() {

        pickAvatarDialog = new PickAvatarDialog(getContext(), new PickAvatarDialog.OnPickAvatarDialogListener() {
            @Override
            public void pickFromAlbum() {
                Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);//ACTION_OPEN_DOCUMENT
                getAlbum.addCategory(Intent.CATEGORY_OPENABLE);
                getAlbum.setType("image/*");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    startActivityForResult(getAlbum, PHOTO_REQUEST_GALLERY_KITKAT);
                } else {
                    startActivityForResult(getAlbum, PHOTO_REQUEST_GALLERY);
                }
            }

            @Override
            public void takePicture() {
                Intent cameraintent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                // 指定调用相机拍照后照片的储存路径
                cameraintent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(captureFile));
                startActivityForResult(cameraintent,
                        PHOTO_REQUEST_TAKEPHOTO);
            }
        });
    }

    boolean checkSignIn(){
        userInfo = JMessageClient.getMyInfo();
        if (userInfo == null) {
            Intent intent = new Intent(getContext(), LoginActivity_.class);
            startActivity(intent);
            return false;
        }

        return true;
    }

    void initUserInfo() {

        userInfo = JMessageClient.getMyInfo();

        if (userInfo == null) {
            Intent intent = new Intent(getContext(), LoginActivity_.class);
            startActivity(intent);
        } else {
            if (userInfo != null && !TextUtils.isEmpty(userInfo.getAvatar())) {
                userInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                    @Override
                    public void gotResult(int status, String desc, Bitmap bitmap) {
                        if (status == 0) {
                            ivAvatar.setImageBitmap(bitmap);
                        } else {
                            ivAvatar.setImageResource(R.drawable.ic_avatar_default);
                            HandleResponseCode.onHandle(getContext(), status, false);
                        }
                    }
                });
            } else {
                ivAvatar.setImageResource(R.drawable.ic_avatar_default);
            }

            tvNick.setText(userInfo.getNickname());
        }


    }


}
