package com.acap.toolkit;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import java.util.Timer;
import java.util.TimerTask;

/**
 * <pre>
 * Tip:
 *      计时器工具
 *
 * Created by ACap on 2020/12/28 17:15
 * </pre>
 */
public class TimerTaskUtils {

    private Timer mTimer;
    private long delay;
    private long period;

    public TimerTaskUtils(Lifecycle lifecycle, long delay, long period) {
        this.delay = delay;
        this.period = period;

        if (lifecycle != null) {
            lifecycle.addObserver(new LifecycleEventObserver() {
                @Override
                public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
                    if (event == Lifecycle.Event.ON_DESTROY) {
                        cancel();
                    }
                }
            });
        }
    }

    /**
     * 开始计时器
     */
    public void start() {
        if (mTimer != null) return;
        mTimer = new Timer();
        mTimer.schedule(mTimerTask, delay, period);
    }

    /**
     * 取消计时器
     */
    public void cancel() {
        if (mTimer == null) return;
        mTimer.cancel();
        mTimer = null;
    }

    private OnTimerSignalListener mOnTimerSignalListener;

    /**
     * 监听计时器信号
     *
     * @param mOnTimerSignalListener
     */
    public void setOnTimerSignalListener(OnTimerSignalListener mOnTimerSignalListener) {
        this.mOnTimerSignalListener = mOnTimerSignalListener;
    }

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mOnTimerSignalListener != null) mOnTimerSignalListener.onSignal();
        }
    };

    private final TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            mRunnable.run();
        }
    };

    /**
     * 计时器信号监听
     */
    public interface OnTimerSignalListener {
        void onSignal();
    }
}
