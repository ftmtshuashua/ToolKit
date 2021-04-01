package com.acap.toolkit.view;

import android.graphics.Paint;

/**
 * <pre>
 * Tip:
 *      Canvas画笔
 *
 * Created by ACap on 2021/1/20 17:48
 * </pre>
 */
public class XPaint extends Paint {

    public XPaint() {
        super(Paint.ANTI_ALIAS_FLAG);
    }

    /**
     * 设置画笔样式
     *
     * @param type   参数
     * @param styles 画笔样式
     */
    public void setStyle(int type, PStyle... styles) {
        reset();
        if (styles != null) {
            setAntiAlias(true);
            for (PStyle style : styles) {
                style.onChange(type, this);
            }
        }
    }

    /**
     * 设置画笔样式
     *
     * @param styles
     */
    public void setStyle(PStyle... styles) {
        setStyle(0, styles);
    }

    /**
     * Paint样式
     */
    public interface PStyle {

        void onChange(int type, Paint paint);
    }

}
