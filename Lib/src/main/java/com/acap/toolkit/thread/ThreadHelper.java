package com.acap.toolkit.thread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.acap.toolkit.phone.CpuUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <pre>
 * Tip:
 *      线程调度工具，保证业务处理在正确的线程下进行
 *
 * Function:
 *
 * Created by ACap on 2018/11/21 13:48
 * </pre>
 */
public class ThreadHelper {
    //全局Handler消息处理器
    private static Handler miHandler;
    private static ExecutorService pIO;
    private static ExecutorService pComputation;

    /**
     * 获得主进程Handler对象
     */
    public static final Handler getMainHandler() {
        if (miHandler == null) {
            synchronized (ThreadHelper.class) {
                if (miHandler == null) {
                    miHandler = new Handler(Looper.getMainLooper()) {
                        @Override
                        public void handleMessage(@NonNull Message msg) {
                            super.handleMessage(msg);
                            if (msg == null) return;
                            if (msg.obj != null && msg.obj instanceof Runnable) {
                                ((Runnable) msg.obj).run();
                            }
                        }
                    };
                }
            }
        }
        return miHandler;
    }

    private static ExecutorService getComputation() {
        if (pComputation == null) {
            synchronized (ThreadHelper.class) {
                if (pComputation == null) {
                    pComputation = Executors.newFixedThreadPool(CpuUtils.getNumCores());
                }
            }
        }
        return pComputation;
    }

    private static ExecutorService getIo() {
        if (pIO == null) {
            synchronized (ThreadHelper.class) {
                if (pIO == null) {
                    pIO = Executors.newCachedThreadPool();
                }
            }
        }
        return pIO;
    }

    /**
     * 在IO线程执行业务逻辑
     *
     * @param runnable The runnable
     */
    public static void io(Runnable runnable) {
        getIo().submit(runnable);
    }

    /**
     * 在CPU密集型线程中执行业务逻辑
     *
     * @param runnable The runnable
     */
    public static void computation(Runnable runnable) {
        getComputation().submit(runnable);
    }

    /**
     * 在主线程执行业务逻辑
     *
     * @param runnable The runnable
     */
    public static void main(Runnable runnable) {
        if (Looper.getMainLooper() == Looper.myLooper()) runnable.run();
        else getMainHandler().post(runnable);
    }

    /**
     * 在主线程延时执行业务逻辑
     *
     * @param runnable    The runnable
     * @param delayMillis 等待时间
     */
    public static void mainDelayed(long delayMillis, Runnable runnable) {
        getMainHandler().postDelayed(runnable, delayMillis);
    }

    /**判断是否为主线程*/
    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

}
