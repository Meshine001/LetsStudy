package com.meshine.letsstudyclient.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.meshine.letsstudyclient.R;
import com.meshine.letsstudyclient.adapter.SquareInfoAdapter;
import com.meshine.letsstudyclient.bean.SquareInfo;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ming on 2016/4/24.
 */
@EFragment(R.layout.fragment_tab_one)
public class TabOneFragment extends Fragment {

    @ViewById(R.id.id_square_info_list)
    PullToRefreshListView ptrlvInfos;

    ListView lvInfos;

    List<SquareInfo> infos = new ArrayList<>();

    SquareInfoAdapter infoAdapter;

    @AfterViews
    void init(){
        initData();
        initInfoListView();
    }

    void initData(){
        for (int i=0;i<5;i++){
            SquareInfo info = new SquareInfo("http","图书馆二楼","一男二女");
            infos.add(info);
        }
    }
    void initInfoListView(){
        ptrlvInfos.setMode(PullToRefreshBase.Mode.BOTH);
        ptrlvInfos.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                infos.clear();
                initData();
                infoAdapter.notifyDataSetChanged();
                ptrlvInfos.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                infos.add(new SquareInfo("hh","新加的","大家来"));
                infoAdapter.notifyDataSetChanged();
                ptrlvInfos.onRefreshComplete();
            }
        });
        lvInfos = ptrlvInfos.getRefreshableView();
        infoAdapter = new SquareInfoAdapter(infos,getContext());
        lvInfos.setAdapter(infoAdapter);
    }
}
