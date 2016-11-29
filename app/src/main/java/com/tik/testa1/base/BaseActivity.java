package com.tik.testa1.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * @auth tik
 * @date 16/11/23 下午2:26
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected abstract void beforeBindViews();

    protected abstract void afterBindViews();

    protected abstract int getLayoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeBindViews();
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        afterBindViews();
    }
}
