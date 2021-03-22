package com.acap.toolkit.app;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.acap.toolkit.log.LogUtils;

import java.text.MessageFormat;

/**
 * <pre>
 * Tip:
 *      全局异常拦截器
 *
 * Created by ACap on 2021/3/22 17:35
 * </pre>
 */
public final class GlobalCrashIntercept {

    private static GlobalCrashIntercept mInstance;
    private Handler mHandler;
    private OnCastGlobalCrashListener mOnDiscoverGlobalCrashListener;
    private boolean mIsStart = false;

    private GlobalCrashIntercept() {
        this.mHandler = new Handler(Looper.myLooper());
    }

    public static final void init() {
        init(null);
    }

    public static final void init(OnCastGlobalCrashListener listener) {
        if (mInstance == null) {
            synchronized (GlobalCrashIntercept.class) {
                if (mInstance == null) {
                    mInstance = new GlobalCrashIntercept();
                }
            }
        }

        mInstance.setOnCastGlobalCrashListener(listener);
        mInstance.start();
    }

    /**
     * 设置全局异常监听器
     */
    public GlobalCrashIntercept setOnCastGlobalCrashListener(OnCastGlobalCrashListener l) {
        mOnDiscoverGlobalCrashListener = l;
        return this;
    }

    /**
     * 全局异常监听器
     */
    public interface OnCastGlobalCrashListener {
        void handler(Thread thread, Throwable throwable);
    }

    //当异常抛出时
    private void onCastCrash(Thread t, Throwable e) {
        LogUtils.e("CastCrash", MessageFormat.format("at {0} thread", (t == null ? "Null" : t.getName())), e);
        if (mOnDiscoverGlobalCrashListener != null) {
            mOnDiscoverGlobalCrashListener.handler(t, e);
        }
    }

    /**
     * 启动全局异常捕获器
     */
    public void start() {
        if (mIsStart) return;
        mHandler.post(new LoopRunnable());
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());         //所有线程异常拦截，由于主线程的异常都被我们catch住了，所以下面的代码拦截到的都是子线程的异常
        mIsStart = true;
    }

    private class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
            onCastCrash(t, e);
        }
    }

    private class LoopRunnable implements Runnable {

        @Override
        public void run() {
            for (; ; ) {
                try {
                    Looper.loop();
                } catch (Throwable e) {
                    onCastCrash(Looper.getMainLooper().getThread(), e);
                }
            }
        }
    }

}