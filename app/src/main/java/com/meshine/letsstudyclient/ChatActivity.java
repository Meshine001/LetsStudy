package com.meshine.letsstudyclient;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.meshine.letsstudyclient.adapter.ChatMessageAdapter;
import com.meshine.letsstudyclient.bean.ChatMessage;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meshine on 16/4/28.
 */
@EActivity(R.layout.activity_chat)
public class ChatActivity extends Activity {

    List<ChatMessage> messages = new ArrayList<>();
    ChatMessageAdapter messageAdapter;

    @ViewById(R.id.id_chat_msg_panel)
    ListView msgPanel;

    @AfterViews
    void init(){
        initMsgPanel();
    }

    void initMsgPanel(){

        messages.add(new ChatMessage("d","w","Hi~~~", ChatMessageAdapter.CHAT_TYPE_TEXT,0));
        messages.add(new ChatMessage("d","w","Hi~~~", ChatMessageAdapter.CHAT_TYPE_TEXT,0));
        messages.add(new ChatMessage("d","w","Hi~~~", ChatMessageAdapter.CHAT_TYPE_TEXT,0));
        messages.add(new ChatMessage("d","w","Hi~~~your sister!!", ChatMessageAdapter.CHAT_TYPE_TEXT,1));
        messages.add(new ChatMessage("d","w","Oh.贱人!!", ChatMessageAdapter.CHAT_TYPE_TEXT,0));
        messages.add(new ChatMessage("d","w","Oh.贱人!!", ChatMessageAdapter.CHAT_TYPE_TEXT,0));
        messages.add(new ChatMessage("d","w","不要这样说人家嘛~~", ChatMessageAdapter.CHAT_TYPE_TEXT,1));
        messages.add(new ChatMessage("d","w","Oh.贱人!!", ChatMessageAdapter.CHAT_TYPE_TEXT,0));
        messages.add(new ChatMessage("d","w","Oh.贱人!!", ChatMessageAdapter.CHAT_TYPE_TEXT,0));
        messages.add(new ChatMessage("d","w","Oh.贱人!!", ChatMessageAdapter.CHAT_TYPE_TEXT,0));
        messages.add(new ChatMessage("d","w","我要哭了~~", ChatMessageAdapter.CHAT_TYPE_TEXT,1));


        messageAdapter = new ChatMessageAdapter(messages,ChatActivity.this);
        msgPanel.setAdapter(messageAdapter);


    }



}
