package com.uxin.testa1.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 霓虹灯效果
 * @auth tik
 */
public class CustomTextView extends TextView {
    /**
     * 边框画笔
     */
    Paint mPaint1;
    /**
     * 内容背景画笔
     */
    Paint mPaint2;
    /**
     * 内容文本画笔
     */
    Paint mPaint;
    /**
     * 控件宽度
     */
    int mViewWidth;
    /**
     * 控件高度
     */
    int mViewHeight;
    /**
     * 边框渐变
     */
    LinearGradient mLinearGradient;
    /**
     * 文本渐变
     */
    LinearGradient mLinearGradient2;
    Matrix matrix;
    /**
     * 平移距离
     */
    int mTranslate;
    /**
     * 平移速度
     */
    int mSpeed = 20;
    /**
     * 边框宽度
     */
    int mPadding = 10;

    public CustomTextView(Context context) {
        super(context);
        init();
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint1 = new Paint();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mPaint1.setColor(getResources().getColor(android.R.color.holo_blue_light, null));
        }else{
            mPaint1.setColor(getResources().getColor(android.R.color.holo_blue_light));
        }
        mPaint1.setStyle(Paint.Style.FILL);

        mPaint2 = new Paint();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mPaint2.setColor(getResources().getColor(android.R.color.holo_blue_light, null));
        }else{
            mPaint2.setColor(getResources().getColor(android.R.color.holo_blue_light));

        }
        mPaint2.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("A", "A --> onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec){
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if(specMode == MeasureSpec.EXACTLY){//match_parent or exactly px
            result = specSize;
            Log.i("A", "specMode --> EXACTLY");
        }else{
            result = (int) getPaint().measureText(getText().toString());
            if(specMode == MeasureSpec.AT_MOST){//wrap_content
                result = Math.min(result, specSize);
                Log.i("A", "specMode --> AT_MOST");

            }
        }
        setPadding(mPadding, 0, mPadding, 0);
        Log.i("A", "specSize=" + specSize + ", result=" + result);
        return result;
    }

    private int measureHeight(int measureSpec){
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if(specMode == MeasureSpec.EXACTLY){//match_parent or exactly px
            result = specSize;
            Log.i("A", "specMode --> EXACTLY");
        }else{
            result = getMeasuredHeight();
            if(specMode == MeasureSpec.AT_MOST){//wrap_content
                result = Math.min(result, specSize);
                Log.i("A", "specMode --> AT_MOST");

            }
        }
        Log.i("A", "specSize=" + specSize + ", result=" + result);
        return result;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i("A", "A --> onLayout");
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Log.i("A", "A --> onDraw");
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint1);
        canvas.drawRect(mPadding, mPadding, getMeasuredWidth() - mPadding, getMeasuredHeight() - mPadding, mPaint2);

        super.onDraw(canvas);

        /*
            对空间宽度进行取整，防止抖动
         */
        int viewWidthInt = mViewWidth/mSpeed*mSpeed;
        if(matrix != null){
            mTranslate += 20;
            if(mTranslate > 1 * viewWidthInt){
                mTranslate = 0;
            }
            matrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(matrix);
            mLinearGradient2.setLocalMatrix(matrix);
            postInvalidateDelayed(100);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.i("A", "A --> onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
        if(mViewWidth == 0){
            mViewWidth = getMeasuredWidth();
            if(mViewWidth > 0){
                mPaint = getPaint();
                mLinearGradient = new LinearGradient(
                        0, 0, 30, 30,
                        new int[]{Color.RED, Color.YELLOW, Color.GREEN},
                        null,
                        Shader.TileMode.MIRROR);
                mLinearGradient2 = new LinearGradient(
                        0, 0, 30, 0,
                        new int[]{Color.RED, Color.YELLOW, Color.GREEN},
                        null,
                        Shader.TileMode.CLAMP);
                mPaint.setShader(mLinearGradient2);
                mPaint1.setShader(mLinearGradient);
                matrix = new Matrix();
            }
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.i("A", "A --> onFinishInflate");
    }
}
