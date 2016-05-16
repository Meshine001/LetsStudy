package com.meshine.letsstudyclient;

import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.meshine.letsstudyclient.adapter.RankAdapter;
import com.meshine.letsstudyclient.bean.User;
import com.meshine.letsstudyclient.tools.AppManager;
import com.meshine.letsstudyclient.widget.CircleImageView;
import com.meshine.letsstudyclient.widget.ExpandListView;
import com.meshine.letsstudyclient.widget.TopBarView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meshine on 16/5/16.
 */
@EActivity(R.layout.activity_rank)
public class RankActivity extends BaseActivity {


    @ViewById(R.id.id_rank_topbar)
    TopBarView topBar;

    @ViewById(R.id.id_rank_top1_avatar)
    CircleImageView top1Avatar;
    @ViewById(R.id.id_rank_top1_nick)
    TextView top1Nick;
    @ViewById(R.id.id_rank_top1_vitality)
    TextView top1Vitality;

    @ViewById(R.id.id_rank_top2_avatar)
    CircleImageView top2Avatar;
    @ViewById(R.id.id_rank_top2_nick)
    TextView top2Nick;
    @ViewById(R.id.id_rank_top2_vitality)
    TextView top2Vitality;

    @ViewById(R.id.id_rank_top3_avatar)
    CircleImageView top3Avatar;
    @ViewById(R.id.id_rank_top3_nick)
    TextView top3Nick;
    @ViewById(R.id.id_rank_top3_vitality)
    TextView top3Vitality;


    @ViewById(R.id.id_rank_list)
    ExpandListView rankList;

    List<User> users = new ArrayList<>();
    RankAdapter rankAdapter;

    List<User> topThree = new ArrayList<>();

    @AfterViews
    void init(){
        initTopbar();
        initListView();
        getTopThree();
        getRank();
    }



    void initListView(){
        rankAdapter = new RankAdapter(users,this);
        rankList.setAdapter(rankAdapter);
    }

    void getTopThree(){
        for (int i=0;i<3;i++){
            User u = new User("","","WOll"+(i+1),"http://img4.imgtn.bdimg.com/it/u=1357613315,1239107303&fm=21&gp=0.jpg",(300-i));
            topThree.add(u);
        }
        showTopThree();
    }

    void showTopThree(){

        Glide.with(this).load(topThree.get(0).getAvatar())
                .placeholder(R.drawable.ic_logo)
                .dontAnimate()
                .dontTransform()
                .into(top1Avatar);
        top1Nick.setText(topThree.get(0).getNick());
        top1Vitality.setText("活跃度: "+topThree.get(0).getVitality());


        Glide.with(this).load(topThree.get(1).getAvatar())
                .placeholder(R.drawable.ic_logo)
                .dontAnimate()
                .dontTransform()
                .into(top2Avatar);
        top2Nick.setText(topThree.get(1).getNick());
        top2Vitality.setText("活跃度: "+topThree.get(1).getVitality());

        Glide.with(this).load(topThree.get(2).getAvatar())
                .placeholder(R.drawable.ic_logo)
                .dontAnimate()
                .dontTransform()
                .into(top3Avatar);
        top3Nick.setText(topThree.get(2).getNick());
        top3Vitality.setText("活跃度: "+topThree.get(2).getVitality());
    }

    void getRank(){
        for (int i=0;i<7;i++){
            User u = new User("","","nIKC"+i,"http://img4.imgtn.bdimg.com/it/u=1357613315,1239107303&fm=21&gp=0.jpg",(200-i));
            users.add(u);
        }

        rankAdapter.notifyDataSetChanged();
    }

    @Override
    public void initTopbar() {
        super.initTopbar();
        topBar.setOnTopBarClickListener(new TopBarView.OnTopBarClickListener() {
            @Override
            public void onTopBarRightClick(View v) {

            }

            @Override
            public void onTopBarLeftClick(View v) {
                AppManager.getAppManager().finishActivity();
            }
        });
    }
}
