package com.uxin.testa1;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.uxin.testa1.base.BaseActivity;
import com.uxin.testa1.custom.CustomTextView;
import com.uxin.testa1.custom.TopBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    private static final String TAG = "Main";

    @BindView(R.id.topbar)
    TopBar mTopBar;

    @BindView(R.id.tv)
    TextView mTextView;

    @BindView(R.id.custom_textview)
    CustomTextView mCustomTextView;

    @Override
    protected void beforeBindViews() {

    }

    @Override
    protected void afterBindViews() {
        mTextView.setTextColor(Color.RED);
        mTopBar.setTopbarClickListener(new TopBar.TopbarClickListener() {
            @Override
            public void leftClick() {
                Log.i(TAG, "left button click");
            }

            @Override
            public void rightClick() {
                Log.i(TAG, "right button click");
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

}
