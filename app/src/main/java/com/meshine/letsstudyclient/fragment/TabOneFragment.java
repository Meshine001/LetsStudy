package com.meshine.letsstudyclient.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.trace.T;
import com.bumptech.glide.Glide;
import com.meshine.letsstudyclient.EventDetailsActivity_;
import com.meshine.letsstudyclient.EventsActivity_;
import com.meshine.letsstudyclient.HeadlineActivity;
import com.meshine.letsstudyclient.HeadlineActivity_;
import com.meshine.letsstudyclient.LetsRunActivity_;
import com.meshine.letsstudyclient.NewEventActivity_;
import com.meshine.letsstudyclient.R;
import com.meshine.letsstudyclient.RankActivity;
import com.meshine.letsstudyclient.RankActivity_;
import com.meshine.letsstudyclient.UserProfileActivity;
import com.meshine.letsstudyclient.UserProfileActivity_;
import com.meshine.letsstudyclient.adapter.EventAdapter;
import com.meshine.letsstudyclient.bean.Event;
import com.meshine.letsstudyclient.bean.SquareInfo;
import com.meshine.letsstudyclient.net.MyRestClient;
import com.meshine.letsstudyclient.tools.EventUtil;
import com.meshine.letsstudyclient.widget.AdBannerView;
import com.meshine.letsstudyclient.widget.ExpandListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ming on 2016/4/24.
 */
@EFragment(R.layout.fragment_tab_one)
public class TabOneFragment extends BaseFragment {

    private static final String TAG = "Main Tab";
    /**
     * 广告页
     */
    @ViewById(R.id.id_square_ad_banner)
    AdBannerView adBanner;

    /**
     * 主页菜单
     */
    @ViewById(R.id.id_square_menu_newest)
    LinearLayout menuNewest;
    @ViewById(R.id.id_square_menu_hotest)
    LinearLayout menuHotest;
    @ViewById(R.id.id_square_menu_classic)
    LinearLayout menuClassic;
    @ViewById(R.id.id_square_menu_rank)
    LinearLayout menuRank;
    @ViewById(R.id.id_square_menu_my)
    LinearLayout menuMy;
    @ViewById(R.id.id_square_menu_lets_eat)
    LinearLayout menuLetsEat;
    @ViewById(R.id.id_square_menu_lets_run)
    LinearLayout menuLetsRun;
    @ViewById(R.id.id_square_menu_lets_study)
    LinearLayout menuLetsStudy;
    @ViewById(R.id.id_square_menu_lets_report)
    LinearLayout menuLetsReport;
    @ViewById(R.id.id_square_menu_newest)
    LinearLayout menuOthers;


    @ViewById(R.id.id_square_top1_iv)
    ImageView ivTop1;
    @ViewById(R.id.id_square_top2_iv)
    ImageView ivTop2;
    @ViewById(R.id.id_square_top3_iv)
    ImageView ivTop3;
    @ViewById(R.id.id_square_top4_iv)
    ImageView ivTop4;
    @ViewById(R.id.id_square_top5_iv)
    ImageView ivTop5;


    @ViewById(R.id.id_square_recommend_events_lv)
    ExpandListView recommendLv;

    List<Event> recommendEvents = new ArrayList<>();

    EventAdapter eventAdapter;

    @AfterViews
    void init() {
        initAd();
        initTop5();
        initInfoListView();
        initData();

    }

