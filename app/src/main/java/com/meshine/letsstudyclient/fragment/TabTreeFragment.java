package com.meshine.letsstudyclient.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meshine.letsstudyclient.R;
import com.meshine.letsstudyclient.widget.TopBarView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Ming on 2016/4/24.
 */
@EFragment(R.layout.fragment_tab_tree)
public class TabTreeFragment extends Fragment {

    @ViewById(R.id.id_conversation_topbar)
    TopBarView topbar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }

    @AfterViews
    void init(){
        initTopbar();
    }

    void initTopbar(){

    }
}
