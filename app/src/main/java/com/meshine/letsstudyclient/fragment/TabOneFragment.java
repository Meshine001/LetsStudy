package com.meshine.letsstudyclient.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.meshine.letsstudyclient.EventDetailsActivity_;
import com.meshine.letsstudyclient.NewEventActivity_;
import com.meshine.letsstudyclient.R;
import com.meshine.letsstudyclient.adapter.EventAdapter;
import com.meshine.letsstudyclient.bean.Event;
import com.meshine.letsstudyclient.bean.SquareInfo;
import com.meshine.letsstudyclient.widget.AdBannerView;
import com.meshine.letsstudyclient.widget.ExpandListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ming on 2016/4/24.
 */
@EFragment(R.layout.fragment_tab_one)
public class TabOneFragment extends BaseFragment {

    @ViewById(R.id.id_square_ad_banner)
    AdBannerView adBanner;

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
     * */
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

    /**
     * 初始化Top5
     */
    void initTop5(){
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

    void initData() {
        recommendEvents.add(new Event("http://ww2.sinaimg.cn/crop.0.0.1152.1152.1024/005Y9c72jw8epp0o2ogxjj30w00w0abq.jpg",
                "我是学霸",
                "周日图书馆约自习",
                "2",
                "男女不限",
                "2016-05-12",
                "来几个漂亮的妹子，一起搞学术。"));
        recommendEvents.add(new Event("http://cdn.duitang.com/uploads/item/201407/01/20140701090724_FFTZS.jpeg",
                "啊萌",
                "星期二一起跑步",
                "2",
                "女生",
                "2016-05-14",
                "不喜欢和不帅的男生一起跑步，美女们约跑不。"));
        recommendEvents.add(new Event("http://cdnq.duitang.com/uploads/item/201408/23/20140823154838_w4YCe.png",
                "黄小鸭",
                "谁一起去吃个饭啊",
                "2",
                "男生",
                "2016-05-14",
                "身高170+，长的帅，幽默，会哄女孩子。别的不说，就是要帅帅帅！！！不帅的不要！！！"));
        eventAdapter.notifyDataSetChanged();
    }

    void initInfoListView() {
        eventAdapter = new EventAdapter(recommendEvents,getContext());
        recommendLv.setAdapter(eventAdapter);
        recommendLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(),EventDetailsActivity_.class);
                startActivity(intent);
            }
        });
    }

    @Click({R.id.id_square_new_event})
    void onClick(View view){
        switch (view.getId()){
            case R.id.id_square_new_event:
                if (isLogIn()){
                    Intent intent = new Intent(getContext(), NewEventActivity_.class);
                    startActivity(intent);
                }else {
                    logIn();
                }
                break;
        }
    }

}
