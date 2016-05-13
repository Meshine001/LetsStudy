package com.meshine.letsstudyclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.meshine.letsstudyclient.ChatActivity;
import com.meshine.letsstudyclient.R;
import com.meshine.letsstudyclient.adapter.ConversationAdapter;
import com.meshine.letsstudyclient.net.MyRestClient;
import com.meshine.letsstudyclient.widget.TopBarView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by Ming on 2016/4/24.
 */
@EFragment(R.layout.fragment_tab_tree)
public class TabTreeFragment extends Fragment {

    private static final String TAG = TabTreeFragment.class.getName();
    @ViewById(R.id.id_conversation_topbar)
    TopBarView topbar;
    @ViewById(R.id.id_conversation_list)
    ListView convList;

    List<Conversation> conversations = new ArrayList<>();
    ConversationAdapter adapter;

    @RestService
    MyRestClient netClient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }

    @AfterViews
    void init(){
        initTopbar();
        initConversationList();
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    void initTopbar(){

    }

    void initConversationList(){
        adapter = new ConversationAdapter(conversations,getContext());
        convList.setAdapter(adapter);
        convList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ChatActivity.class);
                UserInfo info = (UserInfo) conversations.get(position).getTargetInfo();
                intent.putExtra("targetUserName",info.getUserName());
                intent.putExtra("targetNick",info.getNickname());
                startActivity(intent);
            }
        });
    }

    void initData(){
//        conversations.clear();
//        List<Conversation> conv = JMessageClient.getConversationList();
//        conversations.addAll(conv);
//        adapter.notifyDataSetChanged();
//        getContacts(JMessageClient.getMyInfo().getUserID());
    }

    @Background
    void getContacts(long userId){
        showContacts(netClient.getContacts(userId));
    }

    @UiThread
    void showContacts(List<String> contacts){
        Log.i(TAG,contacts.toString());
    }

}
