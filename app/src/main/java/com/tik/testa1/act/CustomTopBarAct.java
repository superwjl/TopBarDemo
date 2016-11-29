package com.tik.testa1.act;

import android.widget.Toast;

import com.tik.testa1.base.BaseActivity;
import com.tik.testa1.custom.TopBar;
import com.uxin.testa1.R;

import butterknife.BindView;

/**
 * @auth wangjinlong wangjinlong@xin.com
 * @date 16/11/28 下午4:09
 */
public class CustomTopBarAct extends BaseActivity {

    @BindView(R.id.topbar)
    TopBar mTopBar;



    @Override
    protected void beforeBindViews() {

    }

    @Override
    protected void afterBindViews() {
//        mTopBar.setTitleTextSize(16);
//        mTopBar.setRightImageSrc(getResources().getDrawable(R.drawable.info));
        mTopBar.setTopbarClickListener(new TopBar.TopbarClickListener() {
            @Override
            public void leftClick() {
                finish();
            }

            @Override
            public void rightClick() {
                Toast.makeText(CustomTopBarAct.this, "右上角按钮被点击了", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_topbar;
    }
}
