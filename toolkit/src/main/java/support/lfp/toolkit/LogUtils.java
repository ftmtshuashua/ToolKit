package support.lfp.toolkit;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import java.lang.reflect.Method;
import java.text.MessageFormat;

/**
 * <pre>
 * Tip:
 *      日志输出工具 - 全局日志控制
 *      API与android.util.Log保持一致，可以使用全局替换掉Log的方法
 *      API说明请参照Log
 *
 * Function:
 *      init()      :初始化日志输出器
 *      setDebug()  :启用和关闭日志输出
 *
 *
 * Created by LiFuPing on 2018/11/7 10:24
 * </pre>
 */
public class LogUtils {
    /**
     * Priority constant for the println method; use Log.v.
     */
    public static final int VERBOSE = Log.VERBOSE;

    /**
     * Priority constant for the println method; use Log.d.
     */
    public static final int DEBUG = Log.DEBUG;

    /**
     * Priority constant for the println method; use Log.i.
     */
    public static final int INFO = Log.INFO;

    /**
     * Priority constant for the println method; use Log.w.
     */
    public static final int WARN = Log.WARN;

    /**
     * Priority constant for the println method; use Log.e.
     */
    public static final int ERROR = Log.ERROR;

    /**
     * Priority constant for the println method.
     */
    public static final int ASSERT = Log.ASSERT;


    private static boolean isDebug = false;
    private static final String TAG = "LogUtils";

    private LogUtils() {
    }

    /**
     * 初始化日志输出器，会根据APK版本自动开启和关闭日志
     * <br>
     * release : open logutils
     * <br>
     * debug : close logutils
     *
     * @param context The context
     */
    public static final void init(Context context) {
        try {
            setDebug((context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0);
        } catch (Exception e) {
            setDebug(false);
        }
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
     * 获得LogUtils当前状态
     */
    public static final boolean isDebug() {
        return isDebug;
    }

    public static boolean isLoggable(String tag, int level) {
        return Log.isLoggable(tag, level);
    }

    public static int wtf(String tag, String msg) {
        return wtf(tag, msg, null);
    }

    public static int wtf(String tag, Throwable tr) {
        return wtf(tag, null, tr);
    }

    public static int wtf(String tag, String msg, Throwable tr) {
        if (!isDebug) return -1;
        return Log.wtf(tag, msg, tr);
    }

    public static void v(String msg) {
        println(VERBOSE, msg);
    }

    public static void v(String tag, String msg) {
        println(VERBOSE, tag, msg);
    }

    public static void v(String tag, String msg, Throwable tr) {
        printlns(VERBOSE, tag, msg, tr);
    }

    public static void d(String msg) {
        println(DEBUG, msg);
    }

    public static void d(String tag, String msg) {
        println(DEBUG, tag, msg);
    }

    public static void d(String tag, String msg, Throwable tr) {
        printlns(DEBUG, tag, msg, tr);
    }

    public static void w(String msg) {
        println(WARN, msg);
    }

    public static void w(String tag, String msg) {
        println(WARN, tag, msg);
    }

    public static void w(String tag, String msg, Throwable tr) {
        printlns(WARN, tag, msg, tr);
    }

    public static void a(String msg) {
        println(ASSERT, msg);
    }

    public static void a(String tag, String msg) {
        println(ASSERT, tag, msg);
    }

    public static void a(String tag, String msg, Throwable tr) {
        printlns(ASSERT, tag, msg, tr);
    }

    public static void i(String msg) {
        println(INFO, msg);
    }

    public static void i(String tag, String msg) {
        println(INFO, tag, msg);
    }

    public static void i(String tag, String msg, Throwable tr) {
        printlns(INFO, tag, msg, tr);
    }

    public static void e(String msg) {
        e(null, msg);
    }

    public static void e(String tag, String msg) {
        e(tag, msg, null);
    }

    public static void e(Throwable throwable) {
        e(null, null, throwable);
    }

    public static void e(String msg, Throwable throwable) {
        e(null, msg, throwable);
    }

    public static void e(String tag, String msg, Throwable throwable) {
        if (throwable != null) {
            String errmsg = getStackTraceString(throwable);
            if (Utils.isEmpty(msg)) println(ERROR, tag, errmsg);
            else println(ERROR, tag, MessageFormat.format("{0}\n{1}", msg, errmsg));
        } else println(ERROR, tag, msg);
    }

    public static void println(int priority, String msg) {
        println(priority, null, msg);
    }

    public static void println(int priority, String tag, String msg) {
        if (!isDebug) return;
        Log.println(
                priority,
                Utils.isEmpty(tag) ? TAG : tag,
                msg);
    }

    public static int printlns(int priority, String tag, String msg, Throwable tr) {
        try {
            Method method = Log.class.getDeclaredMethod("printlns");
            return (int) method.invoke(null, 0, priority, tag, msg, tr);
        } catch (Exception e) {
            e(e);
        }
        return -1;
    }

    public static String getStackTraceString(Throwable tr) {
        return Log.getStackTraceString(tr);
    }
}
