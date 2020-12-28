package com.acap.toolkit.action;

/**
 * <pre>
 * Tip:
 *      适配Lambda语法的回调函数 - 2参
 *
 * Created by ACap on 2019/3/21 13:45
 * </pre>
 */
@FunctionalInterface
public interface Callback2<T1, T2, R> {
    R call(T1 t1, T2 t2);
}
