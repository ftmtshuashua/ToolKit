package com.acap.toolkit.action;

/**
 * <pre>
 * Tip:
 *      适配Lambda语法的接口 - 2参
 *
 * Created by ACap on 2018/10/29 14:36
 * </pre>
 */
@FunctionalInterface
public interface Action2<T1,T2> {
    void call(T1 t1, T2 t2);
}
