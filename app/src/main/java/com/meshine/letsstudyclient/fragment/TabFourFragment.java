package com.meshine.letsstudyclient.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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
import android.widget.Toast;

import com.meshine.letsstudyclient.MainActivity_;
import com.meshine.letsstudyclient.R;
import com.meshine.letsstudyclient.tools.FileUtil;
import com.meshine.letsstudyclient.tools.HandleResponseCode;
import com.meshine.letsstudyclient.widget.CircleImageView;
import com.meshine.letsstudyclient.widget.PickAvatarDialog;
import com.soundcloud.android.crop.Crop;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.io.File;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by Ming on 2016/4/24.
 */
@EFragment(R.layout.fragment_tab_four)
public class TabFourFragment extends Fragment {
    private static final String TAG = "TabMe";

    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果

    @ViewById(R.id.id_me_avatar)
    CircleImageView ivAvatar;
    @ViewById(R.id.id_me_nickname)
    TextView tvNick;

    PickAvatarDialog pickAvatarDialog;
    File tempFile;


    UserInfo userInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }

    @AfterViews
    void init(){
        initUserInfo();
        initDialogs();
    }

    @Click({R.id.id_me_avatar})
    void onClick(View view){
        switch (view.getId()){
            case R.id.id_me_avatar:
                pickAvatarDialog.show();
                break;
        }
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_REQUEST_TAKEPHOTO:// 当选择拍照时调用
                startPhotoZoom(Uri.fromFile(tempFile));
                break;
            case PHOTO_REQUEST_GALLERY:// 当选择从本地获取图片时
                // 做非空判断，当我们觉得不满意想重新剪裁的时候便不会报异常，下同
                if (data != null) {
                    System.out.println("11================");
                    startPhotoZoom(data.getData());
                } else {
                    System.out.println("================");
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


    void modifyAvatar(Intent data){
        Log.i(TAG,"裁剪完成 ");
        // 拿到剪切数据
        Bitmap bmap = data.getParcelableExtra("data");
        ivAvatar.setImageBitmap(bmap);

    }

    String avatarUri;

    void initDialogs(){
        pickAvatarDialog = new PickAvatarDialog(getContext(), new PickAvatarDialog.OnPickAvatarDialogListener() {
            @Override
            public void pickFromAlbum() {
                Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
                getAlbum.setType("image/*");
                startActivityForResult(getAlbum, PHOTO_REQUEST_GALLERY);
            }

            @Override
            public void takePicture() {
                Intent cameraintent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                // 指定调用相机拍照后照片的储存路径
                cameraintent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(tempFile));
                startActivityForResult(cameraintent,
                        PHOTO_REQUEST_TAKEPHOTO);
            }
        });
    }

    void initUserInfo(){
        userInfo = JMessageClient.getMyInfo();

        if (userInfo != null && !TextUtils.isEmpty(userInfo.getAvatar())){
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
        }else {
            ivAvatar.setImageResource(R.drawable.ic_avatar_default);
        }

        tvNick.setText(userInfo.getNickname());
    }

}
