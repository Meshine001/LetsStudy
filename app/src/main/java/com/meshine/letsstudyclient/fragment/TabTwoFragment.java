package com.meshine.letsstudyclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.meshine.letsstudyclient.ChatActivity;
import com.meshine.letsstudyclient.R;
import com.meshine.letsstudyclient.adapter.ContactsAdapter;
import com.meshine.letsstudyclient.application.MyApplication;
import com.meshine.letsstudyclient.bean.Contacts;
import com.meshine.letsstudyclient.db.DataHelper;
import com.meshine.letsstudyclient.net.MyRestClient;
import com.meshine.letsstudyclient.tools.CommonUtil;
import com.meshine.letsstudyclient.tools.HandleResponseCode;
import com.meshine.letsstudyclient.tools.JSONUtil;
import com.meshine.letsstudyclient.widget.TopBarView;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.ViewHolder;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by Ming on 2016/4/24.
 */
@EFragment(R.layout.fragment_tab_two)
public class TabTwoFragment extends BaseFragment {
    private static final String TAG = "TabContact";
    private static final int MESSAGE_USER_INFO = 0X0001;
    @ViewById(R.id.id_contacts_list)
    ListView contactsList;
    List<UserInfo> contactsInfos = new ArrayList<>();
    List<String> contactsNames = new ArrayList<>();
    ContactsAdapter contactsAdapter;

    @ViewById(R.id.id_contacts_topbar)
    TopBarView topbar;

    DataHelper dataHelper = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApplication.isLogin) {
           // initData();
        }
    }

    @AfterViews
    void init() {
        initTopbar();
        initListView();
        initDB();
        initData();
    }

    void initDB() {
        dataHelper = new DataHelper(getContext());
    }

    void initTopbar() {
        topbar.setOnTopBarClickListener(new TopBarView.OnTopBarClickListener() {
            @Override
            public void onTopBarRightClick(View v) {
                if (!checkSignIn(true)) return;
                addContacts();
            }

            @Override
            public void onTopBarLeftClick(View v) {

            }
        });
    }

    /**
     * 添加联系人
     */
    private void addContacts() {
        int height = CommonUtil.getScreenHeight(getActivity());

        Holder holder = new ViewHolder(R.layout.dialog_contacts_content);
        final DialogPlus dialog = DialogPlus.newDialog(getContext())
                .setGravity(Gravity.CENTER)
                .setContentHolder(holder)
                .setContentHeight(height / 3)
                .create();
        View dialogContent = dialog.getHolderView();
        final EditText etUserName = (EditText) dialogContent.findViewById(R.id.id_dialog_contacts_username);
        Button cancel = (Button) dialogContent.findViewById(R.id.id_dialog_contacts_cancel);
        Button submit = (Button) dialogContent.findViewById(R.id.id_dialog_contacts_submit);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserInfo(etUserName.getText().toString().trim(), dialog);
            }
        });
        dialog.show();

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_USER_INFO:
                    UserInfo info = (UserInfo) msg.obj;
                    addContacts2Server(JMessageClient.getMyInfo().getUserName(), info.getUserName());
                    break;
            }
        }
    };

    private void getUserInfo(String userName, final DialogPlus dialog) {

        JMessageClient.getUserInfo(userName, new GetUserInfoCallback() {
            @Override
            public void gotResult(int status, String s, UserInfo userInfo) {
                if (status == 0) {
                    Message msg = new Message();
                    msg.obj = userInfo;
                    msg.what = MESSAGE_USER_INFO;
                    handler.sendMessage(msg);
                } else {
                    // CommonUtil.showToast(getContext(), "该用户不存在!");
                    HandleResponseCode.onHandle(getContext(), status, false);
                }
                dialog.dismiss();
            }
        });
    }


    void initListView() {
        contactsAdapter = new ContactsAdapter(contactsInfos, getContext());
        contactsList.setAdapter(contactsAdapter);
        contactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("targetUserName", contactsInfos.get(position).getUserName());
                intent.putExtra("targetNick", contactsInfos.get(position).getNickname());
                startActivity(intent);
            }
        });
    }

    @RestService
    MyRestClient httpClient;

    void initData() {
        if (!checkSignIn(false)) return;
        getContactsFromLocal();
        getContacts(JMessageClient.getMyInfo().getUserName());
    }

    /**
     * 获取联系人
     *
     * @param username
     */
    @Background
    void getContacts(String username) {
        String response = httpClient.getContacts(username);
        parseContacts(response);
    }

    /**
     * 展示联系人
     *
     * @param response
     */
    @UiThread
    void parseContacts(String response) {
        Log.i(TAG,response);
        try {
            JSONArray ja = JSONUtil.getJSONArray(new JSONObject(response), "data");
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                String contactsName = JSONUtil.getString(jo,"contacts");
                Log.i(TAG,contactsName);
                boolean isContains = false;
                for (String s:contactsNames){
                    if (s.equals(contactsName)){
                        isContains = true;
                    }

                }
                if (!isContains){
                    getContactsInfoFromJMessage(contactsName);
                    saveContacts2Local(contactsName);
                }
            }
        } catch (JSONException e) {
            //e.printStackTrace();
        }
    }

    private void saveContacts2Local(String contactsName) {
        dataHelper.addContacts(new Contacts(JMessageClient.getMyInfo().getUserName(),contactsName));
    }

    void getContactsInfoFromJMessage(String contactsName){
        JMessageClient.getUserInfo(contactsName, new GetUserInfoCallback() {
            @Override
            public void gotResult(int status, String s, UserInfo userInfo) {
                if (status == 0) {
                    contactsInfos.add(userInfo);
                    contactsAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * @param username
     * @param contacts
     */
    @Background
    void addContacts2Server(String username, String contacts) {
        String response = httpClient.addContacts(username, contacts);
        parseContacts(response);
    }

    public void getContactsFromLocal() {
        contactsInfos.clear();
        contactsNames.clear();
        List<Contacts> cs = dataHelper.getContactsList(JMessageClient.getMyInfo().getUserName());
        for (Contacts c:cs){
            contactsNames.add(c.getContacts());
            getContactsInfoFromJMessage(c.getContacts());
        }

    }
}
