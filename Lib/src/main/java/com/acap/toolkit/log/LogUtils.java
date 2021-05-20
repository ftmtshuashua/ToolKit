package com.acap.toolkit.log;

import android.util.Log;

import com.acap.toolkit.Utils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Tip:
 *      日志输出工具 - 全局日志控制
 *      正式包中自动移除日志相关代码
 *
 *      使用 [_f] 后缀方式拼接日志字符串
 *
 * Function:
 *      setDebug()  :启用和关闭日志输出
 *
 *
 * Created by ACap on 2018/11/7 10:24
 * </pre>
 */
public class LogUtils {
    /**
     * 不需要设置Debug模式,日志将会在发布Release包时候自动从代码中移除
     */
    private static boolean isDebug = true;
    private static final String TAG = "LGU";
    /**
     * 是否弃用输出日志栈信息
     */
    private static boolean isLogStackEnable = false;

    public static final int VERBOSE = Log.VERBOSE;  //Level 2
    public static final int DEBUG = Log.DEBUG;      //Level 3
    public static final int INFO = Log.INFO;        //Level 4
    public static final int WARN = Log.WARN;        //Level 5
    public static final int ERROR = Log.ERROR;      //Level 6
    public static final int ASSERT = Log.ASSERT;    //Level 7


    private LogUtils() {
    }

    /**
     * 设置是否显示日志输出，当发布Release包的时候日志相关代码将会自动从源码中移除
     *
     * @param debug The true or false
     */
    public static final void setDebug(boolean debug) {
        isDebug = debug;
    }

    /**
     * 设置日志栈是否启用
     */
    public static final void setLogStackEnable(boolean enable) {
        isLogStackEnable = enable;
    }

    /**
     * 打印长文本日志，由于AS对日志长度有限制，所以想要显示更多的字符需要使用此方法
     */
    public static void l(String msg) {
        l(null, msg);
    }

    /**
     * 打印长文本日志
     */
    public static void l(String tag, String msg) {
        if (!isDebug) return;
        int lever = INFO;
        int split = 3072;
        //组装
        List<String> array = new ArrayList<>();
        while (msg.length() > split) {
            array.add(msg.substring(0, split));
            msg = msg.substring(split);
        }
        //输出
        List<String> lumpFormat = getLogLumpFormat(array);
        for (String s : lumpFormat) {
            println(lever, tag, s);
        }
    }

    /**
     * 打印长文本日志
     * 通过 {@link MessageFormat#format(String, Object...)} 拼接日志字符串
     *
     * @param pattern   the pattern string
     * @param arguments object(s) to format
     */
    public static void l_f(String pattern, Object... arguments) {
        if (!isDebug) return;
        l(MessageFormat.format(pattern, arguments));
    }

    /**
     * 打印长文本日志
     * 通过 {@link MessageFormat#format(String, Object...)} 拼接日志字符串
     *
     * @param pattern   the pattern string
     * @param arguments object(s) to format
     */
    public static void l_ft(String tag, String pattern, Object... arguments) {
        if (!isDebug) return;
        l(tag, MessageFormat.format(pattern, arguments));
    }


    public static void v(String msg) {
        println(VERBOSE, msg);
    }

    public static void v(String tag, String msg) {
        println(VERBOSE, tag, msg);
    }

    /**
     * 通过 {@link MessageFormat#format(String, Object...)} 拼接日志字符串
     *
     * @param pattern   the pattern string
     * @param arguments object(s) to format
     */
    public static void v_f(String pattern, Object... arguments) {
        if (!isDebug) return;
        v(MessageFormat.format(pattern, arguments));
    }

