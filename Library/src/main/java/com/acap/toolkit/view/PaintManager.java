package com.acap.toolkit.view;

import android.graphics.Paint;

import com.acap.toolkit.transform.ArraysUtils;

import java.util.Arrays;

/**
 * 画笔管理
 * <p>
 * <br/>
 * Author:ACap<br/>
 * Time:2020/12/4 16:20
 */
public class PaintManager extends Paint {

    public PaintManager() {
        super(Paint.ANTI_ALIAS_FLAG);

    }

    public Paint getPaint() {
        return this;
    }


    private PaintStyle[] mCurrent; //缓存配置，非必要情况不更新

    /**
     * 设置画笔样式
     *
     * @param styles
     */
    public void setStyle(PaintStyle... styles) {
        if (equals(mCurrent, styles)) return;
        reset();
        if (styles != null) {
            setAntiAlias(true);
            for (PaintStyle style : styles) {
                style.onChange(this);
            }
            mCurrent = styles;
        }
    }

    /**
     * Paint样式
     */
    public interface PaintStyle {

        void onChange(Paint paint);
    }

    //判断两个数据源是否相同
    private boolean equals(PaintStyle[] pss1, PaintStyle[] pss2) {
        return ArraysUtils.equals(pss1, pss2);
    }


}
