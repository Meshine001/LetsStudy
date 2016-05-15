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
import com.meshine.letsstudyclient.adapter.ContactsAdapter;
import com.meshine.letsstudyclient.widget.TopBarView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by Ming on 2016/4/24.
 */
@EFragment(R.layout.fragment_tab_two)
public class TabTwoFragment extends Fragment {
    private static final String TAG = "TabContact";
    @ViewById(R.id.id_contacts_list)
    ListView contactsList;
    List<UserInfo> userInfos;
    ContactsAdapter contactsAdapter;

    @ViewById(R.id.id_contacts_topbar)
    TopBarView topbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }

    @AfterViews
    void init(){
        initTopbar();
        initListView();
        initData();
    }

    void initTopbar(){
        topbar.setOnTopBarClickListener(new TopBarView.OnTopBarClickListener() {
            @Override
            public void onTopBarRightClick(View v) {

            }

            @Override
            public void onTopBarLeftClick(View v) {

            }
        });
    }

    void initListView(){
        userInfos = new ArrayList<>();
        contactsAdapter = new ContactsAdapter(userInfos,getContext());
        contactsList.setAdapter(contactsAdapter);
        contactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("chatTarget",userInfos.get(position).getUserName());
                intent.putExtra("chatNick",userInfos.get(position).getNickname());
                startActivity(intent);
            }
        });
    }

    void initData(){


    }


}
