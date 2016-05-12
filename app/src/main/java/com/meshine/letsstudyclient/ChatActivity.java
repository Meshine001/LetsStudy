package com.meshine.letsstudyclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.meshine.letsstudyclient.adapter.ChatMessageAdapter;
import com.meshine.letsstudyclient.adapter.ConversationAdapter;
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
import cn.jpush.im.android.api.content.CustomContent;
import cn.jpush.im.android.api.content.EventNotificationContent;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.content.VoiceContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by Meshine on 16/4/28.
 */
public class ChatActivity extends FragmentActivity implements View.OnClickListener,EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener {

    private static final String TAG = ChatActivity.class.getName();

    TopBarView topbar;

    EmojiconEditText etMessage;

    ImageView ivExtendPlus;

    Button btnSend;

    ImageView ivEmoj;

    boolean useDefault = true;

    FrameLayout emojIcons;

    /**
     * 接收消息进程的消息
     */
    Handler messageHandler = new Handler(){
        @Override
        public void handleMessage(android.os.Message message) {
            Message msg = (Message) message.obj;
            handleJMessage(msg);
        }
    };

    List<Message> messages = new ArrayList<>();
    ChatMessageAdapter messageAdapter;
    Conversation mConv;

    ListView msgPanel;

    String targetUserName;
    String targetNick;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        JMessageClient.registerEventReceiver(this);
        initViews();
        initExtras();
        initComponent();
    }

    @Override
    protected void onDestroy() {
        JMessageClient.unRegisterEventReceiver(this);
        super.onDestroy();
    }

    void initExtras(){
        targetUserName = getIntent().getStringExtra("targetUserName");
        targetNick = getIntent().getStringExtra("targetNick");
    }
    void initComponent() {
        initMsgPanel();
        initEmoj();
        initConversation();
    }

    void initViews(){

        topbar = (TopBarView) findViewById(R.id.id_chat_topbar);
        topbar.setOnTopBarClickListener(new TopBarView.OnTopBarClickListener() {
            @Override
            public void onTopBarRightClick(View v) {

            }

            @Override
            public void onTopBarLeftClick(View v) {
                finish();
            }
        });

        etMessage = (EmojiconEditText) findViewById(R.id.id_chat_et_message);
        etMessage.setOnClickListener(this);
        ivExtendPlus = (ImageView) findViewById(R.id.id_chat_extend_plus);
        ivExtendPlus.setOnClickListener(this);
        btnSend = (Button) findViewById(R.id.id_chat_send);
        btnSend.setOnClickListener(this);
        ivEmoj = (ImageView) findViewById(R.id.id_chat_emoj);
        ivEmoj.setOnClickListener(this);
        emojIcons = (FrameLayout) findViewById(R.id.id_chat_emoj_icons);
        msgPanel = (ListView) findViewById(R.id.id_chat_msg_panel);

    }

    void handleJMessage(Message msg){

        switch (msg.getContentType()){
            case text:
                //处理文字消息
                TextContent textContent = (TextContent) msg.getContent();
                textContent.getText();
                Log.i(TAG,"Received a text message => "+msg.getDirect());
                showNewMessage(msg);
                break;
            case image:
                //处理图片消息
                ImageContent imageContent = (ImageContent) msg.getContent();
                imageContent.getLocalPath();//图片本地地址
                imageContent.getLocalThumbnailPath();//图片对应缩略图的本地地址
                break;
            case voice:
                //处理语音消息
                VoiceContent voiceContent = (VoiceContent) msg.getContent();
                voiceContent.getLocalPath();//语音文件本地地址
                voiceContent.getDuration();//语音文件时长
                break;
            case custom:
                //处理自定义消息
                CustomContent customContent = (CustomContent) msg.getContent();
                customContent.getNumberValue("custom_num"); //获取自定义的值
                customContent.getBooleanValue("custom_boolean");
                customContent.getStringValue("custom_string");
                break;
            case eventNotification:
                //处理事件提醒消息
                EventNotificationContent eventNotificationContent = (EventNotificationContent)msg.getContent();
                switch (eventNotificationContent.getEventNotificationType()){
                    case group_member_added:
                        //群成员加群事件
                        break;
                    case group_member_removed:
                        //群成员被踢事件（只有被踢的用户能收到此事件）
                        break;
                    case group_member_exit:
                        //群成员退群事件（已弃用）
                        break;
                }
                break;
        }
    }

    public void onEvent(MessageEvent event){
        Message msg = event.getMessage();
        Log.i(TAG,"Received a new message.");
        android.os.Message m = new android.os.Message();
        m.obj = msg;
        messageHandler.sendMessage(m);
    }

    void initConversation(){
        topbar.setTilte(targetNick);
        JMessageClient.enterSingleConversation(targetUserName);
        mConv = JMessageClient.getSingleConversation(targetUserName);
        if (mConv!=null){
            List<Message> msgs = mConv.getAllMessage();
            for (Message m:msgs){
                Log.i(TAG,m.toString());
            }
            messages.addAll(msgs);
            messageAdapter.notifyDataSetChanged();
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




    void sendMessage(){
        Message sendMsg;
        if (mConv == null){
            mConv = Conversation.createSingleConversation(targetUserName);
        }

        String msgContent = etMessage.getText().toString();
        etMessage.setText("");
        TextContent content = new TextContent(msgContent);
        sendMsg = mConv.createSendMessage(content);
        JMessageClient.sendMessage(sendMsg);


        showNewMessage(sendMsg);

    }

    void showNewMessage(Message msg){
        messages.add(msg);
        messageAdapter.notifyDataSetChanged();
        msgPanel.setSelection(messageAdapter.getCount()-1);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_chat_emoj:
                emojSwitch();
                break;
            case R.id.id_chat_send:
                sendMessage();
                break;
        }

    }
}
