package com.acap.toolkit.action;

/**
 * <pre>
 * Tip:
 *      适配Lambda语法的接口 - 8参
 *
 * Created by ACap on 2018/10/29 14:36
 * </pre>
 */
@FunctionalInterface
public interface Action8<T1, T2, T3, T4, T5, T6,T7,T8> {
    void call(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7,T8 t8);
}
