package com.uxin.testa1.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uxin.testa1.R;

/**
 * 顶部导航条
 * @auth tik
 */
public class TopBar extends RelativeLayout {

    private int mLeftTextColor;
    private float mLeftTextSize;
    private Drawable mLeftBackground;
    private String mLeftText;

    private int mRightTextColor;
    private float mRightTextSize;
    private Drawable mRightBackground;
    private Drawable mRightImageSrc;
    private String mRightText;

    private int mTitleTextColor;
    private float mTitleTextSize;
    private String mTitleText;

    private Button mLeftBtn;
    private Button mRightBtn;
    private ImageView mRightImage;
    private TextView mTitle;

    private boolean mLeftVisible;
    private boolean mRightTextVisible;
    private boolean mRightImageVisible;

    private TopbarClickListener mTopbarClickListener;

    public TopBar(Context context) {
        super(context);
    }

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TopBar);

        mLeftTextColor = ta.getColor(R.styleable.TopBar_leftTextColor, 0);
        mLeftTextSize = ta.getDimension(R.styleable.TopBar_leftTextSize, 10f);
        mLeftBackground = ta.getDrawable(R.styleable.TopBar_leftBackground);
        mLeftText = ta.getString(R.styleable.TopBar_leftText);
        mLeftVisible = ta.getBoolean(R.styleable.TopBar_leftVisible, true);

        mRightTextColor = ta.getColor(R.styleable.TopBar_rightTextColor, 0);
        mRightTextSize = ta.getDimension(R.styleable.TopBar_rightTextSize, 10f);
        mRightBackground = ta.getDrawable(R.styleable.TopBar_rightBackground);
        mRightImageSrc = ta.getDrawable(R.styleable.TopBar_rightImageSrc);
        mRightText = ta.getString(R.styleable.TopBar_rightText);
        mRightTextVisible = ta.getBoolean(R.styleable.TopBar_rightTextVisible, false);
        mRightImageVisible = ta.getBoolean(R.styleable.TopBar_rightImageVisible, false);

        mTitleTextColor = ta.getColor(R.styleable.TopBar_titleTextColor, 0);
        mTitleTextSize = ta.getDimension(R.styleable.TopBar_titleTextSize, 10f);
        mTitleText = ta.getString(R.styleable.TopBar_title);

        //一定要回收
        ta.recycle();

        mLeftBtn = new Button(context);
        mRightBtn = new Button(context);
        mRightImage = new ImageView(context);
        mTitle = new TextView(context);

        mLeftBtn.setVisibility(mLeftVisible ? View.VISIBLE : View.GONE);
        mRightBtn.setVisibility(mRightTextVisible ? View.VISIBLE : View.GONE);
        mRightImage.setVisibility(mRightImageVisible ? View.VISIBLE : View.GONE);

        if(mLeftVisible){
            mLeftBtn.setTextColor(mLeftTextColor);
            mLeftBtn.setTextSize(mLeftTextSize);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mLeftBtn.setBackground(mLeftBackground);
            }else{
                mLeftBtn.setBackgroundDrawable(mLeftBackground);
            }
            mLeftBtn.setText(mLeftText);
        }

        if(mRightTextVisible){
            mRightBtn.setTextColor(mRightTextColor);
            mRightBtn.setTextSize(mRightTextSize);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mRightBtn.setBackground(mRightBackground);
            }else{
                mRightBtn.setBackgroundDrawable(mRightBackground);
            }
            mRightBtn.setText(mRightText);
        }

        if(mRightImageVisible){
            mRightImage.setImageDrawable(mRightImageSrc);
            mRightImage.setPadding(10, 10, 10, 10);
            mRightImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }

        mTitle.setTextColor(mTitleTextColor);
        mTitle.setTextSize(mTitleTextSize);
        mTitle.setText(mTitleText);
        mTitle.setGravity(Gravity.CENTER);

        LayoutParams leftParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        addView(mLeftBtn, leftParams);

        if(mRightTextVisible){
            LayoutParams rightBtnParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            rightBtnParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
            addView(mRightBtn, rightBtnParams);
        }

        if(mRightImageVisible){
            LayoutParams rightImageParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            rightImageParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
            addView(mRightImage, rightImageParams);
        }

        LayoutParams titleParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        addView(mTitle, titleParams);

        mLeftBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTopbarClickListener != null) {
                    mTopbarClickListener.leftClick();
                }
            }
        });

        mRightBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTopbarClickListener != null){
                    mTopbarClickListener.rightClick();
                }
            }
        });

        mRightImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTopbarClickListener != null){
                    mTopbarClickListener.rightClick();
                }
            }
        });

    }

    public void setLeftBtnVisible(boolean visible){
        mLeftBtn.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setmRightTextVisible(boolean visible){
        mRightBtn.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setTopbarClickListener(TopbarClickListener listener){
        mTopbarClickListener = listener;
    }

    public interface TopbarClickListener{

        void leftClick();

        void rightClick();
    }
}
