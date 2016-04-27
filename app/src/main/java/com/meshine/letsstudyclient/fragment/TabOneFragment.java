package com.meshine.letsstudyclient.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.meshine.letsstudyclient.R;
import com.meshine.letsstudyclient.adapter.SquareInfoAdapter;
import com.meshine.letsstudyclient.bean.SquareInfo;
import com.meshine.letsstudyclient.widget.AdBannerView;
import com.meshine.letsstudyclient.widget.ExpandListView;

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

    @ViewById(R.id.id_square_scroll_view)
    PullToRefreshScrollView scrollView;

    @ViewById(R.id.id_square_ad_banner)
    AdBannerView adBanner;

    @ViewById(R.id.id_square_info_list)
    ExpandListView lvInfos;

    List<SquareInfo> infos = new ArrayList<>();

    SquareInfoAdapter infoAdapter;

    @AfterViews
    void init() {
        initAd();
        initData();
        initInfoListView();
    }

    void initAd() {
        List<AdBannerView.BannerItem> bannerItems = new ArrayList<>();
        AdBannerView.BannerItem item1 = new AdBannerView.BannerItem("切, 你是否能在天堂看到人来人往", "https://img3.doubanio.com/view/photo/photo/public/p2327027746.jpg");
        AdBannerView.BannerItem item2 = new AdBannerView.BannerItem("欢迎来到较大图书馆", "http://www.lib.xjtu.edu.cn/image/banner.png");
        bannerItems.add(item1);
        bannerItems.add(item2);
        adBanner.setTitleEnabled(true);
        adBanner.setBannerItems(bannerItems);
        adBanner.startLoop();
        adBanner.setOnItemClickListener(new AdBannerView.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getContext(),"You click " + String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });

        adBanner.setImageLoadder(new AdBannerView.ImageLoader(){
            @Override
            public void loadImage(ImageView imageView, String url) {
                Glide.with(imageView.getContext())
                        .load(url)
                        .placeholder(R.drawable.ic_tab_square_normal)
                        .dontAnimate()
                        .dontTransform()
                        .into(imageView);
            }
        });
    }

    @Override
    public void onDestroyView() {
        adBanner.stopLoop();
        super.onDestroyView();
    }

    void initData() {
        for (int i = 0; i < 5; i++) {
            SquareInfo info = new SquareInfo("http", "图书馆二楼", "一男二女");
            infos.add(info);
        }
    }

    void initInfoListView() {

        scrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                //Toast.makeText(getContext(),"You pulled down ", Toast.LENGTH_SHORT).show();
//                infos.clear();
//                initData();
//                infoAdapter.notifyDataSetChanged();
                scrollView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                //Toast.makeText(getContext(),"You pulled up ", Toast.LENGTH_SHORT).show();
                infos.add(new SquareInfo("hh", "新加的", "大家来"));
                infoAdapter.notifyDataSetChanged();
                scrollView.onRefreshComplete();
                scrollView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            }
        });


        infoAdapter = new SquareInfoAdapter(infos, getContext());
        lvInfos.setAdapter(infoAdapter);

        scrollView.setFocusableInTouchMode(true);
        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
    }
}
