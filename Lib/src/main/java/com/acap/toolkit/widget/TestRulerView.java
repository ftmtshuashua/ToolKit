package com.acap.toolkit.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.acap.toolkit.phone.ScreenUtils;

/**
 * 用于统计View大小的尺
 */
public class TestRulerView extends View {
    public TestRulerView(Context context) {
        super(context);
        init();
    }

    public TestRulerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestRulerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private int Scale_20 = ScreenUtils.dip2px(20);
    private int Scale_10 = ScreenUtils.dip2px(10);
    private int Scale_5 = ScreenUtils.dip2px(5);
    private int Scale_100 = ScreenUtils.dip2px(100);

    private int Length_10 = ScreenUtils.dip2px(6);
    private int Length_5 = ScreenUtils.dip2px(3);


    private Paint mPaint_Black = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaint_Red = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaint_dotted = new Paint(Paint.ANTI_ALIAS_FLAG); //虚线

    private void init() {
        mPaint_Black.setColor(Color.BLACK);
        mPaint_Black.setStrokeWidth(2);
        mPaint_Red.setColor(Color.RED);
        mPaint_Red.setStrokeWidth(3);

        mPaint_dotted.setStrokeWidth(1);
        mPaint_dotted.setColor(Color.BLACK);
        mPaint_dotted.setAlpha((int) (255 * 0.2));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);

        drawLine_Width(canvas, Scale_20, getHeight(), mPaint_dotted);
        drawLine_Height(canvas, Scale_20, getWidth(), mPaint_dotted);

        drawLine_Width(canvas, Scale_5, Length_5, mPaint_Black);
        drawLine_Width(canvas, Scale_10, Length_10, mPaint_Black);

        drawLine_Height(canvas, Scale_5, Length_5, mPaint_Black);
        drawLine_Height(canvas, Scale_10, Length_10, mPaint_Black);

        drawLine_Width(canvas, Scale_100, Length_10, mPaint_Red);
        drawLine_Height(canvas, Scale_100, Length_10, mPaint_Red);
    }

    private void drawLine_Width(Canvas canvas, int scale, int length, Paint paint) {
        int value = 0;
        do {
            canvas.drawLine(value, 0, value, length, paint);
            value += scale;
        } while (value <= getWidth());
    }

    private void drawLine_Height(Canvas canvas, int scale, int length, Paint paint) {
        int value = 0;
        do {
            canvas.drawLine(0, value, length, value, paint);
            value += scale;
        } while (value <= getHeight());
    }


}
