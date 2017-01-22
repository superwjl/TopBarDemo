package com.tik.testa1.act;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.tik.testa1.R;
import com.tik.testa1.base.BaseActivity;

import butterknife.BindView;


public class MarqueeViewAct extends BaseActivity {

    @BindView(R.id.vf)
    ViewFlipper vf;

    @BindView(R.id.vf2)
    ViewFlipper vf2;

    @Override
    protected void beforeBindViews() {

    }

    @Override
    protected void afterBindViews() {

        final String[] ads = {
                "广告时间：我是广告111",
                "广告时间：我是广告222",
                "广告时间：我是广告333"
        };
        for (int i = 0; i < ads.length; i++){
            View view = View.inflate(this, R.layout.view_advertisement, null);
            TextView tv = (TextView) view.findViewById(R.id.tv);
            tv.setText(ads[i]);
            vf.addView(view);

            View view2 = View.inflate(this, R.layout.view_advertisement, null);
            TextView tv2 = (TextView) view2.findViewById(R.id.tv);
            tv2.setText(ads[i]);
            vf2.addView(view2);
        }
        vf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("vf1", ""+ads[vf.getDisplayedChild()]);
                Toast.makeText(MarqueeViewAct.this, ""+ads[vf.getDisplayedChild()], Toast.LENGTH_SHORT).show();
            }
        });
        vf2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("vf2", ""+ads[vf2.getDisplayedChild()]);
                Toast.makeText(MarqueeViewAct.this, ""+ads[vf2.getDisplayedChild()], Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_marqueeview;
    }
}
