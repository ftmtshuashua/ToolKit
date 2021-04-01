package com.acap.toolkit.action;

/**
 * <pre>
 * Tip:
 *      适配Lambda语法的接口 - 1参
 *
 * Created by ACap on 2018/10/29 14:36
 * </pre>
 */
@FunctionalInterface
public interface Action1<T> {
    void call(T t);
}
