package com.acap.toolkit.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.HashMap;
import java.util.Map;

/**
 * 将drawable元素与文本剧中显示,暂时只支持一张图<br>
 * Created by ACap on 2018/6/1.
 */
public class DrawableCenterTextView extends AppCompatTextView {

    public DrawableCenterTextView(Context context) {
        this(context, null);
    }

    public DrawableCenterTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawableCenterTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    public CharSequence getHint() {
        CharSequence hint = super.getHint();
        return hint == null ? "" : hint;
    }

    private float getTextWidth() {
        String text = getText().toString();
        if (text.length() == 0) {
            getPaint().measureText(getHint().toString());
        }
        return getPaint().measureText(text);
    }

    private Drawable[] mDrawables;
    private static final int LEFT = 0;
    private static final int TOP = 1;
    private static final int RIGHT = 2;
    private static final int BOTTOM = 3;
    private static final Map<Integer, Integer> mGravitys = new HashMap<>();

    static {
        mGravitys.put(LEFT, Gravity.CENTER_VERTICAL | Gravity.LEFT);
        mGravitys.put(TOP, Gravity.CENTER_HORIZONTAL | Gravity.TOP);
        mGravitys.put(RIGHT, Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        mGravitys.put(BOTTOM, Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!hasDrawables()) {
            setGravity(Gravity.CENTER);
            super.onDraw(canvas);
        } else {

            Drawable drawable = null;
            int type = -1;
            for (int i = 0; i < mDrawables.length; i++) {
                if (mDrawables[i] != null) {
                    drawable = mDrawables[i];
                    type = i;
                    break;
                }
            }
            if (drawable == null || type == -1) return;


            //Drawable与文件的间距
            int drawablePadding = getCompoundDrawablePadding();
            //文本的大小
            float textWidth = getTextWidth();
            float textHeight = getPaint().getTextSize();

            //Drawable的大小
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();

            //整体的大小
            float size_w = 0;
            float size_h = 0;
            if (type == RIGHT || type == LEFT) {
                size_h = Math.max(textHeight, intrinsicHeight);
                size_w = textWidth + drawablePadding + intrinsicWidth;
            } else {
                size_w = Math.max(textWidth, intrinsicWidth);
                size_h = textHeight + drawablePadding + intrinsicHeight;
            }

            //View的大小
            int width = getWidth() - getPaddingLeft() - getPaddingRight();
            int height = getHeight() - getPaddingTop() - getPaddingBottom();


            float translate_x = 0;
            float translate_y = 0;

            setGravity(mGravitys.get(type));

            switch (type) {
                case LEFT:
                    translate_x = (width - size_w) / 2;
                    break;
                case TOP:
                    translate_y = (height - size_h) / 2;
                    break;
                case RIGHT:
                    translate_x = -(width - size_w) / 2;
                    break;
                case BOTTOM:
                    translate_y = -(height - size_h) / 2;
                    break;
            }

            int scount = canvas.save();
            canvas.translate(translate_x, translate_y);
            super.onDraw(canvas);
            canvas.restoreToCount(scount);
        }
    }

    //是否有Drawable
    private boolean hasDrawables() {
        return mDrawables != null && (mDrawables[LEFT] != null || mDrawables[TOP] != null || mDrawables[RIGHT] != null || mDrawables[BOTTOM] != null);
    }

    @Override
    public void setCompoundDrawables(@Nullable Drawable left, @Nullable Drawable top, @Nullable Drawable right, @Nullable Drawable bottom) {
        super.setCompoundDrawables(left, top, right, bottom);
        mDrawables = getCompoundDrawables();
    }


}
