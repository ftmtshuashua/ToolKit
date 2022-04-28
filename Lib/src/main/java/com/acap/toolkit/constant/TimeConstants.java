package com.acap.toolkit.constant;

import androidx.annotation.LongDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <pre>
 * desc:
 *
 * function:
 *
 * Created by ACap on 2018/6/27.
 * </pre>
 */
public final class TimeConstants {

    /**
     * 毫秒
     */
    public static final long MSEC = 1;
    /**
     * 秒
     */
    public static final long SEC = 1000;
    /**
     * 分
     */
    public static final long MIN = 60000;
    /**
     * 时
     */
    public static final long HOUR = 3600000;
    /**
     * 天
     */
    public static final long DAY = 86400000;

    @LongDef({MSEC, SEC, MIN, HOUR, DAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }
}
