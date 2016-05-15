package com.meshine.letsstudyclient;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.meshine.letsstudyclient.tools.AppManager;
import com.meshine.letsstudyclient.widget.TopBarView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ming on 2016/5/14.
 */
@EActivity(R.layout.activity_preview_image)
public class PreViewImageActivity extends BaseActivity{
    @ViewById(R.id.id_preview_image_topbar)
    TopBarView topbar;

    @ViewById(R.id.id_preview_image_pager)
    ViewPager viewPager;

    @Extra
    Integer position;
    @Extra
    ArrayList<String> picPaths;

    List<ImageView> picViews = new ArrayList<>();

    Integer cur;

    @AfterViews
    void init(){
        initTopbar();
        initViewPager();
    }


    void initViewPager(){
       for (String s:picPaths){
           ImageView iv = new ImageView(this);
           iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                   ViewGroup.LayoutParams.MATCH_PARENT));
           iv.setImageURI(Uri.fromFile(new File(s)));
           picViews.add(iv);
       }

        cur = position;

        updateLeftText();

        PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return picViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(picViews.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(picViews.get(position));
                return picViews.get(position);
            }
        };

        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(cur);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                cur = position;
                updateLeftText();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void updateLeftText() {
        topbar.setLeftText((cur+1)+"/"+picPaths.size());
    }

    @Override
    public void initTopbar() {
        topbar.setOnTopBarClickListener(new TopBarView.OnTopBarClickListener() {
            @Override
            public void onTopBarRightClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("position", cur);
                setResult(NewEventActivity.REQUEST__PRE_IMAGE, intent);
                AppManager.getAppManager().finishActivity();
            }

            @Override
            public void onTopBarLeftClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("position", -1);
                setResult(NewEventActivity.REQUEST__PRE_IMAGE, intent);
                AppManager.getAppManager().finishActivity();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("position", -1);
        setResult(NewEventActivity.REQUEST__PRE_IMAGE, intent);
        AppManager.getAppManager().finishActivity();
    }
}
