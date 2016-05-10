package com.meshine.letsstudyclient.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meshine.letsstudyclient.R;
import com.meshine.letsstudyclient.tools.DisplayUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ming on 2016/5/7.
 */
public class TopBarView extends RelativeLayout implements View.OnClickListener {
    // 左边的图片
    @Bind(R.id.iv_top_bar_left)
    ImageView mLeftIv;

    // 标题
    @Bind(R.id.tv_top_bar_title)
    TextView mTitleTv;

    // 右边文本
    @Bind(R.id.tv_top_bar_right)
    TextView mRightTv;

    // 右边的图片
    @Bind(R.id.iv_top_bar_right)
    ImageView mRightIv;

    // 如果有提醒小圆点 , 显示
    @Bind(R.id.iv_top_bar_warn)
    ImageView mWarn;

    private Context mContext;

    private int leftSrc;
    private String centerText;
    private String rightText;
    private int rightSrc;
    private int warnSrc;
    private boolean isShowWarn;
    private boolean isShowLeft;
    private boolean isShowRight;
    private boolean isBack;

    private float centerTextSize;
    private float rightTextSize;
    private int centerTextColor;
    private int rightTextColor;

    private View mView;

    private OnTopBarClickListener listener;

    public TopBarView(Context context) {
        this(context,null);
    }

    public TopBarView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public TopBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
            init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs){
        this.mContext = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TopBar);
        leftSrc = ta.getResourceId(R.styleable.TopBar_leftSrc, R.mipmap.ic_launcher);
        centerText = ta.getString(R.styleable.TopBar_centerText);
        rightText = ta.getString(R.styleable.TopBar_rightText);
        rightSrc = ta.getResourceId(R.styleable.TopBar_rightSrc, R.mipmap.ic_launcher);
        warnSrc = ta.getResourceId(R.styleable.TopBar_warnSrc, R.mipmap.topbar_warn_dot);
        isShowWarn = ta.getBoolean(R.styleable.TopBar_isShowWarn, false);
        isShowLeft = ta.getBoolean(R.styleable.TopBar_isShowLeft, false);
        isShowRight = ta.getBoolean(R.styleable.TopBar_isShowRight, false);
        isBack = ta.getBoolean(R.styleable.TopBar_isBack, false);
        centerTextSize = ta.getDimension(R.styleable.TopBar_centerTextSize, 15);
        rightTextSize = ta.getDimension(R.styleable.TopBar_rightTextSize, 15);
        centerTextColor = ta.getColor(R.styleable.TopBar_centerTextColor, getResources().getColor(android.R.color.white));
        rightTextColor = ta.getColor(R.styleable.TopBar_rightTextColor, getResources().getColor(android.R.color.white));

        ta.recycle();

        initContentView();
        initView();
        initListener();
    }


    private void initContentView() {
        mView = View.inflate(mContext, R.layout.view_top_bar, this);
        ButterKnife.bind(this);
    }

//    void findViews(){
//        mLeftIv = (ImageView) findViewById(R.id.iv_top_bar_left);
//        mTitleTv = (TextView) findViewById(R.id.tv_top_bar_title);
//        mRightIv = (ImageView) findViewById(R.id.iv_top_bar_right);
//        mWarn = (ImageView) findViewById(R.id.iv_top_bar_warn);
//    }

    private void initView() {

        mTitleTv.setTextSize(centerTextSize);
        mTitleTv.setTextColor(centerTextColor);
        mTitleTv.setText(centerText);

        if (isShowLeft) {
            mLeftIv.setVisibility(View.VISIBLE);
            mLeftIv.setImageResource(leftSrc);
        } else {
            mLeftIv.setVisibility(View.INVISIBLE);
        }

        if (isShowRight) {
            mRightIv.setVisibility(View.VISIBLE);
            mRightIv.setImageResource(rightSrc);
            if (rightSrc == R.mipmap.ic_launcher){
                mRightIv.setVisibility(View.GONE);
            }

            mRightTv.setVisibility(View.VISIBLE);
            mRightTv.setTextSize(rightTextSize);
            mRightTv.setTextColor(rightTextColor);
            mRightTv.setText(rightText);

        } else {
            mRightIv.setVisibility(View.INVISIBLE);
            mRightTv.setVisibility(View.INVISIBLE);
        }

        showWarn();
    }

    //是否显示提醒
    private void showWarn() {
        if (isShowWarn) {
            mWarn.setImageResource(warnSrc);
            mWarn.setVisibility(View.VISIBLE);
        } else {
            mWarn.setVisibility(View.INVISIBLE);
        }
    }

    private void initListener() {
        mLeftIv.setOnClickListener(this);
        mRightIv.setOnClickListener(this);
        mRightTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_top_bar_left:
                if (listener != null) {
                    listener.onTopBarLeftClick(v);
                }
                break;

            case R.id.iv_top_bar_right:
                if (listener != null) {
                    listener.onTopBarRightClick(v);
                }
                break;
        }
    }

    public void setOnTopBarClickListener(OnTopBarClickListener listener) {
        this.listener = listener;
    }

    public interface OnTopBarClickListener {
        void onTopBarRightClick(View v);
        void onTopBarLeftClick(View v);
    }


    public boolean isShowWarn() {
        return isShowWarn;
    }

    //设置是否显示提醒圆点
    public void setShowWarn(boolean isShowWarn) {
        this.isShowWarn = isShowWarn;
        showWarn();
    }

    public void setTilte(String title){
        this.mTitleTv.setText(title);
    }

}
