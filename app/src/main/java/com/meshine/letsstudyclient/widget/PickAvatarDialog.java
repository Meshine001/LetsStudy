package com.meshine.letsstudyclient.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.meshine.letsstudyclient.R;

/**
 * Created by Ming on 2016/5/4.
 */
public class PickAvatarDialog extends Dialog {
    Button pickFromAlbum;
    Button takePicture;
    Context context;
    OnPickAvatarDialogListener onPickAvatarDialogListener;
    public interface OnPickAvatarDialogListener{
        void pickFromAlbum();
        void takePicture();
    }
    public PickAvatarDialog(Context context,OnPickAvatarDialogListener onPickAvatarDialogListener) {
        super(context);
        this.context = context;
        this.onPickAvatarDialogListener = onPickAvatarDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pick_avatar);
        pickFromAlbum = (Button) findViewById(R.id.id_dialog_pick_avatar_form_album);
        takePicture = (Button) findViewById(R.id.id_dialog_pick_avatar_take_picture);

        pickFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPickAvatarDialogListener.pickFromAlbum();
                dismiss();
            }
        });

        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPickAvatarDialogListener.takePicture();
                dismiss();
            }
        });
    }
}
