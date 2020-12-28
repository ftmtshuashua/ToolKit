package com.acap.toolkit.action;

/**
 * <pre>
 * Tip:
 *      适配Lambda语法的回调函数 - 5参
 *
 * Created by ACap on 2019/3/21 13:45
 * </pre>
 */
@FunctionalInterface
public interface Callback5<T1, T2, T3, T4, T5, R> {
    R call(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5);
}
