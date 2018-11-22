package lfp.support.toolkit.action;

/**
 * <pre>
 * Tip:
 *      只有一个参数的回调
 *
 * Created by LiFuPing on 2018/10/29 14:36
 * </pre>
 */
@FunctionalInterface
public interface Action4<A, B, C, D> {
    void call(A a, B b, C c, D d);
}
