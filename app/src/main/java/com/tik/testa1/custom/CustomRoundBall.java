package com.tik.testa1.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * @auth tik
 */
public class CustomRoundBall extends View {

    private float mCircleXY;
    private float mRadius;
    private RectF mArcRectF;
    private int mScreenWidth;
    private Paint mCirclePaint;
    private Paint mArcPaint;
    private Paint mTextPaint;
    /**
     * 百分比
     */
    private int mSweepValue = 100;
    /**
     * 角度
     */
    private float mSweepAngle;
    private String mContent = "hello,tik!";
    private Paint mRectPaint;

    public CustomRoundBall(Context context) {
        super(context);
        init();
    }

    public CustomRoundBall(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomRoundBall(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setSweepValue(int value){
        mSweepValue = value;
        this.invalidate();
    }

    private void init(){
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        mScreenWidth = metrics.widthPixels;
        mCircleXY = mScreenWidth / 2;
        mRadius = mScreenWidth * 0.25f;
        mArcRectF = new RectF(mScreenWidth * 0.1f, mScreenWidth * 0.1f, mScreenWidth * 0.9f, mScreenWidth * 0.9f);

        mCirclePaint = new Paint();
        mCirclePaint.setColor(getResources().getColor(android.R.color.holo_blue_light));
        mCirclePaint.setAntiAlias(true);

        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setColor(getResources().getColor(android.R.color.holo_blue_light));
        mArcPaint.setStrokeWidth(mScreenWidth * 0.1f);
        mArcPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.RED);
        mTextPaint.setTextSize(30f);

        mSweepAngle = (mSweepValue / 100f) * 360f;

        mRectPaint = new Paint();
        mRectPaint.setColor(Color.GREEN);
        mRectPaint.setStrokeWidth(2f);
        mRectPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制圆
        canvas.drawCircle(mCircleXY, mCircleXY, mRadius, mCirclePaint);
//        canvas.drawRect(mCircleXY - mRadius, mCircleXY - mRadius, mCircleXY + mRadius, mCircleXY + mRadius, mRectPaint);
//        canvas.drawLine(mCircleXY - mRadius, mCircleXY, mCircleXY + mRadius, mCircleXY, mRectPaint);
//        canvas.drawLine(mCircleXY, mCircleXY - mRadius, mCircleXY, mCircleXY + mRadius, mRectPaint);
        //绘制弧
        canvas.drawArc(mArcRectF, 0, mSweepAngle, false, mArcPaint);
        float textW = mTextPaint.measureText(mContent);
        //绘制文字
        canvas.drawText(mContent, 0, mContent.length(), mCircleXY - textW/2, mCircleXY + 30/4f, mTextPaint);
        postInvalidateDelayed(100);
    }
}
