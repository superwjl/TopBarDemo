package com.tik.testa1.act;

import android.animation.ObjectAnimator;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tik.testa1.R;
import com.tik.testa1.base.BaseActivity;

import butterknife.BindView;

public class ScrollHideListViewAct extends BaseActivity {

    @BindView(R.id.listview)
    ListView listview;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private int mTouchSlop;
    private float mFirstY;
    private float mCurrentY;
    private int direction;
    private boolean mShow = true;
    private ObjectAnimator mAnimator;
    private int mOffset;

    View.OnTouchListener myTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mFirstY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    mCurrentY = event.getY();
                    if (mCurrentY - mFirstY > mTouchSlop) {
                        direction = 0;// down
                    } else if (mFirstY - mCurrentY > mTouchSlop) {
                        direction = 1;// up
                    }
                    if (direction == 1) {
                        if (mShow) {
                            toolbarAnim(1);//hide
                            mShow = !mShow;
                        }
                    } else if (direction == 0) {
                        if (!mShow) {
                            toolbarAnim(0);//show
                            mShow = !mShow;
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }
            return false;
        }
    };

    @Override
    protected void beforeBindViews() {

    }

    @Override
    protected void afterBindViews() {
        mTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();

        String[] data = new String[20];
        for (int i = 0; i < data.length; i++){
            data[i] = "Item "+i;
        }

        mOffset = getResources().getDimensionPixelOffset(android.support.v7.appcompat.R.dimen.abc_action_bar_default_height_material);
        View header = new View(this);
        header.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                getResources().getDimensionPixelOffset(android.support.v7.appcompat.R.dimen.abc_action_bar_default_height_material)));

        listview.addHeaderView(header);
        listview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, data));
        listview.setOnTouchListener(myTouchListener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scrollhidelistview;
    }

    private void toolbarAnim(int flag) {
        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.cancel();
        }
        if (flag == 0) {
            mAnimator = ObjectAnimator.ofFloat(mToolbar,
                    "translationY", mToolbar.getTranslationY(), 0);
        } else {
            mAnimator = ObjectAnimator.ofFloat(mToolbar,
                    "translationY", mToolbar.getTranslationY(),
                    -mToolbar.getHeight());
        }
        mAnimator.start();
    }
}
