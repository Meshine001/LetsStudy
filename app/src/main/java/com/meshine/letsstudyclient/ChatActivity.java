package com.meshine.letsstudyclient;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.meshine.letsstudyclient.adapter.ChatMessageAdapter;
import com.meshine.letsstudyclient.adapter.TextWatcherAdapter;
import com.meshine.letsstudyclient.bean.ChatMessage;
import com.rockerhieu.emojicon.EmojiconEditText;
import com.rockerhieu.emojicon.EmojiconGridFragment;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.rockerhieu.emojicon.emoji.Emojicon;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meshine on 16/4/28.
 */
@EActivity(R.layout.activity_chat)
public class ChatActivity extends FragmentActivity implements EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener {

    @ViewById(R.id.id_chat_et_message)
    EmojiconEditText etMessage;

    @ViewById(R.id.id_chat_emoj)
    ImageView ivEmoj;

    boolean useDefault = true;

    @ViewById(R.id.id_chat_emoj_icons)
    FrameLayout emojIcons;


    List<ChatMessage> messages = new ArrayList<>();
    ChatMessageAdapter messageAdapter;


    @ViewById(R.id.id_chat_msg_panel)
    ListView msgPanel;

    @AfterViews
    void init() {
        initMsgPanel();
        initEmoj();
    }

    void initMsgPanel() {

        messages.add(new ChatMessage("d", "w", "Hi~~~", ChatMessageAdapter.CHAT_TYPE_TEXT, 0));
        messages.add(new ChatMessage("d", "w", "Hi~~~", ChatMessageAdapter.CHAT_TYPE_TEXT, 0));
        messages.add(new ChatMessage("d", "w", "Hi~~~", ChatMessageAdapter.CHAT_TYPE_TEXT, 0));
        messages.add(new ChatMessage("d", "w", "Hi~~~your sister!!", ChatMessageAdapter.CHAT_TYPE_TEXT, 1));
        messages.add(new ChatMessage("d", "w", "Oh.贱人!!", ChatMessageAdapter.CHAT_TYPE_TEXT, 0));
        messages.add(new ChatMessage("d", "w", "Oh.贱人!!", ChatMessageAdapter.CHAT_TYPE_TEXT, 0));
        messages.add(new ChatMessage("d", "w", "不要这样说人家嘛~~", ChatMessageAdapter.CHAT_TYPE_TEXT, 1));
        messages.add(new ChatMessage("d", "w", "Oh.贱人!!", ChatMessageAdapter.CHAT_TYPE_TEXT, 0));
        messages.add(new ChatMessage("d", "w", "Oh.贱人!!", ChatMessageAdapter.CHAT_TYPE_TEXT, 0));
        messages.add(new ChatMessage("d", "w", "Oh.贱人!!", ChatMessageAdapter.CHAT_TYPE_TEXT, 0));
        messages.add(new ChatMessage("d", "w", "我要哭了~~", ChatMessageAdapter.CHAT_TYPE_TEXT, 1));


        messageAdapter = new ChatMessageAdapter(messages, ChatActivity.this);
        msgPanel.setAdapter(messageAdapter);


    }

    void initEmoj(){


        etMessage.setUseSystemDefault(useDefault);
        setEmojiconFragment(useDefault);

        etMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!useDefault){
                    useDefault = true;
                    etMessage.setUseSystemDefault(useDefault);
                    setEmojiconFragment(useDefault);
                }
            }
        });

    }

    InputMethodManager imm = null;

    @Click({R.id.id_chat_emoj})
    void onclick(View view) {
        switch (view.getId()) {
            case R.id.id_chat_emoj:
                useDefault = !useDefault;
                setDefaultInput(useDefault);
                etMessage.setUseSystemDefault(useDefault);
                setEmojiconFragment(useDefault);
                break;
        }
    }
    void setDefaultInput(boolean useSystemDefault){
        if(imm == null){
            imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        }

        if (!useSystemDefault)imm.hideSoftInputFromWindow(etMessage.getWindowToken(),0);
    }

    void setEmojiconFragment(boolean useSystemDefault) {
        if (!useSystemDefault)
            emojIcons.setVisibility(View.VISIBLE);
        else emojIcons.setVisibility(View.GONE);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.id_chat_emoj_icons, EmojiconsFragment.newInstance(useSystemDefault))
                .commit();


    }

    @Override
    public void onEmojiconBackspaceClicked(View view) {
        EmojiconsFragment.backspace(etMessage);
    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(etMessage, emojicon);
    }
}
