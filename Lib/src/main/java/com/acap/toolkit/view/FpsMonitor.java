package com.acap.toolkit.view;


import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Choreographer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <pre>
 * Tip:
 *      Fps 监听
 *
 * Created by A·Cap on 2021/10/15 18:32
 * </pre>
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class FpsMonitor {

    /**
     * 当前手机性能
     */
    public enum Performance {
        /**
         * 高性能
         */
        HIGH,
        /**
         * 低性能
         */
        Low
    }

    private static FpsMonitor mInstance;

    private static final long FPS_INTERVAL_TIME = 1000L;
    //用于统计一段事件内屏幕刷新的次数
    private int count = 0;
    //单位时间内的刷新次数
    private int unit_count;
    //防止重复开启
    private boolean isFpsOpen = false;

    private Signal mSignal;
    private Handler mHandler;
    private List<OnFpsChangeListener> mFpsChangeListener;

    public FpsMonitor() {
        mSignal = new Signal();
        mHandler = new SignalHandler();

        mFpsChangeListener = new CopyOnWriteArrayList<>();
    }

    public void registerOnFpsChangeListener(OnFpsChangeListener listener) {
        mFpsChangeListener.add(listener);
    }

    public void unregisterOnFpsChangeListener(OnFpsChangeListener listener) {
        mFpsChangeListener.remove(listener);
    }


    public static FpsMonitor getInstance() {
        if (mInstance == null) {
            synchronized (FpsMonitor.class) {
                if (mInstance == null) {
                    mInstance = new FpsMonitor();
                }
            }
        }
        return mInstance;
    }

    /**
     * 启动 Fps 监听器
     */
    public synchronized void start() {
        if (!isFpsOpen) {
            isFpsOpen = true;
            sendSignal();
            monitorSignal();
        }
    }

    /**
     * 停止 Fps 监听器
     */
    public synchronized void stop() {
        if (isFpsOpen) {
            isFpsOpen = false;
            mHandler.removeCallbacks(mSignal);
            Choreographer.getInstance().removeFrameCallback(mSignal);
        }
    }

    //等待一秒之后发送检测信号
    private void sendSignal() {
        if (isFpsOpen) {
            mHandler.postDelayed(mSignal, FPS_INTERVAL_TIME);
        }
    }

    //监听Fps刷新信号
    private void monitorSignal() {
        if (isFpsOpen) {
            Choreographer.getInstance().postFrameCallback(mSignal);
        }
    }

    private class Signal implements Choreographer.FrameCallback, Runnable {

        @Override
        public void doFrame(long frameTimeNanos) {
            count++;
            monitorSignal();
        }

        @Override
        public void run() {
            unit_count = count;
            count = 0;
            sendSignal();
        }
    }

    private class SignalHandler extends Handler {
        public SignalHandler() {
            super(Looper.myLooper());
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            int unit_count = FpsMonitor.this.unit_count;

            if (unit_count >= 55) {
//                HIGH
            } else {
//                  Low
            }


            for (int i = 0; i < mFpsChangeListener.size(); i++) {
                OnFpsChangeListener onFpsChangeListener = mFpsChangeListener.get(i);
                if (onFpsChangeListener != null) {
                    onFpsChangeListener.onChange(unit_count);
                }
            }
        }
    }

    /**
     * Fps 变化监听器
     */
    public interface OnFpsChangeListener {
        void onChange(int fps);
    }

}
