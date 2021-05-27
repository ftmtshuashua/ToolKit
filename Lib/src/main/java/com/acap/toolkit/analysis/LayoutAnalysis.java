package com.acap.toolkit.analysis;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.LayoutInflaterCompat;

import com.acap.toolkit.log.LogUtils;
import com.acap.toolkit.view.RUtils;

/**
 * <pre>
 * Tip:
 *      布局分析器
 *
 * Created by ACap on 2021/5/27 15:51
 * </pre>
 */
public class LayoutAnalysis implements LayoutInflater.Factory2 {

    private String mTag = "耗时View分析";
    private AppCompatActivity mActivity;
    /**
     * 加载时间超过该数值的View将被打印出来,单位MS
     */
    private long mShowTime = 3;

    private boolean mIsRunning = false;

    public LayoutAnalysis(AppCompatActivity mActivity) {
        this.mActivity = mActivity;
    }


    /**
     * View的创建时间超过该值的View才会被打印出来，设置一个合适的时间阀值利于快速的找到耗时的点.
     *
     * @param time 设置时间阀值,单位ms
     */
    public LayoutAnalysis setTime(long time) {
        mShowTime = time;
        return this;
    }


    /**
     * 设置日志的TAG信息
     *
     * @param tag
     * @return
     */
    public LayoutAnalysis setTag(String tag) {
        mTag = tag;
        return this;
    }

    public void start() {
        if (mIsRunning) throw new IllegalStateException("分析器已启动,请勿重复启动");
        mIsRunning = true;
        LayoutInflaterCompat.setFactory2(mActivity.getLayoutInflater(), this);
    }


    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        long start = System.currentTimeMillis();
        View view = mActivity.getDelegate().createView(parent, name, context, attrs);
        long end = System.currentTimeMillis();
        if (view == null) return null;
        long cost = end - start;
        if (cost < mShowTime) return view;
        LogUtils.e_ft(mTag, "{0}({1}) cost {2,number,0}ms", name, RUtils.getNameById(view.getId()), cost);
        return view;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return null;
    }


}
