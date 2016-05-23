package com.meshine.letsstudyclient;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meshine.letsstudyclient.fragment.TabFourFragment;
import com.meshine.letsstudyclient.fragment.TabFourFragment_;
import com.meshine.letsstudyclient.fragment.TabOneFragment;
import com.meshine.letsstudyclient.fragment.TabOneFragment_;
import com.meshine.letsstudyclient.fragment.TabTreeFragment;
import com.meshine.letsstudyclient.fragment.TabTreeFragment_;
import com.meshine.letsstudyclient.fragment.TabTwoFragment;
import com.meshine.letsstudyclient.fragment.TabTwoFragment_;
import com.meshine.letsstudyclient.tools.AppManager;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.jpush.android.api.JPushInterface;

@EActivity(R.layout.activity_main)
public class MainActivity extends FragmentActivity {

    /**
     * 广场Tab
     */
    private TabOneFragment tabOne;
    /**
     * 联系人Tab
     */
    private TabTwoFragment tabTwo;
    /**
     * 约读Tab
     */
    private TabTreeFragment tabTree;
    /**
     * 我Tab
     */
    private TabFourFragment tabFour;
    /**
     * 用来管理Fragment
     */
    private FragmentManager fragmentManager;

    /**
     * 广场Button
     */
    @ViewById(R.id.id_btn_tab_bottom_square)
    public ImageButton ibSquare;
    /**
     * 联系人Button
     */
    @ViewById(R.id.id_btn_tab_bottom_contacts)
    public ImageButton ibContacts;
    /**
     * 约书Button
     */
    @ViewById(R.id.id_btn_tab_bottom_dates)
    public ImageButton ibDates;
    /**
     * 我Button
     */
    @ViewById(R.id.id_btn_tab_bottom_me)
    public ImageButton ibMe;

    @ViewById(R.id.id_tab_bottom_square)
    public LinearLayout llSqaure;
    @ViewById(R.id.id_tab_bottom_contacts)
    public LinearLayout llContacts;
    @ViewById(R.id.id_tab_bottom_dates)
    public LinearLayout llStudy;
    @ViewById(R.id.id_tab_bottom_me)
    public LinearLayout llMe;


    /**
     * 初始化Tab
     */
    @AfterViews
    public void initTab(){
        AppManager.getAppManager().addActivity(this);
        fragmentManager = getSupportFragmentManager();
        setTabSelection(1);
    }

    /**
     * 根据传入的index参数来设置选中的tab页
     *
     * @param index
     */
    private void setTabSelection(int index) {
        // 重置所有按钮
        resetBtn();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);

        switch (index) {
            case 1:
                ibSquare.setSelected(true);
                if(tabOne == null){
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    tabOne = new TabOneFragment_();
                    transaction.add(R.id.id_main_content, tabOne);
                }else{
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(tabOne);
                }

                break;
            case 2:
                ibContacts.setSelected(true);
                if(tabTwo == null){
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    tabTwo = new TabTwoFragment_();
                    transaction.add(R.id.id_main_content, tabTwo);
                }else{
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(tabTwo);
                }
                break;
            case 3:
                ibDates.setSelected(true);
                if(tabTree == null){
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    tabTree = new TabTreeFragment_();
                    transaction.add(R.id.id_main_content, tabTree);
                }else{
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(tabTree);
                }
                break;
            case 4:
                ibMe.setSelected(true);
                if(tabFour == null){
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    tabFour = new TabFourFragment_();
                    transaction.add(R.id.id_main_content, tabFour);
                }else{
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(tabFour);
                }
                break;
        }

        transaction.commit();
    }

    /**
     * 清除所有选中状态
     */
    private void resetBtn() {
        ibSquare.setSelected(false);
        ibContacts.setSelected(false);
        ibDates.setSelected(false);
        ibMe.setSelected(false);

    }

    /**
     * 隐藏所有Fragment
     *
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (tabOne != null) {
            transaction.hide(tabOne);
        }
        if (tabTwo != null) {
            transaction.hide(tabTwo);
        }
        if (tabTree != null) {
            transaction.hide(tabTree);
        }
        if (tabFour != null) {
            transaction.hide(tabFour);
        }
    }

    @Click({R.id.id_tab_bottom_square,R.id.id_tab_bottom_contacts,R.id.id_tab_bottom_dates,R.id.id_tab_bottom_me})
    public void onBottomTabClick(View view){
        switch (view.getId()) {
            case R.id.id_tab_bottom_square:
                setTabSelection(1);
                break;
            case R.id.id_tab_bottom_contacts:
                setTabSelection(2);
                break;
            case R.id.id_tab_bottom_dates:
                setTabSelection(3);
                break;
            case R.id.id_tab_bottom_me:
                setTabSelection(4);
                break;

            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(MainActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(MainActivity.this);
    }
}