    /**
     * 通过 {@link MessageFormat#format(String, Object...)} 拼接日志字符串
     *
     * @param pattern   the pattern string
     * @param arguments object(s) to format
     */
    public static void v_ft(String tag, String pattern, Object... arguments) {
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
     *
     * @param pattern   the pattern string
     * @param arguments object(s) to format
     */
    public static void d_f(String pattern, Object... arguments) {
        if (!isDebug) return;
        d(MessageFormat.format(pattern, arguments));
    }

    /**
     * 通过 {@link MessageFormat#format(String, Object...)} 拼接日志字符串
     *
     * @param pattern   the pattern string
     * @param arguments object(s) to format
     */
    public static void d_ft(String tag, String pattern, Object... arguments) {
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
     *
     * @param pattern   the pattern string
     * @param arguments object(s) to format
     */
    public static void w_f(String pattern, Object... arguments) {
        if (!isDebug) return;
        w(MessageFormat.format(pattern, arguments));
    }

    /**
     * 通过 {@link MessageFormat#format(String, Object...)} 拼接日志字符串
     *
     * @param pattern   the pattern string
     * @param arguments object(s) to format
     */
    public static void w_ft(String tag, String pattern, Object... arguments) {
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
     *
     * @param pattern   the pattern string
     * @param arguments object(s) to format
     */
    public static void a_f(String pattern, Object... arguments) {
        if (!isDebug) return;
        a(MessageFormat.format(pattern, arguments));
    }

    /**
     * 通过 {@link MessageFormat#format(String, Object...)} 拼接日志字符串
     *
     * @param pattern   the pattern string
     * @param arguments object(s) to format
     */
    public static void a_ft(String tag, String pattern, Object... arguments) {
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
     *
     * @param pattern   the pattern string
     * @param arguments object(s) to format
     */
    public static void i_f(String pattern, Object... arguments) {
        if (!isDebug) return;
        i(MessageFormat.format(pattern, arguments));
    }

    /**
     * 通过 {@link MessageFormat#format(String, Object...)} 拼接日志字符串
     *
     * @param pattern   the pattern string
     * @param arguments object(s) to format
     */
    public static void i_ft(String tag, String pattern, Object... arguments) {
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
     *
     * @param pattern   the pattern string
     * @param arguments object(s) to format
     */
    public static void e_f(String pattern, Object... arguments) {
        if (!isDebug) return;
        e(MessageFormat.format(pattern, arguments));
    }

    /**
     * 通过 {@link MessageFormat#format(String, Object...)} 拼接日志字符串
     *
     * @param pattern   the pattern string
     * @param arguments object(s) to format
     */
    public static void e_ft(String tag, String pattern, Object... arguments) {
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
            String errmsg = getExceptionStackTrace(throwable);
            if (Utils.isEmpty(msg)) println(ERROR, tag, errmsg);
            else println(ERROR, tag, MessageFormat.format("{0}\n{1}", msg, errmsg));
        } else println(ERROR, tag, msg);
    }


    /**
     * 获得异常的栈信息
     */
    public static String getExceptionStackTrace(Throwable tr) {
        return Log.getStackTraceString(tr);
    }

    /**
     * 获得在代码中调用LogUtils的代码所在位置
     */
    private final static StackTraceElement getStackTrace() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        //被排除的栈对象
        String[] excludes = new String[]{LogUtils.class.getName()};

        for (int index = 2; index < stackTrace.length; ++index) {
            String str = stackTrace[index].getClassName();

            boolean isExclude = false;
            for (String exclude : excludes) {
                if (str.equals(exclude)) {
                    isExclude = true;
                    break;
                }
            }

            if (isExclude) {
                continue;
            }
            return stackTrace[index];
        }
        return null;
    }

    private static void println(int priority, String msg) {
        if (!isDebug) return;
        println(priority, null, msg);
    }

    private static void println(int priority, String tag, String msg) {
        if (!isDebug) return;
        String mTag = TAG;
        if (!Utils.isEmpty(tag)) {
            mTag = MessageFormat.format("{0}-{1}", TAG, tag);
        }

        if (isLogStackEnable) {
            if (msg.contains("\n")) {
                int i = msg.indexOf("\n");
                String begin = msg.substring(0, i);
                String end = msg.substring(i);
                begin = getFormatStackInfo(mTag, begin);
                Log.println(priority, mTag, begin + end);
            } else {
                Log.println(priority, mTag, getFormatStackInfo(mTag, msg));
            }
        } else {
            Log.println(priority, mTag, msg);
        }
    }


    /**
     * 格式化一组相关的日志,提高他们的可读性
     *
     * @param logs
     */
    public static List<String> getLogLumpFormat(String... logs) {
        if (logs == null) {
            return new ArrayList<>();
        }
        ArrayList<String> log_array = new ArrayList<>();
        log_array.add("╔═══════════════════════════════════════════════════");
        for (int i = 0; i < logs.length; i++) {
            log_array.add("║ " + logs[i]);
        }
        log_array.add("╚═══════════════════════════════════════════════════");
        return log_array;
    }

    /**
     * 格式化一组相关的日志,提高他们的可读性
     *
     * @param logs
     */
    public static List<String> getLogLumpFormat(Iterable logs) {
        if (logs == null) {
            return new ArrayList<>();
        }
        ArrayList<String> log_array = new ArrayList<>();
        log_array.add("╔═══════════════════════════════════════════════════");
        Iterator iterator = logs.iterator();
        while (iterator.hasNext()) {
            log_array.add("║ " + iterator.next());
        }
        log_array.add("╚═══════════════════════════════════════════════════");
        return log_array;
    }


    /**
     * 格式化栈元素
     *
     * @param element
     * @return
     */
    private final static String formatStackTrace(StackTraceElement element) {
        if (element == null) {
            return "StackTraceElement is null!";
        }
//        return MessageFormat.format("{0}.{1}({2}:{3,number,0})", element.getClassName(), element.getMethodName(), element.getFileName(), element.getLineNumber());

        String className = element.getClassName();
        String fileName = element.getFileName();
        String methodName = element.getMethodName();
        int lineNumber = element.getLineNumber();
        if (fileName.length() > 7) {
            return MessageFormat.format("it.{0}({1}:{2,number,0})", methodName, fileName, lineNumber);
        } else {
            return MessageFormat.format("{3}.{0}({1}:{2,number,0})", methodName, fileName, lineNumber, className);
        }
    }

    private static Map<String, String> CacheRepeatedStr = new HashMap<>();

    //生产格式化的栈信息
    private static String getFormatStackInfo1(String tag, String msg) {
        return MessageFormat.format("{0}\t\t-{1}", msg, formatStackTrace(getStackTrace()));
    }

    private static String getFormatStackInfo(String tag, String msg) {
//        String stack = MessageFormat.format("<<-- at {0}", Utils.formatStackTrace(getStackTrace()));
        String stack = MessageFormat.format("-{0}", formatStackTrace(getStackTrace()));
        int width = (tag + msg).length();
        int size = width / 4;
        int count = 10 - size;
        if (count < 1) count = 1;

        String key = "\t" + count;
        String joinstr = CacheRepeatedStr.get(key);
        if (joinstr == null) {
            joinstr = Utils.createRepeatedStr("\t", count, "");
            CacheRepeatedStr.put(key, joinstr);
        }
        return msg + joinstr + stack;
    }

}