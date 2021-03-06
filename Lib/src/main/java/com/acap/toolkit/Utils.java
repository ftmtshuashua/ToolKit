package com.acap.toolkit;

import android.os.Build;
import android.os.Looper;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.collection.LongSparseArray;
import androidx.collection.SimpleArrayMap;

import com.acap.toolkit.action.Action1;
import com.acap.toolkit.transform.ArraysUtils;

import java.lang.reflect.Array;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;


/**
 * <pre>
 * Tip:
 *      常用工具方法集合
 * Function:
 *      isEmpty()       :判断是否为空或者空数据
 *      isNotEmpty()    :判断非空并且非空数据
 *      checkNotNull()  :检查对象是否为空
 *      equals()        :比较两个对象是否相等
 *      isMainThread()  :判断是否为主线程
 *
 * Created by ACap on 2018/5/30.
 * </pre>
 */
public class Utils {


    /**
     * Return whether object is empty.
     *
     * @param obj The object.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isEmpty(final Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
            return true;
        }
        if (obj instanceof CharSequence && obj.toString().length() == 0) {
            return true;
        }
        if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof Map && ((Map) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof SimpleArrayMap && ((SimpleArrayMap) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof SparseArray && ((SparseArray) obj).size() == 0) {
            return true;
        }
        if (obj instanceof SparseBooleanArray && ((SparseBooleanArray) obj).size() == 0) {
            return true;
        }
        if (obj instanceof SparseIntArray && ((SparseIntArray) obj).size() == 0) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (obj instanceof SparseLongArray && ((SparseLongArray) obj).size() == 0) {
                return true;
            }
        }
        if (obj instanceof LongSparseArray && ((LongSparseArray) obj).size() == 0) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (obj instanceof android.util.LongSparseArray
                    && ((android.util.LongSparseArray) obj).size() == 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmpty(final CharSequence obj) {
        return obj == null || obj.toString().length() == 0;
    }

    public static boolean isEmpty(final Collection obj) {
        return obj == null || obj.isEmpty();
    }

    public static boolean isEmpty(final Map obj) {
        return obj == null || obj.isEmpty();
    }

    public static boolean isEmpty(final SimpleArrayMap obj) {
        return obj == null || obj.isEmpty();
    }

    public static boolean isEmpty(final SparseArray obj) {
        return obj == null || obj.size() == 0;
    }

    public static boolean isEmpty(final SparseBooleanArray obj) {
        return obj == null || obj.size() == 0;
    }

    public static boolean isEmpty(final SparseIntArray obj) {
        return obj == null || obj.size() == 0;
    }

    public static boolean isEmpty(final LongSparseArray obj) {
        return obj == null || obj.size() == 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static boolean isEmpty(final SparseLongArray obj) {
        return obj == null || obj.size() == 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean isEmpty(final android.util.LongSparseArray obj) {
        return obj == null || obj.size() == 0;
    }

    /**
     * Return whether object is not empty.
     *
     * @param obj The object.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isNotEmpty(final Object obj) {
        return !isEmpty(obj);
    }

    public static boolean isNotEmpty(final CharSequence obj) {
        return !isEmpty(obj);
    }

    public static boolean isNotEmpty(final Collection obj) {
        return !isEmpty(obj);
    }

    public static boolean isNotEmpty(final Map obj) {
        return !isEmpty(obj);
    }

    public static boolean isNotEmpty(final SimpleArrayMap obj) {
        return !isEmpty(obj);
    }

    public static boolean isNotEmpty(final SparseArray obj) {
        return !isEmpty(obj);
    }

    public static boolean isNotEmpty(final SparseBooleanArray obj) {
        return !isEmpty(obj);
    }

    public static boolean isNotEmpty(final SparseIntArray obj) {
        return !isEmpty(obj);
    }

    public static boolean isNotEmpty(final LongSparseArray obj) {
        return !isEmpty(obj);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static boolean isNotEmpty(final SparseLongArray obj) {
        return !isEmpty(obj);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean isNotEmpty(final android.util.LongSparseArray obj) {
        return !isEmpty(obj);
    }

    /**
     * 判断整个字符串是否包为空白字符
     *
     * @param s 被检查的数据
     * @return true 空白字符串
     */
    public static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 如果这个对象为空这抛出 {@code NullPointerException},否则返回这个对象
     *
     * @param obj 被检测的对象
     * @param <T> object
     * @return 如果对象不为空则返回这个对象
     */
    public static <T> T requireNonNull(@Nullable final T obj) {
        if (obj == null) throw new NullPointerException();
        return obj;
    }

    /**
     * 如果这个对象为空这抛出 {@code NullPointerException},否则返回这个对象
     *
     * @param obj 被检测的对象
     * @param msg 对象为空时候显示的消息
     * @param <T> object
     * @return 如果对象不为空则返回这个对象
     */
    public static <T> T requireNonNull(@Nullable final T obj, String msg) {
        if (obj == null) throw new NullPointerException(msg);
        return obj;
    }

    /**
     * Return whether object1 is equals to object2.
     *
     * @param o1 The first object.
     * @param o2 The second object.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean equals(final Object o1, final Object o2) {
        return o1 == o2 || (o1 != null && o1.equals(o2));
    }

    /**
     * @return 如果当前线程是主线程则返回true
     */
    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }


    /**
     * 获得不为空的数据
     *
     * @param obj The data
     * @return NotNull data
     */
    public static CharSequence getNotNull(CharSequence obj) {
        if (obj == null) return "";
        return obj;
    }

    /**
     * 获得不为空的数据
     *
     * @param obj The data
     * @return NotNull data
     */
    public static String getNotNull(String obj) {
        if (obj == null) return "";
        return obj;
    }

    /**
     * 遍历集合
     *
     * @param collection 被遍历的集合
     * @param action     回调函数
     * @param <T>        集合数据类型
     */
    public static <T> void map(Collection<T> collection, Action1<? super T> action) {
        requireNonNull(collection);
        requireNonNull(action);
        Iterator<T> iterator = collection.iterator();
        while (iterator.hasNext()) {
            T module = iterator.next();
            action.call(module);
        }
    }


    /**
     * 获得Object的唯一标识
     */
    public static final String getObjectId(Object object) {
        if (object == null) return "Null_Obj";
        return Integer.toHexString(System.identityHashCode(object));
    }

    /**
     * 获得在代码中调用LogUtils的代码所在位置
     */
    public final static StackTraceElement[] getStackTrace() {
        return Thread.currentThread().getStackTrace();
    }


    /**
     * 重复字符串多次
     *
     * @param seed      被重复的字符串
     * @param count     重复的次数
     * @param delimiter 分隔符
     * @return 例：重复 “X” 3次 = "XXX"
     */
    public static final String createRepeatedStr(String seed, int count, String delimiter) {
        return ArraysUtils.join(Collections.nCopies(count, seed), delimiter);

    }
}
