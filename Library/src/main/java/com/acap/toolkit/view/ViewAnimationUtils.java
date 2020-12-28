package com.acap.toolkit.view;

import android.view.View;
import android.view.animation.Interpolator;

import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;

/**
 * 用于实现一些简单的View动画
 *
 * <p>
 * Created by ACap on 2020/12/28 16:40
 * </pre>
 */
public class ViewAnimationUtils {
    private long duration = 1000;
    private AnimationProgressValue mValue;
    private long offset;

    public static final int RESTART = 1;   /* 重新开始 */

    public static final int REVERSE = 2;  /* 反转 */

    private int mRepeatMode = RESTART;

    public ViewAnimationUtils() {

    }

    /**
     * 设置动画的偏移时间
     *
     * @param offset 单位ms
     */
    public void setOffset(long offset) {
        this.offset = offset;
    }

    /**
     * 设置执行一次动画的时间
     */
    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getDuration() {
        return duration;
    }

    public void setFloat(float start, float end) {
        mValue = new FloatProgress(start, end);
    }

    public void setInt(int start, int end) {
        mValue = new IntProgress(start, end);
    }

    /**
     * 设置重新开始的模式 {@link ViewAnimationUtils#RESTART} 或 {@link ViewAnimationUtils#REVERSE}
     */
    public void setRepeatMode(int mRepeatMode) {
        this.mRepeatMode = mRepeatMode;
    }

    //内部用于判断是否转向
    private boolean _reverse = false;
    private float _first_paogress;

    /**
     * 获得当前进度 (0f ~ 1f)
     */
    public float getProgress() {
        float values = ((System.currentTimeMillis() + offset) % duration) / (float) duration;  // 0f ~ 1f
        if (mRepeatMode == REVERSE) {   //反转
            if (_first_paogress > values) {
                _reverse = !_reverse;
            }
            _first_paogress = values;
            if (_reverse) {
                values = 1F - values;
            }
        }
        return values;
    }

    public Object getValue() {
        if (mValue == null) mValue = new FloatProgress(0f, 1f);
        float progress = getProgress();
        if (mInterpolator != null) {
            return mValue.get(mInterpolator.getInterpolation(progress));
        }
        return mValue.get(progress);
    }

    public void invalidate(View view) {
        ViewCompat.postInvalidateOnAnimation(view);
    }


    private interface AnimationProgressValue<T> {
        T get(float progress);
    }

    private static final class FloatProgress implements AnimationProgressValue<Float> {
        float start;
        float end;

        public FloatProgress(float start, float end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public Float get(float progress) {
            return (end - start) * MathUtils.clamp(progress, 0f, 1f) + start;
        }

    }

    private static final class IntProgress implements AnimationProgressValue<Integer> {
        int start;
        int end;

        public IntProgress(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public Integer get(float progress) {
            float clamp = MathUtils.clamp(progress, 0f, 1f);

            float v = (end - start) * clamp + start;
            return (int) v;
        }

    }


    Interpolator mInterpolator;

    /**
     * 插值器配置
     */
    public void setInterpolator(Interpolator i) {
        mInterpolator = i;
    }
}
