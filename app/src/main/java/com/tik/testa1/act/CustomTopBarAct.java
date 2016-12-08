package com.tik.testa1.act;

import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tik.testa1.base.BaseActivity;
import com.tik.testa1.custom.CustomRoundBall;
import com.tik.testa1.custom.TopBar;
import com.tik.testa1.R;
import com.tik.testa1.custom.VolumeView;

import butterknife.BindView;

/**
 * @auth tik
 */
public class CustomTopBarAct extends BaseActivity {

    @BindView(R.id.topbar)
    TopBar mTopBar;

    private boolean mFull = false;

    private int mVisible = 0;

    @BindView(R.id.roundball)
    CustomRoundBall ball;

    @BindView(R.id.volumview)
    VolumeView vol;

    @BindView(R.id.layout)
    LinearLayout layout;

    @Override
    protected void beforeBindViews() {

    }

    @Override
    protected void afterBindViews() {
        mTopBar.setTitle("点我");
        mTopBar.setRightText(mFull ? "还原" : "全屏");
//        mTopBar.setRightImageSrc(getResources().getDrawable(R.drawable.info));
        mTopBar.setTopbarClickListener(new TopBar.TopbarClickListener() {
            @Override
            public void leftClick() {
                finish();
            }

            @Override
            public void rightClick() {
                Toast.makeText(CustomTopBarAct.this, "右上角按钮被点击了", Toast.LENGTH_SHORT).show();
                mFull = !mFull;
                full(mFull);
                mTopBar.setRightText(mFull ? "还原" : "全屏");
            }

            @Override
            public void titleClick() {
                mVisible++;
                layout.setVisibility(mVisible%3 == 0 ? View.VISIBLE : View.GONE);
                vol.setVisibility(mVisible%3 == 1 ? View.VISIBLE : View.GONE);
                ball.setVisibility(mVisible%3 == 2 ? View.VISIBLE : View.GONE);


            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_topbar;
    }

    private void full(boolean enable) {
        if (enable) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attr);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
}
