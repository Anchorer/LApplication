package org.anchorer.l.c00;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Anchorer/duruixue on 2015/9/28.
 */
public class CustomView extends View implements Runnable {

    private static final String TEXT = "ap爱哥ξτβбпшㄎㄊěǔぬも┰┠№＠↓";

    private Paint mTextPaint, mLinePaint;
    private Paint.FontMetrics mFontMetrics;

    private int mBaseX, mBaseY;
    private int mRadius = 200;

    public CustomView(Context context) {
        super(context);
        initPaint();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(70);
        mTextPaint.setColor(Color.BLACK);
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(1);
        mLinePaint.setColor(Color.RED);

        mFontMetrics = new Paint.FontMetrics();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawCircle(Utils.getScreenWidthPixels((Activity) getContext()) / 2, Utils.getScreenHeightPixels((Activity) getContext()) / 2,
//                mRadius, mPaint);

        mBaseX = (int) (canvas.getWidth() / 2 - mTextPaint.measureText(TEXT) / 2);
        mBaseY = canvas.getHeight() / 2;
//        mBaseY = (int) (canvas.getHeight() / 2 - (mTextPaint.ascent() + mTextPaint.descent()) / 2);

        canvas.drawText(TEXT, mBaseX, mBaseY, mTextPaint);
        canvas.drawLine(0, canvas.getHeight() / 2, canvas.getWidth(), canvas.getHeight() / 2, mLinePaint);
    }

    @Override
    public void run() {
        while (true) {
            if (mRadius <= 200) {
                mRadius += 2;
            } else {
                mRadius = 0;
            }
            postInvalidate();
            try {
                Thread.sleep(8);
            } catch (InterruptedException e) {}
        }
    }
}
