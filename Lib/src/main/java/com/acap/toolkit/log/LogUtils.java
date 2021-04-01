package com.acap.toolkit.log;

import android.util.Log;

import com.acap.toolkit.Utils;

import java.text.MessageFormat;

/**
 * <pre>
 * Tip:
 *      日志输出工具 - 全局日志控制
 *      正式包中自动移除日志相关代码
 *
 * Function:
 *      setDebug()  :启用和关闭日志输出
 *
 *
 * Created by ACap on 2018/11/7 10:24
 * </pre>
 */
public class LogUtils {
    public static final int VERBOSE = Log.VERBOSE;//Level 2
    public static final int DEBUG = Log.DEBUG;//Level 3
    public static final int INFO = Log.INFO;//Level 4
    public static final int WARN = Log.WARN;//Level 5
    public static final int ERROR = Log.ERROR;//Level 6
    public static final int ASSERT = Log.ASSERT; //Level 7

    private static boolean isDebug = false;
    private static final String TAG = "LGU";

    private LogUtils() {
    }

    /**
     * 设置是否启用日志输出
     *
     * @param debug The true or false
     */
    public static final void setDebug(boolean debug) {
        isDebug = debug;
    }

    /**
     * 打印长文本日志
     */
    public static void l(String msg) {
        l(null, msg);
    }

    /**
     * 打印长文本日志
     */
    public static void l(String tag, String msg) {
        if (!isDebug) return;
        int split = 3072;
        while (msg.length() > split) {
            println(INFO, tag, msg.substring(0, split));
            msg = msg.substring(split);
        }
    }

    /**
     * 打印长文本日志
     * 通过 {@link MessageFormat#format(String, Object...)} 拼接日志字符串
     */
    public static void fl(String pattern, Object... arguments) {
        if (!isDebug) return;
        l(MessageFormat.format(pattern, arguments));
    }

    /**
     * 打印长文本日志
     * 通过 {@link MessageFormat#format(String, Object...)} 拼接日志字符串
     */
    public static void flt(String tag, String pattern, Object... arguments) {
        if (!isDebug) return;
        l(MessageFormat.format(pattern, arguments));
    }


    public static void v(String msg) {
        println(VERBOSE, msg);
    }

    public static void v(String tag, String msg) {
        println(VERBOSE, tag, msg);
    }

    /**
     * 通过 {@link MessageFormat#format(String, Object...)} 拼接日志字符串
     */
    public static void fv(String pattern, Object... arguments) {
        if (!isDebug) return;
        v(MessageFormat.format(pattern, arguments));
    }

    /**
     * 通过 {@link MessageFormat#format(String, Object...)} 拼接日志字符串
     */
    public static void fvt(String tag, String pattern, Object... arguments) {
        if (!isDebug) return;
        v(tag, MessageFormat.format(pattern, arguments));
    }

    public static void d(String msg) {
        println(DEBUG, msg);
    }

    public static void d(String tag, String msg) {
        println(DEBUG, tag, msg);
    }


    /**
     * 通过 {@link MessageFormat#format(String, Object...)} 拼接日志字符串
     */
    public static void fd(String pattern, Object... arguments) {
        if (!isDebug) return;
        d(MessageFormat.format(pattern, arguments));
    }

    /**
     * 通过 {@link MessageFormat#format(String, Object...)} 拼接日志字符串
     */
    public static void fdt(String tag, String pattern, Object... arguments) {
        if (!isDebug) return;
        d(tag, MessageFormat.format(pattern, arguments));
    }

    public static void w(String msg) {
        println(WARN, msg);
    }

    public static void w(String tag, String msg) {
        println(WARN, tag, msg);
    }

    /**
     * 通过 {@link MessageFormat#format(String, Object...)} 拼接日志字符串
     */
    public static void fw(String pattern, Object... arguments) {
        if (!isDebug) return;
        w(MessageFormat.format(pattern, arguments));
    }

    /**
     * 通过 {@link MessageFormat#format(String, Object...)} 拼接日志字符串
     */
    public static void fwt(String tag, String pattern, Object... arguments) {
        if (!isDebug) return;
        w(tag, MessageFormat.format(pattern, arguments));
    }

    public static void a(String msg) {
        println(ASSERT, msg);
    }

    public static void a(String tag, String msg) {
        println(ASSERT, tag, msg);
    }

    /**
     * 通过 {@link MessageFormat#format(String, Object...)} 拼接日志字符串
     */
    public static void fa(String pattern, Object... arguments) {
        if (!isDebug) return;
        a(MessageFormat.format(pattern, arguments));
    }

    /**
     * 通过 {@link MessageFormat#format(String, Object...)} 拼接日志字符串
     */
    public static void fat(String tag, String pattern, Object... arguments) {
        if (!isDebug) return;
        a(tag, MessageFormat.format(pattern, arguments));
    }

    public static void i(String msg) {
        println(INFO, msg);
    }

    public static void i(String tag, String msg) {
        println(INFO, tag, msg);
    }

    /**
     * 通过 {@link MessageFormat#format(String, Object...)} 拼接日志字符串
     */
    public static void fi(String pattern, Object... arguments) {
        if (!isDebug) return;
        i(MessageFormat.format(pattern, arguments));
    }

    /**
     * 通过 {@link MessageFormat#format(String, Object...)} 拼接日志字符串
     */
    public static void fit(String tag, String pattern, Object... arguments) {
        if (!isDebug) return;
        i(tag, MessageFormat.format(pattern, arguments));
    }

    public static void e(String msg) {
        e(null, msg);
    }

    public static void e(String tag, String msg) {
        e(tag, msg, null);
    }

    /**
     * 通过 {@link MessageFormat#format(String, Object...)} 拼接日志字符串
     */
    public static void fe(String pattern, Object... arguments) {
        if (!isDebug) return;
        e(MessageFormat.format(pattern, arguments));
    }

    /**
     * 通过 {@link MessageFormat#format(String, Object...)} 拼接日志字符串
     */
    public static void fet(String tag, String pattern, Object... arguments) {
        if (!isDebug) return;
        e(tag, MessageFormat.format(pattern, arguments));
    }


    public static void e(Throwable throwable) {
        e(null, null, throwable);
    }

    public static void e(String msg, Throwable throwable) {
        e(null, msg, throwable);
    }

    public static void e(String tag, String msg, Throwable throwable) {
        if (!isDebug) return;
        if (throwable != null) {
            String errmsg = getStackTraceString(throwable);
            if (Utils.isEmpty(msg)) println(ERROR, tag, errmsg);
            else println(ERROR, tag, MessageFormat.format("{0}\n{1}", msg, errmsg));
        } else println(ERROR, tag, msg);
    }


    private static void println(int priority, String msg) {
        if (!isDebug) return;
        println(priority, null, msg);
    }

    private static void println(int priority, String tag, String msg) {
        if (!isDebug) return;
        Log.println(priority, Utils.isEmpty(tag) ? TAG : MessageFormat.format("{0}-{1}", TAG, tag), msg);
    }

    /**
     * 获得异常的栈信息
     */
    public static String getStackTraceString(Throwable tr) {
        return Log.getStackTraceString(tr);
    }
}
