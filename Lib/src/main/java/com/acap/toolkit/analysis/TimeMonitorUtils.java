package com.acap.toolkit.analysis;

import com.acap.toolkit.log.LogUtils;

import java.text.MessageFormat;


/**
 * 耗时监控工具
 * <p>
 * 启动Activity检查：adb shell am start -W [PackageName]/**.**.DemoActivity
 * <p>
 * Created by ACap on 2020/12/28.
 * </pre>
 */
public class TimeMonitorUtils {
    private String mTag;

    private long mTime;

    private TimeMonitorUtils(String tag) {
        mTag = tag;
        setStartPoint();
    }

    /**
     * 查看运行一段逻辑所需要的时间
     *
     * @param tag      日志TAG
     * @param runnable 运行逻辑
     */
    public static final void run(String tag, Runnable runnable) {
        long start = System.currentTimeMillis();
        if (runnable != null) runnable.run();
        log(start, tag);
    }

    /**
     * 设置监控的开始点
     */
    public void setStartPoint() {
        mTime = System.currentTimeMillis();
    }

    /**
     * 查看耗时
     */
    public void look() {
        look(null);
    }

    /**
     * 查看耗时
     *
     * @param tag 日志TAG
     */
    public void look(String tag) {
        if (tag == null) {
            log(mTime, mTag);
        } else {
            log(mTime, MessageFormat.format("{0}-{1}", mTag, tag));
        }
    }


    //显示日志
    private static final void log(long startTime, String msg) {
        LogUtils.i_ft("耗时检查", "{0} -> {1,number,0}ms", msg == null ? "" : msg, System.currentTimeMillis() - startTime);
    }


}
