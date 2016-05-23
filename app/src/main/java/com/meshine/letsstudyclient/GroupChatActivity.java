package com.meshine.letsstudyclient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.meshine.letsstudyclient.tools.AppManager;
import com.meshine.letsstudyclient.widget.TopBarView;
import com.rockerhieu.emojicon.EmojiconEditText;
import com.rockerhieu.emojicon.EmojiconGridFragment;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.rockerhieu.emojicon.emoji.Emojicon;


/**
 * Created by Meshine on 16/5/23.
 */
public class GroupChatActivity extends FragmentActivity implements View.OnClickListener,EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener {

    private static final String TAG = GroupChatActivity.class.getName();
    TopBarView topbar;
    ImageView ivInputSwitch;
    ImageView ivEmoj;
    ImageView ivInputExpand;
    EmojiconEditText etMessageContent;

    //Emoj表情面板
    FrameLayout flEmojs;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
        initViews();
        initEvents();
    }

    private void initEvents() {
        topbar.setOnTopBarClickListener(new TopBarView.OnTopBarClickListener() {
            @Override
            public void onTopBarRightClick(View v) {

            }

            @Override
            public void onTopBarLeftClick(View v) {
                AppManager.getAppManager().finishActivity();
            }
        });

        ivInputSwitch.setOnClickListener(this);
        ivEmoj.setOnClickListener(this);
        ivInputExpand.setOnClickListener(this);

    }

    private void initViews() {
        topbar = (TopBarView) findViewById(R.id.id_group_chat_topbar);
        ivInputSwitch = (ImageView) findViewById(R.id.id_chat_panel_input_switch);
        ivEmoj = (ImageView) findViewById(R.id.id_chat_panel_input_emoj);
        ivInputExpand = (ImageView) findViewById(R.id.id_chat_panel_input_extend);
        etMessageContent = (EmojiconEditText) findViewById(R.id.id_chat_panel_input_edit);
        flEmojs = (FrameLayout) findViewById(R.id.id_chat_panel_emoj_icons);


        etMessageContent.setUseSystemDefault(false);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_chat_panel_input_switch:
                break;
            case R.id.id_chat_panel_input_emoj:
                openEmojPanel();
                break;
            case R.id.id_chat_panel_input_extend:
                break;
        }
    }

    private void openEmojPanel() {
        Log.i(TAG,"打开Emoj表情");
        flEmojs.setVisibility(View.VISIBLE);
        if (flEmojs.getChildCount()<1){
            EmojiconsFragment emojfragment = new EmojiconsFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.id_chat_panel_emoj_icons, emojfragment)
                    .commit();
        }

    }

    @Override
    public void onEmojiconBackspaceClicked(View v) {
        EmojiconsFragment.backspace(etMessageContent);
    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(etMessageContent, emojicon);
    }
}
