package com.tik.testa1.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.Toast;

import com.tik.testa1.R;

/**
 * @auth wangjinlong wangjinlong@xin.com
 * @date 16/12/1 下午2:56
 */
public class MyScrollView extends ViewGroup {

    private int mScreenHeight;
    private Scroller mScroller;
    private int mLastY;
    private int mStart;
    private int mEnd;

    public MyScrollView(Context context) {
        super(context);
        initView(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        mScreenHeight = metrics.heightPixels;
        mScroller = new Scroller(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        MarginLayoutParams params = (MarginLayoutParams) getLayoutParams();
        params.height = mScreenHeight * childCount;
        setLayoutParams(params);
        for (int i = 0; i < childCount; i++){
            View child = getChildAt(i);
            if(child.getVisibility() != View.GONE){
                child.layout(l, i * mScreenHeight, r, (i + 1) * mScreenHeight);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        for (int i = 0; i < count; i++){
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                mStart = getScrollY();
                Toast.makeText(getContext(), "getY="+y+"\ngetRawY="+event.getRawY()+"\ngetScrollY="+mStart+"\ngetHeight="+getHeight(), Toast.LENGTH_SHORT).show();
                break;
            case MotionEvent.ACTION_MOVE:
                if(!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                int dy = mLastY - y;
                if(getScrollY() < 0){
                    dy = 0;
                }
                if(getScrollY() > getHeight() - mScreenHeight){
                    dy = 0;
                }
                scrollBy(0, dy);
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                int dscrollY = checkAlignment();
                if(dscrollY > 0){
                    if(dscrollY < mScreenHeight/3){
                        mScroller.startScroll(0, getScrollY(), 0, -dscrollY);
                    }else{
                        mScroller.startScroll(0, getScrollY(), 0, mScreenHeight - dscrollY);
                    }
                }else{
                    if(-dscrollY < mScreenHeight/3){
                        mScroller.startScroll(0, getScrollY(), 0, -dscrollY);
                    }else{
                        mScroller.startScroll(0, getScrollY(), 0, -mScreenHeight - dscrollY);
                    }
                }
                break;
        }
        postInvalidate();
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()){
            scrollTo(0, mScroller.getCurrY());
            postInvalidate();
        }
    }

    private int checkAlignment() {
        int mEnd = getScrollY();
        boolean isUp = ((mEnd - mStart) > 0) ? true : false;
        int lastPrev = mEnd % mScreenHeight;
        int lastNext = mScreenHeight - lastPrev;
        if (isUp) {
            //向上的
            return lastPrev;
        } else {
            return -lastNext;
        }
    }
}
