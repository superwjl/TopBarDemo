package com.tik.testa1;

import android.content.Intent;
import android.widget.Button;

import com.tik.testa1.act.CustomTopBarAct;
import com.tik.testa1.act.FocusListViewAct;
import com.tik.testa1.act.MarqueeViewAct;
import com.tik.testa1.act.MyScrollViewAct;
import com.tik.testa1.act.QRCodeAct;
import com.tik.testa1.act.ScanningAct;
import com.tik.testa1.act.ScrollHideListViewAct;
import com.tik.testa1.base.BaseActivity;
import com.tik.testa1.R;
import com.tik.testa1.custom.MyScrollView;
import com.tik.testa1.test.TestAct;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity{
    private static final String TAG = "Main";

    @BindView(R.id.myscrollview)
    Button myScrollView;

    @OnClick(R.id.topbar)
    void topbar(){
        startActivity(new Intent(getApplicationContext(), CustomTopBarAct.class));
    }

    @OnClick(R.id.scanning)
    void scanning(){
        startActivity(new Intent(getApplicationContext(), ScanningAct.class));
    }

    @OnClick(R.id.qrcode)
    void qrcode(){
        startActivity(new Intent(getApplicationContext(), QRCodeAct.class));
    }

    @OnClick(R.id.myscrollview)
    void myscrollview(){
        toActivity(MyScrollViewAct.class);
    }

    @OnClick(R.id.event)
    void eventPass(){
        toActivity(TestAct.class);
    }

    @OnClick(R.id.scrollhide)
    void scrollHide(){
        toActivity(ScrollHideListViewAct.class);
    }

    @OnClick(R.id.focuslistview)
    void focuslistview(){
        toActivity(FocusListViewAct.class);
    }

    @OnClick(R.id.marqueeview)
    void marqueeview(){
        toActivity(MarqueeViewAct.class);
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

    private void toActivity(Class<?> cls){
        startActivity(new Intent(getApplicationContext(), cls));
    }
}
