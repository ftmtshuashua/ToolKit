package com.acap.toolkit.action;

/**
 * <pre>
 * Tip:
 *      适配Lambda语法的回调函数 - 1参
 *
 * Created by ACap on 2019/3/21 13:45
 * </pre>
 */
@FunctionalInterface
public interface Callback1<T,R> {
    R call(T t);
}