    /**
     * 初始化广告Banner
     */
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
                Toast.makeText(getContext(), "You click " + String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });

        adBanner.setImageLoadder(new AdBannerView.ImageLoader() {
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

    /**
     * 初始化Top5
     */
    void initTop5() {
        String top1 = "http://img1.imgtn.bdimg.com/it/u=693362385,3280695814&fm=21&gp=0.jpg";
        String top2 = "http://www.qqpk.cn/Article/UploadFiles/201205/20120510093022882.jpg";
        String top3 = "http://www.touxiang.cn/uploads/20131121/21-074919_163.jpg";
        String top4 = "http://hdn.xnimg.cn/photos/hdn321/20130612/2235/h_main_NNN4_e80a000007df111a.jpg";
        String top5 = "http://www.qq1234.org/uploads/allimg/150311/114332F11-2.jpg";

        Glide.with(this).load(top1)
                .placeholder(R.drawable.ic_tab_square_normal)
                .dontAnimate()
                .dontTransform()
                .into(ivTop1);
        Glide.with(this).load(top2)
                .placeholder(R.drawable.ic_tab_square_normal)
                .dontAnimate()
                .dontTransform()
                .into(ivTop2);
        Glide.with(this).load(top3)
                .placeholder(R.drawable.ic_tab_square_normal)
                .dontAnimate()
                .dontTransform()
                .into(ivTop3);
        Glide.with(this).load(top4)
                .placeholder(R.drawable.ic_tab_square_normal)
                .dontAnimate()
                .dontTransform()
                .into(ivTop4);
        Glide.with(this).load(top5)
                .placeholder(R.drawable.ic_tab_square_normal)
                .dontAnimate()
                .dontTransform()
                .into(ivTop5);

    }

    @Override
    public void onDestroyView() {
        adBanner.stopLoop();
        super.onDestroyView();
    }

    @RestService
    MyRestClient httpClient;

    void initData() {
        getRecommentData();
    }

    @Background
    void getRecommentData() {
        String respone = httpClient.getRecommend();

        List<Event> rs = EventUtil.parseEventsData(respone);
        if (rs != null){
            updateRecomment(rs);
        }

    }

    @UiThread
    void updateRecomment(List<Event> recommends) {
        recommendEvents.clear();
        recommendEvents.addAll(recommends);
        for (Event e:recommendEvents){
            Log.i(TAG,e.toString());
        }
        eventAdapter.notifyDataSetChanged();
    }

    void initInfoListView() {
        eventAdapter = new EventAdapter(recommendEvents, getContext());
        recommendLv.setAdapter(eventAdapter);
        recommendLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), EventDetailsActivity_.class);
                startActivity(intent);
            }
        });
    }

    @Click({R.id.id_square_new_event,
            R.id.id_square_menu_newest,
            R.id.id_square_menu_hotest,
            R.id.id_square_menu_classic,
            R.id.id_square_menu_rank,
            R.id.id_square_menu_my,
            R.id.id_square_menu_lets_eat,
            R.id.id_square_menu_lets_run,
            R.id.id_square_menu_lets_study,
            R.id.id_square_menu_lets_report,
            R.id.id_square_menu_lets_others,
            R.id.id_headline_layout,
            R.id.id_square_top1_iv,
            R.id.id_square_top2_iv,
            R.id.id_square_top3_iv,
            R.id.id_square_top4_iv,
            R.id.id_square_top5_iv,})
    void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            //新活动
            case R.id.id_square_new_event:
                if (!checkSignIn(true)) return;
                intent = new Intent(getContext(), NewEventActivity_.class);
                startActivity(intent);

                break;
            //最新活动
            case R.id.id_square_menu_newest:
                goEvents(Event.NEWEST);
                break;
            //热门活动
            case R.id.id_square_menu_hotest:
                goEvents(Event.HOTEAST);
                break;
            //活动宝典
            case R.id.id_square_menu_classic:
                break;
            //达人榜
            case R.id.id_square_menu_rank:
                intent = new Intent(getContext(), RankActivity_.class);
                startActivity(intent);

                break;
            //我的活动
            case R.id.id_square_menu_my:
                break;
            //约饭
            case R.id.id_square_menu_lets_eat:
                goEvents(Event.EATE);
                break;
            //约跑
            case R.id.id_square_menu_lets_run:
//                intent = new Intent(getContext(), LetsRunActivity_.class);
//                startActivity(intent);
                goEvents(Event.RUN);
                break;
            //约自习
            case R.id.id_square_menu_lets_study:
                goEvents(Event.STUDY);
                break;
            //约讲座
            case R.id.id_square_menu_lets_report:
                goEvents(Event.REPORT);
                break;
            //其他
            case R.id.id_square_menu_lets_others:
                goEvents(Event.OTHERS);
                break;
            case R.id.id_headline_layout:
                intent = new Intent(getContext(), HeadlineActivity_.class);
                startActivity(intent);
                break;
            case R.id.id_square_top1_iv:
                goUserProfile(1);
                break;
            case R.id.id_square_top2_iv:
                goUserProfile(2);
                break;
            case R.id.id_square_top3_iv:
                goUserProfile(3);
                break;
            case R.id.id_square_top4_iv:
                goUserProfile(4);
                break;
            case R.id.id_square_top5_iv:
                goUserProfile(5);
                break;
        }
    }

    private void goUserProfile(int level) {
        Intent intent = new Intent(getContext(), UserProfileActivity_.class);
        startActivity(intent);
    }

    private void goEvents(int type) {
        Intent intent = new Intent(getContext(), EventsActivity_.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }

}
