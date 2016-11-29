package com.tik.testa1.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.common.StringUtils;
import com.uxin.testa1.R;

import butterknife.internal.Utils;

/**
 * 顶部导航条
 * @auth tik
 */
public class TopBar extends RelativeLayout {

    private int mLeftTextColor;
    private float mLeftTextSize;
    private Drawable mLeftBackground;
    private Drawable mLeftImageSrc;
    private String mLeftText;

    private int mRightTextColor;
    private float mRightTextSize;
    private Drawable mRightBackground;
    private Drawable mRightImageSrc;
    private String mRightText;

    private int mTitleTextColor;
    private float mTitleTextSize;
    private String mTitleText;

    private TextView mLeftBtn;
    private TextView mRightBtn;
    private TextView mTitle;

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
        mLeftTextSize = ta.getDimension(R.styleable.TopBar_leftTextSize, 14f);
        mLeftBackground = ta.getDrawable(R.styleable.TopBar_leftBackground);
        mLeftImageSrc = ta.getDrawable(R.styleable.TopBar_leftImageSrc);
        mLeftText = ta.getString(R.styleable.TopBar_leftText);

        mRightTextColor = ta.getColor(R.styleable.TopBar_rightTextColor, 0);
        mRightTextSize = ta.getDimension(R.styleable.TopBar_rightTextSize, 14f);
        mRightBackground = ta.getDrawable(R.styleable.TopBar_rightBackground);
        mRightImageSrc = ta.getDrawable(R.styleable.TopBar_rightImageSrc);
        mRightText = ta.getString(R.styleable.TopBar_rightText);

        mTitleTextColor = ta.getColor(R.styleable.TopBar_titleTextColor, 0);
        mTitleTextSize = ta.getDimension(R.styleable.TopBar_titleTextSize, 16f);
        mTitleText = ta.getString(R.styleable.TopBar_title);

        //一定要回收
        ta.recycle();

        mLeftBtn = new TextView(context);
        mRightBtn = new TextView(context);
        mTitle = new TextView(context);


        boolean leftVisible = !(mLeftText == null || mLeftText.length() == 0);
        boolean rightVisible = (mRightText != null && mRightText.length() != 0)
                || mRightImageSrc != null;


        mLeftBtn.setVisibility(leftVisible ? View.VISIBLE : View.GONE);
        mRightBtn.setVisibility(rightVisible ? View.VISIBLE : View.GONE);

        if(leftVisible){
            mLeftBtn.setTextColor(mLeftTextColor);
            mLeftBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLeftTextSize);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mLeftBtn.setBackground(mLeftBackground);
            }else{
                mLeftBtn.setBackgroundDrawable(mLeftBackground);
            }
            mLeftBtn.setText(mLeftText);
            if(mLeftImageSrc != null){
                mLeftImageSrc.setBounds(0, 0, mLeftImageSrc.getMinimumWidth(), mLeftImageSrc.getMinimumHeight());
                mLeftBtn.setCompoundDrawables(mLeftImageSrc, null, null, null);
            }
        }

        if(rightVisible){
            mRightBtn.setTextColor(mRightTextColor);
            mRightBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, mRightTextSize);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mRightBtn.setBackground(mRightBackground);
            }else{
                mRightBtn.setBackgroundDrawable(mRightBackground);
            }
            mRightBtn.setText(mRightText);
            if(mRightImageSrc != null){
                mRightImageSrc.setBounds(0, 0, mRightImageSrc.getMinimumWidth(), mRightImageSrc.getMinimumHeight());
                mRightBtn.setCompoundDrawables(null, null, mRightImageSrc, null);
            }
        }

        mTitle.setTextColor(mTitleTextColor);
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleTextSize);
        mTitle.setText(mTitleText);
        mTitle.setGravity(Gravity.CENTER);

        LayoutParams leftParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        leftParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
        addView(mLeftBtn, leftParams);
        mLeftBtn.setPadding(20, 0, 20, 0);
        mLeftBtn.setGravity(Gravity.CENTER);

        LayoutParams rightBtnParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        rightBtnParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        rightBtnParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
        addView(mRightBtn, rightBtnParams);
        mRightBtn.setPadding(20, 0, 20, 0);
        mRightBtn.setGravity(Gravity.CENTER);


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

    }


    public void setLeftTextColor(int color){
        mLeftTextColor = color;
        mLeftBtn.setTextColor(mLeftTextColor);

    }

    public void setLeftTextSize(float size){
        mLeftTextSize = size;
        mLeftBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLeftTextSize);
    }

    public void setLeftBackground(Drawable d){
        mLeftBackground = d;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mLeftBtn.setBackground(mLeftBackground);
        }else{
            mLeftBtn.setBackgroundDrawable(mLeftBackground);
        }
    }

    public void setLeftImageSrc(Drawable d){
        mLeftImageSrc = d;
        if(mLeftImageSrc != null){
            mLeftImageSrc.setBounds(0, 0, mLeftImageSrc.getMinimumWidth(), mLeftImageSrc.getMinimumHeight());
        }
        mLeftBtn.setCompoundDrawables(mLeftImageSrc, null, null, null);
    }

    public void setLeftText(String text){
        mLeftText = text;
        mLeftBtn.setText(mLeftText);
    }

    public void setRightTextColor(int color){
        mRightTextColor = color;
        mRightBtn.setTextColor(mRightTextColor);
    }

    public void setRightTextSize(float size){
        mRightTextSize = size;
        mRightBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, mRightTextSize);
    }

    public void setRightBackground(Drawable d){
        mRightBackground = d;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mRightBtn.setBackground(mRightBackground);
        }else{
            mRightBtn.setBackgroundDrawable(mRightBackground);
        }
    }

    public void setRightText(String text){
        mRightText = text;
        mRightBtn.setText(mRightText);
    }

    public void setRightImageSrc(Drawable d){
        mRightImageSrc = d;
        if(d != null){
            d.setBounds(0, 0, mRightImageSrc.getMinimumWidth(), mRightImageSrc.getMinimumHeight());
        }
        mRightBtn.setCompoundDrawables(null, null, mRightImageSrc, null);
    }

    public void setTitleTextColor(int color){
        mTitleTextColor = color;
        mTitle.setTextColor(mTitleTextColor);
    }

    public void setTitleTextSize(float size){
        mTitleTextSize = size;
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleTextSize);
    }

    public void setTitle(String text){
        mTitleText = text;
        mTitle.setText(mTitleText);
    }

    public void setTopbarClickListener(TopbarClickListener listener){
        mTopbarClickListener = listener;
    }

    public interface TopbarClickListener{

        void leftClick();

        void rightClick();
    }
}
