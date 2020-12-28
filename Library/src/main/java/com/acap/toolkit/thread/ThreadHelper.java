package com.acap.toolkit.thread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.acap.toolkit.phone.CpuUtils;

import java.util.ArrayList;
import java.util.List;
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
    private static List<MainHandlerMessageIntercept> mMainHandlerMessageIntercept;
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
                            if (mMainHandlerMessageIntercept != null) {
                                synchronized (mMainHandlerMessageIntercept) {
                                    if (!mMainHandlerMessageIntercept.isEmpty()) {
                                        int size = mMainHandlerMessageIntercept.size();
                                        for (int i = 0; i < size; i++) {
                                            if (mMainHandlerMessageIntercept.get(i).onIntercept(msg)) {
                                                return;
                                            }
                                        }
                                    }
                                }
                            }
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

    private static final ExecutorService getComputation() {
        if (pComputation == null) {
            synchronized (ThreadHelper.class) {
                if (pComputation == null) {
                    pComputation = Executors.newFixedThreadPool(CpuUtils.getNumCores());
                }
            }
        }
        return pComputation;
    }

    private static final ExecutorService getIo() {
        if (pIO == null) {
            synchronized (ThreadHelper.class) {
                if (pIO == null) {
                    pIO = Executors.newCachedThreadPool();
                }
            }
        }
        return pIO;
    }

    private static final List<MainHandlerMessageIntercept> getMainHandlerMessageIntercept() {
        if (mMainHandlerMessageIntercept == null) {
            synchronized (ThreadHelper.class) {
                if (mMainHandlerMessageIntercept == null) {
                    mMainHandlerMessageIntercept = new ArrayList<>();
                }
            }
        }
        return mMainHandlerMessageIntercept;
    }

    /**
     * 在IO线程执行业务逻辑
     *
     * @param runnable The runnable
     */
    public static final void io(Runnable runnable) {
        getIo().submit(runnable);
    }

    /**
     * 在CPU密集型线程中执行业务逻辑
     *
     * @param runnable The runnable
     */
    public static final void computation(Runnable runnable) {
        getComputation().submit(runnable);
    }

    /**
     * 在主线程执行业务逻辑
     *
     * @param runnable The runnable
     */
    public static final void main(Runnable runnable) {
        if (Looper.getMainLooper() == Looper.myLooper()) runnable.run();
        else getMainHandler().post(runnable);
    }

    /**
     * 在主线程延时执行业务逻辑
     *
     * @param runnable    The runnable
     * @param delayMillis 等待时间
     */
    public static final void mainDelayed(Runnable runnable, long delayMillis) {
        getMainHandler().postDelayed(runnable, delayMillis);
    }

    /**
     * 在主线程延时执行业务逻辑
     *
     * @param wait        消息的Wait
     * @param runnable    The runnable
     * @param delayMillis 等待时间
     */
    public static final void sendMainHandlerMessage(int wait, Runnable runnable, long delayMillis) {
        Message obtain = Message.obtain(getMainHandler(), wait);
        obtain.obj = runnable;
        getMainHandler().sendMessageDelayed(obtain, delayMillis);
    }

    /**
     * 移除在主线程中执行的逻辑
     */
    public static final void removeMainHandlerMessage(int wait) {
        getMainHandler().removeMessages(wait);
    }

    /**
     * 添加全局Handler消息处理
     */
    public static final void addMainHandlerMessageIntercept(MainHandlerMessageIntercept intercept) {
        List<MainHandlerMessageIntercept> mainHandlerMessageIntercept = getMainHandlerMessageIntercept();
        synchronized (mainHandlerMessageIntercept) {
            mainHandlerMessageIntercept.add(intercept);
        }
    }

    /**
     * 移除全局Handler消息处理
     */
    public static final void removeMainHandlerMessageIntercept(MainHandlerMessageIntercept intercept) {
        List<MainHandlerMessageIntercept> mainHandlerMessageIntercept = getMainHandlerMessageIntercept();
        synchronized (mainHandlerMessageIntercept) {
            mainHandlerMessageIntercept.remove(intercept);
        }
    }

    /**
     * 全局Handler消息处理器
     */
    public interface MainHandlerMessageIntercept {
        /**
         * 是否拦截Handler消息
         *
         * @param msg 被处理的消息
         * @return true:拦截，false:不拦截
         */
        boolean onIntercept(Message msg);
    }
}
