package com.tik.testa1;

import android.content.Intent;
import android.widget.Button;

import com.tik.testa1.act.CustomTopBarAct;
import com.tik.testa1.act.ScanningAct;
import com.tik.testa1.base.BaseActivity;
import com.uxin.testa1.R;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity{
    private static final String TAG = "Main";

    /**
     * 自定义topbar
     */
    @BindView(R.id.topbar)
    Button mBtnTopBar;

    /**
     * 二维码/条形码扫描
     */
    @BindView(R.id.scanning)
    Button mBtnScanning;

    @OnClick(R.id.topbar)
    void topbar(){
        startActivity(new Intent(getApplicationContext(), CustomTopBarAct.class));
    }

    @OnClick(R.id.scanning)
    void scanning(){
        startActivity(new Intent(getApplicationContext(), ScanningAct.class));
    }


    @Override
    protected void beforeBindViews() {

    }

    @Override
    protected void afterBindViews() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

}
