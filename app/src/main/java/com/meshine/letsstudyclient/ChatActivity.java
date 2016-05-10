package com.meshine.letsstudyclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.meshine.letsstudyclient.adapter.ChatMessageAdapter;
import com.meshine.letsstudyclient.adapter.TextWatcherAdapter;
import com.meshine.letsstudyclient.bean.ChatMessage;
import com.meshine.letsstudyclient.widget.TopBarView;
import com.rockerhieu.emojicon.EmojiconEditText;
import com.rockerhieu.emojicon.EmojiconGridFragment;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.rockerhieu.emojicon.emoji.Emojicon;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by Meshine on 16/4/28.
 */
@EActivity(R.layout.activity_chat)
public class ChatActivity extends FragmentActivity implements EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener {

    @ViewById(R.id.id_chat_topbar)
    TopBarView topbar;

    @ViewById(R.id.id_chat_et_message)
    EmojiconEditText etMessage;

    @ViewById(R.id.id_chat_extend_plus)
    ImageView ivExtendPlus;

    @ViewById(R.id.id_chat_send)
    Button btnSend;

    @ViewById(R.id.id_chat_emoj)
    ImageView ivEmoj;

    boolean useDefault = true;

    @ViewById(R.id.id_chat_emoj_icons)
    FrameLayout emojIcons;


    List<Message> messages = new ArrayList<>();
    ChatMessageAdapter messageAdapter;
    Conversation mConv;

    @ViewById(R.id.id_chat_msg_panel)
    ListView msgPanel;

    @Extra("chatTarget")
    String chatTarget;
    @Extra("chatNick")
    String chatNick;

    @AfterViews
    void init() {
        initMsgPanel();
        initEmoj();
        initConversation();
    }



    void initConversation(){
        topbar.setTilte(chatNick);
        if (mConv == null){
            mConv = JMessageClient.getSingleConversation(chatTarget);
            if (mConv == null){
                mConv = Conversation.createSingleConversation(chatTarget);

            }
        }

    }


    void initMsgPanel() {
        messageAdapter = new ChatMessageAdapter(messages,ChatActivity.this);
        msgPanel.setAdapter(messageAdapter);
        if (messages.size()>1)
        msgPanel.setSelection(messages.size()-1);


    }

    void initEmoj(){
        etMessage.setUseSystemDefault(false);
        etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etMessage.getText().toString().length()>0){
                    ivExtendPlus.setVisibility(View.GONE);
                    btnSend.setVisibility(View.VISIBLE);
                }else {
                    ivExtendPlus.setVisibility(View.VISIBLE);
                    btnSend.setVisibility(View.GONE);
                }
            }
        });



        etMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emojIcons.getVisibility()==View.VISIBLE){
                    emojIcons.setVisibility(View.INVISIBLE);
                }
            }
        });

        emojIcons.setVisibility(View.GONE);

    }

    InputMethodManager imm = null;

    @Click({R.id.id_chat_emoj,R.id.id_chat_send})
    void onclick(View view) {
        switch (view.getId()) {
            case R.id.id_chat_emoj:
                emojSwitch();
                break;
            case R.id.id_chat_send:
                sendMessage();
                break;
        }
    }

    void sendMessage(){
        initConversation();
        Message sendMsg;
        if (mConv == null){
            sendMsg = JMessageClient.createSingleTextMessage("Meshine",etMessage.getText().toString());
        }else {
            String msgContent = etMessage.getText().toString();
            etMessage.setText("");
            TextContent content = new TextContent(msgContent);
             sendMsg = mConv.createSendMessage(content);

        }

        messages.add(sendMsg);

        messageAdapter.notifyDataSetChanged();
        msgPanel.setSelection(messages.size()-1);

    }



    void emojSwitch(){
        switch (emojIcons.getVisibility()){
            case  View.INVISIBLE:
                emojIcons.setVisibility(View.VISIBLE);
                setInputBoardVisible(false);
                break;
            case  View.VISIBLE:
                emojIcons.setVisibility(View.INVISIBLE);
                setInputBoardVisible(true);
                setEmojiconFragment(true);
                break;
            default:
                emojIcons.setVisibility(View.VISIBLE);
                setEmojiconFragment(true);
                setInputBoardVisible(false);
                break;
        }
    }
    void setInputBoardVisible(boolean visible){
        if(imm == null){
            imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        if (imm.isActive() && !visible){
            imm.hideSoftInputFromWindow(etMessage.getWindowToken(),0);
        }

        if (visible){
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }



    void setEmojiconFragment(boolean flag) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.id_chat_emoj_icons, EmojiconsFragment.newInstance(!flag))
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
